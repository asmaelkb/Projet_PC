package prodcons.v2;

public class Consumer extends Thread {
	
	// Ce thread Consumer consomme un message et le traite en un temps consTime donné
	
	ProdConsBuffer buffer;
	int time;
	
	public Consumer(ProdConsBuffer buffer, int consTime) {
		this.buffer = buffer;
		this.time = consTime;
	}
	
	public void run() {
		
//		int nbMsg = buffer.nmsg(); // Nombre de messages produits par le thread Producer
//		int nbCons = 0; // Nombre de messages consommés
//		
		// L'application se termine lorsque tous les messages produits ont été consommés
		
		while(true) {
			
			Message msg = buffer.get();
			System.out.println("Consommation du message : " + msg.mot );
			
			// Traitement du message
			try {
				sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
}