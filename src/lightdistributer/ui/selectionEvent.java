package lightdistributer.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import lightdistributer.domain.Road;
import lightdistributer.domain.RoadGeometry;

public class selectionEvent implements EventHandler<ActionEvent> {

	private ComboBox combo;
	private TextField endStake;
	private TextField radius;
	private CheckBox restricted;
	private Road road;
	private ListView<String> list;

	public selectionEvent(ComboBox combo, TextField endStake, TextField radius,
		CheckBox restricted, ListView<String> list, Road road) {
		this.combo = combo;
		this.endStake = endStake;
		this.radius = radius;
		this.restricted = restricted;
		this.list = list;
		this.road = road;
	}

	@Override
	public void handle(ActionEvent event) {
		if (eventInputIsEmpty()) {
			System.out.println("TODO: Choose road geometry type and end stake first!");
		} else {
			addNewRoadSection();
		}
	}

	private boolean eventInputIsEmpty() {
		return combo.getSelectionModel().isEmpty() || endStake.getText().isEmpty();
	}

	private void addNewRoadSection() {
		try {
			int selectedCombo = combo.getSelectionModel().getSelectedIndex();
			int end = Integer.parseInt(endStake.getText().trim());
			if (endBeforeLastStake(end)) {
				System.out.println("TODO: End stake too small!");
			}else if (selectedCombo == 0) {
				addStraight(end);
			} else if (curveRadiusIsMissing(selectedCombo)) {
				System.out.println("TODO: Choose curve radus!");
			}  else {
				tryAddCurve(selectedCombo, end);
			}
		} catch (Exception e) {
			System.out.println("TODO: end stake was not a proper number");
		}
	}

	private boolean endBeforeLastStake(int end) {
		return end < 0 || end <= road.getLength();
	}

	private void addStraight(int end) {
		if (intervalLengthLongerThanSmax(end)) {
			System.out.println("TODO: Restricted section too long, no possible solutions!");
		} else {
			if (restricted.isSelected()) road.addStraightSection(end, false);
			else road.addStraightSection(end, true);
			updateList();
		}
	}

	private void updateList() {
		ObservableList<String> items = FXCollections.observableArrayList();
		for (RoadGeometry geometry : road.getRoadGeometry()) {
			items.add(geometry.toString());
		}
		list.setItems(items);
	}

	private boolean intervalLengthLongerThanSmax(int end) {
		return restricted.isSelected() && end - road.sectionBeginning() >= road.getSMax();
	}

	private boolean curveRadiusIsMissing(int selectedCombo) {
		return selectedCombo > 0 && radius.getText().isEmpty();
	}

	private void tryAddCurve(int selectedCombo, int end) {
		try {
			addCurve(selectedCombo, end);
		}catch (Exception e) {
			System.out.println("TODO: radius was not a proper number");
		}
	}

	private void addCurve(int selectedCombo, int end) throws NumberFormatException {
		int newRadius = Integer.parseInt(radius.getText().trim());
		if (selectedCombo == 1) {
			addOutsideCurve(end, newRadius);
		} else {
			addInsideCurve(end, newRadius);
		}
		updateList();
	}

	private void addOutsideCurve(int end, int newRadius) {
		if (restricted.isSelected()) {
			int sectionBeginning = road.sectionBeginning();
			road.addOutsideCurve(end, false, newRadius);
			if (sectionLongerThanSmax(end, sectionBeginning)) return;
		} else {
			road.addOutsideCurve(end, true, newRadius);
		}
	}

	private void addInsideCurve(int end, int newRadius) {
		if (restricted.isSelected()) {
			int sectionBeginning = road.sectionBeginning();
			road.addInsideCurve(end, false, newRadius);
			if (sectionLongerThanSmax(end, sectionBeginning)) return;
		} else {
			road.addInsideCurve(end, true, newRadius);
		}
	}

	private boolean sectionLongerThanSmax(int end, int sectionBeginning) {
		if (end - sectionBeginning >= road.getLastRoadGeometry().getSmax()) {
			road.removeLastRoadGeometry();
			System.out.println("TODO: Restricted section too long, no possible solutions!");
			return true;
		}
		return false;
	}

}
