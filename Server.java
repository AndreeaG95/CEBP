package src;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* The Server is responsible for writing the logging messages received in the corresponding files. 
 * 
 */
public class Server {

	private Queue q;
	private ExecutorService executor;
	private HashMap<String, Semaphore> mutexHM;
	
	public Server(Queue q, int maxThreads){
		this.q = q;
		
		mutexHM = new HashMap<>();
		mutexHM.put("CRITICAL", new Semaphore(1));
		mutexHM.put("ERROR", new Semaphore(1));
		mutexHM.put("INFO", new Semaphore(1));
		mutexHM.put("DEBUG", new Semaphore(1));
		mutexHM.put("WARNING", new Semaphore(1));
		
		executor = Executors.newFixedThreadPool(maxThreads);
	}
	
	// Start starts as many threads as clients for writing in the files.
	public void start(int threadCount){
		for(int i=0; i < threadCount; i++){
			ServerThread thread = new ServerThread(q, mutexHM);
			executor.execute(thread);
		
		}
	}
	
	public void shutdown(){		
		
		try {
			executor.shutdown();
            boolean didntTerminate = !executor.awaitTermination(10, TimeUnit.SECONDS);
            if (didntTerminate){
            	executor.shutdownNow();
            }
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
