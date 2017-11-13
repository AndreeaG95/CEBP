import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/* The Server is responsible for writing the logging messages received in the corresponding files. 
 * 
 */
public class Server {

	private Queue q;
	private ExecutorService executor;
	private Semaphore mutex;
	
	public Server(Queue q, int maxThreads){
		this.q = q;
		mutex = new Semaphore(1);
		executor = Executors.newFixedThreadPool(maxThreads);
	}
	
	// Start starts as many threads as clients for writing in the files.
	public void start(int threadCount){
		for(int i=0; i < threadCount; i++){
			ServerThread thread = new ServerThread(q, mutex);
			executor.execute(thread);
		}
	}
	
	public void shutdown(){		
		executor.shutdown();
	}
}
