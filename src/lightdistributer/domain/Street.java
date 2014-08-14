
package lightdistributer.domain;

import java.util.ArrayList;
import java.util.List;

public class Street {
	private List<StakeInterval> stakeIntervals;
	private int sMax;

	public Street(int sMax) {
		stakeIntervals = new ArrayList<StakeInterval>();
		this.sMax = sMax;
	}

	public void addStraightSection(int beginning, int end) {
		stakeIntervals.add(new StraightSection(beginning, end, sMax));
	}
	
	public void addOutsideCurve(int beginning, int end, int radius) {
		stakeIntervals.add(new OutsideCurve(beginning, end, sMax, radius));
	}
	
	public void addInsideCurve(int beginning, int end, int radius) {
		stakeIntervals.add(new InsideCurve(beginning, end, sMax, radius));
	}
	
	public void addRestrictedSection(int beginning, int end) {
		stakeIntervals.add(new RestrictedSection(beginning, end, sMax));
	}

	public List<StakeInterval> getStakeIntervals() {
		return stakeIntervals;
	}
	
}
