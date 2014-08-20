/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lightdistributer.logic;

import java.util.List;
import lightdistributer.domain.Road;
import lightdistributer.domain.RoadGeometry;

/**
 *
 * @author kipsu
 */
public class ColumnCounter {
	private List<RoadGeometry> geometry;
	private Road road;

	public ColumnCounter(Road road, List<RoadGeometry> geometry) {
		this.road = road;
		this.geometry = geometry;
	}

	private double countColumnsBetween(int beginning, int end) {
		double columns = 0.0;
		for (RoadGeometry roadGeometry : geometry) {
			if (road.intervalContainsGeometry(roadGeometry, beginning, end)) {
				columns = addColumnsFromGeometry(roadGeometry, beginning, end, columns);
			}
		}
		return columns;
	}

	public int getNeededColumns() {
		int columns = (int) Math.ceil(exactColumnCount());
		return columns;
	}

	public int getNeededColumns(int beginning, int end) {
		int columns = (int) Math.ceil(exactColumnCount(beginning, end));
		return columns;
	}

	public double exactColumnCount(int beginning, int end) {
		double columns = countColumnsBetween(beginning, end);
		return columns + 1;
	}

	public double exactColumnCount() {
		double columns = 0.0;
		for (RoadGeometry interval : geometry) {
			columns += interval.length() / (double) interval.getSmax();
		}
		return columns + 1;
	}

	private double addColumnsFromGeometry(RoadGeometry roadGeometry, int beginning, int end, double columns) {
		if (geometryFitsInside(roadGeometry, beginning, end)) {
			columns += roadGeometry.length() / (double) roadGeometry.getSmax();
		} else if (geometryEndIsOutside(roadGeometry, beginning, end)) {
			columns += (end - roadGeometry.getBeginning()) / (double) roadGeometry.getSmax();
		} else if (geometryEndIsInside(roadGeometry, beginning, end)) {
			columns += (roadGeometry.getEnd() - beginning) / (double) roadGeometry.getSmax();
		} else if (geometryIsAround(roadGeometry, beginning, end)) {
			columns += (end - beginning) / (double) roadGeometry.getSmax();
		}
		return columns;
	}

	private static boolean geometryFitsInside(RoadGeometry roadGeometry, int beginning, int end) {
		return roadGeometry.getBeginning() >= beginning && roadGeometry.getEnd() <= end;
	}

	private static boolean geometryEndIsOutside(RoadGeometry roadGeometry, int beginning, int end) {
		return roadGeometry.getBeginning() >= beginning && roadGeometry.getEnd() > end;
	}

	private static boolean geometryEndIsInside(RoadGeometry roadGeometry, int beginning, int end) {
		return roadGeometry.getBeginning() < beginning && roadGeometry.getEnd() <= end;
	}

	private static boolean geometryIsAround(RoadGeometry roadGeometry, int beginning, int end) {
		return roadGeometry.getBeginning() < beginning && roadGeometry.getEnd() > end;
	}

}
