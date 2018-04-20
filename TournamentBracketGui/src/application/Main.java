package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			int numberOfTeams = 16;
			VersusBox[] matches = new VersusBox[numberOfTeams - 1];
			// create matches
			HBox bracketBox = new HBox();
			VBox[] round = new VBox[4];
			int divider = 2;
			int matchesCounter = 0;
			int x = 294;
			int y  = 10484;
			for (int i = 0; i < round.length; i++) {
				round[i] = new VBox();
				round[i].setMinHeight(380);
				round[i].setMaxHeight(600);
				round[i].setPrefHeight(390);
				round[i].setStyle("-fx-background-color: #FFFFFF;");
				for (int j = 0; j < (numberOfTeams / divider); j++) {
//					matches[matchesCounter].setPadding(new Insets(20, 10, 20, 10));
//					round[i].getChildren().add(matches[matchesCounter]);
					Label a = new Label("Hello");
					int numberOfVersusBoxes = numberOfTeams / divider;
					double verticalSpacing = (600 - (numberOfVersusBoxes * 20)) / (numberOfVersusBoxes + 1);
//					a.setText(Double.toString(round[i].getHeight()));
					verticalSpacing = verticalSpacing / 2;
					a.setPadding(new Insets(verticalSpacing, 10, verticalSpacing, 10));
					round[i].getChildren().add(a);
					matchesCounter++;
				}
				bracketBox.getChildren().add(round[i]);
				divider *= 2;
			}
			
			BorderPane root = new BorderPane();
			root.setCenter(bracketBox);
			Scene scene = new Scene(root,400,800);
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
