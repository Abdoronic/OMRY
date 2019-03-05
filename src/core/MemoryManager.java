package core;

public class MemoryManager {
	private byte[] memory;
	int stackPtr = 0;
	
	public MemoryManager(int meomorySize) {
		memory = new byte[meomorySize];
		stackPtr = 0;
	}
	
	public boolean releaseMemory(int blockSize) {
		if (blockSize > stackPtr)
			return false;
		stackPtr -= blockSize;
		return true;
	}
	
	public boolean allocateMemory(int blockSize) {
		if(remainingMemory() < blockSize)
			return false;
		stackPtr += blockSize;
		return true;
	}
	
	public int remainingMemory() { //Returns remaining bytes
		return memory.length - stackPtr;
	}
}
