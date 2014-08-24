
package lightdistributer.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
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
		addSmax();
		addInstruction();
		addRoadSelection();
		showGeometries();
	}

	private void addTitle() {
		Text title = new Text("Light distributer 1.0");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		grid.add(title, 0, 0, 2, 1);
		addSpace(1);
	}

	private void addSpace(int row) {
		Pane spring = new Pane();
		spring.minHeight(10);
		grid.add(spring, 0, row);
	}

	private void addSmax() {
		Text sMaxText = new Text("Maximum column spacing:");
		sMaxText.setFont(Font.font("Arial", 12));
		TextField sMAx = new TextField("Smax");
		sMAx.setPrefWidth(80);
		grid.add(sMaxText, 0, 2);
		grid.add(sMAx, 1, 2);
		addSpace(3);
	}

	private void addInstruction() {
		Text instruction = new Text("Select road geometries and press add:");
		instruction.setFont(Font.font("Arial", 12));
		grid.add(instruction, 0, 4, 2, 1);
	}

	private void addRoadSelection() {
		addComboBox();
		addEndStakeField();
		addRadiusField();
		addRestrictionBox();
		addSpace(7);
		addRoadSelectionButton();
	}

	private void addComboBox() {
		ObservableList<String> geometries = addTypes();
		ComboBox combo = new ComboBox(geometries);
		grid.add(combo, 0, 5);
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
		grid.add(endStake, 1, 5);
	}

	private void addRadiusField() {
		TextField radius = new TextField();
		radius.setPromptText("radius");
		radius.setPrefWidth(65);
		radius.setDisable(true);
		grid.add(radius, 1, 6);
	}

	private void addRestrictionBox() {
		CheckBox restricted = new CheckBox("restricted");
		grid.add(restricted, 0, 6);
	}

	private void addRoadSelectionButton() {
		Button button = new Button("Add road geometry");
		button.setPrefWidth(250);
		grid.add(button, 0, 8, 2, 1);
		addSpace(9);
	}

	private void showGeometries() {
		ListView<String> list = new ListView<>();
		grid.add(list, 0, 10, 2, 1);
		addSpace(11);
	}

}
