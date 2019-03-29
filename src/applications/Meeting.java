package applications;

import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class Meeting extends Task implements Runnable{

	String group;
	public Meeting(Kernel kernal, TCB pcb, long deadline, String group, OMRY omry, Semaphore s) {
		super(kernal, pcb, deadline, omry, s);
		this.group = group;
	}
	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()-500){}
		String meeting = "You have a meeting with "+group+" now!";
		try {
			getSemaphore().await();
			getOmry().talk(meeting);
			getSemaphore().signal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getKernal().getTaskManager().terminateRunnigTask();
		
	}

}
