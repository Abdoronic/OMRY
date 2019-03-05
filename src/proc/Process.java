package proc;

public class Process extends Thread implements Comparable<Process> {
	private PCB pcb;
	private long deadline;
	
	public Process(PCB pcb, long deadline) {
		super();
		this.pcb = pcb;
		this.deadline = deadline;
	}
	
	@Override
	public void run() {
		int cnt = 0;
		for(int i = 0; i < 10000000; i++)
			cnt++;
		System.out.println(cnt);
	}

	public PCB getPcb() {
		return pcb;
	}

	public long getDeadline() {
		return deadline;
	}

	@Override
	public int compareTo(Process o) {
		return (int)(deadline - o.deadline);
	}
	
}
