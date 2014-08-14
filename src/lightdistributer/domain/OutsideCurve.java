
package lightdistributer.domain;

public class OutsideCurve extends StakeInterval {
	private int radius;
	private int sMax;

	public OutsideCurve(int beginning, int end, int sMax, int radius) {
		super(beginning, end);
		this.radius = radius;
		this.sMax = setSmax(sMax);
	}
	
	private int setSmax(int sMax) {
		if (radius <= 100) {
			return (int) Math.floor(0.8 * sMax);
		} else if (radius > 1000) {
			return sMax;
		} else {
			return 0; // TODO after getting correction factor.
		}
	}

	@Override
	public int getSmax() {
		return sMax;
	}
	
}
