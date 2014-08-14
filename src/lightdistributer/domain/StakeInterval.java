
package lightdistributer.domain;

public abstract class StakeInterval {
	private int beginning;
	private int end;

	public StakeInterval(int beginning, int end) {
		this.beginning = beginning;
		this.end = end;
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

	abstract public int getSmax();

}