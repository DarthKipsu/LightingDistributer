
package lightdistributer.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UserInterface {
	private GridPane grid;

	public UserInterface() {
		grid = new GridPane();
		grid.setPadding(new Insets(5));
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setAlignment(Pos.CENTER);
	}

	public GridPane addGridPane() {
		addDescriptions();
		return grid;
	}

	private void addDescriptions() {
		addTitle();
		addInstruction();
		addRoadSelection();
	}

	private void addTitle() {
		Text title = new Text("Light distributer 1.0");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(title, 0, 0, 2, 1);
		addSpaceAfterTitle();
	}

	private void addSpaceAfterTitle() {
		Pane spring = new Pane();
		spring.minHeight(10);
		grid.add(spring, 0, 1);
	}

	private void addInstruction() {
		Text instruction = new Text("Select road geometries and press add:");
		instruction.setFont(Font.font("Arial", 12));
		grid.add(instruction, 0, 2, 2, 1);
	}

	private void addRoadSelection() {
		addComboBox();
		addEndStakeField();
	}

	private void addComboBox() {
		ObservableList<String> geometries = addTypes();
		ComboBox combo = new ComboBox(geometries);
		grid.add(combo, 0, 3);
	}

	private ObservableList<String> addTypes() {
		return FXCollections.observableArrayList(
			"Straight section",
			"Outside curve",
			"Inside curve"
		);
	}

	private void addEndStakeField() {
		TextField endStake = new TextField();
		endStake.setPromptText("end stake");
		endStake.setPrefWidth(80);
		grid.add(endStake, 1, 3);
	}

}
