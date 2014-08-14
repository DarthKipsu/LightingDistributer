
package lightdistributer.domain;

public class InsideCurve extends StakeInterval {
	private int radius;
	private int sMax;

	public InsideCurve(int beginning, int end, int sMax, int radius) {
		super(beginning, end);
		this.radius = radius;
		this.sMax = setSmax(sMax);
	}
	
	private int setSmax(int sMax) {
		if (radius <= 100) {
			return (int) Math.floor(0.65 * sMax);
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
