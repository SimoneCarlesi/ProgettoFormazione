package it.perigea.formazione.aggregator.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoInterruptedException;

import it.perigea.formazione.aggregator.controller.ThreadController;

@Service
public class HandleThreadMap {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadController.class);
	
	private static final Map<String,Manager> mapManager=new ConcurrentHashMap<>();
	
	@Autowired
	private ExecutorService cached; 
	
	public HandleThreadMap() {
	}
	
	public synchronized void addManagersToMap(String key,Manager objectManager) throws InterruptedException  {
		if(key != null 	|| !(mapManager.containsKey(key))) {
			mapManager.put(key, objectManager);
			LOGGER.info("ObjectManager aggiunto alla mappa: " + Thread.currentThread().getName());
			objectManager.startThread(cached);
		}
	}
	
	
	public synchronized void removeManagersFromMap(String key) throws InterruptedException,MongoInterruptedException {
		try {
			if(mapManager.containsKey(key)) {
				mapManager.get(key).stopThread();
				mapManager.remove(key);
				if(!(mapManager.containsKey(key))) {
					LOGGER.info("Object rimosso dalla mappa");
				}
			}
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}
	}
}
