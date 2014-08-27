
package lightdistributer.domain;

public class OutsideCurve extends RoadGeometry {
	private int radius;
	private int sMax;

	public OutsideCurve(int beginning, int end, boolean columnsAllowed, int sMax, int radius) {
		super(beginning, end, columnsAllowed);
		this.radius = radius;
		this.sMax = sMax(sMax);
	}

	@Override
	public void setSmax(int sMax) {
		this.sMax = sMax(sMax);
	}
	
	private int sMax(int sMax) {
		if (radius <= 100) {
			return (int) Math.floor(0.8 * sMax);
		} else if (radius >= 800) {
			return sMax;
		} else {
			double result = 0.09 * Math.log1p(radius) + 0.4;
			return (int) Math.floor(result*sMax);
		}
	}

	@Override
	public int getSmax() {
		return sMax;
	}

	@Override
	public String toString() {
		return getBeginning() + "-" + getEnd() + ", OC radius " + radius;
	}

}
