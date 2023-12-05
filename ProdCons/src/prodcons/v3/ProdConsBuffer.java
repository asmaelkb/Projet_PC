package prodcons.v3;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer {
	Semaphore notFull;
	Semaphore notEmpty;
	Semaphore mutex;
	int taille; // Taille du buffer
	int in;
	int out;
	int n; // Nombre de messages présent dans le buffer
	
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
	
	
	public void put(Message msg) throws InterruptedException {
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
	
	public Message get() throws InterruptedException {
		notEmpty.acquire();
		mutex.acquire();
		Message msg;
		// Récupère à l'index out
		synchronized(this) {
			msg = buffer[out];
			out = out + 1 % n;
			
		}
		
		mutex.release();
		notFull.release();
		return msg;
	}


	@Override
	public int nmsg() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int totmsg() {
		// TODO Auto-generated method stub
		return 0;
	}
}
