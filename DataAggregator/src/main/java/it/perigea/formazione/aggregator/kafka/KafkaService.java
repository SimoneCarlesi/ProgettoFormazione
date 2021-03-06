package it.perigea.formazione.aggregator.kafka;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import com.mongodb.MongoInterruptedException;

import it.perigea.formazione.aggregator.controller.Controller;
import it.perigea.formazione.aggregator.entity.ClinicalStatusEntity;
import it.perigea.formazione.aggregator.entity.SomministrationsEntity;
import it.perigea.formazione.aggregator.mongodb.MongoDB;
import it.perigea.formazione.aggregator.thread.ControlledRunnable;
import it.perigea.formazione.comune.ClinicalStatusDto;
import it.perigea.formazione.comune.SomministrationsDto;

@Service
public class KafkaService implements ControlledRunnable{

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Value(value = "${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@Autowired
	private MongoDB mongo;

	@Value("${topicName}")
	private String topicName;
	

	@Autowired
	private KafkaConsumer<String, SomministrationsDto> kafkaConsumer;


	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	private boolean closed = false;

	@Override
	public void run() throws WakeupException {
		closed=false;
		try {
			LOGGER.info("Il nome del thread ??: " + Thread.currentThread().getName());
			ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
			String dataString = (zonedDateTimeNow.getDayOfMonth() + "/" + zonedDateTimeNow.getMonthValue() + "/"
					+ zonedDateTimeNow.getYear());
			mongo.dataController(dataString);
			while(closed==false) {
				consumeMessages(topicName);
			}
		} catch (InterruptedException e) {
			LOGGER.warn("Catch Interrupted: " + e.getMessage());
		} catch (MongoInterruptedException e) {
			LOGGER.warn("Catch Mongo: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.warn("Catch Generic: " + e.getMessage());
		}
	}

	public void stop() {
		closed=true;
	}

	public void consumeMessages(String topicName) throws InterruptedException {
		int i = 0;
		int recordCount = 0;
		List<SomministrationsDto> messagesfromKafka = new ArrayList<>();
		kafkaConsumer.subscribe(Arrays.asList(topicName));
		TopicPartition topicPartition = new TopicPartition(topicName, 0);
		List<TopicPartition> list = new ArrayList<>();
		list.add(topicPartition);
		LOGGER.info("Subscribed to topic " + kafkaConsumer.listTopics());
		Date start = new Date();
		try {
			while (true) {
				ConsumerRecords<String, SomministrationsDto> records = kafkaConsumer.poll(1000);
				recordCount = records.count();
				LOGGER.info("recordCount " + recordCount);
				for (ConsumerRecord<String, SomministrationsDto> record : records) {
					SomministrationsDto dto = new SomministrationsDto();
					dto = record.value();
					messagesfromKafka.add(dto);
				}
				Date now = new Date();
				if (messagesfromKafka.size() >= 500 || now.getTime() - start.getTime() > 90000000) {
					List<SomministrationsEntity> listEntity = mongo.fromListDtoToListEntity(messagesfromKafka);
					mongo.insertListEntityToMongoDB(listEntity);
					start = now;
				}
				messagesfromKafka.clear();
			}
		}
		catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
	}
	
}