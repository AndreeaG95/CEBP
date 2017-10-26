package root;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LogClient {
	
	public static void main(String[] args) {
		try {
			Socket client = new Socket("192.168.0.170", 5000);
			DataInputStream din = new DataInputStream(client.getInputStream());
			DataOutputStream   dout = new DataOutputStream(client.getOutputStream());
			
			dout.write("Hi nigga".getBytes("UTF8"));
			dout.flush();
			
	        } catch (IOException e) {
	            System.err.println("Couldn't create socket channel in FileSender");
	            e.printStackTrace();
	        }
	}
	
}
