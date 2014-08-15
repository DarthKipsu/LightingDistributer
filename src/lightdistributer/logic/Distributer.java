
package lightdistributer.logic;

import java.util.List;
import lightdistributer.domain.StakeInterval;
import lightdistributer.domain.Street;

public class Distributer {
	private Street street;
	private List<StakeInterval> stakeIntervals;

	public Distributer(Street street) {
		this.street = street;
		stakeIntervals = street.getStakeIntervals();
	}

	public int countNeededColumns() {
		double columns = 0.0;
		for (StakeInterval interval : stakeIntervals) {
			columns += (interval.getEnd() - interval.getBeginning()) / (double) interval.getSmax();
		}
		columns = Math.ceil(columns) + 1;
		return (int) columns;
	}
	
}
