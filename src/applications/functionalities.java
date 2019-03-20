package applications;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;

public class functionalities {
	static class Event implements Comparable<Event> {
		int type, time;

		public Event(int time, int type) {
			this.time = time;
			this.type = type;
		}

		@Override
		public int compareTo(Event o) {
			return time - o.time;
		}
	}

	static PriorityQueue<Event> event = new PriorityQueue<>();

	private static String organizer(String result) {
		if (result.contains("make alarm at") || result.contains("set alarm at") || result.contains("set meeting at")) {
			String[] tmp = result.split(" ");
			int i = 0;
			int type = -1;
			for (i = 0; i < tmp.length; i++) {
				if (tmp[i].equals("meeting"))
					type = 1;
				if (tmp[i].contains("alarm"))
					type = 0;
				if (tmp[i].equals("at"))
					break;
			}
			try {
				if (type == -1)
					throw new Exception("Type can not be specified");
				int hour = Integer.parseInt(tmp[i].split(":")[0]) * 100 + Integer.parseInt(tmp[i].split(":")[1]);
				event.add(new Event(hour, type));
				return "Alarm Setted";
			} catch (Exception e) {
				return "Reapeat again alarm wasn't setted";
			}
		} else if (result.equals("what is the time now")) {
			return time();
		} else if (result.substring(0, 4).equals("call")) {
			return "calling " + result.substring(5, result.length());
		} else if (result.equals("good morning") || result.equals("good afternoon") || result.equals("good evening")
				|| result.equals("hey") || result.equals("hello")) {
			return "hello";
		} else if (result.equals("tell me a joke")) {
			return joke();
		}

		return "what are you saying";

		// make comparable

	}

	private static String joke() {
		String[] x = { "Today at the bank, an old lady asked me to help check her balance. So I pushed her over",
				"I bought some shoes from a drug dealer. I don't know what he laced them with, but I've been tripping all day",
				"I'm so good at sleeping. I can do it with my eyes closed",
				"My boss told me to have a good day.. so I went home",
				"Why do blind people hate skydiving? It scares the hell out of their dogs",
				"What do you call a guy with a rubber toe? Roberto",
				"I know a lot of jokes about unemployed people but none of them work" };

		return x[(int) (Math.random() * ((5 - 0) + 1))];
	}

	private static String time() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		String x = dateFormat.format(date);
		int y = Integer.parseInt(x.substring(0, 2));
		return dateFormat.format(date);
	}

	public static void main(String[] args) throws Exception {
//		String path = "C:/Users/moham/Spyder-python/Output.TXT";
		String path = new File("").getAbsolutePath() + "/";
		while (true) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(path));
				while (br.ready()) {
					String result = br.readLine();
					if (result.equals("") || result.charAt(0) == '$')
						continue;
					if (result.equals("bye bye")) {
						System.out.println("You said: " + result + "\n");
						FileChannel.open(Paths.get(path), StandardOpenOption.WRITE).truncate(0).close();
						try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
							out.println("!see you soon sir");
						} catch (IOException e) {
							System.err.println(e);
						}
						return;
					} else {
						System.out.println("You said: " + result + "\n");
						String say = organizer(result);
						System.out.println("done");
						FileChannel.open(Paths.get(path), StandardOpenOption.WRITE).truncate(0).close();
						try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)))) {
							out.println("$" + say);
						} catch (IOException e) {
							System.err.println(e);
						}
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

	}

}
