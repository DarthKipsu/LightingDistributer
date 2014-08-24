
package lightdistributer.domain;

public class StraightSection extends RoadGeometry {
	private int sMax;

	public StraightSection(int beginning, int end, boolean columnsAllowed, int sMax) {
		super(beginning, end, columnsAllowed);
		this.sMax = sMax;
	}

	@Override
	public int getSmax() {
		return sMax;
	}

	@Override
	public void setSmax(int sMax) {
		this.sMax = sMax;
	}
	
}
