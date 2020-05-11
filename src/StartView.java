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

public class StartView {

    private Controller controller;
    private Pane mainPane;
    private static final String startScreenURL = "src/images/startscreen.png";
    private static final Image startScreen = new Image(startScreenURL);

    StartView(Controller controller) {
        this.controller = controller;
    }

    public Scene init() {
        mainPane = new Pane();
        mainPane.setPrefSize(1280, 800);
        Scene ret = new Scene(mainPane);
        Rectangle rect = new Rectangle(0, 0, 1280, 800);
        rect.setFill(new ImagePattern(startScreen));
        mainPane.getChildren().add(rect);
        ret.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.S) {
                controller.startGame(e);
            }
        });
        return ret;
    }
}