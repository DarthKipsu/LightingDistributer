package lightdistributer.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Road {

	private List<RoadGeometry> geometry;
	private Map<RoadGeometry, List<Integer>> stakes;
	private int sMax;

	public Road(int sMax) {
		geometry = new ArrayList<RoadGeometry>();
		stakes = new HashMap<>();
		this.sMax = sMax;
	}

	public void addStraightSection(int beginning, int end, boolean columnsAllowed) {
		RoadGeometry newSection = new StraightSection(beginning, end, columnsAllowed, sMax);
		geometry.add(newSection);
		stakesAdd(newSection);
	}

	public void addStraightSection(int end, boolean columnsAllowed) {
		addStraightSection(sectionBeginning(), end, columnsAllowed);
	}

	public void addOutsideCurve(int beginning, int end, boolean columnsAllowed, int radius) {
		RoadGeometry newSection = new OutsideCurve(beginning, end, columnsAllowed, sMax, radius);
		geometry.add(newSection);
		stakesAdd(newSection);
	}

	public void addOutsideCurve(int end, boolean columnsAllowed, int radius) {
		addOutsideCurve(sectionBeginning(), end, columnsAllowed, radius);
	}

	public void addInsideCurve(int beginning, int end, boolean columnsAllowed, int radius) {
		RoadGeometry newSection = new InsideCurve(beginning, end, columnsAllowed, sMax, radius);
		geometry.add(newSection);
		stakesAdd(newSection);
	}

	public void addInsideCurve(int end, boolean columnsAllowed, int radius) {
		addInsideCurve(sectionBeginning(), end, columnsAllowed, radius);
	}

	private int sectionBeginning() {
		if (geometry.isEmpty()) {
			return 0;
		} else {
			return geometry.get(geometry.size() - 1).getEnd() + 1;
		}
	}

	private void stakesAdd(RoadGeometry section) {
		List<Integer> sectionStakes = new ArrayList<>();
		for (int i = section.getBeginning(); i <= section.getEnd(); i++) {
			sectionStakes.add(i);
		}
		stakes.put(section, sectionStakes);
	}

	public boolean intervalContainsGeometry(RoadGeometry section, int beginning, int end) {
		for (int i = beginning; i <= end; i++) {
			if (stakes.get(section).contains(i)) {
				return true;
			}
		}
		return false;
	}

	public int getGeometryIndex(int stake) {
		RoadGeometry toFind = geometry.get(0);
		for (RoadGeometry section : stakes.keySet()) {
			if (stakes.get(section).contains(stake)) {
				toFind = section;
				break;
			}
		}
		return geometry.indexOf(toFind);
	}

	public List<RoadGeometry> getRoadGeometry() {
		return geometry;
	}

	public int getSMax() {
		return sMax;
	}

}
