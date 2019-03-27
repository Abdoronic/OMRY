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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;


public class HelloWorld {
	
	private static String organizer(String result) {
		if(result.contains("make alarm at")) {
			int x=StoI(result);
			System.out.println(result+"         " +x);
			
		}
		if(result.equals("what is the time now")) {
				return time();
				
		}
		else if(result.equals("good morning")||result.equals("good afternoon")||result.equals("good evening")||result.equals("hey")||result.equals("hello")) {
			return "hello";
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


	public static void main(String[] args) throws Exception {
		while(true) {		
			try {
				BufferedReader br = new BufferedReader(new FileReader("C:/Users/abdel/.spyder-py3/Output.TXT"));
				while(br.ready()) {
					String result=br.readLine();
					if(result.equals("") || result.charAt(0)=='$')
						continue;
					if(result.equals("see you soon")) {
						System.out.println("You said: " + result + "\n");
						FileChannel.open(Paths.get("C:/Users/abdel/.spyder-py3/Output.TXT"), StandardOpenOption.WRITE).truncate(0).close();
						try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:/Users/abdel/.spyder-py3/Output.TXT", true)))) {
						    out.println("$bye bye");
						} catch (IOException e) {
						    System.err.println(e);
						}
						return;
					}
					else {
						System.out.println("You said: " + result + "\n");
						String say = organizer(result);
						System.out.println("done");
					FileChannel.open(Paths.get("C:/Users/abdel/.spyder-py3/Output.TXT"), StandardOpenOption.WRITE).truncate(0).close();
					try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:/Users/abdel/.spyder-py3/Output.TXT", true)))) {
					    out.println("$"+say);
					} catch (IOException e) {
					    System.err.println(e);
					}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
	}
			
	}
}

