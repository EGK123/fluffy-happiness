import javafx.*;
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

  public VersusBox(int something) {

  }

}
