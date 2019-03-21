package applications;

import core.Kernel;
import task.TCB;
import task.Task;

public class Message extends Task implements Runnable{
	
	public Message(Kernel kernal, TCB pcb, long deadline) {
		super(kernal, pcb, deadline);
	}

	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()-1500){}
		getKernal().getTaskManager().terminateRunnigTask();		
		String message = "sendt!";
	}

}
