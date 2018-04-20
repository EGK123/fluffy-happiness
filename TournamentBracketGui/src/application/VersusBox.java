package application;

import javafx.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class VersusBox extends VBox implements Scoreable {
  private int teamScore1;
  private int teamScore2;

  private HBox team1Box;
  private HBox team2Box;

  private HBox btnBox;
  
  private Label team1Lbl;
  private Label team2Lbl;

  private TextField team1TxtField;
  private TextField team2TxtField;

  private Button submitBtn;

	public VersusBox() {
		HBox team1Box = new HBox();
		HBox team2Box = new HBox();
		HBox btnBox = new HBox();

		team1Lbl = new Label("");
		team2Lbl = new Label("");

		team1TxtField = new TextField();
		team2TxtField = new TextField();

		submitBtn = new Button();

		team1Box.getChildren().addAll(team1Lbl, team1TxtField);
		team2Box.getChildren().addAll(team2Lbl, team2TxtField);
		btnBox.getChildren().add(submitBtn);

		this.getChildren().addAll(team1Box, team2Box, btnBox);
	}

	public VersusBox(TypeOfMatch match) {
		this();
		matchType = match;
	}

	public VersusBox(TypeOfMatch match, Team team1, Team team2) {
		this(match);
		team1Lbl.setText(team1.seed + " " + team1.teamName);
		team2Lbl.setText(team2.seed + " " + team2.teamName);
	}

  @Override
  public Team getWinner() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Team getLoser() {
    // TODO Auto-generated method stub
    return null;
  }

  public void sendWinner(Team winner) {

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
}
