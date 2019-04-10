package applications;


import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class Call extends Task implements Runnable{
	
	String contact;
	public Call(Kernel kernal, TCB pcb, long deadline, OMRY omry, String contact, Semaphore s) {
		super(kernal, pcb, deadline, omry, s);
		this.contact = contact;
	}

	@Override
	public void run() {
//		while(System.currentTimeMillis() < getDeadline()-1500){}
		try {
			getSemaphore().await();
			getOmry().talk("Calling "+contact);
			getSemaphore().signal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getKernal().getTaskManager().terminateRunnigTask();		
	}
}
