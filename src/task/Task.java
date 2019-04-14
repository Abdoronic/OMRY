package task;

import core.Kernel;
import core.OMRY;
import core.Semaphore;

public class Task extends Thread implements Comparable<Task> {
	private Kernel kernal;
	private TCB pcb;
	private long deadline;
	private OMRY omry;
	private Semaphore semaphore;
	private String name;
	public Task(Kernel kernal, TCB pcb, long deadline, OMRY omry, Semaphore semaphore, String name) {
		super();
		this.kernal = kernal;
		this.pcb = pcb;
		this.deadline = deadline;
		this.omry = omry;
		this.semaphore = semaphore;
		this.name = name;
	}
	public String getNameTask(){
		return name;
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
