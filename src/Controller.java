import javafx.scene.input.*;
import javafx.stage.Stage;

public class Controller {

    Model model;
    Stage stage;
    String direction;
    int level;

    Controller(Stage stage, Model model) {
        this.stage = stage;
        this.model = model;
        this.direction = "right";
        int level = 1;
    }

    public void changeDirection(KeyEvent e) {
        if (e.getCode() == KeyCode.UP && direction != "down") {
            direction = "up";
        } else if (e.getCode() == KeyCode.LEFT && direction != "right") {
            direction = "left";
        } else if (e.getCode() == KeyCode.RIGHT && direction != "left") {
            direction = "right";
        } else if (e.getCode() == KeyCode.DOWN && direction != "up") {
            direction = "down";
        }
        model.updateModelControl(direction);
    }

    public void changeLevel(KeyEvent e) {
        if (e.getCode() == KeyCode.DIGIT1) {
            level = 1;
        } else if (e.getCode() == KeyCode.DIGIT2) {
            level = 2;
        } else if (e.getCode() == KeyCode.DIGIT3) {
            level = 3;
        }
        model.updateModelLevel(level);
    }

    public void startGame(KeyEvent e) {
        if (e.getCode() == KeyCode.S) {
            model.startGame();
        }
    }

}
