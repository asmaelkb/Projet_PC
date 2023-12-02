package prodcons.v2;

public class Producer extends Thread {
	
	// Ce thread Producer produit un nombre aléatoire de messages (entre minProd et maxProd)
	// et les ajoute au ProdConsBuffer
	
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
			Message msg = new Message(Integer.toString(nbMsg));
			buffer.put(msg);
		}
		
	}
}