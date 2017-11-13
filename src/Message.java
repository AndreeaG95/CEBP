import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* The Message class defines the structure of a message that in transmitted between the client 
 * and the logging server. The message   
 */
public class Message {
	private String type;
	private String source;
	
	public Message(String type, String source){
		this.type = type;
		this.source = source;
	}
	
	public String getType(){
		return type;
	}
	
	public String toString(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		return this.source + " " + dateFormat.format(cal.getTime()) + " " + this.type + "\n";
	}

}
