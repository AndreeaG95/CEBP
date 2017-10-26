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
			int size;
			try {
				String s;
				size = din.available();
				byte[] buf = new byte[size];
					
					while (din.read(buf) != -1){
						//System.out.println(String.valueOf(buf).toString());
						s = new String(buf);
						System.out.print(s);
						
					}
					
					//System.out.println(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
}
