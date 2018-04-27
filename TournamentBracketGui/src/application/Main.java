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

/**
 * Main class that places GUI elements and runs the program.
 * 
 * @author A-Team 42
 *
 */
public class Main extends Application {
	private static Label first;
	private static Label second;
	private static Label third;
	// score of the loser of the first quarter-final match to finish;
	// used to check who gets 3rd place
	private static int firstQFinalMatchScore = Integer.MAX_VALUE;
	private static Team firstQFinalTeam;
	private static int numberOfTeams;

	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Tournament Bracket");

			// parses the first command-line argument, which should be the input filename
			ArrayList<Team> teams = parseInput(getParameters().getRaw().get(0));
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
							// puts a versus box in to the grid
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
							// put in a "spacer" box in the grid
							VBox blank = new VBox();
							blank.setMinHeight(100);
							blank.setMaxHeight(100);
							blank.setPrefHeight(100);
							grid.add(blank, i, j);
						}
					}
				}

				// sets a "parent" box so the VersusBox knows where to send the winning team to
				// implemented as a heap
				for (int i = 0; i < matches.length; i++) {
					int parent = (i - 1) / 2;
					if (parent < 0)
						continue;

					matches[i].setNext(matches[parent]);
				}
			}

			// creates the ListView to display all the teams
			ListView<String> teamList = new ListView<String>();
			ObservableList<String> items = FXCollections.observableArrayList();
			for (int i = 0; i < teams.size(); i++)
				items.add(teams.get(i).toString());

			teamList.setItems(items);

			// creates an area to display the first, second, and third place teams
			VBox winners = new VBox();
			winners.setMinWidth(200);
			first = (teams.size() == 1) ? new Label("First: " + teams.get(0).toString())
					: (teams.size() == 0) ? new Label("No winner; no competitors.") : new Label("First: TBD");
			second = (teams.size() > 1) ? new Label("Second: TBD") : new Label("");
			third = (teams.size() > 2) ? new Label("Third: TBD") : new Label("");
			winners.getChildren().addAll(first, second, third);

			// adds everything to the root and shows
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

	/**
	 * Reads a list of teams from a file and puts them in an ArrayList.
	 * 
	 * @param filename
	 *            the name of the file that teams are read from
	 * @return an ArrayList of teams
	 * @throws FileNotFoundException
	 */
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

	/**
	 * Sorts the teams in pairs of two based on their opponent in the first round.
	 * The highest-seeded team plays the lowest-seeded team, the second
	 * highest-seeded team plays the second lowest-seeded team, and so on.
	 * 
	 * @param teams
	 *            the ArrayList of teams
	 * @return an ArrayList of teams sorted in pairs of two based on who they will
	 *         play in the first round
	 */
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

	/**
	 * pre-condition: takes input only from lists already sorted by seed
	 * 
	 * Sorts teams in to their final ordering for the first round.
	 * 
	 * @param teams
	 *            the ArrayList of teams
	 * @return an ArrayList of teams sorted in the order they will be placed in the
	 *         GUI
	 */
	private static ArrayList<Team> sortForFirstRound(ArrayList<Team> teams) {
		// if there are 4 or fewer teams, they are already in the order they will need
		// to be for the first round
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

	/**
	 * Updates the first-place label from "TBA" to the team that got first place in
	 * the tournament.
	 * 
	 * @param team
	 *            the team that got first place in the tournament
	 */
	public static void setFirstPlace(Team team) {
		first.setText("First: " + team.toString());
	}

	/**
	 * Updates the second-place label from "TBA" to the team that got second place
	 * in the tournament.
	 * 
	 * @param team
	 *            the team that got second place in the tournament
	 */
	public static void setSecondPlace(Team team) {
		second.setText("Second: " + team.toString());
	}

	/**
	 * Updates the third-place label. If only one of the semi-finals has been
	 * completed, the third-place team is the team that lost that semi-final. When
	 * the next semi-final is completed, the team that gets third is the one that
	 * had a higher score in the semi-final round.
	 * 
	 * @param team
	 *            the team that lost the semi-final
	 * @param score
	 *            the score of the team passed in to the method in the semi-final
	 *            round
	 */
	public static void setThirdPlace(Team team, int score) {
		if (firstQFinalMatchScore == Integer.MAX_VALUE) {
			firstQFinalMatchScore = score;
			firstQFinalTeam = team;
		} else {
			third.setText("Third: " + ((score > firstQFinalMatchScore) ? team.toString() : firstQFinalTeam.toString()));
		}
	}

	/**
	 * The main method of the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
