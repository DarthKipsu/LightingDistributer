
package lightdistributer.domain;

import java.util.ArrayList;
import java.util.List;

public class Street {
	private List<StakeInterval> stakeIntervals;
	private int sMax;

	public Street(int sMax) {
		stakeIntervals = new ArrayList<>();
		this.sMax = sMax;
	}

	public void addStraightSection(int beginning, int end) {
		StakeInterval interval = new StraightSection(beginning, end, sMax);
		stakeIntervals.add(interval);
	}
	
	public void addOutsideCurve(int beginning, int end, int radius) {
		StakeInterval interval = new OutsideCurve(beginning, end, sMax, radius);
		stakeIntervals.add(interval);
	}
	
	public void addInsideCurve(int beginning, int end, int radius) {
		StakeInterval interval = new InsideCurve(beginning, end, sMax, radius);
		stakeIntervals.add(interval);
	}
	
	public void addRestrictedSection(int beginning, int end) {
		StakeInterval interval = new RestrictedSection(beginning, end, sMax);
		stakeIntervals.add(interval);
	}
	
}
