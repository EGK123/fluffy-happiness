package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
	private static int numberOfTeams;

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Tournament Bracket");
	
			// parses the first command-line argument, which should be the input filename
			ArrayList<Team> teams = parseInput("teams.txt"); // getParameters().getRaw().get(0));
			ArrayList<Team> sortedTeams = sortForFirstRound(sortBySeed(teams));
			for (int i = 0; i < sortedTeams.size(); i++)
				System.out.println(sortedTeams.get(i).toString());
	
			BorderPane root = new BorderPane();
			GridPane grid = new GridPane();
	
			if (teams.size() > 0) {
				VersusBox[] matches = new VersusBox[numberOfTeams - 1];
				int teamSelector = 0;
				int heapBuilder = numberOfTeams - 2;
	
				int totalRounds = (int) (Math.log(numberOfTeams) / Math.log(2));
	
				for (int i = 0; i < Math.log(numberOfTeams) / Math.log(2); i++) {
	
					for (int j = 0; j < numberOfTeams - 1; j++) {
						if (j % Math.pow(2, i + 1) == Math.pow(2, i) - 1) {
							TypeOfMatch matchType = (i == totalRounds - 1) ? TypeOfMatch.GRAND_CHAMPIONSHIP
									: (i == totalRounds - 2) ? TypeOfMatch.SEMI_FINAL
											: (i == totalRounds - 3) ? TypeOfMatch.QUARTER_FINAL
													: TypeOfMatch.NORMAL_GAME;
							VersusBox add;
							if (i == 0) {
								Team t1 = sortedTeams.get(teamSelector * 2);
								Team t2 = sortedTeams.get((teamSelector * 2) + 1);
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
	
				for (int i = 0; i < matches.length; i++) {
					int parent = (i - 1) / 2;
					if (parent < 0)
						continue;
	
					matches[i].setNext(matches[parent]);
				}
			}
	
			ListView<String> teamList = new ListView<String>();
			ObservableList<String> items = FXCollections.observableArrayList();
	
			for (int i = 0; i < teams.size(); i++)
				items.add(teams.get(i).toString());
	
			teamList.setItems(items);
	
			VBox winners = new VBox();
			first = (teams.size() == 1) ? new Label("First: " + teams.get(0).toString())
					: (teams.size() == 0) ? new Label("No winner; no competitors.") : new Label("First: TBD");
			second = (teams.size() > 1) ? new Label("Second: TBD") : new Label("");
			third = (teams.size() > 2) ? new Label("Third: TBD") : new Label("");
			winners.getChildren().addAll(first, second, third);
			ScrollPane scroll = new ScrollPane();
			scroll.setContent(grid);
			root.setCenter(scroll);
			root.setLeft(teamList);
			root.setRight(winners);
	
			Scene scene = new Scene(root, 1400, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (FileNotFoundException e) {
			System.out.println("Invalid file provided.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<Team> parseInput(String filename) throws FileNotFoundException {
		Scanner in = new Scanner(new File(filename));
		ArrayList<Team> teams = new ArrayList<Team>();
		int n = 0;
		while (in.hasNext()) {
			String line = in.nextLine();
			if (line.equals(""))
				continue;

			n++;
			teams.add(new Team(line, n));
		}

		numberOfTeams = n;
		return teams;
	}

	private static ArrayList<Team> sortBySeed(ArrayList<Team> teams) {
		if (teams.size() <= 2)
			return teams;

		ArrayList<Team> out = new ArrayList<Team>();
		int numberOfTeams = teams.size();
		for (int i = 0; i < numberOfTeams; i++) {
			if (out.contains(teams.get(i)))
				continue;

			Team t1 = teams.get(i);
			Team t2 = null;
			for (int j = i + 1; j < numberOfTeams; j++) {
				t2 = teams.get(j);
				if (t1.seed + t2.seed == numberOfTeams + 1) {
					t2 = teams.get(j);
					break;
				}
			}

			out.add(t1);
			out.add(t2);
		}

		return out;
	}

	// pre-condition: takes input only from lists already sorted by seed
	private static ArrayList<Team> sortForFirstRound(ArrayList<Team> teams) {
		if (teams.size() <= 4)
			return teams;

		Team[] temp = new Team[teams.size()];
		// organize the teams into correct set of 2 matches
		int currSeed = 1;
		while (currSeed <= teams.size() / 4) {
			int _match1 = -1;
			int _match2 = -1;

			for (int i = 0; i < teams.size(); i++) {
				if (teams.get(i).seed == currSeed) {
					_match1 = i;
				} else if (teams.get(i).seed + currSeed == (teams.size() / 2) + 1) {
					_match2 = i;
				}
			}
			// assuming the teams ArrayList was properly populated _match1 and _match2 will
			// be >= -1
			temp[((currSeed - 1) * 4)] = teams.get(_match1);
			temp[((currSeed - 1) * 4) + 1] = teams.get(_match1 + 1);
			temp[((currSeed - 1) * 4) + 2] = teams.get(_match2);
			temp[((currSeed - 1) * 4) + 3] = teams.get(_match2 + 1);

			currSeed++;
		}

		// organize the teams into their correct display order
		Team[] out = new Team[teams.size()];
		Team[][] helper = new Team[teams.size() / 4][4];
		for (int i = 0; i < teams.size(); i += 4) {
			helper[i / 4][0] = temp[i];
			helper[i / 4][1] = temp[i + 1];
			helper[i / 4][2] = temp[i + 2];
			helper[i / 4][3] = temp[i + 3];
		}

		Team[][] helperTemp = new Team[teams.size() / 4][4];
		for (int i = 0; i < helper.length; i++) {
			switch (((i + 1) / 2) % 2) {
			case 0:
				helperTemp[i / 2] = helper[i];
				break;
			case 1:
				helperTemp[(helper.length - 1) - (i / 2)] = helper[i];
				break;
			}
		}

		for (int i = 0; i < helperTemp.length; i++) {
			for (int j = 0; j < helperTemp[i].length; j++) {
				out[(i * 4) + j] = helperTemp[i][j];
			}
		}

		return new ArrayList<Team>(Arrays.asList(out));
	}

	public static void setFirstPlace(Team team) {
		first.setText("First: " + team.toString());
	}

	public static void setSecondPlace(Team team) {
		second.setText("Second: " + team.toString());
	}

	public static void setThirdPlace(Team team, int score) {
		if (firstQFinalMatchScore == Integer.MAX_VALUE) {
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
