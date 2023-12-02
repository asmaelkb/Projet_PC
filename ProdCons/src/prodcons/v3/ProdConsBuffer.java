package prodcons.v3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class ProdConsBuffer {
	Semaphore notFull;
	Semaphore notEmpty;
	Semaphore mutex;
	int taille; // Taille du buffer
	int in;
	int out;
	int n; // Nombre de messages présent dans le buffer
	
	
	// Reader et writer : n consumer et 1 producer en même temps
	
	Message buffer[]; // Buffer circulaire
 	
	public ProdConsBuffer(int taille) {
		this.taille = taille; // Taille du buffer
		this.in = 0;
		this.out = 0;
		this.n = 0;
		buffer = new Message[taille];
		
		notFull = new Semaphore(taille);
		notEmpty = new Semaphore(0);
		mutex = new Semaphore(1);
	}
	
	
	public void Produce(Message msg) throws InterruptedException {
		notFull.acquire();
		mutex.acquire();
		
		// Ajout à l'index in
		synchronized(this) {
			buffer[in] = msg;
			in = in + 1 % n;
		}
		
		mutex.release();
		notEmpty.release();
	}
	
	public Message Consume() throws InterruptedException {
		notEmpty.acquire();
		mutex.acquire();
		
		// Récupère à l'index out
		synchronized(this) {
			Message msg = buffer[out];
			out = out + 1 % n;
		}
		
		mutex.release();
		notFull.release();
		return msg;
	}
}
