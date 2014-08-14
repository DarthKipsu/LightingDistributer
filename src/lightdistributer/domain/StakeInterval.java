
package lightdistributer.domain;

public abstract class StakeInterval {
	private int stakeInterval;

	public StakeInterval(int stakeInterval) {
		this.stakeInterval = stakeInterval;
	}

	public int getStakeInterval() {
		return stakeInterval;
	}

	abstract public int getSmax();

}