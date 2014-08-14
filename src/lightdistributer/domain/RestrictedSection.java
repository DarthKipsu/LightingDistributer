
package lightdistributer.domain;

public class RestrictedSection extends StakeInterval {
	private int sMax;

	public RestrictedSection(int beginning, int end, int sMax) {
		super(beginning, end);
		this.sMax = sMax;
	}

	@Override
	public int getSmax() {
		return sMax;
	}
	
}
