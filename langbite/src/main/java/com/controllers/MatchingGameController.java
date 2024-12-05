package com.controllers;

import com.model.DataLoader;
import com.model.Word;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class MatchingGameController {

    @FXML
    private Pane mainPane;

    @FXML
    private VBox wordsBox, targetsBox;

    @FXML
    private Button btnShuffle, btnReset;

    @FXML
    private Label lblMatchStatus;

    private List<Word> words;
    private Random random = new Random();
    private List<Line> lines = new ArrayList<>();
    private Map<Line, Boolean> correctLines = new HashMap<>();
    private Label selectedWordLabel = null;

    @FXML
    public void initialize() {
        words = DataLoader.loadWords();
        shuffleWordsAndLoadGameItems();
    }

    private void shuffleWordsAndLoadGameItems() {
        // Shuffle the words list to randomize the game each time
        if (words != null && words.size() >= 5) {
            Collections.shuffle(words, random);
            loadGameItems();
        } else {
            System.out.println("Not enough words loaded to initialize game items.");
        }
    }

    private void loadGameItems() {
        // Clear existing items
        if (wordsBox != null && targetsBox != null) {
            wordsBox.getChildren().clear();
            targetsBox.getChildren().clear();
            clearLines();
        }

        // Load the first 5 words
        if (words != null && words.size() >= 5) {
            List<Word> selectedWords = words.subList(0, 5);

            // Shuffle selected words to randomize the pairing
            List<Word> shuffledWords = new ArrayList<>(selectedWords);
            Collections.shuffle(shuffledWords, random);

            // Create a map to store unique part of speech labels
            Map<String, StackPane> partOfSpeechTargets = new HashMap<>();

            for (Word word : selectedWords) {
                // Create Label for the word in French
                Label wordLabel = createWordLabel(word.getFrench());
                wordsBox.getChildren().add(wordLabel);

                // Add click event to select the word
                wordLabel.setOnMouseClicked(event -> handleWordClick(wordLabel));

                // Create or get the target pane for the part of speech
                String partOfSpeech = word.getPartOfSpeech();
                final StackPane targetPane;

                if (partOfSpeechTargets.containsKey(partOfSpeech)) {
                    targetPane = partOfSpeechTargets.get(partOfSpeech);
                } else {
                    targetPane = new StackPane();
                    targetPane.getStyleClass().add("drop-zone");
                    Label partOfSpeechLabel = new Label(partOfSpeech);
                    partOfSpeechLabel.setStyle("-fx-font-size: 16px; -fx-padding: 5;");
                    targetPane.getChildren().add(partOfSpeechLabel);
                    targetsBox.getChildren().add(targetPane);
                    partOfSpeechTargets.put(partOfSpeech, targetPane);

                    // Add click event to select the target
                    targetPane.setOnMouseClicked(event -> handleTargetClick(targetPane));
                }

                // Store the reference for drawing lines later
                wordLabel.setUserData(targetPane);
            }
        } else {
            System.out.println("Not enough words loaded to initialize game items.");
        }
    }

    @FXML
    private void handleShuffle() {
        // Shuffle logic for the game elements
        shuffleWordsAndLoadGameItems();
    }

    @FXML
    private void handleReset() {
        // Reset logic for the game elements
        shuffleWordsAndLoadGameItems();
        lblMatchStatus.setText("Matches: 0/5");
    }

    private Label createWordLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-background-color: #649a38; -fx-text-fill: white; -fx-padding: 5; -fx-font-size: 14px;");
        return label;
    }

    private void handleWordClick(Label wordLabel) {
        // Select the word label
        selectedWordLabel = wordLabel;
    }

    private void handleTargetClick(StackPane targetPane) {
        // If a word is selected, try to connect it to the target
        if (selectedWordLabel != null) {
            drawLineBetween(selectedWordLabel, targetPane);
            selectedWordLabel = null; // Reset the selection
        }
    }

    private void drawLineBetween(Label wordLabel, StackPane targetPane) {
    // Create a new line connecting word label and its target pane
    Line line = new Line();

    // Get the scene coordinates for the word label and target pane
    double startX = wordLabel.localToScene(wordLabel.getBoundsInLocal()).getMinX() + wordLabel.getWidth() / 2;
    double startY = wordLabel.localToScene(wordLabel.getBoundsInLocal()).getMinY() + wordLabel.getHeight() / 2;
    double endX = targetPane.localToScene(targetPane.getBoundsInLocal()).getMinX() + targetPane.getWidth() / 2;
    double endY = targetPane.localToScene(targetPane.getBoundsInLocal()).getMinY() + targetPane.getHeight() / 2;

    // Convert the scene coordinates to coordinates relative to mainPane
    double mainPaneStartX = mainPane.sceneToLocal(startX, startY).getX();
    double mainPaneStartY = mainPane.sceneToLocal(startX, startY).getY();
    double mainPaneEndX = mainPane.sceneToLocal(endX, endY).getX();
    double mainPaneEndY = mainPane.sceneToLocal(endX, endY).getY();

    line.setStartX(mainPaneStartX);
    line.setStartY(mainPaneStartY);
    line.setEndX(mainPaneEndX);
    line.setEndY(mainPaneEndY);

    line.setStyle("-fx-stroke: #000000; -fx-stroke-width: 2;");
    mainPane.getChildren().add(line);
    lines.add(line);
    correctLines.put(line, false);

    // Check if the line connects correctly
    if (isCorrectConnection(wordLabel, targetPane)) {
        correctLines.put(line, true);
    } else {
        // If incorrect, remove the line after a short delay
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            if (!correctLines.get(line)) {
                mainPane.getChildren().remove(line);
                lines.remove(line);
            }
        });
        pause.play();
    }
}


    private boolean isCorrectConnection(Label wordLabel, StackPane targetPane) {
        Word word = words.stream()
                .filter(w -> w.getFrench().equals(wordLabel.getText()))
                .findFirst()
                .orElse(null);

        if (word != null) {
            String partOfSpeech = ((Label) targetPane.getChildren().get(0)).getText();
            return word.getPartOfSpeech().equals(partOfSpeech);
        }
        return false;
    }

    private void clearLines() {
        // Remove all previously drawn lines
        mainPane.getChildren().removeAll(lines);
        lines.clear();
        correctLines.clear();
    }
}
