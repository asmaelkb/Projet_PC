package prodcons.v6;

import java.util.concurrent.Semaphore;

import java.lang.Thread;

public class ProdConsBuffer implements IProdConsBuffer {
	
	int size; 				// Taille du tableau
	int nempty; 		// Nombre de cases vides
	int nfull; 			// Nombre de cases remplies
	int totmsg; 		// Nombre total de msgs mis dans le buffer depuis sa création
	int first_index;	// index du premier msg mis dans le buffer
	int current_index;	// index de la position du dernier msg dans le tableau
	
	Message[] buffer; // Tableau à n cases
	
	// Sémaphore bloquant permettant que le producteur est bloqué tant que 
	// tous les messages n'ont pas été consommés
	Semaphore pop = new Semaphore(0);  
	Semaphore push = new Semaphore(0);
	
	public ProdConsBuffer(int n) {
		// Création d'un tableau vide
		this.size = n;
		this.nempty = n;
		this.nfull = 0;
		this.totmsg = 0;
		this.first_index = 0;
		this.current_index = 0;
		
		buffer = new Message[n];
	}
	
	// Méthode pour ajouter un msg au buffer
	public void add(Message msg) {
		buffer[current_index%size] = msg;
		current_index++;
	}
	
	// Méthode pour extraire le premier msg ajouté au buffer
	public Message remove() {
		int i = first_index%size;
		first_index++;
		return buffer[i];
	}
	
	
	@Override
	public  Message get() {
		Message msg;
		
		synchronized(this) {
			while (nempty == size) { // Toutes les cases sont vides
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			msg = remove();
			msg.push.release();
			nempty++;
			nfull--;
			notifyAll();
		}
		//prod.release();
		try {
			System.out.println("Thread consommateur bloqué : " + Thread.currentThread().getName());
			msg.pop.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return msg;
	}

	@Override
	public void put(Message m, int n) throws InterruptedException {
		//push = new Semaphore(n);
		
		synchronized(this) {
			while (nfull == size) { // Toutes les cases sont remplies
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < n; i++) {
				add(m);
			}
			nempty -= n;
			nfull += n;
			totmsg += n;
			notifyAll();
		}
//		for (int i = 0; i < n; i++) {
//			prod.acquire();
//		}
		
		System.out.println("Thread producteur bloqué : " + Thread.currentThread().getName());
		m.push.acquire(n);
		m.pop.release(n);
	}


}
