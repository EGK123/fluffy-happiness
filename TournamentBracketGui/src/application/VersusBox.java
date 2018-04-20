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
    int teamScore1 = 0;
    int teamScore2 = 0;

    HBox team1Box = null;
    HBox team2Box = null;
    HBox btnBox = null;

    team1Lbl = null;
    team2Lbl = null;

    team1TxtField = null;
    team2TxtField = null;

    submitBtn = null;

  }

  public VersusBox(TypeOfMatch match) {


  }

  public VersusBox(TypeOfMatch match, Team team1, Team team2) {

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
