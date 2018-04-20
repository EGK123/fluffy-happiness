package application;

import javafx.scene.layout.*;
import javafx.scene.control.*;

public class VersusBox extends VBox implements Scoreable {
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

  public VersusBox() {
    HBox team1Box = new HBox();
    HBox team2Box = new HBox();
    HBox btnBox = new HBox();

    team1Lbl = new Label("TBD");
    team2Lbl = new Label("TBD");

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
    setMatchType(match);
  }

  public VersusBox(TypeOfMatch match, Team team1, Team team2) {
    this(match);
    team1Lbl.setText(team1.seed + " " + team1.teamName);
    team2Lbl.setText(team2.seed + " " + team2.teamName);
  }

  @Override
  public Team getWinner() {
    try {
      teamScore1 = Integer.parseInt(team1TxtField.getText());
      teamScore2 = Integer.parseInt(team2TxtField.getText());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return (teamScore1 > teamScore2) ? team1 : ((teamScore2 > teamScore1) ? team2 : null);
  }


  @Override
  public Team getLoser() {
    try {
      teamScore1 = Integer.parseInt(team1TxtField.getText());
      teamScore2 = Integer.parseInt(team2TxtField.getText());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return (teamScore1 > teamScore2) ? team2 : ((teamScore2 > teamScore1) ? team1 : null);
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

  public TypeOfMatch getMatchType() {
    return matchType;
  }

  public void setMatchType(TypeOfMatch matchType) {
    this.matchType = matchType;
  }
}
