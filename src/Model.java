import java.awt.Point;
import java.util.*;
import java.util.Random.*;
import javafx.application.*;
import javafx.animation.*;
import javafx.beans.value.*;
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

public class Model {

    static View view;
    static int numFruit;
    static String direction;
    static ArrayList<Point> snakeBody;
    static ArrayList<Point> fruits;
    static ArrayList<String> fruitTypes;
    int level;
    Label levelLabel;
    int speed;
    int timeLeft;
    static Stage stage;
    boolean isPaused;

    static final ArrayList<String> fruitList = new ArrayList(Arrays.asList("apple", "banana", "cherry", "grape", "strawberry"));

    Model(Stage stage) {
        this.stage = stage;
        numFruit = 0;
        snakeBody = new ArrayList();
        snakeBody.add(new Point (20, 11));
        snakeBody.add(new Point(19, 11));
        fruits = new ArrayList();
        fruitTypes = new ArrayList();
        level = 1;
        direction = "right";
        levelLabel = new Label(Integer.toString(level));
        speed = 150;
        timeLeft = 30;
        isPaused = false;
    }

    int getScore() {
        return numFruit;
    }

    void setView(View view) {
        this.view = view;
    }

    // boundaries are 40 width, 22 height
    public static void moveSnake() {
        int nextX = (int) snakeBody.get(0).getX();
        int nextY = (int) snakeBody.get(0).getY();

        if (direction == "up") {
            --nextY;
        } else if (direction == "down") {
            ++nextY;
        } else if (direction == "right") {
            ++nextX;
        } else if (direction == "left") {
            --nextX;
        }

        if (collision(nextX, nextY)) {
            stage.fireEvent(new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.Q, true, true, true, true));
        } else if (ateFruit() != -1) {
            int index = ateFruit();
            fruits.remove(index);
            fruitTypes.remove(index);
            ++numFruit;
            snakeBody.add(0, new Point(nextX, nextY));
            genNewFruit();
        } else {
            int size = snakeBody.size();
            snakeBody.remove(size - 1);
            snakeBody.add(0, new Point(nextX, nextY));
            view.updateView(snakeBody, fruits, fruitTypes, numFruit);
        }
    }

    private static boolean collision(int x, int y) {
        return (snakeBody.contains(new Point(x, y)) || x < 0 || x >= 40 || y < 0 || y >= 23);
    }

    private static int ateFruit() {
        int numFruits = fruits.size();
        for (int i = 0; i < numFruits; ++i) {
            if (snakeBody.get(0).equals(fruits.get(i))) {
                return i;
            }
        }
        return -1;
    }

    void startGame() {
        stage.setScene(view.init());
    }

    void initModelLevel(int level) {
        this.level = level;
        fruits.clear();
        fruitTypes.clear();
        levelLabel.setText(Integer.toString(level));
        if (level == 1) {
            speed = 150;
            fruits.add(new Point(5, 10));
            fruits.add(new Point(13, 13));
            fruits.add(new Point(2, 18));
            fruits.add(new Point(37, 5));
            fruits.add(new Point(21, 4));
            fruitTypes.add("apple");
            fruitTypes.add("banana");
            fruitTypes.add("cherry");
            fruitTypes.add("grape");
            fruitTypes.add("strawberry");
        } else if (level == 2) {
            speed = 100;
            fruits.add(new Point(5, 10));
            fruits.add(new Point(13, 13));
            fruits.add(new Point(2, 18));
            fruits.add(new Point(37, 5));
            fruits.add(new Point(21, 4));
            fruits.add(new Point(3, 9));
            fruits.add(new Point(32, 4));
            fruits.add(new Point(10, 17));
            fruits.add(new Point(28, 15));
            fruits.add(new Point(36,  1));
            fruitTypes.add("apple");
            fruitTypes.add("banana");
            fruitTypes.add("cherry");
            fruitTypes.add("grape");
            fruitTypes.add("strawberry");
            fruitTypes.add("apple");
            fruitTypes.add("banana");
            fruitTypes.add("cherry");
            fruitTypes.add("grape");
            fruitTypes.add("strawberry");
        } else if (level == 3) {
            speed = 70;
            fruits.add(new Point(5, 10));
            fruits.add(new Point(13, 13));
            fruits.add(new Point(2, 18));
            fruits.add(new Point(37, 5));
            fruits.add(new Point(21, 4));
            fruits.add(new Point(3, 9));
            fruits.add(new Point(32, 4));
            fruits.add(new Point(10, 17));
            fruits.add(new Point(28, 15));
            fruits.add(new Point(36,  1));
            fruits.add(new Point(8, 11));
            fruits.add(new Point(17, 3));
            fruits.add(new Point(32, 8));
            fruits.add(new Point(24, 0));
            fruits.add(new Point(13, 2));
            fruitTypes.add("apple");
            fruitTypes.add("banana");
            fruitTypes.add("cherry");
            fruitTypes.add("grape");
            fruitTypes.add("strawberry");
            fruitTypes.add("apple");
            fruitTypes.add("banana");
            fruitTypes.add("cherry");
            fruitTypes.add("grape");
            fruitTypes.add("strawberry");
            fruitTypes.add("apple");
            fruitTypes.add("banana");
            fruitTypes.add("cherry");
            fruitTypes.add("grape");
            fruitTypes.add("strawberry");
        }
        // if fruit spawns on snake, eat the fruit
        int size = fruits.size();
        for (int i = size - 1; i >= 0; --i) {
            if (snakeBody.contains(fruits.get(i))) {
                fruits.remove(i);
                fruitTypes.remove(i);
                ++numFruit;
                genNewFruit();
            }
        }
        // reset timer to 30
        if (level == 1 || level == 2) {
            timeLeft = 30;
            view.setTimerVisible(true);
        } else {
            view.setTimerVisible(false);
        }
        view.updateView(snakeBody, fruits, fruitTypes, numFruit);
    }

    static void genNewFruit() {
        // collision detection with snake and other fruit
        int x;
        int y;
        while (true) {
            Random r = new Random();
            x = r.nextInt(39);
            y = r.nextInt(19);
            if (!snakeBody.contains(new Point(x, y)) &&  !fruits.contains(new Point(x, y))) {
                break;
            }
        }
        fruits.add(new Point(x, y));
        Random i = new Random();
        int s = i.nextInt(4);
        fruitTypes.add(fruitList.get(s));
        view.updateView(snakeBody, fruits, fruitTypes, numFruit);
    }

    void updateModelControl(String direction) {
        this.direction = direction;
        view.updateView(snakeBody, fruits, fruitTypes, numFruit);
    }

    void tickTimer() {
        Timeline timeline = new Timeline();
        Timeline timeline2 = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(
            Duration.seconds(1),
            event -> {
                if (timeLeft > 0) {
                    Platform.runLater(() -> view.updateCountdown(timeLeft));
                    --timeLeft;
                } else {
                    if (level == 1) {
                        initModelLevel(2);
                    } else if (level == 2) {
                        initModelLevel(3);
                    }
                }
            }
        ));
        timeline2.getKeyFrames().add(new KeyFrame(
            Duration.millis(speed),
            event -> {
                moveSnake();
            }
        ));
        timeline.play();
        timeline2.play();
        levelLabel.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov, String original, String newvalue) {
                if (level == 1) {
                    timeline2.setRate(1);
                } else if (level == 2) {
                    timeline2.setRate(1.5);
                } else if (level == 3) {
                    timeline2.setRate(2);
                }
            }
        });
        stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.R || code == KeyCode.Q) {
                timeline.stop();
                timeline2.stop();
                timeline.setCycleCount(0);
                timeline2.setCycleCount(0);
            } else if (code == KeyCode.P) {
                if (isPaused) {
                    timeline.play();
                    timeline2.play();
                    isPaused = false;
                } else {
                    timeline.pause();
                    timeline2.pause();
                    isPaused = true;
                }
            }
        });
    }

    void updateModelLevel(int level) {
        this.level = level;
        initModelLevel(level);
    }
}