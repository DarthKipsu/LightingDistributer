
package lightdistributer.domain;

public abstract class StakeInterval {
	private int beginning;
	private int end;
	private boolean columnsAllowed;

	public StakeInterval(int beginning, int end, boolean canBePlaced) {
		this.beginning = beginning;
		this.end = end;
		this.columnsAllowed = canBePlaced;
	}

	public int[] getStakeInterval() {
		int[] interval = {beginning, end};
		return interval;
	}

	public int getBeginning() {
		return beginning;
	}

	public int getEnd() {
		return end;
	}

	public int length() {
		return end - beginning;
	}

	public boolean isColumnsAllowed() {
		return columnsAllowed;
	}

	abstract public int getSmax();

}