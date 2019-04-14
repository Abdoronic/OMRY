package applications;



import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class Weather extends Task implements Runnable{

	public Weather(Kernel kernal, TCB pcb, long deadline, OMRY omry, Semaphore s) {
		super(kernal, pcb, deadline, omry, s, "Weather");
	}

	@Override
	public void run() {
//		while(System.currentTimeMillis() < getDeadline()-1500){}
		String weather = weather();
		try {
			getSemaphore().await();
			getOmry().talk(weather);
			getSemaphore().signal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getKernal().getTaskManager().terminateRunnigTask();
	}
	private static String weather() {
		return "today is a sunny day with 25 degree Celsius";
	}
}
