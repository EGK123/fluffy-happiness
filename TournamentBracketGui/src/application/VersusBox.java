package application;

import javafx.scene.layout.*;
import javafx.scene.control.*;

public class VersusBox extends VBox {
	int teamScore1;
	int teamScore2;

	HBox team1Box;
	HBox team2Box;

	HBox btnBox;

	Label team1Lbl;
	Label team2Lbl;

	TextField team1TxtField;
	TextField team2TxtField;

	Button submitBtn;
	TypeOfMatch matchType;

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

}
