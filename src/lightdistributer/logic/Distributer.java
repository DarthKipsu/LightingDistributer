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
	private Set<Integer> stakes;
	private List<Integer> forcedPoints;
	private int beginningStake;
	
	public Distributer(Road road, int beginningStake) {
		this.road = road;
		this.beginningStake = beginningStake;
		geometry = road.getRoadGeometry();
	}

	private void addStakes() {
		stakes = new HashSet<>();
		forcedPoints = new ArrayList<>();
		int endStake = geometry.get(geometry.size()-1).getEnd();

		stakes.add(beginningStake);
		forcedPoints.add(beginningStake);
		
		distributeInterval(beginningStake, endStake, 0);
	}

	private void distributeInterval(int beginning, int end, int i) {
		List<Integer> stakeTemp = new ArrayList<>();
		stakeTemp.add(beginning);
		Double leftOvers = 0.0;

		while (last(stakeTemp) <= end) {
			int geometryEnd = geometry.get(i).getEnd();
			int nextStake = nextStake(stakeTemp, i, leftOvers);
			
			if (geometryEnd >= nextStake) {
				stakeTemp.add(nextStake);
				leftOvers = 0.0;
			} else if (geometry.size() == i+1) {
				if (geometryEnd - last(stakeTemp) > 0.5*sMax(i)) {
						stakeTemp.add(geometryEnd);
				}
				break;
			} else {
				leftOvers += (nextStake - geometryEnd) / (double) sMax(i);
				i++;
			}
		}

		stakes.addAll(stakeTemp);

	}

	private int nextStake(List<Integer> stakeTemp, int i, Double leftOvers) {
		if (leftOvers > 0.0) {
			return geometry.get(i).getBeginning() + (int) (leftOvers * sMax(i));
		}
		return last(stakeTemp) + sMax(i);
	}

	private int last(List<Integer> stakeTemp) {
		return stakeTemp.get(stakeTemp.size()-1);
	}

	private int sMax(int i) {
		return (int) (smoothingFactor() * geometry.get(i).getSmax());
	}

	public List<Integer> getStakes() {
		addStakes();
		List<Integer> stakeExport = new ArrayList<>(stakes);
		Collections.sort(stakeExport);
		return stakeExport;
	}

	private double smoothingFactor() {
		return exactColumnCount() / getNeededColumns();
	}

	public int getNeededColumns() {
		int columns = (int) Math.ceil(exactColumnCount());
		return columns;
	}

	private double exactColumnCount() {
		double columns = 0.0;
		for (RoadGeometry interval : geometry) {
			columns += interval.length() / (double) interval.getSmax();
		}
		return columns + 1;
	}

}
