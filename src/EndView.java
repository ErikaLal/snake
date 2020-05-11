import java.util.*;
import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class EndView {

    private Controller controller;
    private Pane mainPane;
    private static final String endScreenURL = "/src/images/endscreen.png";
    private static final Image endScreen = new Image(endScreenURL);

    EndView() {}

    public Scene init(int cs, int hs) {
        mainPane = new Pane();
        mainPane.setPrefSize(1280, 800);
        Scene ret = new Scene(mainPane);
        Rectangle rect = new Rectangle(0, 0, 1280, 800);
        rect.setFill(new ImagePattern(endScreen));
        mainPane.getChildren().add(rect);
        Label curScore = new Label(Integer.toString(cs));
        Label highScore = new Label(Integer.toString(hs));
        curScore.setFont(new Font("Arial", 50));
        curScore.relocate(790, 480);
        highScore.setFont(new Font("Arial", 50));
        highScore.relocate(770, 580);
        mainPane.getChildren().add(curScore);
        mainPane.getChildren().add(highScore);
        ret.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.Q) {
                System.exit(0);
            }
        });
        return ret;
    }
}