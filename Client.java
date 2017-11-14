package src;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client {
	private Queue q;
	private int maxThreads;
	private ExecutorService executor;
	private static final int MESSAGES_TO_BE_GENERATED = 1;

	public Client(Queue q, int maxThreads) {
		this.q = q;
		this.maxThreads = maxThreads;
		executor = Executors.newFixedThreadPool(maxThreads);
	}

	// Start a new thread for each client.
	public void start() {
		for (int i = 0; i < maxThreads; i++) {
			ClientThread thread = new ClientThread(q, "client" + i, MESSAGES_TO_BE_GENERATED);
			executor.execute(thread);
		}
	}

	public void shutdown() {
		executor.shutdown();
//		try {
//            boolean didntTerminate = executor.awaitTermination(25, TimeUnit.SECONDS);
//            if (didntTerminate){
//            	executor.shutdownNow();
//            }
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
