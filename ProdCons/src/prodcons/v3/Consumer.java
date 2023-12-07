package prodcons.v3;

public class Consumer extends Thread {
	
	ProdConsBuffer buffer;
	int time;
	
	public Consumer(ProdConsBuffer buffer, int consTime) {
		this.buffer = buffer;
		this.time = consTime;
	}
	
	public void run() {
		while(true) {
			
			Message msg = new Message("");
			try {
				msg = buffer.get();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("Consommation du message : " + msg.mot);
			
			// Traitement du message
			try {
				sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
}