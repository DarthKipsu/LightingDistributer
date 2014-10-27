package lightdistributer.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
			System.out.println("Choose road geometry type and end stake first!");
		} else {
			addNewRoadSection();
		}
	}

	private boolean eventInputIsEmpty() {
		return combo.getSelectionModel().isEmpty() || endStake.getText().isEmpty();
	}

	private void addNewRoadSection() {
		int selectedCombo = combo.getSelectionModel().getSelectedIndex();
		int end;
		try {
			end = Integer.parseInt(endStake.getText().trim());
			if (end < 0 || end <= road.getLength()) {
				System.out.println("End stake too small!");
			} else if (selectedCombo > 0 && radius.getText().isEmpty()) {
				System.out.println("Choose curve radus!");
			} else if (selectedCombo == 0) {
				if (restricted.isSelected() && end - road.sectionBeginning() >= road.getSMax()) {
					System.out.println("Restricted section too long, no possible solutions!");
				} else {
					if (restricted.isSelected()) road.addStraightSection(end, false);
					else road.addStraightSection(end, true);
					updateList();
					System.out.println("Straight section added!");
				}
			} else {
				int newRadius;
				try {
					newRadius = Integer.parseInt(radius.getText().trim());
					if (selectedCombo == 1) {
						if (restricted.isSelected()) {
							int sectionBeginning = road.sectionBeginning();
							road.addOutsideCurve(end, false, newRadius);
							if (end - sectionBeginning >= road.getLastRoadGeometry().getSmax()) {
								road.removeLastRoadGeometry();
								System.out.println("Restricted section too long, no possible solutions!");
								return;
							}
							System.out.println(road.getRoadGeometry());
						} else {
							road.addOutsideCurve(end, true, newRadius);
						}
						updateList();
						System.out.println("Outside curve added!");
					} else {
						if (restricted.isSelected()) {
							int sectionBeginning = road.sectionBeginning();
							road.addInsideCurve(end, false, newRadius);
							if (end - sectionBeginning >= road.getLastRoadGeometry().getSmax()) {
								road.removeLastRoadGeometry();
								System.out.println("Restricted section too long, no possible solutions!");
								return;
							}
							System.out.println(road.getRoadGeometry());
						} else {
							road.addInsideCurve(end, true, newRadius);
						}
						updateList();
						System.out.println("Inside curve added!");
					}
				}catch (Exception e) {
					System.out.println("radius was not a proper number");
				}
			}
		}catch (Exception e) {
			System.out.println("end stake was not a proper number");
		}
	}

	private void updateList() {
		ObservableList<String> items = FXCollections.observableArrayList();
		for (RoadGeometry geometry : road.getRoadGeometry()) {
			items.add(geometry.toString());
		}
		list.setItems(items);
	}

}
