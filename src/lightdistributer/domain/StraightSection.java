
package lightdistributer.domain;

public class StraightSection extends StakeInterval {
	private int sMax;

	public StraightSection(int stakeInterval, int sMax) {
		super(stakeInterval);
		this.sMax = sMax;
	}

	@Override
	public int getSmax() {
		return sMax;
	}
	
}
