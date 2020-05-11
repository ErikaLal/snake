import java.awt.Point;
import java.util.*;
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

// main window
public class View {
    private int numFruit;
    private Model model;
    private Controller controller;
    private GraphicsContext gc;
    private Label fruitCount;
    private int timeLeft;
    private Label timer;
    private BorderPane mainPane;

    private final int gridSize = 30;
    // fruits
    private static final String appleURL = "src/images/apple.png";
    private static final String bananaURL = "src/images/banana.png";
    private static final String cherryURL = "src/images/cherry.png";
    private static final String grapeURL = "src/images/grape.png";
    private static final String strawberryURL = "src/images/strawberry.png";
    private static final Image apple = new Image(appleURL);
    private static final Image banana = new Image(bananaURL);
    private static final Image cherry = new Image(cherryURL);
    private static final Image grape = new Image(grapeURL);
    private static final Image strawberry = new Image(strawberryURL);
    // snake parts
    // start snake
    private static final String ssuURL = "src/images/ssu.png";
    private static final String sslURL = "src/images/ssl.png";
    private static final String ssrURL = "src/images/ssr.png";
    private static final String ssdURL = "src/images/ssd.png";
    // heads
    private static final String shulURL = "src/images/shul.png";
    private static final String shurURL = "src/images/shur.png";
    private static final String shluURL = "src/images/shlu.png";
    private static final String shldURL = "src/images/shld.png";
    private static final String shruURL = "src/images/shru.png";
    private static final String shrdURL = "src/images/shrd.png";
    private static final String shdlURL = "src/images/shdl.png";
    private static final String shdrURL = "src/images/shdr.png";
    private static final String shuURL = "src/images/shu.png";
    private static final String shlURL = "src/images/shl.png";
    private static final String shrURL = "src/images/shr.png";
    private static final String shdURL = "src/images/shd.png";
    // tongues
    private static final String stuURL = "src/images/stu.png";
    private static final String stlURL = "src/images/stl.png";
    private static final String strURL = "src/images/str.png";
    private static final String stdURL = "src/images/std.png";
    // bodies
    private static final String sbulURL = "src/images/sbul.png";
    private static final String sburURL = "src/images/sbur.png";
    private static final String sbdlURL = "src/images/sbdl.png";
    private static final String sbdrURL = "src/images/sbdr.png";
    private static final String sbudURL = "src/images/sbud.png";
    private static final String sblrURL = "src/images/sblr.png";
    // tails
    private static final String tuURL = "src/images/tu.png";
    private static final String tlURL = "src/images/tl.png";
    private static final String trURL = "src/images/tr.png";
    private static final String tdURL = "src/images/td.png";
    private static final Image ssu = new Image(ssuURL);
    private static final Image ssl = new Image(sslURL);
    private static final Image ssr = new Image(ssrURL);
    private static final Image ssd = new Image(ssdURL);
    private static final Image shu = new Image(shuURL);
    private static final Image shl = new Image(shlURL);
    private static final Image shr = new Image(shrURL);
    private static final Image shd = new Image(shdURL);
    private static final Image shul = new Image(shulURL);
    private static final Image shur = new Image(shurURL);
    private static final Image shlu = new Image(shluURL);
    private static final Image shld = new Image(shldURL);
    private static final Image shru = new Image(shruURL);
    private static final Image shrd = new Image(shrdURL);
    private static final Image shdl = new Image(shdlURL);
    private static final Image shdr = new Image(shdrURL);
    private static final Image stu = new Image(stuURL);
    private static final Image stl = new Image(stlURL);
    private static final Image str = new Image(strURL);
    private static final Image std = new Image(stdURL);
    private static final Image sbul = new Image(sbulURL);
    private static final Image sbur = new Image(sburURL);
    private static final Image sbdl = new Image(sbdlURL);
    private static final Image sbdr = new Image(sbdrURL);
    private static final Image sbud = new Image(sbudURL);
    private static final Image sblr = new Image(sblrURL);
    private static final Image tu = new Image(tuURL);
    private static final Image tl = new Image(tlURL);
    private static final Image tr = new Image(trURL);
    private static final Image td = new Image(tdURL);

