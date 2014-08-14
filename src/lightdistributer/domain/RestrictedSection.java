
package lightdistributer.domain;

public class RestrictedSection extends StakeInterval {
	private int sMax;

	public RestrictedSection(int stakeInterval, int sMax) {
		super(stakeInterval);
		this.sMax = sMax;
	}

	@Override
	public int getSmax() {
		return sMax;
	}
	
}
