package lightdistributer.logic;

import java.util.ArrayList;
import java.util.List;
import lightdistributer.domain.RoadGeometry;
import lightdistributer.domain.Road;

public class Distributer {

	private Road road;
	private List<RoadGeometry> geometry;
	private List<Integer> stakes;
	private int beginningStake;

	public Distributer(Road road, int beginningStake) {
		this.road = road;
		this.beginningStake = beginningStake;
		geometry = road.getRoadGeometry();
	}

	private void addStakes(int beginningStake) {
		stakes = new ArrayList<Integer>();
		stakes.add(beginningStake);
		double smoothing = getSmoothingFactor();
		double leftOverFromPreviousInterval = 0;
		for (int i = 0; i < geometry.size(); i++) {
			RoadGeometry interval = geometry.get(i);
			int sMax = interval.getSmax();
			if (leftOverFromPreviousInterval > 0) {
				int end = interval.getEnd();
				if (getIntervalColumns(interval) >= 1) {
					double spacingLeft = 1 - leftOverFromPreviousInterval;
					int spacing = (int) (spacingLeft * (smoothing * sMax));
					stakes.add(interval.getBeginning() + spacing);
				} else if(i==geometry.size()-1) {
					stakes.add(end);
				} else {
					int intervalLength = interval.getEnd() - interval.getBeginning();
					leftOverFromPreviousInterval += intervalLength / (smoothing * sMax);
					continue;
				}
			}
			double columns = getIntervalColumns(geometry.get(i));
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
		return stakes.get(stakes.size() - 1);
	}

	private double getIntervalColumns(RoadGeometry interval) {
		return (interval.getEnd() - previousStake())
			/ (getSmoothingFactor() * interval.getSmax());
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
		for (RoadGeometry interval : geometry) {
			columns += interval.length() / (double) interval.getSmax();
		}
		return columns + 1;
	}

}
