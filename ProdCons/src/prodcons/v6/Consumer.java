package prodcons.v6;

public class Consumer extends Thread{
	ProdConsBuffer buffer;
	int time;
	
	public Consumer(ProdConsBuffer buffer, int consTime) {
		this.buffer = buffer;
		this.time = consTime;
	}
	
	public void run() {
		while(true) {
			
			Message msg = buffer.get();
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