    View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        numFruit = 0;
        timeLeft = 30;
    }

    public Scene init() {
        mainPane = new BorderPane();
        mainPane.setPrefSize(1280, 800);
        Scene ret = new Scene(mainPane);
        ToolBar toolbar = new ToolBar();
        Canvas gameArea = new Canvas(1200, 690);
        GraphicsContext gc = gameArea.getGraphicsContext2D();
        this.gc = gc;

        Image apple = new Image(getClass().getResource(appleURL).toString(), true);
        ImageView appleView = new ImageView();
        appleView.setImage(apple);
        Label fruitCount = new Label(Integer.toString(numFruit));
        fruitCount.setFont(new Font("Arial", 20));
        this.fruitCount = fruitCount;
        Region gap = new Region();
        HBox.setHgrow(gap, Priority.ALWAYS);
        gap.setMinWidth(Region.USE_PREF_SIZE);
        Label timer = new Label(Integer.toString(timeLeft));
        timer.setFont(new Font("Arial", 20));
        this.timer = timer;
        toolbar.getItems().add(appleView);
        toolbar.getItems().add(fruitCount);
        toolbar.getItems().add(gap);
        toolbar.getItems().add(timer);

        mainPane.setPrefSize(1280, 800);
        mainPane.setTop(toolbar);
        mainPane.setCenter(gameArea);

        ret.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.UP || code == KeyCode.DOWN || code == KeyCode.LEFT || code == KeyCode.RIGHT) {
                controller.changeDirection(e);
            } else if (code == KeyCode.DIGIT1 || code == KeyCode.DIGIT2 || code == KeyCode.DIGIT3) {
                controller.changeLevel(e);
            }
        });
        model.tickTimer();
        model.initModelLevel(1);

        return ret;
    }

    void updateCountdown(int timeLeft) {
        this.timeLeft = timeLeft;
        timer.setText(Integer.toString(timeLeft));
    }

    void setTimerVisible(boolean b) {
        timer.setVisible(b);
    }

    ImagePattern getSnakePart(ArrayList<Point> snakeBody, int index) {
        ImagePattern ret = null;
        Point curPoint = snakeBody.get(index);
        if (index == 0) {
            // use tongue
            if (snakeBody.get(1).getX() > curPoint.getX()) {
                ret = new ImagePattern(stl);
            } else if (snakeBody.get(1).getY() < curPoint.getY()) {
                ret = new ImagePattern(std);
            } else if (snakeBody.get(1).getY() > curPoint.getY()) {
                ret = new ImagePattern(stu);
            } else {
                ret = new ImagePattern(str);
            }
        } else if (snakeBody.size() == 2) {
            if (snakeBody.get(0).getX() > curPoint.getX()) {
                ret = new ImagePattern(ssr);
            } else if (snakeBody.get(0).getX() < curPoint.getX()) {
                ret = new ImagePattern(ssl);
            } else if (snakeBody.get(0).getY() > curPoint.getY()) {
                ret = new ImagePattern(ssd);
            } else {
                ret = new ImagePattern(ssu);
            }
        } else if (index == snakeBody.size() - 1) {
            // use tail
            if (snakeBody.get(index - 1).getX() > curPoint.getX()) {
                ret = new ImagePattern(tl);
            } else if (snakeBody.get(index - 1).getY() < curPoint.getY()) {
                ret = new ImagePattern(td);
            } else if (snakeBody.get(index - 1).getY() > curPoint.getY()) {
                ret = new ImagePattern(tu);
            } else {
                ret = new ImagePattern(tr);
            }
        } else {
            String start = "";
            String end = "";
            if (snakeBody.get(index - 1).getX() < curPoint.getX()) {
                start = "left";
            } else if (snakeBody.get(index - 1).getX() > curPoint.getX()) {
                start = "right";
            } else if (snakeBody.get(index - 1).getY() < curPoint.getY()) {
                start = "up";
            } else {
                start = "down";
            }
            if (snakeBody.get(index + 1).getX() < curPoint.getX()) {
                end = "left";
            } else if (snakeBody.get(index + 1).getX() > curPoint.getX()) {
                end = "right";
            } else if (snakeBody.get(index + 1).getY() < curPoint.getY()) {
                end = "up";
            } else {
                end = "down";
            }
            if (start == "up") {
                if (end == "left") {
                    if (index == 1) {
                        ret = new ImagePattern(shul);
                    } else {
                        ret = new ImagePattern(sbul);
                    }
                } else if (end == "right") {
                    if (index == 1) {
                        ret = new ImagePattern(shur);
                    } else {
                        ret = new ImagePattern(sbur);
                    }
                } else {
                    if (index == 1) {
                        ret = new ImagePattern(shu);
                    } else {
                        ret = new ImagePattern(sbud);
                    }
                }
            } else if (start == "left") {
                if (end == "up") {
                    if (index == 1) {
                        ret = new ImagePattern(shlu);
                    } else {
                        ret = new ImagePattern(sbul);
                    }
                } else if (end == "right") {
                    if (index == 1) {
                        ret = new ImagePattern(shl);
                    } else {
                        ret = new ImagePattern(sblr);
                    }
                } else {
                    if (index == 1) {
                        ret = new ImagePattern(shld);
                    } else {
                        ret = new ImagePattern(sbdl);
                    }
                }
            } else if (start == "right") {
                if (end == "up") {
                    if (index == 1) {
                        ret = new ImagePattern(shru);
                    } else {
                        ret = new ImagePattern(sbur);
                    }
                } else if (end == "left") {
                    if (index == 1) {
                        ret = new ImagePattern(shr);
                    } else {
                        ret = new ImagePattern(sblr);
                    }
                } else {
                    if (index == 1) {
                        ret = new ImagePattern(shrd);
                    } else {
                        ret = new ImagePattern(sbdr);
                    }
                }
            } else if (start == "down") {
                if (end == "up") {
                    if (index == 1) {
                        ret = new ImagePattern(shd);
                    } else {
                        ret = new ImagePattern(sbud);
                    }
                } else if (end == "right") {
                    if (index == 1) {
                        ret = new ImagePattern(shdr);
                    } else {
                        ret = new ImagePattern(sbdr);
                    }
                } else {
                    if (index == 1) {
                        ret = new ImagePattern(shdl);
                    } else {
                        ret = new ImagePattern(sbdl);
                    }
                }
            }
        }
        return ret;
    }

    void updateView(ArrayList<Point> snakeBody, ArrayList<Point> fruits, ArrayList<String> fruitTypes, int numFruit) {
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(0, 0, 1200, 690);
        int snakeLength = snakeBody.size();
        for (int i = 0; i < snakeLength; ++i) {
            gc.setFill(getSnakePart(snakeBody, i));
            gc.fillRect(snakeBody.get(i).getX() * gridSize, snakeBody.get(i).getY() * gridSize, gridSize, gridSize);
        }
        int fruitAmount = fruits.size();
        for (int i = 0; i < fruitAmount; ++i) {
            if (fruitTypes.get(i) == "apple") {
                gc.setFill(new ImagePattern(apple));
            } else if (fruitTypes.get(i) == "banana") {
                gc.setFill(new ImagePattern(banana));
            } else if (fruitTypes.get(i) == "cherry") {
                gc.setFill(new ImagePattern(cherry));
            } else if (fruitTypes.get(i) == "grape") {
                gc.setFill(new ImagePattern(grape));
            } else if (fruitTypes.get(i) == "strawberry") {
                gc.setFill(new ImagePattern(strawberry));
            }
            gc.fillRect(fruits.get(i).getX() * gridSize, fruits.get(i).getY() * gridSize, gridSize, gridSize);
        }
        fruitCount.setText(Integer.toString(numFruit));
    }
}