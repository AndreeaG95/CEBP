import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	private Queue q;
	private int maxThreads;
	private ExecutorService executor;

	public Client(Queue q, int maxThreads) {
		this.q = q;
		this.maxThreads = maxThreads;
		executor = Executors.newFixedThreadPool(maxThreads);
	}

	// Start a new thread for each client.
	public void start() {
		for (int i = 0; i < maxThreads; i++) {
			ClientThread thread = new ClientThread(q, "client" + i);
			executor.execute(thread);
		}
	}

	public void shutdown() {
		executor.shutdown();
	}

}
