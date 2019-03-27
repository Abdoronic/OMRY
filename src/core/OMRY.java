package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import demo.sphinx.helloworld.TextToSpeech;

public class OMRY {

	String pythonPath;
	String filePath;
	TextToSpeech tts ;
	BufferedReader br;
	public OMRY(String pythonPath, String filePath) throws FileNotFoundException {
		this.filePath = filePath;
		this.pythonPath = pythonPath;
		tts = new TextToSpeech();
		tts.setVoice("dfki-poppy-hsmm");
		br = new BufferedReader(new FileReader(filePath));  
	}
	public void talk(String s) throws IOException, InterruptedException
	{
		System.out.println("Speak: s");
		tts.speak(s,2.0f,false,false);
		Thread.sleep(1500);
	}
	public String listen() throws IOException
	{
		System.out.println("Listen");
//		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "python "+pythonPath);
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "python D:\\Eclipse\\helloworld\\temp.py");
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader Br=new BufferedReader(new InputStreamReader( p.getInputStream()));
		System.out.println("Python : " + Br.readLine());
		String in=Br.readLine();
//		while(true)
//		{
//			in = br.readLine();
//			System.out.println("in: "+in);
//			if(in == null || in.equals(""))
//				break;
//			else
//				break;
//		}
		FileChannel.open(Paths.get(filePath), StandardOpenOption.WRITE).truncate(0).close();
		System.out.println("Done Listening: "+in);
		return in;
	}
}
