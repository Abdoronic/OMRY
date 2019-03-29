package applications;

import java.io.IOException;

import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class Alarm extends Task implements Runnable{

	public Alarm(Kernel kernal, TCB tcb, long deadline, OMRY omry, Semaphore semaphore) {
		super(kernal, tcb, deadline, omry, semaphore);
	}
	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()-500){}
		try {
			getSemaphore().await();
			getOmry().talk("Alarm Alarm Alarm Alarm");
			getSemaphore().signal();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getKernal().getTaskManager().terminateRunnigTask();
	}
}
