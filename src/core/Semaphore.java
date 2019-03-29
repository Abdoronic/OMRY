package core;

public class Semaphore {
	private boolean s;
	
	public Semaphore(boolean s) {
		this.s = s;
	}
	
	public void await() {
//		while(!s);
//		s = false;
	}
	
	public void signal() {
//		s = true;
	}
}
