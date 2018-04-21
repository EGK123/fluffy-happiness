package application;

import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

public class VersusBox extends VBox implements Scoreable, EventHandler<ActionEvent> {
	private VersusBox next;

	private int teamScore1;
	private int teamScore2;

	private Team team1;
	private Team team2;

	private HBox team1Box;
	private HBox team2Box;

	private HBox btnBox;

	private Label team1Lbl;
	private Label team2Lbl;

	private TextField team1TxtField;
	private TextField team2TxtField;

	private Button submitBtn;

	private TypeOfMatch matchType;
	boolean topGame;

	public VersusBox() {
		this.setMinHeight(100);
		this.setMaxHeight(100);
		this.setPrefHeight(100);

		team1Box = new HBox();
		team2Box = new HBox();
		btnBox = new HBox();

		team1Lbl = new Label("TBD");
		team2Lbl = new Label("TBD");

		team1TxtField = new TextField();
		team2TxtField = new TextField();

		submitBtn = new Button("Submit");
		submitBtn.setOnAction(this);

		team1Box.getChildren().addAll(team1Lbl, team1TxtField);
		team2Box.getChildren().addAll(team2Lbl, team2TxtField);
		btnBox.getChildren().add(submitBtn);

		this.getChildren().addAll(team1Box, team2Box, btnBox);
	}

	public void parseScores() {
		try {
			teamScore1 = Integer.parseInt(team1TxtField.getText());
			teamScore2 = Integer.parseInt(team2TxtField.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public VersusBox(TypeOfMatch match, boolean isTop) {
		this();
		this.topGame = isTop;
		setMatchType(match);
	}

	public VersusBox(TypeOfMatch match, Team team1, Team team2, boolean isTop) {
		this(match, isTop);
		this.team1 = team1;
		this.team2 = team2;
		team1Lbl.setText(team1.toString());
		team2Lbl.setText(team2.toString());
	}

	@Override
	public Team getWinner() {
		parseScores();

		// return null if it's a tie
		return (teamScore1 > teamScore2) ? team1 : ((teamScore2 > teamScore1) ? team2 : null);
	}

	@Override
	public Team getLoser() {
		parseScores();

		// return null if it's a tie
		return (teamScore1 > teamScore2) ? team2 : ((teamScore2 > teamScore1) ? team1 : null);
	}

	public void sendWinner(Team winner) {
		if (next == null || winner == null)
			return;

		if (topGame)
			next.setTeam1(winner);
		else
			next.setTeam2(winner);
	}

	public void setNext(VersusBox box) {
		this.next = box;
	}

	public void setTeam1(Team team) {
		team1 = team;
		team1Lbl.setText(team.toString());
	}

	public void setTeam2(Team team) {
		team2 = team;
		team2Lbl.setText(team.toString());
	}

	public int getTeamScore1() {
		return teamScore1;
	}

	public void setTeamScore1(int teamScore1) {
		this.teamScore1 = teamScore1;
	}

	public int getTeamScore2() {
		return teamScore2;
	}

	public void setTeamScore2(int teamScore2) {
		this.teamScore2 = teamScore2;
	}

	public TypeOfMatch getMatchType() {
		return matchType;
	}

	public void setMatchType(TypeOfMatch matchType) {
		this.matchType = matchType;
	}

	@Override
	public void handle(ActionEvent e) {
		switch(matchType) {
			case GRAND_CHAMPIONSHIP:
				Main.setFirstPlace(getWinner());
				Main.setSecondPlace(getLoser());
				break;
			case SEMI_FINAL:
				Main.setThirdPlace(getLoser(), (teamScore1 > teamScore2) ? teamScore2 : teamScore1);
				sendWinner(getWinner());
				break;
			default:
				sendWinner(getWinner());
				break;
		}
	}
}
