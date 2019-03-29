package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import demo.sphinx.helloworld.TextToSpeech;

public class OMRY {

	String pythonPath;
	TextToSpeech tts ;
	public OMRY() throws FileNotFoundException {
		this.pythonPath = new File("").getAbsolutePath().replaceAll("\\\\", "\\\\\\\\")+"\\\\temp.py";
		tts = new TextToSpeech();
		tts.setVoice("dfki-poppy-hsmm");
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
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "python " +pythonPath);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader Br=new BufferedReader(new InputStreamReader( p.getInputStream()));
		System.out.println("Python : " + Br.readLine());
		String in=Br.readLine();
		System.out.println("Done Listening: "+in);
		return in;
	}
}
