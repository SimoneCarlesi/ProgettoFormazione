package it.perigea.formazione.aggregator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.perigea.formazione.aggregator.kafka.KafkaService;
import it.perigea.formazione.aggregator.thread.HandleThreadMap;
import it.perigea.formazione.aggregator.thread.ManagerImpl;

@RestController
@RequestMapping("/Thread")
public class ThreadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadController.class);
	
	@Autowired
	private HandleThreadMap handleThreadMap;
	
	@Autowired
	private KafkaService kafkaService;

	// Metodo per lanciare nuovi thread
	@GetMapping(value = "/startThread")
	public void startThread(@RequestParam String key) throws InterruptedException {
		ManagerImpl manager=new ManagerImpl(kafkaService);
		handleThreadMap.addManagersToMap(key,manager);
	}

	// Metodo per lanciare nuovi thread
	//@ TODO dare la key per gestire lo stop thread
	@GetMapping(value = "/stopThread")
	public void stopThread(@RequestParam String key) throws InterruptedException {
		handleThreadMap.removeManagersFromMap(key);
	}
	
}
