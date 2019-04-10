package task;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import core.Kernel;
import core.OMRY;
import core.Semaphore;

public class Task extends Thread implements Comparable<Task> {
	private Kernel kernal;
	private TCB pcb;
	private long deadline;
	private OMRY omry;
	private Semaphore semaphore;
	public Task(Kernel kernal, TCB pcb, long deadline, OMRY omry, Semaphore semaphore) {
		super();
		this.kernal = kernal;
		this.pcb = pcb;
		this.deadline = deadline;
		this.omry = omry;
		this.semaphore = semaphore;
	}
	
	public Semaphore getSemaphore(){
		return semaphore;
	}
	
	public OMRY getOmry() {
		return omry;
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
