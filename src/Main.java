import java.awt.Point;
import java.util.*;
import java.lang.Math.*;
import javafx.application.*;
import javafx.animation.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static Stage mainStage;
    private Model model;
    private Controller controller;
    private StartView sv;
    private EndView ev;
    private View view;

    int highScore = 0;
    int curScore = 0;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Snake");
        stage.setResizable(false);
        stage.show();
        model = new Model(stage);
        controller = new Controller(stage, model);
        sv = new StartView(controller);
        ev = new EndView();
        view = new View(model, controller);
        model.setView(view);
        // set scene
        stage.setScene(sv.init());

        stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.R) {
                model = new Model(stage);
                controller = new Controller(stage, model);
                view = new View(model, controller);
                model.setView(view);
                stage.setScene(sv.init());
            } else if (code == KeyCode.Q) {
                curScore = model.getScore();
                highScore = Math.max(highScore, curScore);
                model = new Model(stage);
                controller = new Controller(stage, model);
                view = new View(model, controller);
                model.setView(view);
                stage.setScene(ev.init(curScore, highScore));
            }
        });
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    int getHighScore() {
        return highScore;
    }

    void setHighScore(int score) {
        highScore = score;
    }
}