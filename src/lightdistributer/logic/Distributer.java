
package lightdistributer.logic;

import java.util.ArrayList;
import java.util.List;
import lightdistributer.domain.StakeInterval;
import lightdistributer.domain.Street;

public class Distributer {
	private Street street;
	private List<StakeInterval> stakeIntervals;
	private List<Integer> stakes;
	private int beginningStake;

	public Distributer(Street street, int beginningStake) {
		this.street = street;
		this.beginningStake = beginningStake;
		stakeIntervals = street.getStakeIntervals();
	}

	private void addStakes(int beginningStake) {
		stakes = new ArrayList<Integer>();
		stakes.add(beginningStake);
		double smoothing = getSmoothingFactor();
		double leftOverFromPreviousInterval = 0;
		for (int i=0; i<stakeIntervals.size(); i++) {
			int sMax = stakeIntervals.get(i).getSmax();
			if (leftOverFromPreviousInterval > 0) {
				double spacingLeft = 1 -leftOverFromPreviousInterval;
				int spacing = (int) (spacingLeft * (smoothing * sMax));
				stakes.add(stakeIntervals.get(i).getBeginning() + spacing);
			}
			double columns = getIntervalColumns(stakeIntervals.get(i));
			while (columns >= 1.0) {
				stakes.add(previousStake() + (int) (smoothing * sMax));
				columns--;
			}
			leftOverFromPreviousInterval = columns;
		}
	}

	public List<Integer> getStakes() {
		addStakes(beginningStake);
		return stakes;
	}

	private Integer previousStake() {
		return stakes.get(stakes.size()-1);
	}

	private double getIntervalColumns(StakeInterval interval) {
		return (interval.getEnd() - previousStake()) /
			(getSmoothingFactor() * interval.getSmax());
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
			columns += interval.length() / (double) interval.getSmax();
		}
		return columns + 1;
	}

}
