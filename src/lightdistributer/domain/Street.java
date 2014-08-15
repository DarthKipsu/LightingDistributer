
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

	public void addStraightSection(int beginning, int end, boolean columnsAllowed) {
		stakeIntervals.add(new StraightSection(beginning, end, columnsAllowed, sMax));
	}
	
	public void addStraightSection(int end, boolean columnsAllowed) {
		addStraightSection(sectionBeginning(), end, columnsAllowed);
	}
	
	public void addOutsideCurve(int beginning, int end, boolean columnsAllowed, int radius) {
		stakeIntervals.add(new OutsideCurve(beginning, end, columnsAllowed, sMax, radius));
	}
	
	public void addOutsideCurve(int end, boolean columnsAllowed, int radius) {
		addOutsideCurve(sectionBeginning(), end, columnsAllowed, radius);
	}
	
	public void addInsideCurve(int beginning, int end, boolean columnsAllowed, int radius) {
		stakeIntervals.add(new InsideCurve(beginning, end, columnsAllowed, sMax, radius));
	}
	
	public void addInsideCurve(int end, boolean columnsAllowed, int radius) {
		addInsideCurve(sectionBeginning(), end, columnsAllowed, radius);
	}

	private int sectionBeginning() {
		if (stakeIntervals.isEmpty()) return 0;
		else return stakeIntervals.get(stakeIntervals.size()-1).getEnd(); 
	}
	
	public List<StakeInterval> getStakeIntervals() {
		return stakeIntervals;
	}

	public int getSMax() {
		return sMax;
	}
	
}
