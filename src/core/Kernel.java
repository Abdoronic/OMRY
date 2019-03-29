package core;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Date;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import applications.Alarm;
import applications.Call;
import applications.Greeting;
import applications.Joke;
import applications.Meeting;
import applications.Time;
import task.Status;
import task.TCB;
import task.Task;

public class Kernel {

	private FileSystem fileSystem;
	private IOManager ioManager;
	private MemoryManager meomoryManager;
	private TaskManager taskManager;
	private Semaphore semaphore;
	private OMRY omry;
	static int TC = 1;
	private static String filePath = "D:\\Eclipse\\OMRY\\src\\disk\\output.txt";
	private static String pythonPath = "python D:\\Eclipse\\helloworld\\temp.py";
	private long processID;
	public Kernel() throws IOException, InterruptedException {
		meomoryManager = new MemoryManager(256 << 20);
		fileSystem = new FileSystem();
		ioManager = new IOManager();
		taskManager = new TaskManager();
		ioManager.writeToConsole("OMRY is turned on!");
		omry = new OMRY(pythonPath, filePath);
		processID = 0;
		semaphore = new Semaphore(true);
		FileChannel.open(Paths.get(filePath), StandardOpenOption.WRITE).truncate(0).close();
//		DummyProg p1 = new DummyProg(this, new TCB(TC++, 0, false, Status.NEW),
//				System.currentTimeMillis() + 10000, "P1");
//		taskManager.addTask(p1);
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		DummyProg p2 = new DummyProg(this, new TCB(TC++, 0, false, Status.NEW),
//				System.currentTimeMillis() + 3000, "P2");
//		taskManager.addTask(p2);
//		
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		DummyProg p3 = new DummyProg(this, new TCB(TC++, 0, false, Status.NEW),
//				System.currentTimeMillis() + 1000, "P3");
//		taskManager.addTask(p3);
		
		runShell();
	}

	public void runShell() throws IOException, InterruptedException {
		String line;
		while (true) {
			System.out.println("Start");
			semaphore.await();
			line = omry.listen();
			semaphore.signal();
//			line = ioManager.readFromConsole(); //ReadFile to know which function
			System.out.println(line);
			if (line.contains("quit")) {
				break;
			}
			else {
				Task newTask = answer(line); //
				if(newTask != null)
				{	
					taskManager.addTask(newTask);
					Thread.sleep(3000);
				}
			}
			Thread.sleep(2000);
		}
	}
	public Task answer(String question) throws IOException, InterruptedException
	{
		TCB tcb = new TCB(processID++, 0l, false, Status.NEW);
		if (question.equalsIgnoreCase("help")) {
			semaphore.await();
			omry.talk(bashCommandsDoc());
			semaphore.signal();
			return null;
		}if (question.contains("make alarm at") || question.contains("alarm at") || question.contains("set meeting at")) {
			return Alarm_Meeting(question, tcb);
		}
//		else if(question.contains("set meeting at")) {
//			return new Meeting(this, pcb, deadline, "", omry);
//		}
		else if (question.contains("time")) {
			return new Time(this, tcb, System.currentTimeMillis()+500, omry, semaphore);
		} else if (question.contains("call")) {
			
			String[] x=question.split(" ");
			for(int i=0;i<x.length;i++) {
				if(x[i].equals("call")) {
					return new Call(this, tcb, System.currentTimeMillis(), omry, x[i+1], semaphore);
				}
			}
			return null;
		} else if (Greeting(question)) {
			return new Greeting(this, tcb, System.currentTimeMillis(), omry, semaphore);
		} else if (question.contains("joke")) {
			return new Joke(this, tcb, System.currentTimeMillis(), omry, semaphore);
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
			String h=tmp[i].split(":")[0];
			String m=tmp[i].split(":")[1];
			System.out.println(Arrays.toString(tmp));
			System.out.println(h+"   "+m);
//			int hour = Integer.parseInt(h) * 60 * 60 * 100 + Integer.parseInt(m) * 60 * 100;
			DateFormat df = new SimpleDateFormat("HH:mm");
			Date date1 = new Date();
			Date date2 = df.parse(h+":"+m);
			long diff = date1.getTime() - date2.getTime();
			Task t;
			if(type==1)
				t = new Meeting(this, tcb, diff, "Someone i don't know", omry, semaphore);
			else
				t = new Alarm(this, tcb, diff, omry, semaphore);
			semaphore.await();
			omry.talk("Alarm Setted at "+h+":"+m);
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
}
