
package lightdistributer.domain;

public class InsideCurve extends RoadGeometry {
	private int radius;
	private int sMax;

	public InsideCurve(int beginning, int end, boolean columnsAllowed, int sMax, int radius) {
		super(beginning, end, columnsAllowed);
		this.radius = radius;
		this.sMax = setSmax(sMax);
	}
	
	private int setSmax(int sMax) {
		if (radius <= 100) {
			return (int) Math.floor(0.65 * sMax);
		} else if (radius >= 800) {
			return sMax;
		} else {
			double result = 0.18 * Math.log1p(radius) - 0.2;
			return (int) Math.floor(result*sMax);
		}
	}

	@Override
	public int getSmax() {
		return sMax;
	}
	
}
