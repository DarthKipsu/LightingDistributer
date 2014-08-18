package lightdistributer.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lightdistributer.domain.RoadGeometry;
import lightdistributer.domain.Road;

public class Distributer {

	private Road road;
	private List<RoadGeometry> geometry;
	private List<Integer> stakes;
	private List<Integer> forcedPoints;
	private int beginningStake;

	public Distributer(Road road, int beginningStake) {
		this.road = road;
		this.beginningStake = beginningStake;
		geometry = road.getRoadGeometry();
	}

	private void addStakes() {
		int endStake = geometry.get(geometry.size() - 1).getEnd();
		createNewStakesAndForcedPoints();
		distributeInterval(beginningStake, endStake, 0);
	}

	private void createNewStakesAndForcedPoints() {
		stakes = new ArrayList<>();
		forcedPoints = new ArrayList<>();

		forcedPoints.add(beginningStake);
	}

	private void distributeInterval(int beginning, int end, int i) {
		List<Integer> stakeTemp = new ArrayList<>();
		stakeTemp.add(beginning);
		Double leftOvers = 0.0;

		while (last(stakeTemp) <= end) {
			int geometryEnd = geometry.get(i).getEnd();
			int nextStake = nextStake(stakeTemp, i, leftOvers, beginning, end);

			if (geometryEnd >= nextStake) {
				if (geometry.get(i).isColumnsAllowed()) {
					stakeTemp.add(nextStake);
					leftOvers = 0.0;
				} else if (canBePlacedAfter(i, stakeTemp.size())) {
					stakeTemp.clear();
					forcedPoints.add(geometry.get(i+1).getBeginning());
					distributeInterval(forcedPoints.get(forcedPoints.size()-2), forcedPoints.get(forcedPoints.size()-1), road.getGeometryIndex(forcedPoints.size()-2));
					stakeTemp.add(stakes.get(stakes.size()-1));
					stakes.remove(stakes.size()-1);
					leftOvers = 0.0;
				} else {

				}
			} else if (geometry.size() == i + 1) {
				if (geometryEnd - last(stakeTemp) > 0.5 * sMax(i, beginning, end)) {
					stakeTemp.add(geometryEnd);
				}
				break;
			} else {
				leftOvers += (nextStake - geometryEnd) / (double) sMax(i, beginning, end);
				i++;
			}
		}

		stakes.addAll(stakeTemp);

	}

	private int nextStake(List<Integer> stakeTemp, int i, Double leftOvers, int beginning, int end) {
		if (leftOvers > 0.0) {
			double nextStake = geometry.get(i).getBeginning() + (leftOvers * sMax(i, beginning, end));
			int sMaxI = geometry.get(i).getSmax();
			int sMax2 = geometry.get(i-1).getSmax();
			if (nextStake > sMaxI && nextStake > sMax2) {
				nextStake = sMaxI<sMax2?sMax2:sMaxI;
				nextStake = last(stakeTemp) + nextStake;
			}
			return (int) Math.round(nextStake);
		}
		double nextStake = last(stakeTemp) + sMax(i, beginning, end);
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
		int intervalBeginning = forcedPoints.get(forcedPoints.size()-1);
		double neededColumnsWithMaxSpacing = exactColumnCount(intervalBeginning, restrictionEnd);
		return neededColumnsWithMaxSpacing >= columns;
	}

	private int sMax(int i, int beginning, int end) {
		return (int) (smoothingFactor(beginning, end) * geometry.get(i).getSmax());
	}

	public List<Integer> getStakes() {
		addStakes();
		return sortedStakes();
	}

	private List<Integer> sortedStakes() {
		List<Integer> sorted = new ArrayList<>(stakes);
		Collections.sort(sorted);
		return sorted;
	}

	private double smoothingFactor(int beginning, int end) {
		return exactColumnCount(beginning, end) / getNeededColumns(beginning, end);
	}

	public int getNeededColumns() {
		int columns = (int) Math.ceil(exactColumnCount());
		return columns;
	}

	public int getNeededColumns(int beginning, int end) {
		int columns = (int) Math.ceil(exactColumnCount(beginning, end));
		return columns;
	}

	private double exactColumnCount() {
		double columns = 0.0;
		for (RoadGeometry interval : geometry) {
			columns += interval.length() / (double) interval.getSmax();
		}
		return columns + 1;
	}

	private double exactColumnCount(int beginning, int end) {
		double columns = 0.0;
		for (RoadGeometry roadGeometry : geometry) {
			if (road.intervalContainsGeometry(roadGeometry, beginning, end)) {
				if (roadGeometry.getBeginning() >= beginning && roadGeometry.getEnd() <= end) {
				columns += roadGeometry.length() / (double) roadGeometry.getSmax();
				} else if (roadGeometry.getBeginning() >= beginning && roadGeometry.getEnd() > end) {
					columns += (end - roadGeometry.getBeginning()) / (double) roadGeometry.getSmax();
					break;
				} else if (roadGeometry.getBeginning() < beginning && roadGeometry.getEnd() <= end) {
					columns += (roadGeometry.getEnd() - beginning) / (double) roadGeometry.getSmax();
				} else if (roadGeometry.getBeginning() < beginning && roadGeometry.getEnd() > end) {
					columns += (end - beginning) / (double) roadGeometry.getSmax();
					break;
				}
			}
		}
		return columns + 1;
	}

}
