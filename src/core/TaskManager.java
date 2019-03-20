package core;

import java.util.ArrayList;
import java.util.PriorityQueue;

import task.Status;
import task.Task;

public class TaskManager {
	
	/*
	 * Scheduling is based on the deadline of the process
	 * the earliest the deadline the higher the priority.
	 */
	
	private PriorityQueue<Task> readyQueue;
	private ArrayList<Task> blockedQueue;
	private PriorityQueue<Task> lateQueue;
	private Task runningTask;
	
	public TaskManager() {
		readyQueue = new PriorityQueue<>();
		blockedQueue = new ArrayList<>();
		lateQueue = new PriorityQueue<>();
	}
	
	public void addTask(Task newTask) {
		readyQueue.add(newTask);
		runNextTask();
	}
	
	@SuppressWarnings("deprecation")
	public void runNextTask() {
		Task pre = runningTask;
		if(pre != null && !readyQueue.isEmpty() && pre.getPcb().getProcessState() != Status.TERMINATED &&pre.getDeadline() < readyQueue.peek().getDeadline())
			return;
		if(readyQueue.isEmpty())
			return;
		Task next = readyQueue.poll();
		if(next.getDeadline() < System.currentTimeMillis()) {
			next.getPcb().setProcessState(Status.PASSED_DEADLINE);
			lateQueue.add(next);
			runNextTask();
		} else {
			if(next.getPcb().getProcessState() == Status.NEW)
				next.start();
			else {
				System.err.println("yalla ya mada fa");
				synchronized (next) {
					next.resume();
				}
			}
			System.err.println("run mada fa");
			next.getPcb().setProcessState(Status.RUNNING);
			runningTask = next;
		}
		if(pre != null && pre.getPcb().getProcessState() == Status.RUNNING) {
			synchronized (pre) {
				pre.suspend();
				System.err.println("Wait mada fa");
			}
			readyQueue.add(pre);
			pre.getPcb().setProcessState(Status.READY);
		}
	}
	
	public void terminateRunnigTask() {
		Task curr = runningTask;
		curr.getPcb().setProcessState(Status.TERMINATED);
		runNextTask();
	}
	
	public void respondToListenInterrupt() {
		if(runningTask != null) {
			runningTask.getPcb().setProcessState(Status.BLOCKED);
			synchronized (runningTask) {
				runningTask.suspend();
				System.err.println("Wait mada fa");
			}
			blockedQueue.add(runningTask);
		}
	}
	
	public void unblockListenInterrupt() {
		if(runningTask != null) {
			runningTask.getPcb().setProcessState(Status.RUNNING);
			System.err.println("yalla ya mada fa");
			synchronized (runningTask) {
				runningTask.resume();
			}
			blockedQueue.remove(runningTask);
		}
	}
	
//	public void respondToInterrupt() {
//	runningTask.getPcb().setProcessState(Status.BLOCKED);
//	runningTask.suspend();
//	blockedQueue.add(runningTask);
//	runNextTask();
//}
//
//public void unblockTask(int pid) {
//	for(int i = 0; i < blockedQueue.size(); i++) {
//		Task p = blockedQueue.get(i);
//		if(p.getPcb().getProcessID() == pid) {
//			p.getPcb().setProcessState(Status.READY);
//			readyQueue.add(p);
//			blockedQueue.remove(i);
//			break;
//		}
//	}
//	runNextTask();
//}
	
}
