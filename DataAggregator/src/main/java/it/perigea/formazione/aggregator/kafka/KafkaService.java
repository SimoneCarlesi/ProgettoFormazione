package it.perigea.formazione.aggregator.kafka;

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
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import it.perigea.formazione.aggregator.configuration.KafkaConsumerConfig;
import it.perigea.formazione.aggregator.controller.Controller;
import it.perigea.formazione.aggregator.entity.SomministrationsEntity;
import it.perigea.formazione.aggregator.mongodb.MongoDB;
import it.perigea.formazione.aggregator.repository.SomministrationsRepository;
import it.perigea.formazione.comune.SomministrationsDto;

@Service
public class KafkaService {

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Value(value = "${spring.kafka.consumer.group-id}")
	private String groupId;


	@Autowired
	private KafkaConsumerConfig kafkaConsumer;
	
	@Autowired
	private MongoDB mongo;
	

//	@Value(value = "${spring.kafka.consumer.properties.spring.json.trusted.packages=}")
//	private String jsontrustedpackages;
//	
//	@Value(value = "${spring.kafka.producer.value-serializer}")
//	private String valueSerializer;
//	
//	@Value(value = "${spring.kafka.producer.properties.spring.json.add.type.headers}")
//	private String addtypeheaders;

//	 @Value(value = "${topicName}")
//	    private String topicName;
	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

	
	public void consumeMessages(String topicName) {
		KafkaConsumer<String, SomministrationsDto> kafkaConsumer = null;
		int i = 0;
		int recordCount = 0;
		List<SomministrationsDto> messagesfromKafka = new ArrayList<>();
		Properties props = new Properties();
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

		kafkaConsumer = new KafkaConsumer<>(props);
		kafkaConsumer.subscribe(Arrays.asList(topicName));
		TopicPartition topicPartition = new TopicPartition(topicName, 0);
		List<TopicPartition> list = new ArrayList<>();
		list.add(topicPartition);
		LOGGER.info("Subscribed to topic " + kafkaConsumer.listTopics());
		// @ TODO definire qua la data d'inizio (x)
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
				// @ TODO definire una data "now" (y)
				Date now = new Date();
				// @ TODO se y-x > tot minuti, allora carico a prescindere dal numero di
				// messaggi (almeno 1)
				// @ TODO dopo il primo salvataggio, x=y o x=now e y continua a cambiare
				if (messagesfromKafka.size() >= 500 || now.getTime() - start.getTime() > 900000 ) {
					// @ TODO funzione per caricare a database e valutarlo con
					List<SomministrationsEntity> listEntity=mongo.fromListDtoToListEntity(messagesfromKafka);
					mongo.insertListEntityToMongoDB(listEntity);
					start = now;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { 
			kafkaConsumer.close();
		}
	}

	// earlylist
}