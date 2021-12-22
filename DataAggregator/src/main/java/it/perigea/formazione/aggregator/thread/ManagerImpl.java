package it.perigea.formazione.aggregator.thread;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManagerImpl implements Manager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ManagerImpl.class);
	

	private Thread thread;
	private ControlledRunnable controlledRunnable;
	
	public ManagerImpl(ControlledRunnable controlledRunnable) {
		this.controlledRunnable = controlledRunnable;
	}
	
	// Metodo per stoppare il thread
	@Override
	public void stopThread() throws InterruptedException {
		controlledRunnable.stop();
		this.thread.interrupt();
	}

	// Metodo per avviare il thread
	@Override
	public void startThread(ExecutorService cached) throws InterruptedException {
		cached.execute(() -> {
			this.thread=Thread.currentThread();
			this.controlledRunnable.run();
			});
	}
}
