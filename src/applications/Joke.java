package applications;

import core.Kernel;
import task.TCB;
import task.Task;

public class Joke extends Task implements Runnable{

	public Joke(Kernel kernal, TCB pcb, long deadline) {
		super(kernal, pcb, deadline);
	}

	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()-1500){}
		getKernal().getTaskManager().terminateRunnigTask();
		String joke = joke();
	}
	private static String joke() {
		String[] x = { "Today at the bank, an old lady asked me to help check her balance. So I pushed her over",
				"I bought some shoes from a drug dealer. I don't know what he laced them with, but I've been tripping all day",
				"I'm so good at sleeping. I can do it with my eyes closed",
				"My boss told me to have a good day.. so I went home",
				"Why do blind people hate skydiving? It scares the hell out of their dogs",
				"What do you call a guy with a rubber toe? Roberto",
				"I know a lot of jokes about unemployed people but none of them work" };

		return x[(int) (Math.random() * ((5 - 0) + 1))];
	}
}
