package root;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args){
		
		if (args.length != 2){
            System.err.println("Usage: java Server <host name> <port number>");
            System.exit(1);
        }
		
		String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber, 100, InetAddress.getByName(hostName));

                while (true){
                    System.out.println("Server listening on: " + hostName + ", port:" +portNumber);

                    Socket client = serverSocket.accept();
                    new Thread(new LogWorker(client)).start();

                }


        } catch (IOException e) {
            System.err.println("Don't know the host:" +hostName );
            e.printStackTrace();
        }
		
	}
}
