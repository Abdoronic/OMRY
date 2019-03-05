package core;

import java.io.IOException;

public class Kernel {
	
	private FileSystem fileSystem;
	private IOManager ioManager;
	private MemoryManager meomoryManager;
	private ProcessManager processManager; 
	
	public Kernel() {
		meomoryManager = new MemoryManager(256 << 20);
		fileSystem = new FileSystem();
		ioManager = new IOManager();
		processManager = new ProcessManager();
		ioManager.writeToConsole("OMRY is turned on!");
		runShell();
	}
	
	public void runShell() {
		String line;
		while(true) {
			line = ioManager.readFromConsole();
			if(line.equalsIgnoreCase("help")) {
				ioManager.writeToConsole(bashCommandsDoc());
			} else if(line.equalsIgnoreCase("quit")) {
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
	
	public static void main(String[] args) throws IOException {
		new Kernel();
	}
}
