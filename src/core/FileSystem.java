package core;

import java.io.File;

public class FileSystem {
	
	String root;
	
	public FileSystem() {
		root = new File("").getAbsolutePath() + "/";
	}
	
	public boolean createFile(String path, String filename) { //returns true if Dir. created
		new File(path + filename);
		return true;
	}
	
	public boolean createDirectory(String path, String directoryName) {
		File theDir = new File(path + directoryName);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			try {
				theDir.mkdir();
			} catch (SecurityException e) {
				return false;
			}
		}
		return true;
	}

	public boolean deleteFile(File file) {
		return file.delete();
	}
	
	public boolean deleteFile(String path) {
		return new File(path).delete();
	}
	
	public boolean deleteDirectory(String path) {
		return deleteDirectory(new File(path));
	}
	
	public boolean deleteDirectory(File file) {
		File[] allSubFiles = file.listFiles();
		if(allSubFiles != null)
			for(File f : allSubFiles)
				deleteDirectory(f);
		return file.delete();
	}
	
	
}
