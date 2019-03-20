package core;

import java.io.IOException;

import applications.DummyProg;
import task.Status;
import task.TCB;

public class Kernel {

	private FileSystem fileSystem;
	private IOManager ioManager;
	private MemoryManager meomoryManager;
	private TaskManager taskManager;
	static int TC = 1;

	public Kernel() {
		meomoryManager = new MemoryManager(256 << 20);
		fileSystem = new FileSystem();
		ioManager = new IOManager();
		taskManager = new TaskManager();
		ioManager.writeToConsole("OMRY is turned on!");
		
		DummyProg p1 = new DummyProg(this, new TCB(TC++, 0, false, Status.NEW),
				System.currentTimeMillis() + 10000, "P1");
		taskManager.addTask(p1);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DummyProg p2 = new DummyProg(this, new TCB(TC++, 0, false, Status.NEW),
				System.currentTimeMillis() + 3000, "P2");
		taskManager.addTask(p2);
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		DummyProg p3 = new DummyProg(this, new TCB(TC++, 0, false, Status.NEW),
				System.currentTimeMillis() + 1000, "P3");
		taskManager.addTask(p3);
		
//		runShell();
	}

	public void runShell() {
		String line;
		while (true) {
			line = ioManager.readFromConsole();
			if (line.equalsIgnoreCase("help")) {
				ioManager.writeToConsole(bashCommandsDoc());
			} else if (line.equalsIgnoreCase("quit")) {
				break;
			} else {
				ioManager.writeToConsole("Command not recognized. Type \"help\" for more Info.");
			}
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

	public static void main(String[] args) throws IOException {
		new Kernel();
	}
}
