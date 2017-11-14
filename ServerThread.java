package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ServerThread extends Thread {
	private Queue q;
	private static final int max_file_size = 500;
	private HashMap<String, Semaphore> mutexHM;
	
	public ServerThread(Queue q, HashMap<String, Semaphore> mutexHM) {
		this.q = q;
		this.mutexHM = mutexHM;
		
	}
	
	public void run() {
		Message msg;
		
		//TODO : the problem is that when there are no more messages in the queue
		// the thread which tries to get one will be put on hold indefinitely
		while ((msg = q.remove()) != null) {
			//msg = q.remove();
			if (msg != null) {
				String msgType = msg.getType();
				
				try{
					mutexHM.get(msgType).acquire();
					
					System.out.println(this.getName() + ": aquired the " + msgType + " file at :" + new SimpleDateFormat("dd - HH.mm.ss").format(new Date()) + " \n" );
					writeInFile(msg);
					System.out.println(this.getName() + ": wrote in the " + msgType + " at : " + new SimpleDateFormat("dd - HH.mm.ss").format(new Date()) + " \n");
				} catch (InterruptedException e) {
					System.err.println(this.getName() + ": could not acquire resource!"  );
					e.printStackTrace();
				}finally{
					mutexHM.get(msgType).release();
				}
				
			}
		}
	}

	private void writeInFile(Message msg) {
		String fileName = msg.getType();
	
			File file = new File(fileName);

			// If the file exceeds the limit rename the existing file and create
			// a new empty one.
			if (file.length() > max_file_size) {
				String time = new SimpleDateFormat("dd - HH.mm.ss").format(new Date());
				String tempFileName =  fileName + " "   + time;
				File newFile = new File(tempFileName);
				
				if (!file.renameTo(newFile)) {
					System.err.println("Rename op was not succesful!");
				};
				
			 
				//make an empty file for the message
				file = new File(fileName);
				
			}

			try {
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter fileWriter = new BufferedWriter(fw);
				fileWriter.write(msg.toString() + "\n");
				//System.out.println(msg.toString());
				fileWriter.close();
			} catch (Exception e) {
				System.out.println("Error writing in file");
			} 
		
	}

}
