package it.perigea.formazione.aggregator.controller;

import java.util.List;

import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.perigea.formazione.aggregator.configuration.MongoConfig;
import it.perigea.formazione.aggregator.kafka.KafkaService;
import it.perigea.formazione.aggregator.mongodb.MongoDB;
import it.perigea.formazione.comune.SomministrationsDto;

@RestController
@RequestMapping("/Vaccini")
public class Controller {

	private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);
	
	private static final Logger logger =LoggerFactory.getLogger(Controller.class);
	
	@Value("${topicName}")
	private String topicName;
	
	@Autowired
	private KafkaService kafkaService;
	
	@PostMapping(value = "/consumer")
	@ResponseBody
	public void fromTopicToJava() throws Exception {
		kafkaService.consumeMessages(topicName);
	}
	
	
//	@PostMapping(value = "/deleteMessagesFromTopic")
//	@ResponseBody
//	public  void deleteMessagesFromTopic() throws Exception {
//		TopicPartition topicPartition = new TopicPartition(topicName, 0);
//		kafkaService.deleteRecords(topicPartition,4000);
//	}
	
}
