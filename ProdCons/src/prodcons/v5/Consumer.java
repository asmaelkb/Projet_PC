package prodcons.v5;

public class Consumer extends Thread {
	
	ProdConsBuffer buffer;
	int time;
	int k;
	
	public Consumer(ProdConsBuffer buffer, int consTime, int k) {
		this.buffer = buffer;
		this.time = consTime;
		this.k = k;
	}
	
	public void run() {
		while(true) {
			
			Message[] msg = buffer.get(k);
			System.out.println("Consommation du message : " + msg);
			
			// Traitement du message
			try {
				sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
}
