package applications;



import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class Joke extends Task implements Runnable{

	public Joke(Kernel kernal, TCB pcb, long deadline, OMRY omry, Semaphore s) {
		super(kernal, pcb, deadline, omry, s,"Joke");
	}

	@Override
	public void run() {
//		while(System.currentTimeMillis() < getDeadline()-1500){}
		String joke = joke();
		try {
			getSemaphore().await();
			getOmry().talk(joke);
			getSemaphore().signal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getKernal().getTaskManager().terminateRunnigTask();
	}
	private static String joke() {
		String[] x = { 
				"An old lady asked me to help check her balance. So I pushed her over",
				"I bought some shoes from a drug dealer.",
				"I'm so good at sleeping. I can do it with my eyes closed",
				"My boss told me to have a good day.. so I went home",
				"Why do blind people hate skydiving? It scares the hell out of their dogs",
				"What do you call a guy with a rubber toe? Roberto",
				"I know a lot of jokes about unemployed people but none of them work",
				"take a deep breath and you will do great"};

		return x[(int) (Math.random() * x.length)];
	}
}
