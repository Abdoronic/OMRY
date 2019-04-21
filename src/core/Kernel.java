package core;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import GUI.ActivityMonitor;
import applications.Alarm;
import applications.Call;
import applications.Greeting;
import applications.Joke;
import applications.Meeting;
import applications.Music;
import applications.Time;
import applications.Weather;
import task.Status;
import task.TCB;
import task.Task;

public class Kernel {

	private boolean stopMusic;
	private FileSystem fileSystem;
	private IOManager ioManager;
	private MemoryManager meomoryManager;
	private TaskManager taskManager;
	private Semaphore semaphore;
	private OMRY omry;
	static int TC = 1;
	private long processID;
	private ActivityMonitor am;
	public Kernel() throws IOException, InterruptedException {
		am = new ActivityMonitor();
		meomoryManager = new MemoryManager(256 << 20);
		fileSystem = new FileSystem();
		ioManager = new IOManager();
		taskManager = new TaskManager(am);
		ioManager.writeToConsole("OMRY is turned on!");
		omry = new OMRY();
		processID = 0;
		semaphore = new Semaphore(true);
	}

	public boolean runShell() throws IOException, InterruptedException {
		stopMusic=true;
		String line;
//		while (true) 
		{
			System.out.println("Start");
			getMeomoryManager().allocateMemory(1);
			semaphore.await();
//			line = omry.listen();
//			getMeomoryManager().addInMemory(omry.listen());//List to the Voice
			semaphore.signal();
			getMeomoryManager().addInMemory(ioManager.readFromConsole());//ReadFile to know which function
//			line = ioManager.readFromConsole(); 
//			System.out.println(line);
			System.out.println(getMeomoryManager().getFromMemory());
			if (getMeomoryManager().getFromMemory().contains("quit")) {
//				break;
				getMeomoryManager().releaseMemory(1);
				omry.talk("See you soon, Bye!");
				return false;
			}
			else {
				Task newTask = answer(getMeomoryManager().getFromMemory()); //
				getMeomoryManager().releaseMemory(1);
				if(newTask != null)
				{	
					taskManager.addTask(newTask);
					Thread.sleep(3000);
				}
			}
			Thread.sleep(2000);
		}
		return true;
	}
	public Task answer(String question) throws IOException, InterruptedException
	{
		TCB tcb = new TCB(processID++, 0l, false, Status.NEW);
		if (question.equalsIgnoreCase("help")) {
			semaphore.await();
			omry.talk(bashCommandsDoc());
			semaphore.signal();
			return null;
		}
		if(question.contains("music"))
		{
			stopMusic=false;
			return new Music(this, tcb, System.currentTimeMillis()+100, omry, semaphore);
		}
		if(question.contains("weather"))
		{
			return new Weather(this, tcb, System.currentTimeMillis()+100, omry, semaphore);
		}
		if (question.contains("make alarm at") || question.contains("alarm at") || question.contains("set meeting at")) {
			return Alarm_Meeting(question, tcb);
		}
		else if (question.contains("time")) {
			return new Time(this, tcb, System.currentTimeMillis()+500, omry, semaphore);
		} else if (question.contains("call")) {
			
			String[] x=question.split(" ");
			for(int i=0;i<x.length;i++) {
				if(x[i].equals("call")) {
					return new Call(this, tcb, System.currentTimeMillis()+100, omry, x[i+1], semaphore);
				}
			}
			return null;
		} else if (Greeting(question)) {
			return new Greeting(this, tcb, System.currentTimeMillis()+100, omry, semaphore);
		} else if (question.contains("joke")) {
			return new Joke(this, tcb, System.currentTimeMillis()+100, omry, semaphore);
		} 
		else {
			semaphore.await();
			omry.talk("Command not recognized.");
			semaphore.signal();
			return null;
		}
	}
	private static boolean Greeting(String result)
	{
		return result.contains("morning") || result.equals("good afternoon") || result.equals("good evening")
				|| result.equals("hey") || result.equals("hello") || result.contains("hey omry");
	}
	@SuppressWarnings("deprecation")
	private Task Alarm_Meeting(String result, TCB tcb) throws IOException, InterruptedException
	{
		String[] tmp = result.split(" ");
		System.out.println(Arrays.toString(tmp));
		int i = 0;
		int type = -1;
		for (i = 0; i < tmp.length; i++) {
			if (tmp[i].equals("meeting"))
				type = 1;
			if (tmp[i].contains("alarm"))
				type = 0;
			if (tmp[i].equals("at"))
			{
				i++;
				break;
			}
		}
		System.out.println(i+" "+type);
		try {
			if (type == -1)
				throw new Exception("Type can not be specified");
			getMeomoryManager().allocateMemory(1);
			getMeomoryManager().addInMemory(tmp[i].split(":")[0]);//hours
			int hours = Integer.parseInt(getMeomoryManager().getFromMemory());
			getMeomoryManager().allocateMemory(1);
			getMeomoryManager().addInMemory(tmp[i].split(":")[1]);//minus
			int minutes = Integer.parseInt(getMeomoryManager().getFromMemory());
			getMeomoryManager().releaseMemory(2);
			System.out.println(Arrays.toString(tmp));
			System.out.println(hours+"   "+minutes);
			Date date = new Date();
			date.setHours(hours);
			date.setMinutes(minutes);
			date.setSeconds(0);
			long millis = date.getTime() ;
			Task t;
			if(type==1)
				t = new Meeting(this, tcb, millis, "Someone i don't know", omry, semaphore);
			else
				t = new Alarm(this, tcb, millis, omry, semaphore);
			semaphore.await();
			omry.talk("Alarm Setted at "+hours+":"+minutes);
			semaphore.signal();
			return t;
		} catch (Exception e) {
			semaphore.await();
			omry.talk("Reapeat again alarm wasn't setted");
			semaphore.signal();
			return null;
		}
	}
	public String bashCommandsDoc() {
		StringBuilder sb = new StringBuilder();
		sb.append("##############################\n");
		sb.append("Shell Commands: ");
		sb.append("help : Prints all the shell commands and their use.\n");
		sb.append("quit : Turns off OMRY.\n");
		sb.append("##############################\n");
		return sb.toString();
	}

	public FileSystem getFileSystem() {
		return fileSystem;
	}

	public IOManager getIoManager() {
		return ioManager;
	}

	public MemoryManager getMeomoryManager() {
		return meomoryManager;
	}

	public TaskManager getTaskManager() {
		return taskManager;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		new Kernel();
	}
	public boolean stopMusic()
	{
		return stopMusic;
	}
}
