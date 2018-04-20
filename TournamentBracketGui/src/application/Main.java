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
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1920,1080);
			
			int numberOfTeams = 8;
			VersusBox[] matches = new VersusBox[numberOfTeams - 1];
			// create matches
			HBox bracketBox = new HBox();
			VBox[] round = new VBox[3];
			int vboxHeight = 134;
			int divisor = 2;
			for (int i = 0; i < round.length; i++) {
				VBox box = new VBox();
				box.setMinHeight(700);
				box.setMaxHeight(720);
				box.setPrefHeight(710);
				round[i] = box;
				
				double padding = (scene.getHeight() - (vboxHeight * numberOfTeams/divisor))/(numberOfTeams+1);
				for(int j = 0; j < numberOfTeams/divisor; j++) {
					VersusBox vbox = new VersusBox(TypeOfMatch.NORMAL_GAME, new Team("asdf", vboxHeight), new Team("fdsa", (int)padding));
					vbox.setPadding(new Insets(padding));
					round[i].getChildren().add(vbox);
					divisor *= 2;
				}
				bracketBox.getChildren().add(round[i]);
			}
			
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
