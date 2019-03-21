package applications;

import core.Kernel;
import task.TCB;
import task.Task;

public class Meeting extends Task implements Runnable{

	String group;
	public Meeting(Kernel kernal, TCB pcb, long deadline, String group) {
		super(kernal, pcb, deadline);
	}
	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()-500){}
		getKernal().getTaskManager().terminateRunnigTask();
		String meeting = "You have a meeting with "+group+" now!";
	}

}
