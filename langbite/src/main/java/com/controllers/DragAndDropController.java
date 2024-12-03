package com.controllers;

import java.util.ArrayList;
import java.util.Collections;

import com.model.DataLoader;
import com.model.Word;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class DragAndDropController {

    @FXML
    private VBox wordsVBox, englishWordsVBox, frenchWordsVBox;

    @FXML
    private Button shuffleButton, resetButton;

    private ArrayList<Word> wordsList;

    public void initialize() {
        wordsList = DataLoader.loadWords();
        Collections.shuffle(wordsList);
        populateWordLabels();

        // shuffle button logic
        //shuffleButton.setOnAction(event -> shuffleWords());

        // reset button logic
        resetButton.setOnAction(event -> resetGame());
    }


    // private void shuffleWords() {
    //     // Randomizes the order of the draggable words in the list
    //     System.out.println("Shuffle words - To be implemented");
    // }

    private void resetGame() {
        for (Word word: wordsList) {
            word.setMastered(false);
            word.setCorrectCount(0);
            word.incrementAttempts();
        }
        populateWordLabels();
    }

    private void populateWordLabels() {
        englishWordsVBox.getChildren().clear();
        frenchWordsVBox.getChildren().clear();

        for (Word word: wordsList) {
            Label englishLabel = createDraggableLabel(word.getEnglish());
            Label frenchLabel = createTargetLabel(word.getFrench());

            englishWordsVBox.getChildren().add(englishLabel);
            frenchWordsVBox.getChildren().add(frenchLabel);

            setDragEvents(englishLabel, frenchLabel, word.getEnglish(), word.getFrench());
        }
    }

    // CREATE DRAGGABLE LABEL
    private Label createDraggableLabel(String text) {
        Label label = new Label(text);
        // using our label set style
        label.setStyle("-fx-background-color: #649a38; -fx-text-fill: white; -fx-padding: 5; -fx-font-size: 14px;");
        label.setOnDragDetected(event -> {
            Dragboard db = label.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(label.getText());
            db.setContent(content);
            event.consume();
        });
        return label;
    }
    
    private Label createTargetLabel(String text) {
        Label label = new Label("Drop here: " + text);
        label.setStyle("-fx-background-color: #f5f5f5; -fx-text-fill: #808080; -fx-padding: 5; -fx-font-size: 14px;");
        return label;
    }

    private void setDragEvents(Label draggableLabel, Label targetLabel, String english, String french) {
        targetLabel.setOnDragOver(event -> { // when being dragged it can be moved
            if (event.getGestureSource() == draggableLabel) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        targetLabel.setOnDragDropped(event -> { // when the drag is over, it is either right or wrong
            Dragboard db = event.getDragboard(); 
            if (db.hasString() && db.getString().equals(english)) {
                targetLabel.setText("Correct! " + english + " = " + french);
                draggableLabel.setDisable(true);
            } else {
                targetLabel.setText("Try Again"); // prompts the user to try again
            }
            event.setDropCompleted(true);
            event.consume();
        });


    }

}
