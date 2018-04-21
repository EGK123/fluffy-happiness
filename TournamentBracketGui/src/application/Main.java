package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private static Label first;
	private static Label second;
	private static Label third;
	// score of the loser of the first quarter-final match to finish;
	// used to check who gets 3rd place
	private static int firstQFinalMatchScore = Integer.MAX_VALUE;
	private static Team firstQFinalTeam;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			GridPane grid = new GridPane();
			
			int numberOfTeams = 16;
			VersusBox[] matches = new VersusBox[numberOfTeams - 1];
			int teamSelector = 0;
			int heapBuilder = numberOfTeams - 2;
			
			ArrayList<Team> teams = new ArrayList<Team>();
			for(int i = 1; i < numberOfTeams+1; i++)
				teams.add(new Team("Team " + i, i));
			
			int totalRounds = (int)(Math.log(numberOfTeams)/Math.log(2));
			
			for (int i = 0; i < Math.log(numberOfTeams)/Math.log(2); i++ ) {

				for (int j = 0; j < 15; j++) {
					if (j % Math.pow(2, i+1) == Math.pow(2,i) - 1) {
						TypeOfMatch matchType = (i == totalRounds - 1) ? TypeOfMatch.GRAND_CHAMPIONSHIP : (i == totalRounds - 2) ? TypeOfMatch.SEMI_FINAL : (i == totalRounds - 3) ? TypeOfMatch.QUARTER_FINAL : TypeOfMatch.NORMAL_GAME;
						VersusBox add;
						if(i == 0) {
							Team t1 = teams.get(teamSelector * 2);
							Team t2 = teams.get((teamSelector * 2) + 1);
							add = new VersusBox(matchType, t1, t2, teamSelector % 2 == 0);
						} else {
							add = new VersusBox(matchType, teamSelector % 2 == 0);
						}
						
						grid.add(add, i, j);
						matches[heapBuilder] = add;
						teamSelector++;
						heapBuilder--;
					} else {
						VBox blank = new VBox();
						blank.setMinHeight(100);
						blank.setMaxHeight(100);
						blank.setPrefHeight(100);
						grid.add(blank, i, j);
					}
				}
			}
			
			for(int i = 0; i < matches.length; i++) {
				int parent = (i - 1) / 2;
				if(parent < 0)
					continue;
				
				matches[i].setNext(matches[parent]);
			}
			
			ListView<String> teamList = new ListView<String>();
			ObservableList<String> items = FXCollections.observableArrayList();
			
			for(int i = 0; i < teams.size(); i++)
				items.add(teams.get(i).teamName);
			
			teamList.setItems(items);
			
			VBox winners = new VBox();
			first = new Label("First: TBD");
			second = new Label("Second: TBD");
			third = new Label("Third: TBD");
			winners.getChildren().addAll(first, second, third);
			ScrollPane scroll = new ScrollPane();
			scroll.setContent(grid);
			root.setCenter(scroll);
			root.setLeft(teamList);
			root.setRight(winners);
			
			Scene scene = new Scene(root,1400,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setFirstPlace(Team team) {
		first.setText("First: " + team.toString());
	}
	
	public static void setSecondPlace(Team team) {
		second.setText("Second: " + team.toString());
	}
	
	public static void setThirdPlace(Team team, int score) {
		if(firstQFinalMatchScore == Integer.MAX_VALUE) {
			firstQFinalMatchScore = score;
			firstQFinalTeam = team;
		} else {
			third.setText("Third: " + ((score > firstQFinalMatchScore) ? team.toString() : firstQFinalTeam.toString()));
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
