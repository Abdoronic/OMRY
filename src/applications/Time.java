package applications;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import core.Kernel;
import task.TCB;
import task.Task;

public class Time extends Task implements Runnable{
	
	public Time(Kernel kernal, TCB pcb, long deadline) {
		super(kernal, pcb, deadline);
	}

	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()-1500){}
		getKernal().getTaskManager().terminateRunnigTask();	
		String time = time();
	}

	private static String time() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
