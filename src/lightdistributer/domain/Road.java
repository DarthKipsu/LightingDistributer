
package lightdistributer.domain;

import java.util.ArrayList;
import java.util.List;

public class Road {
	private List<RoadGeometry> geometry;
	private int sMax;

	public Road(int sMax) {
		geometry = new ArrayList<RoadGeometry>();
		this.sMax = sMax;
	}

	public void addStraightSection(int beginning, int end, boolean columnsAllowed) {
		geometry.add(new StraightSection(beginning, end, columnsAllowed, sMax));
	}
	
	public void addStraightSection(int end, boolean columnsAllowed) {
		addStraightSection(sectionBeginning(), end, columnsAllowed);
	}
	
	public void addOutsideCurve(int beginning, int end, boolean columnsAllowed, int radius) {
		geometry.add(new OutsideCurve(beginning, end, columnsAllowed, sMax, radius));
	}
	
	public void addOutsideCurve(int end, boolean columnsAllowed, int radius) {
		addOutsideCurve(sectionBeginning(), end, columnsAllowed, radius);
	}
	
	public void addInsideCurve(int beginning, int end, boolean columnsAllowed, int radius) {
		geometry.add(new InsideCurve(beginning, end, columnsAllowed, sMax, radius));
	}
	
	public void addInsideCurve(int end, boolean columnsAllowed, int radius) {
		addInsideCurve(sectionBeginning(), end, columnsAllowed, radius);
	}

	private int sectionBeginning() {
		if (geometry.isEmpty()) return 0;
		else return geometry.get(geometry.size()-1).getEnd(); 
	}
	
	public List<RoadGeometry> getRoadGeometry() {
		return geometry;
	}

	public int getSMax() {
		return sMax;
	}
	
}
