package proc;

public class PCB {
	
	private long processID;
	private long parentID;
	private boolean type;  //Kernel or user
	private int processState;
	
	public PCB(long processID, long parentID, boolean type, int processState) {
		this.processID = processID;
		this.parentID = parentID;
		this.type = type;
		this.processState = processState;
	}

	public long getProcessID() {
		return processID;
	}

	public void setProcessID(long processID) {
		this.processID = processID;
	}

	public long getParentID() {
		return parentID;
	}

	public void setParentID(long parentID) {
		this.parentID = parentID;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public int getProcessState() {
		return processState;
	}

	public void setProcessState(int processState) {
		//0 -> ready
		//1 -> running
		//2 -> blocked
		//3 -> passed Deadline (late)
		//4 -> terminated
		this.processState = processState;
	}
	
}
