package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
			
			int numberOfTeams = 16;
			VersusBox[] matches = new VersusBox[numberOfTeams - 1];
//			// create matches
//			HBox bracketBox = new HBox();
//			VBox[] round = new VBox[4];
//			int divider = 2;
//			int matchesCounter = 0;
//			int padding = 5;
//			int paddingT = 20;
//			int paddingN = 20;
//			for (int i = 0; i < round.length; i++) {
//				round[i] = new VBox();
//				round[i].setMinHeight(600);
//				round[i].setMaxHeight(600);
//				round[i].setPrefHeight(600);
//				round[i].setStyle("-fx-background-color: #FFFFFF;");
//				
//				for (int j = 0; j < (numberOfTeams / divider); j++) {
////					matches[matchesCounter].setPadding(new Insets(20, 10, 20, 10));
////					round[i].getChildren().add(matches[matchesCounter]);
//					Label a = new Label("Hello");
//					a.setPrefHeight(80);
//					a.setMinHeight(80);
//					a.setMaxHeight(80);
//					int numberOfVersusBoxes = numberOfTeams / divider;
////					double verticalSpacing = (600 - (numberOfVersusBoxes * 50)) / (numberOfVersusBoxes + 1);
////					if (j != 0 || j != (numberOfTeams / divider) - 1) {
////						verticalSpacing = verticalSpacing / 2;
////					}
////					a.setText(Double.toString(verticalSpacing));
////					verticalSpacing = verticalSpacing / 2;
////					if (j == 0) {
////						a.setPadding(new Insets(paddingT, 10, paddingN, 10));
////					} else if (j == ((numberOfTeams / divider) - 1)) {
////						a.setPadding(new Insets(paddingN, 10, paddingN, 10));
////					} else {
////						a.setPadding(new Insets(paddingN, 10, paddingT, 10));
////					}
//					a.setPadding(new Insets(5, 5, 5, 5));
//					round[i].getChildren().add(a);
//					matchesCounter++;
//				}
//				bracketBox.getChildren().add(round[i]);
//				divider *= 2;
//				padding = 2*padding + 20;
//				paddingT = 2*paddingT;
//				paddingN = 2*paddingN + 20;
//			}
			
			BorderPane root = new BorderPane();
			GridPane grid = new GridPane();
//			root.setCenter(bracketBox);
			int internalSpaces = 1;
			int topSpaces = 0;
			int bottomSpaces = topSpaces + 1;
			int places = 8;
			
			for (int i = 0; i < 3; i++ ) {

				for (int j = 0; j < 7; j++) {
					if (j % Math.pow(2, i+1) == Math.pow(2,i) - 1) {
						grid.add(new VersusBox(), i, j);
					} else {
						grid.add(new VersusBox(""), i, j);
					}
				}
			
				
//				for (int k = 0; k < topSpaces; k++) {
//					root.add(new VersusBox(""), i, k);
//				}
//				for (int l = 0; l < places; l++) {
//					root.add(new VersusBox(), i, topSpaces + l);
//					for (int m = 0; m < internalSpaces; m++) {
//						root.add(new VersusBox(""), i, topSpaces + l + m);
//					}
//				}
////				root.add(new VersusBox(""), i, k);
//				for (int l = 0; l < bottomSpaces; l++) {
//					root.add(new VersusBox(""), i, topSpaces + internalSpaces + l);
//				}
//				topSpaces = 2*topSpaces + 1;
//				internalSpaces = 2*internalSpaces +1;
//				bottomSpaces = topSpaces+1;
//				places = places / 2;
			}
			String[] teamNames = new String[] {"T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8"};
			Label[] teams = new Label[8];
			HBox myHBox = new HBox();
			for (int i = 0; i < 8; i++) {
				teams[i] = new Label(teamNames[i]);
				myHBox.getChildren().add(teams[i]);
			}
			
			
			ListView aaa = new ListView(myHBox.getChildren());
			root.setCenter(grid);
			root.setLeft(aaa);
			
			Scene scene = new Scene(root,1200,1200);
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
