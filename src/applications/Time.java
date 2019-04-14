package applications;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class Time extends Task implements Runnable{
	
	public Time(Kernel kernal, TCB pcb, long deadline, OMRY omry, Semaphore s) {
		super(kernal, pcb, deadline, omry, s,"Time");
	}

	@Override
	public void run() {
//		while(System.currentTimeMillis() < getDeadline()-1500){}
		String time = time();
		try {
			getSemaphore().await();
			getOmry().talk(time);
			getSemaphore().signal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getKernal().getTaskManager().terminateRunnigTask();	
	}

	private static String time() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
