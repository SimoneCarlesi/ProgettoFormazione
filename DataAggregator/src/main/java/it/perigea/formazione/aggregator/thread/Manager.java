package it.perigea.formazione.aggregator.thread;

import java.util.concurrent.ExecutorService;

public interface Manager {
	
	public void startThread(ExecutorService cached) throws InterruptedException;
	
	public void stopThread() throws InterruptedException;
	
}
