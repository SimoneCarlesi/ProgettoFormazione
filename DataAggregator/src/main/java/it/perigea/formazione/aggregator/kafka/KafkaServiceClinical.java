package it.perigea.formazione.aggregator.kafka;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mongodb.MongoInterruptedException;

import it.perigea.formazione.aggregator.controller.Controller;
import it.perigea.formazione.aggregator.entity.ClinicalStatusEntity;
import it.perigea.formazione.aggregator.mongodb.MongoDB;
import it.perigea.formazione.aggregator.thread.ControlledRunnable;
import it.perigea.formazione.comune.ClinicalStatusDto;

@Service
public class KafkaServiceClinical implements ControlledRunnable{
	

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Value(value = "${spring.kafka.consumer.group-id}")
	private String groupId;
	
	@Value("${ClinicalTopicName}")
	private String ClinicalTopicName;
	
	@Autowired
	private KafkaConsumer<String, ClinicalStatusDto> kafkaConsumer;
	
	@Autowired
	private MongoDB mongo;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	private boolean closed = false;
	
	@Override
	public void run() throws WakeupException {
		closed=false;
		try {
			LOGGER.info("Il nome del thread è: " + Thread.currentThread().getName());
			ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
			String dataString = (zonedDateTimeNow.getDayOfMonth() + "/" + zonedDateTimeNow.getMonthValue() + "/"
					+ zonedDateTimeNow.getYear());
			mongo.deleteClinicalData(dataString);
			while(closed==false) {
				consumeClinicalMessages(ClinicalTopicName);
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

	//	metodo per la lettura dei messaggi relativi al topic degli stati clinici
	public void consumeClinicalMessages(String ClinicalTopicName) throws InterruptedException {
		int i = 0;
		int recordCount = 0;
		List<ClinicalStatusDto> messagesfromKafka = new ArrayList<>();
		kafkaConsumer.subscribe(Arrays.asList(ClinicalTopicName));
		TopicPartition topicPartition = new TopicPartition(ClinicalTopicName, 0);
		List<TopicPartition> list = new ArrayList<>();
		list.add(topicPartition);
		LOGGER.info("Subscribed to topic " + kafkaConsumer.listTopics());
		Date start = new Date(); 
		try {
			while (true) {
				ConsumerRecords<String, ClinicalStatusDto> records = kafkaConsumer.poll(1000);
				recordCount = records.count();
				LOGGER.info("recordCount " + recordCount);
				for (ConsumerRecord<String, ClinicalStatusDto> record : records) {
					ClinicalStatusDto dto = new ClinicalStatusDto();
					dto = record.value();
					messagesfromKafka.add(dto);
				}
				Date now = new Date();
				if (messagesfromKafka.size() >= 500 || now.getTime() - start.getTime() > 90000000 ) {
					List<ClinicalStatusEntity> listEntity=mongo.fromClinicalListDtoToClinicalListEntity(messagesfromKafka);
					mongo.insertClinicalListEntityToMongoDB(listEntity);
					start = now;
				}
				messagesfromKafka.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
