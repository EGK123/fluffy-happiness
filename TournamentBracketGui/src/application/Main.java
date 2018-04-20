package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1920,1080);
			
			int numberOfTeams = 16;
			// create matches
			HBox bracketBox = new HBox();
			VBox[] round = new VBox[4];
			
			int totalCol = (numberOfTeams * 2) - 1;
			GridPane g = new GridPane();
			g.add(new VersusBox(), 0, 0);
			g.add(new VersusBox(""), 0, 1);
			g.add(new VersusBox(), 0, 2);
			
			bracketBox.getChildren().addAll(g);
			
			root.setCenter(bracketBox);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
