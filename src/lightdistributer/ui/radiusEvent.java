
package lightdistributer.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class radiusEvent implements EventHandler<ActionEvent> {
	private TextField radius;
	private ComboBox combo;

	public radiusEvent(TextField radius, ComboBox combo) {
		this.radius = radius;
		this.combo = combo;
	}

	@Override
	public void handle(ActionEvent event) {
		int selection = combo.getSelectionModel().getSelectedIndex();

		if (selection == 0) {
			radius.setDisable(true);
		} else {
			radius.setDisable(false);
		}
	}

}
