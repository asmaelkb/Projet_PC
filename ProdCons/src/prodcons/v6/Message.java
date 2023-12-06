package prodcons.v6;

import java.util.concurrent.Semaphore;

public class Message {
	String mot;
	
	Semaphore push;
	Semaphore pop;
	
	public Message(String mot, Semaphore push, Semaphore pop) {
		this.mot = mot;
		this.push = push;
		this.pop = pop;
	}
}
