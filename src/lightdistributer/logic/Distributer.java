
package lightdistributer.logic;

import java.util.List;
import lightdistributer.domain.StakeInterval;
import lightdistributer.domain.Street;

public class Distributer {
	private Street street;
	private List<StakeInterval> stakeIntervals;
	private List<Integer> stakes;

	public Distributer(Street street) {
		this.street = street;
		stakeIntervals = street.getStakeIntervals();
	}

	private double getSmoothingFactor() {
		return countExactColumns() / countNeededColumns();
	}
	
	public int countNeededColumns() {
		int columns = (int) Math.ceil(countExactColumns());
		return columns;
	}

	private double countExactColumns() {
		double columns = 0.0;
		for (StakeInterval interval : stakeIntervals) {
			columns += (interval.getEnd() - interval.getBeginning()) / (double) interval.getSmax();
		}
		return columns + 1;
	}

}
