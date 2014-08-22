package lightdistributer;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lightdistributer.ui.UserInterface;

public class LightDistributer extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		UserInterface ui = new UserInterface();
		GridPane grid = ui.addGridPane();
			
		StackPane root = new StackPane();
		root.getChildren().add(grid);
		
		Scene scene = new Scene(root, 500, 650);
		
		primaryStage.setTitle("Ligt distributer");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
