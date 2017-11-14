package src;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/* This is used to stored the messages from clients. */

public class Queue {
	private int size;
	private ArrayList<Message> messages;
	
	private Semaphore addSemaphore, rmSemaphore; // fill and empty count.
	private Semaphore mutex; 
	
	public Queue(int size){
		if (size < 0){
			throw new IllegalArgumentException("The queue size should always be positive!\n");
		}
		
		this.size = size;
		this.messages = new ArrayList<Message>(size);
		this.addSemaphore = new Semaphore(this.size); // This will be decremented every time we add something in the queue.
		this.rmSemaphore = new Semaphore(0); // This will be incremented every time we put something in queue.
		this.mutex = new Semaphore(1); // The mutex will prevent adding and removing elements from the queue at the same time.
	}
	
	public void add(Message msg){
		try {
		    this.addSemaphore.acquire();
		    this.mutex.acquire();
		    try {
		    	messages.add(msg);
		    	
		    	
		        this.rmSemaphore.release();
		    } finally {
		        this.mutex.release();
		    }
		} catch(final InterruptedException ie) {
		    // Handle acquire failure here.
			ie.printStackTrace();
		}
	}
	
	public Message remove(){
		Message msg = null;
		try {
			
		    this.rmSemaphore.acquire();
		    this.mutex.acquire();
		    try {
		        msg = messages.remove(0);
		        this.addSemaphore.release();
		    } finally {
		        this.mutex.release();
		    }
		} catch(final InterruptedException ie) {
		    // Handle acquire failure here.
			//ie.printStackTrace();
		}
		return msg;
	}
}
