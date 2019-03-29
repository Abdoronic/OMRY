package applications;

import java.io.IOException;

import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class Greeting extends Task implements Runnable{
	
	
	public Greeting(Kernel kernal, TCB pcb, long deadline, OMRY omry, Semaphore s) {
		super(kernal, pcb, deadline, omry, s);
	}

	@Override
	public void run() {
//		while(System.currentTimeMillis() < getDeadline()-1500){}
		String greeting = greeting();
		try {
			getSemaphore().await();
			getOmry().talk(greeting);
			getSemaphore().signal();
			Thread.sleep(3000);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getKernal().getTaskManager().terminateRunnigTask();		
	}

	private static String greeting() {
		String x = "hello happy to hear from you by the way my name is omry";
		return x;
	}
}
