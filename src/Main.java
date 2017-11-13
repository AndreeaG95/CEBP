
public class Main {

	// Configuration parameters.
	private static final int maxThreads = 8;
	private static final int maxQueue = 15;
	private static Queue q;	 
	
	private static Server server;
	private static Client client;
	
	public static void main(String []args){
		q = new Queue(maxQueue);
		
		// Start server.
		server = new Server(q, maxThreads);
		server.start(maxThreads);
	
		// Start clients.
		client = new Client(q, maxThreads);
		client.start();
		
		//client.shutdown();
		//server.shutdown();
	}

}
