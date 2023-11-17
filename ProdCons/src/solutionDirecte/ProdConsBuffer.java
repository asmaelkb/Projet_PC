package solutionDirecte;
import java.util.concurrent.ArrayBlockingQueue;

public class ProdConsBuffer {
	int n; // Taille du tableau
	int nempty; // Nombre de cases vides
	int nfull; // Nombre de cases remplies
	
	ArrayBlockingQueue<Message> buffer; // Tableau à n cases
	
	public ProdConsBuffer(int n) {
		// Création d'un tableau vide
		this.n = n;
		this.nempty = n;
		this.nfull = 0;
		
		buffer = new ArrayBlockingQueue<Message>(n);
	}
	
	// QUESTION : doit-on implémenter la circularité du tableau?
	// ou alors ArrayBlockingQueue est déjà implémentée avec cette prop?
	
	public synchronized void Producer(Message msg) {
		while (nfull == n) { // Toutes les cases sont remplies
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		buffer.add(msg);
		nempty--;
		nfull++;
		notifyAll();
	}
	
	public synchronized Message Consume() {
		while (nempty == n) { // Toutes les cases sont vides
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Message msg = buffer.remove();
		nempty++;
		nfull--;
		notifyAll();
		return msg;
	}
	
	
}