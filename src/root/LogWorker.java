package root;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class LogWorker implements Runnable {
	private Socket client;
	private DataInputStream din;
	
	public LogWorker(Socket client) {
		this.client = client;
		try {
			din = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		String message;
		try {
			message = din.readUTF();
			System.out.println("ServerSide: " + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
