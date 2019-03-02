/*
 * Copyright 1999-2004 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 *
 */

package demo.sphinx.helloworld;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;
import marytts.modules.synthesis.Voice;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * A simple HelloWorld demo showing a simple speech application built using
 * Sphinx-4. This application uses the Sphinx-4 endpointer, which automatically
 * segments incoming audio into utterances and silences.
 */
public class HelloWorld {

	/**
	 * Main method for running the HelloWorld demo.
	 */
	static void talk(String[] args) {
		try {
			URL url;
			if (args.length > 0) {
				url = new File(args[0]).toURI().toURL();
			} else {
				url = HelloWorld.class.getResource("helloworld.config.xml");
			}

			System.out.println("Loading...");

			ConfigurationManager cm = new ConfigurationManager(url);

			Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
			Microphone microphone = (Microphone) cm.lookup("microphone");

			/* allocate the resource necessary for the recognizer */
			recognizer.allocate();

			/* the microphone will keep recording until the program exits */
			if (microphone.startRecording()) {

				System.out.println(
						"Say: (Good morning | Hello) " + "( Bhiksha | Evandro | Paul | Philip | Rita | Will )");

				while (true) 
				{
					System.out.println("Start speaking. Press Ctrl-C to quit.\n");

					/*
					 * This method will return when the end of speech is reached. Note that the
					 * endpointer will determine the end of speech.
					 */
					Result result = recognizer.recognize();

					if (result != null) {
						String resultText = result.getBestFinalResultNoFiller();
//						String say = organizer(resultText);
						System.out.println("You said: " + resultText + "\n");
						if(resultText.equals("")) {
							continue;
						}
						String say = organizer(resultText);
						System.out.println("You said: " + say + "\n");
						TextToSpeech tts = new TextToSpeech();
						tts.setVoice("dfki-poppy-hsmm");
						tts.speak(say, 2.0f, false, false);
					} else {
						System.out.println("I can't hear what you said.\n");
					}
				}
			} else {
				System.out.println("Cannot start microphone.");
				recognizer.deallocate();
				System.exit(1);
			}
		} catch (IOException e) {
			System.err.println("Problem when loading HelloWorld: " + e);
			e.printStackTrace();
		} catch (PropertyException e) {
			System.err.println("Problem configuring HelloWorld: " + e);
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.err.println("Problem creating HelloWorld: " + e);
			e.printStackTrace();
		}
	}

	private static String organizer(String result) {
		if(result.contains("make alarm at")) {
			int x=StoI(result);
			System.out.println(result+"         " +x);
			
		}
		if(result.equals("what is the time now")) {
				return time();
				
		}
		else if(result.equals("good morning")) {
			return "Hello";
		}
		return "what are you saying";
		
	}

	private static String time() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		return  dateFormat.format(date);
		
	}
	static int StoI(String s)
	{
		String []s1=s.split(" ");
		int hour=0;
		for(int i=1;i<=12 && hour==0 ;i++)
		{
			String out=IntegerToWritten(i);
			if(s1[3].equals(out))
				hour=i;
		}
		if(s1[s1.length-1].equals("pm"))
			hour=(hour+12)%24;
		int minutes=0;
		int end=s1.length-1;
		int start=4;
		if(s1[start].equals("zero"))
			start++;
		String number="";
		for(int i=start;i<end;i++)
			number+=s1[start];
		for(int i=0;i<60;i++)
		{
			if(number.equals(IntegerToWritten(i)))
			{	
				minutes=i;
				break;
			}
		}
		return hour*100+minutes;
	}
	static String[] ones = new String[] { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine" };
    static String[] teens = new String[] { "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen" };
    static String[] tens = new String[] { "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };
    static String[] thousandsGroups = { "", " Thousand", " Million", " Billion" };

    private static String FriendlyInteger(int n, String leftDigits, int thousands)
    {
        if (n == 0)
        {
            return leftDigits;
        }

        String friendlyInt = leftDigits;

        if (friendlyInt.length() > 0)
        {
            friendlyInt += "";
        }

        if (n < 10)
        {
            friendlyInt += ones[n];
        }
        else if (n < 20)
        {
            friendlyInt += teens[n - 10];
        }
        else if (n < 100)
        {
            friendlyInt += FriendlyInteger(n % 10, tens[n / 10 - 2], 0);
        }
        else if (n < 1000)
        {
            friendlyInt += FriendlyInteger(n % 100, (ones[n / 100] + "Hundred"), 0);
        }
        else
        {
            friendlyInt += FriendlyInteger(n % 1000, FriendlyInteger(n / 1000, "", thousands+1), 0);
            if (n % 1000 == 0)
            {
                return friendlyInt;
            }
        }

        return (friendlyInt + thousandsGroups[thousands]).toLowerCase();
    }

    public static String IntegerToWritten(int n)
    {
        if (n == 0)
        {
            return "zero";
        }
        else if (n < 0)
        {
            return "Negative " + IntegerToWritten(-n);
        }

        return FriendlyInteger(n, "", 0);
    }


	public static void main(String[] args) {
		TextToSpeech tts = new TextToSpeech();
		tts.setVoice("dfki-poppy-hsmm");
		tts.speak("i am listening", 2.0f, false, false);
			talk(args);
			
	}
}
