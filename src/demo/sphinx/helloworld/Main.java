package demo.sphinx.helloworld;

import marytts.modules.synthesis.Voice;

public class Main {
	public static void main(String[] args) {
		TextToSpeech tts =new TextToSpeech();
		Voice.getAvailableVoices().stream().forEach(System.out::println);
		tts.setVoice("dfki-poppy-hsmm");
		tts.speak("i'm happy",2.0f,false,false);
		System.out.println("ana aho");

		//dfki-poppy-hsmm
		//cmu-slt-hsmm
		//cmu-rms-hsmm
		
	}
}
