package applications;

import java.io.IOException;

import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class Message extends Task implements Runnable{
	
	public Message(Kernel kernal, TCB pcb, long deadline, OMRY omry, Semaphore s) {
		super(kernal, pcb, deadline, omry, s, "Message");
	}

	@Override
	public void run() {
//		while(System.currentTimeMillis() < getDeadline()-1500){}
		String message = "Message sendt!";
		try {
			getSemaphore().await();
			getOmry().talk(message);
			getSemaphore().signal();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getKernal().getTaskManager().terminateRunnigTask();	
	}

}
