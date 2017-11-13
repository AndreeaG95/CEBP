import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Semaphore;


public class ServerThread extends Thread{
	
	private Queue q;
	private static final int max_file_size = 500;
	private Semaphore mutex;
	private Calendar cal;
	private static DateFormat dateFormat;
	
	public ServerThread(Queue q,Semaphore mutex){
		this.q = q;
		this.mutex = mutex;
		cal = Calendar.getInstance();
		dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
	}
	
	public void run(){
		while (true) {
			Message msg = q.remove();
			if (msg != null) {
				writeInFile(msg);
			}
		}
	}
	
	private void writeInFile(Message msg){
		String fileName = msg.getType();
		try{
			mutex.acquire();
			File file = new File(fileName);
			
			// If the file exceeds the limit rename the existing file and create a new empty one.
			if(file.length() > max_file_size){
				File newFileName = new File(file.getName() + (int)(Math.random() * 10));
				file.renameTo(newFileName);
				System.out.println(newFileName.getName() + " " + file.getName() + " " + file.length());
				
				file = new File(fileName);
			}
			
			try {
				FileWriter fw = new FileWriter(file, true);
				BufferedWriter fileWriter = new BufferedWriter(fw);
				fileWriter.write(msg.toString());
			//	System.out.println(msg.toString());
				fileWriter.close();
			}catch(Exception e){
				System.out.println("Error writing in file");
			}finally{
				mutex.release();
			}
		}catch(InterruptedException ie) {
		    // Handle acquire failure here.
			ie.printStackTrace();
		}
	}

}
