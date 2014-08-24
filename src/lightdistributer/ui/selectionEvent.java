package lightdistributer.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lightdistributer.domain.Road;

public class selectionEvent implements EventHandler<ActionEvent> {

	private ComboBox combo;
	private TextField endStake;
	private TextField radius;
	private CheckBox restricted;
	private Road road;

	public selectionEvent(ComboBox combo, TextField endStake, TextField radius,
		CheckBox restricted, Road road) {
		this.combo = combo;
		this.endStake = endStake;
		this.radius = radius;
		this.restricted = restricted;
		this.road = road;
	}

	@Override
	public void handle(ActionEvent event) {
		if (combo.getSelectionModel().isEmpty() || endStake.getText().isEmpty()) {
			System.out.println("Choose road geometry type and end stake first!");
		} else {
			int selectedCombo = combo.getSelectionModel().getSelectedIndex();
			int end;

			try {
				end = Integer.parseInt(endStake.getText().trim());
				if (selectedCombo > 0 && radius.getText().isEmpty()) {
					System.out.println("Choose curve radus!");
				} else if (selectedCombo == 0) {
					road.addStraightSection(end, true);
					System.out.println("Straight section added!");
				} else {
					int newRadius;

					try {
						newRadius = Integer.parseInt(radius.getText().trim());
						if (selectedCombo == 1) {
							road.addOutsideCurve(end, true, newRadius);
							System.out.println("Outside curve added!");
						} else {
							road.addInsideCurve(end, true, newRadius);
							System.out.println("Inside curve added!");
						}
					} catch (Exception e) {
						System.out.println("radius was not a proper number");
					}
				}
			} catch (Exception e) {
				System.out.println("end stake was not a proper number");
			}
		}
	}

}
