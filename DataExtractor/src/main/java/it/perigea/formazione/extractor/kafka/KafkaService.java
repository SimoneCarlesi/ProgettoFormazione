package it.perigea.formazione.extractor.kafka;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import it.perigea.formazione.comune.SomministrationsDto;


@Service
public class KafkaService{
	
	@Value("${topicName}")
	private String topicName;
	
	@Autowired
	private KafkaTemplate<String, SomministrationsDto> kafkaTemplate;
	
	public void sendMessage(String topic, List<SomministrationsDto> somministrations) {
        
//		for(SomministrationDTO somm: somministrations) {
//			kafkaTemplate.send(topicName,somm);
//		}
		
		somministrations.forEach(e -> kafkaTemplate.send(topicName,e));	
		
//	    ListenableFuture<SendResult<String, List<SomministrationDTO>>> future = 
//	      kafkaTemplate.send(topicName, somministrations);
//		
//	    future.addCallback(new ListenableFutureCallback<SendResult<String, List<SomministrationDTO>>>() {
//
//	        @Override
//	        public void onSuccess(SendResult<String, List<SomministrationDTO>> result) {
//	            System.out.println("Sent message=[" + somministrations + 
//	              "] with offset=[" + result.getRecordMetadata().offset() + "]");
//	        }
//	        @Override
//	        public void onFailure(Throwable ex) {
//	            System.out.println("Unable to send message=[" 
//	              + somministrations + "] due to : " + ex.getMessage());
//	        }
//	    });
	}
	
}