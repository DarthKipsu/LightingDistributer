
package lightdistributer.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import lightdistributer.domain.Road;

public class sMaxEvent implements EventHandler<ActionEvent> {
	private TextField sMax;
	private Road road;

	public sMaxEvent(TextField sMax, Road road) {
		this.sMax = sMax;
		this.road = road;
	}

	@Override
	public void handle(ActionEvent event) {
		if (sMax.getText().isEmpty()) {
			System.out.println("You must set number first!");
		} else {
			String userInput = sMax.getText().trim();
			int newSmax;
			try {
				newSmax = Integer.parseInt(userInput);
				road.setSmax(newSmax);
				System.out.println("Done!");
			} catch (Exception e) {
				System.out.println("Not a proper number!");
			}
		}
	}

}
