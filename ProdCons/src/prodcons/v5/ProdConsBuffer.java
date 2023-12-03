package prodcons.v5;

public class ProdConsBuffer implements IProdConsBuffer {
	int n; 				// Taille du tableau
	int nempty; 		// Nombre de cases vides
	int nfull; 			// Nombre de cases remplies
	int totmsg; 		// Nombre total de msgs mis dans le buffer depuis sa création
	int first_index;	// index du premier msg mis dans le buffer
	int current_index;	// index de la position du dernier msg dans le tableau
	
	Message[] buffer; // Tableau à n cases
	
	public ProdConsBuffer(int n) {
		// Création d'un tableau vide
		this.n = n;
		this.nempty = n;
		this.nfull = 0;
		this.totmsg = 0;
		this.first_index = 0;
		this.current_index = 0;
		
		buffer = new Message[n];
	}
	
	// Méthode pour ajouter un msg au buffer
	public void add(Message msg) {
		buffer[current_index%n] = msg;
		current_index++;
	}
	
	// Méthode pour extraire le premier msg ajouté au buffer
	public Message remove() {
		int tmp = first_index%n;
		first_index++;
		return buffer[tmp];
	}
	
	
	public synchronized void put(Message msg) {
		while (nfull == n) { // Toutes les cases sont remplies
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		add(msg);
		nempty--;
		nfull++;
		totmsg++;
		notifyAll();
	}
	
	public synchronized Message get() {
		while (nempty == n) { // Toutes les cases sont vides
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Message msg = remove();
		nempty++;
		nfull--;
		notifyAll();
		return msg;
	}
	
	// Retirer k messages consécutifs
	public synchronized Message[] get(int k) {
		Message[] m = new Message[k];
		
		while (k > nfull) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < k; i++) {
			m[i] = remove();
		}

		nempty += k;
		nfull -= k;
		notifyAll();
		
		return m;
	}

	@Override
	public int nmsg() {
		return nfull;
	}

	@Override
	public int totmsg() {
		return totmsg;
	}
	
}
