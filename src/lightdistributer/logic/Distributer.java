package lightdistributer.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lightdistributer.domain.RoadGeometry;
import lightdistributer.domain.Road;

public class Distributer {

	private Road road;
	private ColumnCounter counter;
	private List<Integer> stakes;
	private List<Integer> forcedPoints;
	private int beginningStake;
	private int endStake;
	private double leftOvers;
	private List<Integer> stakeTemp;

	public Distributer(Road road, int beginningStake) {
		this.road = road;
		this.beginningStake = beginningStake;
		counter = new ColumnCounter(road);
		
	}

	public int getNeededColumns() {
		return counter.getNeededColumns();
	}

	public List<Integer> getStakes() {
		addStakes();
		return sortedStakes();
	}

	private void addStakes() {
		createVariables();
		distributeInterval(0);
	}

	private void createVariables() {
		stakes = new ArrayList<>();
		forcedPoints = new ArrayList<>();
		forcedPoints.add(beginningStake);
		endStake = road.getEndStakeFor(road.getSize()-1);
	}

	private void distributeInterval(int i) {
		createNewStakeTemp();
		resetLeftovers();
		addStakesToStakeTempStartingFrom(i);
		moveStakeTempToStakes();
	}

	private List<Integer> createNewStakeTemp() {
		stakeTemp = new ArrayList<>();
		stakeTemp.add(beginningStake);
		return stakeTemp;
	}

	private void resetLeftovers() {
		leftOvers = 0.0;
	}

	private void addStakesToStakeTempStartingFrom(int i) {
		while (lastStake() < endStake) {
			if (nextStakeFitsInGeometry(i)) {
				addNewStake(i);
				resetLeftovers();
			} else if (onlyOneStakeLeft(i)) {
				addFinalStake(i);
				break;
			} else {
				addRestOfTheGeometryToLeftovers(i);
				i++;
			}
		}
	}

	private int lastStake() {
		if (stakeTemp.isEmpty()) return 0;
		else return stakeTemp.get(stakeTemp.size() - 1);
	}

	private boolean nextStakeFitsInGeometry(int i) {
		return endOf(i) >= nextStake(i);
	}

	private int endOf(int i) {
		return road.getEndStakeFor(i);
	}

	private int nextStake(int i) {
		int nextStake = lastStakePlusSmax(i);
		if (leftOvers > 0.0) return leftoversPlusSmax(i);
		if (nextStake > endStake) return endStake;
		return nextStake;
	}

	private int leftoversPlusSmax(int i) {
		double nextStake = road.getBeginningStakeFor(i)
			+ (leftOvers * sMax(i));
		nextStake = makeSureSmaxIsntViolated(i, nextStake);
		return (int) Math.round(nextStake);
	}

	private int sMax(int i) {
		return (int) (smoothingFactor() * road.getSmax(i));
	}

	private double smoothingFactor() {
		return counter.exactColumnCount(beginningStake, endStake)
			/ counter.getNeededColumns(beginningStake, endStake);
	}

	private double makeSureSmaxIsntViolated(int i, double nextStake) {
		int sMaxI = road.getSmax(i);
		int sMax2 = road.getSmax(i-1);

		if (nextStake > sMaxI && nextStake > sMax2) {
			nextStake = sMaxI < sMax2 ? sMax2 : sMaxI;
			nextStake = lastStake() + nextStake;
		}
		return nextStake;
	}

	private int lastStakePlusSmax(int i) {
		double nextStake = lastStake() + sMax(i);
		return (int) Math.round(nextStake);
	}

	private void addNewStake(int i) {
		if (geometryIsntRestricted(i)) {
			stakeTemp.add(nextStake(i));
		} else if (sMaxAllowsPlacingAfterRestriction(i)) {
			placeStakeAfterForcedPoint(i);
		} else {
			placeStakeBeforeForcedPoint(i);
		}
	}

	private boolean geometryIsntRestricted(int i) {
		return road.isColumnsAllowed(i);
	}

	private boolean sMaxAllowsPlacingAfterRestriction(int i) {
		return canBePlacedAfter(i);
	}

	private boolean canBePlacedAfter(int i) {
		int restrictionEnd = road.getEndStakeFor(i) + 1;
		int intervalBeginning = forcedPoints.get(forcedPoints.size() - 1);
		double neededColumnsWithMaxSpacing
			= counter.exactColumnCount(intervalBeginning, restrictionEnd);

		return neededColumnsWithMaxSpacing <= stakeTemp.size()+1;
	}

	private void addFinalStake(int i) {
		if (endOf(i) - lastStake() > 0.5 * sMax(i)) {
			stakeTemp.add(endOf(i));
		}
	}

	private void placeStakeAfterForcedPoint(int i) {
		forcedPoints.add(road.getBeginningStakeFor(i+1));
		divideColumnsBetweenForcedPoints();
	}

	private void placeStakeBeforeForcedPoint(int i) {
		forcedPoints.add(road.getEndStakeFor(i-1));
		divideColumnsBetweenForcedPoints();
	}

	private void divideColumnsBetweenForcedPoints() {
		int savedEnd = endStake;
		
		addForcedPointAsEndStake();
		distributeInterval(getBeginningFromForcedPoints());
		restartStakeTemp();

		endStake = savedEnd;
	}

	private void addForcedPointAsEndStake() {
		endStake = forcedPoints.get(forcedPoints.size()-1);
	}

	private int getBeginningFromForcedPoints() {
		return road.getGeometryIndex(forcedPoints.size() - 2);
	}

	private void restartStakeTemp() {
		stakeTemp.add(stakes.get(stakes.size() - 1));
		stakes.remove(stakes.size() - 1);
	}

	private boolean onlyOneStakeLeft(int i) {
		return road.getSize() == i + 1;
	}

	private void addRestOfTheGeometryToLeftovers(int i) {
		leftOvers += (nextStake(i) - endOf(i)) / (double) sMax(i);
	}

	private void moveStakeTempToStakes() {
		stakes.addAll(stakeTemp);
		stakeTemp.clear();
	}

	private List<Integer> sortedStakes() {
		List<Integer> sorted = new ArrayList<>(stakes);
		Collections.sort(sorted);
		return sorted;
	}

}
