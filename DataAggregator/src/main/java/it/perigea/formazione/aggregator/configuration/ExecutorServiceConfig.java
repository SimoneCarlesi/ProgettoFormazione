package it.perigea.formazione.aggregator.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration

public class ExecutorServiceConfig {
	
	@Bean("fixed")
	public ExecutorService fixedThreadPool() {
		return Executors.newFixedThreadPool(5);
	}

	
	@Bean("single")
	public ExecutorService singleThreadedExecutor() {
		return Executors.newSingleThreadExecutor();
	}
	
	@Primary
	@Bean("cached")
	public ExecutorService cachedThreadPool() {
		return Executors.newCachedThreadPool();
	}
	
	@Bean("worksteel")
	public ExecutorService worksteelThreadPool() {
		return Executors.newWorkStealingPool();
	}
	
	

}
