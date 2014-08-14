
package lightdistributer.domain;

public class StraightSection extends StakeInterval {
	private int sMax;

	public StraightSection(int beginning, int end, int sMax) {
		super(beginning, end);
		this.sMax = sMax;
	}

	@Override
	public int getSmax() {
		return sMax;
	}
	
}
