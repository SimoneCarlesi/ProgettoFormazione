package it.perigea.formazione.aggregator.configuration;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import it.perigea.formazione.comune.SomministrationsDto;



@EnableKafka
@Configuration
public class KafkaConsumerConfig{
	
	
	@Autowired
    private KafkaProperties kafkaProperties;
	
	
	@Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;
	
	@Autowired
	private KafkaTemplate<String, SomministrationsDto> kafkaTemplate;
	
	@Value("${topicName}")
	private String topicName; 
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	private final org.apache.logging.log4j.Logger logger = LogManager.getLogger(KafkaConsumerConfig.class);
	
	private KafkaConsumer<String, SomministrationsDto> consumer;
	
	
	@Bean
	public Properties consumerConfigs() {
		Properties props = new Properties();
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return props;
	}

	@Bean
	public KafkaConsumer<String, SomministrationsDto> kafkaConsumer() {
		return new KafkaConsumer<>(consumerConfigs());
	}
}
