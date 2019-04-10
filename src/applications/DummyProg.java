package applications;


import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class DummyProg extends Task{
	private String var;
	
	public DummyProg(Kernel kernal, TCB tcb, long deadline, String var, OMRY omry, Semaphore s) {
		super(kernal, tcb, deadline, omry, s);
		this.var = var;
	}
	
	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getKernal().getIoManager().writeToConsole(var);
		}
		System.err.println("Done " + var);
		getKernal().getTaskManager().terminateRunnigTask();
	}
}
