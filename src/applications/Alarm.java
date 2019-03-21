package applications;

import core.Kernel;
import task.TCB;
import task.Task;

public class Alarm extends Task implements Runnable{

	public Alarm(Kernel kernal, TCB tcb, long deadline) {
		super(kernal, tcb, deadline);
	}
	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()-500){}
		getKernal().getTaskManager().terminateRunnigTask();
		// play alarm
	}
}
