package it.perigea.formazione.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import it.perigea.formazione.aggregator.configuration.KafkaConsumerConfig;
import it.perigea.formazione.aggregator.kafka.KafkaService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableMongoRepositories(basePackages = "it.perigea.formazione.aggregator.repository")
@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(Application.class, args);
		KafkaService consumer = context.getBean(KafkaService.class);
		String topicName="somministrazioni-anticovid19-lombardia";
		try {
			consumer.consumeMessages(topicName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
