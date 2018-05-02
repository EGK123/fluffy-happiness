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
 * @author A-Team 40
 *
 */
public class Main extends Application {

	// Labels for the teams that get first, second, and third place in the
	// tournament
	private static Label first;
	private static Label second;
	private static Label third;

	// score of the loser of the top quarter-final match
	// used to check who gets 3rd place
	private static int topQFinalMatchScore = Integer.MAX_VALUE;
	// score of the loser of the top quarter-final match
	// used to check who gets 3rd place
	private static int bottomQFinalMatchScore = Integer.MAX_VALUE;

	// the loser of the top (physically higher) q-final match
	private static Team topQFinalTeam;
	// the loser of the bottom (physically lower) q-final match
	private static Team bottomQFinalTeam;

	// number of teams in the tournament
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

			BorderPane root = new BorderPane();
			GridPane grid = new GridPane();

			if (teams.size() > 0) {
				VersusBox[] matches = new VersusBox[numberOfTeams - 1];
				int teamSelector = 0;
				int heapBuilder = numberOfTeams - 2;

				int totalRounds = (int) (Math.log(numberOfTeams) / Math.log(2));

				for (int i = 0; i < Math.log(numberOfTeams) / Math.log(2); i++) { // columns in the grid

					for (int j = 0; j < numberOfTeams - 1; j++) { // rows in the grid
						if (j % Math.pow(2, i + 1) == Math.pow(2, i) - 1) {
							// puts a versus box in to the grid
							TypeOfMatch matchType = (i == totalRounds - 1) ? TypeOfMatch.GRAND_CHAMPIONSHIP
									: (i == totalRounds - 2) ? TypeOfMatch.SEMI_FINAL
											: (i == totalRounds - 3) ? TypeOfMatch.QUARTER_FINAL
													: TypeOfMatch.NORMAL_GAME;
							VersusBox add;
							if (i == 0) {
								// first round, includes team match-ups
								Team t1 = sortedTeams.get(teamSelector * 2);
								Team t2 = sortedTeams.get((teamSelector * 2) + 1);
								add = new VersusBox(matchType, t1, t2, teamSelector % 2 == 0);
							} else {
								// later rounds, does not include teams because they still have to be determined
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
	 * Sorts teams in to their final ordering for the first round of the tournament.
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

		// organizes the teams into correct set of 2 matches (e.g. the winner of the 1
		// vs 16 needs to play the winner of 8 vs 9 in the second round in a tournament
		// with 16 teams)
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

		// places teams in the correct ordering within their group of 2 matches (e.g.
		// the 2 vs 15 & 7 vs 10 grouping in a 16 team bracket needs to be displayed
		// as 7 vs 10 then 2 vs 15 rather than 2 vs 15 then 7 vs 10. Look at a NCAA
		// bracket to see the correct ordering of teams.
		Team[] out = new Team[teams.size()];
		Team[][] helper = new Team[teams.size() / 4][4];
		for (int i = 0; i < teams.size(); i += 4) {
			if (i == 0) {
				helper[i / 4][0] = temp[i];
				helper[i / 4][1] = temp[i + 1];
				helper[i / 4][2] = temp[i + 2];
				helper[i / 4][3] = temp[i + 3];
			} else {
				helper[i / 4][0] = temp[i + 2];
				helper[i / 4][1] = temp[i + 3];
				helper[i / 4][2] = temp[i];
				helper[i / 4][3] = temp[i + 1];
			}
		}

		// places groups of 2 matches in the correct display order (e.g. 1 vs 16 & 8 vs
		// 9 need to be the first grouping, 2 vs 15 & 7 vs 10 need to be the last
		// grouping in a tournament with 16 teams)
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

		// transfers teams from helperTemp to out
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
		if (team == null)
			first.setText("First: TBD");
		else
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
		if (team == null)
			second.setText("Second: TBD");
		else
			second.setText("Second: " + team.toString());
	}

	/**
	 * Updates the third-place label when both semi-final rounds have been
	 * completed. The team that gets third is the highest-scoring losing team of the
	 * semi-final round. If both losers of the semi-final round got the same score
	 * in the semi-final round, then the team that finished their semi-final round
	 * first gets third place.
	 * 
	 * @param team
	 *            the team that lost the semi-final
	 * @param score
	 *            the score of the team passed in to the method in the semi-final
	 *            round
	 */
	public static void setThirdPlace(Team team, int score, boolean topGame) {
		if (team == null) {
			if (topGame) {
				topQFinalTeam = null;
				topQFinalMatchScore = Integer.MAX_VALUE;
			} else {
				bottomQFinalTeam = null;
				bottomQFinalMatchScore = Integer.MAX_VALUE;
			}
			third.setText("Third: TBD");
		} else {
			if (topGame) {
				topQFinalTeam = team;
				topQFinalMatchScore = score;
			} else {
				bottomQFinalTeam = team;
				bottomQFinalMatchScore = score;
			}

			if (topQFinalTeam != null && bottomQFinalTeam != null) {
				third.setText("Third: " + ((topQFinalMatchScore > bottomQFinalMatchScore) ? topQFinalTeam.toString()
						: bottomQFinalTeam.toString()));
			}
		}
	}

	/**
	 * The main method of the program. Launches start().
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
