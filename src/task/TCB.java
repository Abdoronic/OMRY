package task;

public class TCB {
	
	private long processID;
	private long parentID;
	private boolean type;  //Kernel or user
	private Status processState;
	
	public TCB(long processID, long parentID, boolean type, Status processState) {
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

	public Status getProcessState() {
		return processState;
	}

	public void setProcessState(Status processState) {
		this.processState = processState;
	}
	
}
