package node.configuraiton;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import node.model.SomministrationsDto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@EnableKafka
@Configuration
public class KafkaConsumerConfig{
	
	
	@Autowired
    private KafkaProperties kafkaProperties;
	
	
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
	
	@Autowired
	private KafkaTemplate<String, SomministrationsDto> kafkaTemplate;
	
	@Value("${topicName}")
	private String topicName; 
	
	@Value("${spring.kafka.consumer.group-id}")
	private String groupId;
	
	public KafkaConsumerConfig(
            final KafkaTemplate<String, SomministrationsDto> kafkaTemplate,
            @Value("${topicName}") final String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
//        this.messagesPerRequest = messagesPerRequest;
//        @Value("${tpd.messages-per-request}") final int messagesPerRequest
    }
	
	@Bean
  public Map<String, Object> consumerConfigs() {
      Map<String, Object> props = new HashMap<>(
              kafkaProperties.buildConsumerProperties()
      );
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
              StringDeserializer.class);
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
              JsonDeserializer.class);
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
      props.put(ConsumerConfig.GROUP_ID_CONFIG,
    		  groupId);

      return props;
  }
    
	 @Bean
	    public ConsumerFactory<String, Object> consumerFactory() {
	        final JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();
	        jsonDeserializer.addTrustedPackages("*");
	        return new DefaultKafkaConsumerFactory<>(
	                kafkaProperties.buildConsumerProperties(), new StringDeserializer(), jsonDeserializer
	        );
	    }

	    @Bean
	    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
	                new ConcurrentKafkaListenerContainerFactory<>();
	        factory.setConsumerFactory(consumerFactory());

	        return factory;
	    }

//	    // String Consumer Configuration
//
//	    @Bean
//	    public ConsumerFactory<String, String> stringConsumerFactory() {
//	        return new DefaultKafkaConsumerFactory<>(
//	                kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new StringDeserializer()
//	        );
//	    }
//
//	    @Bean
//	    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerStringContainerFactory() {
//	        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//	                new ConcurrentKafkaListenerContainerFactory<>();
//	        factory.setConsumerFactory(stringConsumerFactory());
//
//	        return factory;
//	    }
//
//	    // Byte Array Consumer Configuration
//
//	    @Bean
//	    public ConsumerFactory<String, byte[]> byteArrayConsumerFactory() {
//	        return new DefaultKafkaConsumerFactory<>(
//	                kafkaProperties.buildConsumerProperties(), new StringDeserializer(), new ByteArrayDeserializer()
//	        );
//	    }
//
//	    @Bean
//	    public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerByteArrayContainerFactory() {
//	        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
//	                new ConcurrentKafkaListenerContainerFactory<>();
//	        factory.setConsumerFactory(byteArrayConsumerFactory());
//	        return factory;
//	    }
}
