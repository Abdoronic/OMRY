package core;

import java.util.ArrayList;
import java.util.PriorityQueue;

import proc.Process;

public class ProcessManager {
	
	/*
	 * Scheduling is based on the deadline of the process
	 * the earliest the deadline the higher the priority.
	 */
	
	private PriorityQueue<Process> readyQueue;
	private ArrayList<Process> blockedQueue;
	private PriorityQueue<Process> lateQueue;
	private Process runningProcess;
	
	public ProcessManager() {
		readyQueue = new PriorityQueue<>();
		blockedQueue = new ArrayList<>();
		lateQueue = new PriorityQueue<>();
	}
	
	public void runNextProcess() {
		Process pre = runningProcess;
		Process next = readyQueue.poll();
		if(next.getDeadline() < System.currentTimeMillis()) {
			next.getPcb().setProcessState(3);
			lateQueue.add(next);
		} else {
			next.getPcb().setProcessState(1);
			runningProcess = next;
		}
		pre.getPcb().setProcessState(0);
		if(pre.getPcb().getProcessState() == 1)
			readyQueue.add(pre);
	}
	
	public void terminateRunnigProcess() {
		Process curr = runningProcess;
		curr.getPcb().setProcessState(4);
		runNextProcess();
	}
	
	public void respondToInterrupt() {
		runningProcess.getPcb().setProcessState(2);
		blockedQueue.add(runningProcess);
	}
	
	public void unblockProcess(int pid) {
		for(int i = 0; i < blockedQueue.size(); i++) {
			Process p = blockedQueue.get(i);
			if(p.getPcb().getProcessID() == pid) {
				p.getPcb().setProcessState(0);
				readyQueue.add(p);
				blockedQueue.remove(i);
				break;
			}
		}
	}
}
