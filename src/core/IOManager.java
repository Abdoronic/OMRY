package core;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class IOManager {
	private BufferedReader consoleIn;
	private PrintWriter consoleOut;
	
	public IOManager() {
		consoleIn = new BufferedReader(new InputStreamReader(System.in));
		consoleOut = new PrintWriter(System.out, true);
	}
	
	public String readFromConsole() {
		try {
			return consoleIn.readLine();
		} catch (IOException e) {
			consoleOut.println("Error Reading from Console");
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeToConsole(Object o) {
		consoleOut.println(o);
	}
	
	public String readFromFile(String path) {
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(path));
			return reader.readLine();
		} catch (IOException e) {
			consoleOut.println("Error Reading from file");
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void writeToFile(String path, Object o) {
		try {
			FileOutputStream fstream = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fstream);
			oos.writeObject(o);
			oos.close();
		} catch (IOException e) {
			consoleOut.println("Error Writing to file");
			e.printStackTrace();
		}
	}
	
}
