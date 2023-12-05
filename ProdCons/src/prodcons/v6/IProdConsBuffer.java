package prodcons.v6;

public interface IProdConsBuffer {
	
	public void put(Message m, int n) throws InterruptedException;
	
	public Message get() throws InterruptedException;
}
