package applications;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import core.Kernel;
import core.OMRY;
import core.Semaphore;
import task.TCB;
import task.Task;

public class Alarm extends Task implements Runnable{

	public Alarm(Kernel kernal, TCB tcb, long deadline, OMRY omry, Semaphore semaphore) {
		super(kernal, tcb, deadline, omry, semaphore);
	}
	@Override
	public void run() {
		while(System.currentTimeMillis() < getDeadline()-500){}
		try {
//			getSemaphore().await();
//			getOmry().talk("Alarm Alarm Alarm Alarm");
//			getSemaphore().signal();
			File f = new File("4.wav"); // choose form 3 or 4
			System.out.println("the song is playing...");
			AudioInputStream audio = AudioSystem.getAudioInputStream(f);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			c.start();
			while(c.isActive()) {}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getKernal().getTaskManager().terminateRunnigTask();
	}
}
