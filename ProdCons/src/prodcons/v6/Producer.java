package prodcons.v6;

import java.util.concurrent.Semaphore;

public class Producer extends Thread {
	ProdConsBuffer buffer;
	Message msg;
	int time;
	int min;
	int max;
	
	public Producer(ProdConsBuffer buffer, int prodTime, int minProd, int maxProd) {
		this.buffer = buffer;
		this.time = prodTime;
		this.min = minProd;
		this.max = maxProd;
	}
	
	public void run() {
		int nbMsg = (int) Math.floor(Math.random() * (max - min + 1) + min );
		
		// Production d'un message à la fois
		for (int i = 0; i < nbMsg; i++) {
			try {
				sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Semaphore push = new Semaphore(0);
			Semaphore pop = new Semaphore(0);
			Message msg = new Message(Integer.toString(nbMsg), push, pop);
			try {
				buffer.put(msg, nbMsg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
