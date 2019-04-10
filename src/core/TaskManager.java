package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
	private PrintWriter log;
	
	public TaskManager() throws FileNotFoundException {
		readyQueue = new PriorityQueue<>();
		blockedQueue = new ArrayList<>();
		lateQueue = new PriorityQueue<>();
		System.err.println(new File("").getAbsolutePath());
		log = new PrintWriter(new File(new File("").getAbsolutePath()+"\\src\\disk\\log.txt"));
	}
	
	public void addTask(Task newTask) {
		readyQueue.add(newTask);
		runNextTask();
	}
	public PrintWriter getLog()
	{
		return log;
	}
	@SuppressWarnings("deprecation")
	public void runNextTask() {
		Task pre = runningTask;
		Object susp=null,run=null;
		if(pre != null && !readyQueue.isEmpty() && pre.getPcb().getProcessState() != Status.TERMINATED &&pre.getDeadline() < readyQueue.peek().getDeadline())
			return;
		if(readyQueue.isEmpty())
			return;
		Task next = readyQueue.poll();
		if(next.getDeadline() < System.currentTimeMillis()) {
			next.getPcb().setProcessState(Status.PASSED_DEADLINE);
			lateQueue.add(next);
			runNextTask();
			System.err.println("late");
		} else {
			if(next.getPcb().getProcessState() == Status.NEW)
			{
				next.start();
				System.err.println("start");
			}
			else {
				System.err.println("yalla ya mada fa");
				synchronized (next) {
					next.resume();
				}
			}
			System.err.println("run mada fa");
			run=new String("Process Number "+next.getPcb().getProcessID()+" is running");
			next.getPcb().setProcessState(Status.RUNNING);
			runningTask = next;
		}
		if(pre != null && pre.getPcb().getProcessState() == Status.RUNNING) {
			synchronized (pre) {
				pre.suspend();
				System.err.println("Wait mada fa");
				susp=new String("Process Number "+pre.getPcb().getProcessID()+" is suspended");
			}
			readyQueue.add(pre);
			pre.getPcb().setProcessState(Status.READY);
		}
		if(susp!=null)
			log.println(susp.toString());
		if(run!=null)
			log.println(run.toString());
		log.flush();
	}
	
	public void terminateRunnigTask() {
		Task curr = runningTask;
		curr.getPcb().setProcessState(Status.TERMINATED);
		log.println("Process Number "+curr.getPcb().getProcessID()+" is finished");
		log.flush();
		runNextTask();
	}
	
	@SuppressWarnings("deprecation")
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
	
	@SuppressWarnings("deprecation")
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
