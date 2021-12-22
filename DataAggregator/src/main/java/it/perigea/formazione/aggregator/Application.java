package it.perigea.formazione.aggregator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

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
import it.perigea.formazione.aggregator.mongodb.MongoDB;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableMongoRepositories(basePackages = "it.perigea.formazione.aggregator.repository")
@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(Application.class, args);
		KafkaService consumer = context.getBean(KafkaService.class);
		MongoDB mongo=context.getBean(MongoDB.class);
		String topicName="somministrazioni-anticovid19-lombardia";
		ZonedDateTime zonedDateTimeNow = ZonedDateTime.now(ZoneId.of("UTC"));
		String dataString = (zonedDateTimeNow.getDayOfMonth() + "/" + zonedDateTimeNow.getMonthValue() + "/"
				+ zonedDateTimeNow.getYear());	
//		HandleThread ht1=new HandleThread();
//		HandleThread ht2=new HandleThread();
//		Thread t1=new Thread(ht1);
//		Thread t2=new Thread(ht2);
//		t1.start();
//		t2.start();
	}
}
