import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;
/**
 * Jordle class that starts the application.
 *
 * @author Noor Ahmadi
 * @version 9999999.7294
 */
public class Jordle extends Application {
    private static Label[][] lab = new Label[5][6];
    private static Label message = new Label("Try guessing a word!");
    private static int currentRow = 0;
    private static int currentCol = 0;
    private static Random rand = new Random();
    private static String secretWord = Words.list.get(rand.nextInt(Words.list.size())).toUpperCase();
    private static int numGreen = 0;
    private static boolean isSpecial = false;
    private static boolean isSpecialEasy = false;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 650, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Jordle");
        Label title = new Label("Jordle   ");
        Font karnak = Font.loadFont("file:./karnakpro-condensedblack.ttf", 60);
        title.setFont(karnak);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);
        GridPane grid = new GridPane();
        BorderPane.setAlignment(grid, Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                lab[i][j] = new Label();
                lab[i][j].setStyle("-fx-background-color: white; -fx-border-color: #d4d6dA; -fx-border-width: 2;"
                        + " -fx-font-family: 'Verdana'; -fx-font-size: 27px; -fx-font-weight: 800");
                lab[i][j].setPrefSize(62, 62);
                grid.add(lab[i][j], i, j);
            }
        }
        root.setCenter(grid);
        scene.setOnKeyPressed(ke -> {
            if ((!(currentRow >= 6)) && !(numGreen == 5)) {
                checkInput(ke);
            }
        });
        message.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
        Button btn = new Button("Instructions");
        Button btn2 = new Button("Restart");
        RadioButton normalMode = new RadioButton("Classic Mode");
        normalMode.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
        normalMode.fire();
        RadioButton specialMode = new RadioButton("Special Mode");
        specialMode.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
        RadioButton specialModeEasy = new RadioButton("Special Mode Lite");
        specialModeEasy.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
        ToggleGroup modes = new ToggleGroup();
        normalMode.setToggleGroup(modes);
        specialMode.setToggleGroup(modes);
        specialModeEasy.setToggleGroup(modes);
        btn.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
        btn2.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
        GridPane bottom = gridAttributes();
        bottom.add(btn, 2, 3);
        bottom.add(btn2, 1, 3);

        btn.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Stage instructions = new Stage();
                        instructions.setResizable(false);
                        GridPane finalInstrGP = instructions();
                        Scene instructionScene = new Scene(finalInstrGP, 547, 500);
                        instructions.setScene(instructionScene);
                        instructions.show();
                    }
                });
        root.setBottom(bottom);
        RadioButton lightMode = new RadioButton("Light Mode");
        lightMode.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
        RadioButton darkMode = new RadioButton("Dark Mode");
        darkMode.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 12));
        darkMode.setOnAction(e -> {
            darkMode.setTextFill(Color.WHITE);
            title.setTextFill(Color.WHITE);
            lightMode.setTextFill(Color.WHITE);
            message.setTextFill(Color.WHITE);
            specialMode.setTextFill(Color.WHITE);
            normalMode.setTextFill(Color.WHITE);
            specialModeEasy.setTextFill(Color.WHITE);
            root.setBackground(new Background(new BackgroundFill(Color.web("#121214"),
                    CornerRadii.EMPTY, Insets.EMPTY)));
            darkMode();
        });
        lightMode.setOnAction(e -> {
            darkMode.setTextFill(Color.BLACK);
            title.setTextFill(Color.BLACK);
            lightMode.setTextFill(Color.BLACK);
            message.setTextFill(Color.BLACK);
            specialMode.setTextFill(Color.BLACK);
            normalMode.setTextFill(Color.BLACK);
            specialModeEasy.setTextFill(Color.BLACK);
            root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            lightMode();
        });
        ToggleGroup group = new ToggleGroup();
        lightMode.setToggleGroup(group);
        darkMode.setToggleGroup(group);
        lightMode.fire();
        lightMode.setPadding(new Insets(2, 2, 2, 2));
        bottom.add(lightMode, 1, 7);
        bottom.add(darkMode, 2, 7);
        btn2.setOnAction(e -> {
            restart();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    lab[i][j] = new Label();
                    lab[i][j].setPrefSize(62, 62);
                    grid.add(lab[i][j], i, j);
                    if (lightMode.isSelected()) {
                        lab[i][j].setStyle("-fx-background-color: white; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family:"
                                + " 'Verdana'; -fx-font-size: 27px; -fx-font-weight: 800");
                    } else {
                        lab[i][j].setStyle("-fx-background-color: black; -fx-border-color: #3a3a3c;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana';"
                                + " -fx-font-size: 27px; -fx-font-weight: 800; -fx-text-fill: white");
                    }
                }
            }
        });
        normalMode.setOnAction(actionEvent -> {
            isSpecial = false;
            isSpecialEasy = false;
            btn2.fire();
        });
        specialMode.setOnAction(actionEvent -> {
            if (specialMode.isSelected()) {
                isSpecial = true;
                isSpecialEasy = false;
            } else {
                isSpecial = false;
            }
            btn2.fire();
        });
        specialModeEasy.setOnAction(actionEvent -> {
            if (specialModeEasy.isSelected()) {
                isSpecialEasy = true;
                isSpecial = false;
            } else {
                isSpecialEasy = false;
            }
            btn2.fire();
        });
        GridPane right = gridAttributes2();
        GridPane left = gridAttributes2();
        left.add(normalMode, 0, 1);
        right.add(specialMode, 0, 1);
        right.add(specialModeEasy, 0, 2);
        root.setRight(right);
        root.setLeft(left);
    }
    /**
     * Method that checks if key input was valid.
     *
     * @param ke KeyEvent that is the key pressed.
     */
    private static void checkInput(KeyEvent ke) {
        if (ke.getCode().isLetterKey()) {
            if (!(currentCol > 4)) {
                lab[currentCol][currentRow].setText(ke.getCode().getChar());
                lab[currentCol][currentRow].setAlignment(Pos.CENTER);
                currentCol++;
            }
        }
        if (ke.getCode().equals(KeyCode.BACK_SPACE)) {
            if (currentCol > 0) {
                currentCol--;
                lab[currentCol][currentRow].setText(null);
            }
        }
        if (ke.getCode().equals(KeyCode.ENTER)) {
            if (isSpecial) {
                isSpecialEasy = false;
                evaluateSpecial();
            } else if (isSpecialEasy) {
                isSpecial = false;
                evaluateSpecialEasy();
            } else {
                evaluate();
            }
        }
    }

    /**
     * Method that evaluates what the colors of the squares should be.
     */
    private static void evaluate() {
        String guessString = secretWord;
        String greenLetters = "";
        String yellowLetters = "";
        if (numGreen == 5) {
            message.setText("Congratulations! You've guessed the word!");
        } else if (currentRow == 6) {
            message.setText("Game over. The word was " + secretWord.toLowerCase() + ".");
        } else {
            if (currentCol < 5) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Please enter a 5 letter word.");
                error.showAndWait();
            }
            if (currentCol == 5) {
                for (int j = 0; j < 5; j++) {
                    if (lab[j][currentRow].getText().equals(String.valueOf(secretWord.charAt(j)))) {
                        lab[j][currentRow].setStyle("-fx-background-color: #6aaa64; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                + " -fx-font-weight: 800; -fx-text-fill: white");
                        guessString = guessString.replace(String.valueOf(secretWord.charAt(j)), "");
                    } else if (guessString.contains(lab[j][currentRow].getText())) {
                        lab[j][currentRow].setStyle("-fx-background-color: #c9b458; -fx-border-color:"
                                + " #d4d6dA; -fx-border-width: 2; -fx-font-family: 'Verdana';"
                                + " -fx-font-size: 27px; -fx-font-weight: 800; -fx-text-fill: white");
                    } else {
                        lab[j][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                + " -fx-font-weight: 800; -fx-text-fill: white");
                    }
                }
                numGreen = 0;
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: #6aaa64;"
                            + " -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white")) {
                        numGreen++;
                        greenLetters += lab[i][currentRow].getText();
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: #c9b458; -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white")) {
                        if (!(yellowLetters.contains(lab[i][currentRow].getText()))) {
                            yellowLetters += lab[i][currentRow].getText();
                        } else {
                            lab[i][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: #c9b458;"
                            + " -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white")) {
                        if (greenLetters.contains(lab[i][currentRow].getText())) {
                            lab[i][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    }
                }
                currentCol = 0;
                ++currentRow;
                if (currentRow == 6) {
                    message.setText("Game over. The word was " + secretWord.toLowerCase() + ".");
                }
                if (numGreen == 5) {
                    message.setText("Congratulations! You've guessed the word!");
                }
            }
        }
    }

    /**
     * Method that does the colors of the squares for special mode.
     */
    private static void evaluateSpecial() {
        String guessString = secretWord;
        String greenLetters = "";
        String blueLetters = "";
        if (numGreen == 5) {
            message.setText("Congratulations! You've guessed the word!");
        } else if (currentRow == 6) {
            message.setText("Game over. The word was " + secretWord.toLowerCase() + ".");
        } else {
            if (currentCol < 5) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Please enter a 5 letter word.");
                error.showAndWait();
            }
            if (currentCol == 5) {
                for (int j = 0; j < 5; j++) {
                    if (lab[j][currentRow].getText().equals(String.valueOf(secretWord.charAt(j)))) {
                        lab[j][currentRow].setStyle("-fx-background-color: #6aaa64; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                + " -fx-font-weight: 800; -fx-text-fill: white");
                        guessString = guessString.replace(String.valueOf(secretWord.charAt(j)), "");
                    } else if (j == 0) {
                        if (String.valueOf(secretWord.charAt(j + 1)).equals(lab[j][currentRow].getText())) {
                            lab[j][currentRow].setStyle("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        } else {
                            lab[j][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    } else if (j == 4) {
                        if (String.valueOf(secretWord.charAt(j - 1)).equals(lab[j][currentRow].getText())) {
                            lab[j][currentRow].setStyle("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        } else {
                            lab[j][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    } else if (String.valueOf(secretWord.charAt(j - 1)).equals(lab[j][currentRow].getText())
                            || String.valueOf(secretWord.charAt(j + 1)).equals(lab[j][currentRow].getText())) {
                        lab[j][currentRow].setStyle("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                + " -fx-font-weight: 800; -fx-text-fill: white");
                    } else {
                        lab[j][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                + " -fx-font-weight: 800; -fx-text-fill: white");
                    }
                }
                numGreen = 0;
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: #6aaa64;"
                            + " -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white")) {
                        numGreen++;
                        greenLetters += lab[i][currentRow].getText();
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white")) {
                        if (!(blueLetters.contains(lab[i][currentRow].getText()))) {
                            blueLetters += lab[i][currentRow].getText();
                        } else {
                            lab[i][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white")) {
                        if (greenLetters.contains(lab[i][currentRow].getText())) {
                            lab[i][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana';"
                                    + " -fx-font-size: 27px; -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    }
                }
                currentCol = 0;
                ++currentRow;
                if (currentRow == 6) {
                    message.setText("Game over. The word was " + secretWord.toLowerCase() + ".");
                }
                if (numGreen == 5) {
                    message.setText("Congratulations! You've guessed the word!");
                }
            }
        }
    }

    /**
     * Method that does the colors of the Squares for special mode LITE.
     */
    private static void evaluateSpecialEasy() {
        String guessString = secretWord;
        String greenLetters = "";
        String blueLetters = "";
        String yellowLetters = "";
        if (numGreen == 5) {
            message.setText("Congratulations! You've guessed the word!");
        } else if (currentRow == 6) {
            message.setText("Game over. The word was " + secretWord.toLowerCase() + ".");
        } else {
            if (currentCol < 5) {
                Alert error = new Alert(Alert.AlertType.ERROR, "Please enter a 5 letter word.");
                error.showAndWait();
            }
            if (currentCol == 5) {
                for (int j = 0; j < 5; j++) {
                    if (lab[j][currentRow].getText().equals(String.valueOf(secretWord.charAt(j)))) {
                        lab[j][currentRow].setStyle("-fx-background-color: #6aaa64; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                + " -fx-font-weight: 800; -fx-text-fill: white");
                        guessString = guessString.replace(String.valueOf(secretWord.charAt(j)), "");
                    } else if (j == 0) {
                        if (String.valueOf(secretWord.charAt(j + 1)).equals(lab[j][currentRow].getText())) {
                            lab[j][currentRow].setStyle("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        } else if (guessString.contains(lab[j][currentRow].getText())) {
                            lab[j][currentRow].setStyle("-fx-background-color: #c9b458; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        } else {
                            lab[j][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    } else if (j == 4) {
                        if (String.valueOf(secretWord.charAt(j - 1)).equals(lab[j][currentRow].getText())) {
                            lab[j][currentRow].setStyle("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        } else if (guessString.contains(lab[j][currentRow].getText())) {
                            lab[j][currentRow].setStyle("-fx-background-color: #c9b458; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        } else {
                            lab[j][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    } else if (String.valueOf(secretWord.charAt(j - 1)).equals(lab[j][currentRow].getText())
                            || String.valueOf(secretWord.charAt(j + 1)).equals(lab[j][currentRow].getText())) {
                        lab[j][currentRow].setStyle("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                + " -fx-font-weight: 800; -fx-text-fill: white");
                    } else if (guessString.contains(lab[j][currentRow].getText())) {
                        lab[j][currentRow].setStyle("-fx-background-color: #c9b458; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                + " -fx-font-weight: 800; -fx-text-fill: white");
                    } else {
                        lab[j][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                + " -fx-font-weight: 800; -fx-text-fill: white");
                    }
                }
                numGreen = 0;
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: #6aaa64;"
                            + " -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white")) {
                        numGreen++;
                        greenLetters += lab[i][currentRow].getText();
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white")) {
                        if (!(blueLetters.contains(lab[i][currentRow].getText()))) {
                            blueLetters += lab[i][currentRow].getText();
                            yellowLetters += lab[i][currentRow].getText();
                        } else {
                            lab[i][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: blue; -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white")) {
                        if (greenLetters.contains(lab[i][currentRow].getText())) {
                            lab[i][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: #c9b458;"
                            + " -fx-border-color: #d4d6dA; -fx-border-width: 2; -fx-font-family: 'Verdana';"
                            + " -fx-font-size: 27px; -fx-font-weight: 800; -fx-text-fill: white")) {
                        if (!(yellowLetters.contains(lab[i][currentRow].getText()))) {
                            yellowLetters += lab[i][currentRow].getText();
                        } else {
                            lab[i][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    }
                }
                for (int i = 0; i < 5; i++) {
                    if (lab[i][currentRow].getStyle().equals("-fx-background-color: #c9b458;"
                            + " -fx-border-color: #d4d6dA; -fx-border-width: 2; -fx-font-family: 'Verdana';"
                            + " -fx-font-size: 27px; -fx-font-weight: 800; -fx-text-fill: white")) {
                        if (greenLetters.contains(lab[i][currentRow].getText())) {
                            lab[i][currentRow].setStyle("-fx-background-color: #787c7e; -fx-border-color: #d4d6dA;"
                                    + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                                    + " -fx-font-weight: 800; -fx-text-fill: white");
                        }
                    }
                }
                currentCol = 0;
                ++currentRow;
                if (currentRow == 6) {
                    message.setText("Game over. The word was " + secretWord.toLowerCase() + ".");
                }
                if (numGreen == 5) {
                    message.setText("Congratulations! You've guessed the word!");
                }
            }
        }
    }

    /**
     * method that does some of the darkmode logic.
     */
    public static void darkMode() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (lab[i][j].getStyle().equals("-fx-background-color: white; -fx-border-color: #d4d6dA;"
                        + " -fx-border-width: 2; -fx-font-family: 'Verdana';"
                        + " -fx-font-size: 27px; -fx-font-weight: 800")) {
                    lab[i][j].setStyle("-fx-background-color: black; -fx-border-color: #3a3a3c;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                            + " -fx-font-weight: 800; -fx-text-fill: white");
                }
            }

        }

    }

    /**
     * method that does some of the lightmode logic.
     */
    public static void lightMode() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (lab[i][j].getStyle().equals("-fx-background-color: black; -fx-border-color: #3a3a3c;"
                        + " -fx-border-width: 2; -fx-font-family: 'Verdana'; -fx-font-size: 27px;"
                        + " -fx-font-weight: 800; -fx-text-fill: white")) {
                    lab[i][j].setStyle("-fx-background-color: white; -fx-border-color: #d4d6dA;"
                            + " -fx-border-width: 2; -fx-font-family: 'Verdana';"
                            + " -fx-font-size: 27px; -fx-font-weight: 800");
                }
            }
        }
    }

    /**
     * method that does the instruction windows words.
     * @return Gridpane of new instructions menu
     */
    public static GridPane instructions() {
        GridPane gp = new GridPane();
        gp.setVgap(8);
        gp.setPadding(new Insets(3, 20, 20, 7));
        gp.add(new Text("Guess the JORDLE in six tries."), 0, 0);
        gp.add(new Text("Each guess must be a five-letter word."
                + " Hit the enter button to submit."), 0, 2);
        gp.add(new Text("After each guess, the color of the tiles will change"
                + " to show how close your"), 0, 4);
        gp.add(new Text("guess was to the word."), 0, 5);
        gp.add(new Text("------------------------------------------------------"
                + "---------------------------------------------"), 0, 7);
        gp.add(new Text("If the letter was green, the letter is in the word "
                + "and in the correct spot."), 0, 8);
        gp.add(new Text("If the letter was orange, the letter is in the word "
                + "but not in the correct spot."), 0, 10);
        gp.add(new Text("If the letter was grey, the letter is not in the word."), 0, 12);
        gp.add(new Text("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + "~~~~~~~~~~~~~~~~~~~~~~~~"), 0, 13);
        gp.add(new Text("Special Mode: In this mode"
                + " yellow letters are"
                + " eliminated and replaced with blue letters."), 0, 14);
        gp.add(new Text("If the letter becomes blue,"), 0, 15);
        gp.add(new Text("the letter is in the word either one space to"
                + " the left or one space to the right."), 0, 16);
        gp.add(new Text("If the letter is not one before or one after,"
                + " it will be colored grey no matter if it is in the word."), 0, 17);
        gp.add(new Text(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"), 0, 18);
        gp.add(new Text("Special Mode Lite: This mode will include both the yellow"
                + " letters AND the blue letters."), 0, 19);
        gp.add(new Text("Note that blue letters will take priority over yellow letters"), 0, 20);
        gp.add(new Text("For example: if the word was CODES and you guessed SHARE."
                + " The letter S will be yellow as normal,"), 0, 21);
        gp.add(new Text("however the letter E will be blue since it"
                + " is one away from the correct position"), 0, 22);
        return gp;
    }

    /**
     * method that does some of the restart button logic.
     */
    public static void restart() {
        currentRow = 0;
        currentCol = 0;
        message.setText("Try guessing a word!");
        secretWord = Words.list.get(rand.nextInt(Words.list.size())).toUpperCase();
        numGreen = 0;
    }
    /**
     * method that adds attributes to one of the gridpanes.
     * @return Gridpane of new fixed pane
     */
    public static GridPane gridAttributes() {
        GridPane bottom = new GridPane();
        bottom.setHgap(5);
        bottom.setVgap(3);
        bottom.setPrefSize(200, 100);
        bottom.add(message, 0, 3);
        bottom.setAlignment(Pos.CENTER);
        return bottom;
    }
    /**
     * method that adds attributes to one of the gridpanes.
     * @return Gridpane of new fixed pane
     */
    public static GridPane gridAttributes2() {
        GridPane left = new GridPane();
        left.setPadding(new Insets(4, 4, 4, 4));
        left.setVgap(10);
        return left;
    }
}