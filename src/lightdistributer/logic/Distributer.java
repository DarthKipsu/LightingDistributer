package lightdistributer.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lightdistributer.domain.RoadGeometry;
import lightdistributer.domain.Road;

public class Distributer {

	private Road road;
	private List<RoadGeometry> geometry;
	private ColumnCounter counter;
	private List<Integer> stakes;
	private List<Integer> forcedPoints;
	private int beginningStake;
	private int endStake;

	public Distributer(Road road, int beginningStake) {
		this.road = road;
		this.beginningStake = beginningStake;
		geometry = road.getRoadGeometry();
		counter = new ColumnCounter(road);
		
	}

	public List<Integer> getStakes() {
		addStakes();
		return sortedStakes();
	}

	private void addStakes() {
		endStake = geometry.get(geometry.size() - 1).getEnd();
		createNewStakesAndForcedPoints();
		distributeInterval(0);
	}

	private void createNewStakesAndForcedPoints() {
		stakes = new ArrayList<>();
		forcedPoints = new ArrayList<>();

		forcedPoints.add(beginningStake);
	}

	private void distributeInterval(int i) {
		List<Integer> stakeTemp = new ArrayList<>();
		stakeTemp.add(beginningStake);
		Double leftOvers = 0.0;

		addStakesToStakeTemp(stakeTemp, i, leftOvers);

		stakes.addAll(stakeTemp);

	}

	private void addStakesToStakeTemp(List<Integer> stakeTemp, int i, Double leftOvers) {
		while (last(stakeTemp) < endStake) {
			int geometryEnd = geometry.get(i).getEnd();
			int nextStake = nextStake(stakeTemp, i, leftOvers);

			if (geometryEnd >= nextStake) {
				addNewStake(i, stakeTemp, nextStake);
				leftOvers = 0.0;
			} else if (geometry.size() == i + 1) {
				addLastStake(geometryEnd, stakeTemp, i);
				break;
			} else {
				leftOvers += (nextStake - geometryEnd) / (double) sMax(i);
				i++;
			}
		}
	}

	private void addNewStake(int i, List<Integer> stakeTemp, int nextStake) {
		if (geometry.get(i).isColumnsAllowed()) {
			stakeTemp.add(nextStake);
		} else if (canBePlacedAfter(i, stakeTemp.size()+1)) {
			placeStakeAfterForcedPoint(stakeTemp, i);
		} else {
			placeStakeBeforeForcedPoint(stakeTemp, i);
		}
	}

	private void placeStakeAfterForcedPoint(List<Integer> stakeTemp, int i) {
		forcedPoints.add(geometry.get(i + 1).getBeginning());
		divideColumnsBetweenForcedPoints(stakeTemp);
	}

	private void placeStakeBeforeForcedPoint(List<Integer> stakeTemp, int i) {
		forcedPoints.add(geometry.get(i - 1).getEnd());
		divideColumnsBetweenForcedPoints(stakeTemp);
	}

	private void divideColumnsBetweenForcedPoints(List<Integer> stakeTemp) {
		stakeTemp.clear();
		int savedEnd = endStake;
		endStake = forcedPoints.get(forcedPoints.size()-1);
		
		distributeInterval(road.getGeometryIndex(forcedPoints.size() - 2));

		stakeTemp.add(stakes.get(stakes.size() - 1));
		stakes.remove(stakes.size() - 1);
		endStake = savedEnd;
	}

	private void addLastStake(int geometryEnd, List<Integer> stakeTemp, int i) {
		if (geometryEnd - last(stakeTemp) > 0.5 * sMax(i)) {
			stakeTemp.add(geometryEnd);
		}
		return;
	}

	private int nextStake(List<Integer> stakeTemp, int i, Double leftOvers) {
		if (leftOvers > 0.0) {
			return leftoversPlusSmax(i, leftOvers, stakeTemp);
		} 
		int nextStake = lastStakePlusSmax(stakeTemp, i);
		
		if (nextStake > endStake) {
			return endStake;
		}
		return nextStake;
	}

	private int leftoversPlusSmax(int i, Double leftOvers, List<Integer> stakeTemp) {
		double nextStake = geometry.get(i).getBeginning()
			+ (leftOvers * sMax(i));
		nextStake = makeSureSmaxIsntViolated(i, nextStake, stakeTemp);
		return (int) Math.round(nextStake);
	}

	private double makeSureSmaxIsntViolated(int i, double nextStake, List<Integer> stakeTemp) {
		int sMaxI = geometry.get(i).getSmax();
		int sMax2 = geometry.get(i - 1).getSmax();

		if (nextStake > sMaxI && nextStake > sMax2) {
			nextStake = sMaxI < sMax2 ? sMax2 : sMaxI;
			nextStake = last(stakeTemp) + nextStake;
		}
		return nextStake;
	}

	private int lastStakePlusSmax(List<Integer> stakeTemp, int i) {
		double nextStake = last(stakeTemp) + sMax(i);
		return (int) Math.round(nextStake);
	}

	private int last(List<Integer> stakeTemp) {
		if (stakeTemp.isEmpty()) {
			return 0;
		}
		return stakeTemp.get(stakeTemp.size() - 1);
	}

	private boolean canBePlacedAfter(int restrictedInterval, int columns) {
		int restrictionEnd = geometry.get(restrictedInterval).getEnd() + 1;
		int intervalBeginning = forcedPoints.get(forcedPoints.size() - 1);
		double neededColumnsWithMaxSpacing
			= counter.exactColumnCount(intervalBeginning, restrictionEnd);

		return neededColumnsWithMaxSpacing <= columns;
	}

	private int sMax(int i) {
		return (int) (smoothingFactor() * geometry.get(i).getSmax());
	}

	private List<Integer> sortedStakes() {
		List<Integer> sorted = new ArrayList<>(stakes);
		Collections.sort(sorted);
		return sorted;
	}

	private double smoothingFactor() {
		return counter.exactColumnCount(beginningStake, endStake)
			/ counter.getNeededColumns(beginningStake, endStake);
	}

	public int getNeededColumns() {
		return counter.getNeededColumns();
	}

}
