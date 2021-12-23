package it.perigea.formazione.aggregator;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableMongoRepositories(basePackages = "it.perigea.formazione.aggregator.repository")
@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(Application.class, args);
		//KafkaService consumer = context.getBean(KafkaService.class);
	}
}
