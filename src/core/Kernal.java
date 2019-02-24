package core;

import java.io.IOException;


public class Kernal {
	
	private IOManager ioManager;
	
	public Kernal() {
		ioManager = new IOManager();
		ioManager.writeToConsole("OMRY is turned on!");
	}
	
	public static void main(String[] args) throws IOException {
		new Kernal();
	}
}
