package demo.sphinx.helloworld;

import marytts.modules.synthesis.Voice;

public class Main {
	public static void main(String[] args) {
		TextToSpeech tts =new TextToSpeech();
		
		Voice.getAvailableVoices().stream().forEach(System.out::println);
		//dfki-poppy-hsmm
		//cmu-slt-hsmm
		//cmu-rms-hsmm
		tts.setVoice("dfki-poppy-hsmm");
		tts.speak("hello world",2.0f,false,false);
		
	}
}
