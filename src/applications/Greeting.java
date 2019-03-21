package applications;

import core.Kernel;
import task.TCB;
import task.Task;

public class Greeting extends Task implements Runnable{
	
	
	public Greeting(Kernel kernal, TCB pcb, long deadline) {
		super(kernal, pcb, deadline);
	}

	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()-1500){}
		getKernal().getTaskManager().terminateRunnigTask();		
		String greeting = greeting();
	}

	private static String greeting() {
		String[] x = { "Hello", "Hi Sir", "Good Morning"};
		return x[(int) (Math.random() * (x.length + 1))];
	}
}
