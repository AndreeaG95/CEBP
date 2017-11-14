
public class ClientThread extends Thread{

	private Queue q;
	private String name;
	
	public ClientThread(Queue q, String name){
		this.q = q;
		this.name = name;
	}
	
	public void run() {
		int rand;

		while (true) {
			rand = (int) (Math.random() * 10);
			switch (rand) {
			case 0: // DEBUG
			case 1:
				q.add(new Message("DEBUG", this.name));
				break;
			case 2: // INFO
			case 3:
				q.add(new Message("INFO", this.name));
				break;
			case 4: // WARNING
			case 5:
				q.add(new Message("WARNING", this.name));
				break;
			case 6: // ERROR
			case 7:
				q.add(new Message("ERROR", this.name));
				break;
			case 8: // CRITICAL
			case 9:
				q.add(new Message("CRITICAL", this.name));
				break;
			}

			try {
				Thread.sleep(700);
			} catch (Exception e) {
				throw new RuntimeException(this.name);
			}

		}
	}
}
