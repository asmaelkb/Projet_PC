package solutionDirecte;

public interface IProdConsBuffer {
	
	public void Producer(Message msg);
	public Message Consume();
}