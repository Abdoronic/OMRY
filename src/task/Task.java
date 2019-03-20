package task;

import core.Kernel;

public class Task extends Thread implements Comparable<Task> {
	private Kernel kernal;
	private TCB pcb;
	private long deadline;
	
	public Task(Kernel kernal, TCB pcb, long deadline) {
		super();
		this.kernal = kernal;
		this.pcb = pcb;
		this.deadline = deadline;
	}
	
	public TCB getPcb() {
		return pcb;
	}

	public long getDeadline() {
		return deadline;
	}

	public Kernel getKernal() {
		return kernal;
	}

	@Override
	public int compareTo(Task o) {
		return (int)(deadline - o.deadline);
	}
	
}
