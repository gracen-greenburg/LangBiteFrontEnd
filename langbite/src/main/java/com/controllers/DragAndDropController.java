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
        
        shuffleButton.setOnAction(event -> shuffleWords());

        resetButton.setOnAction(event -> resetGame());
    }


    @FXML private void shuffleWords() {
        // Randomizes the order of the draggable words in the list
        Collections.shuffle(wordsList);
        populateWordLabels();
    }

    @FXML private void resetGame() {
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

        // Load 5 words at a time
        int wordsToLoad = Math.min(5, wordsList.size());
        ArrayList<Word> selectedWords = new ArrayList<>(wordsList.subList(0, wordsToLoad));

        //Shuffle the words
        Collections.shuffle(selectedWords);

        ArrayList<String> frenchWords = new ArrayList<>();
        for (Word word : selectedWords) {
            frenchWords.add(word.getFrench());
        }
        Collections.shuffle(frenchWords);

        for (int i = 0; i < wordsToLoad; i++) {
            Word word = selectedWords.get(i);
            String frenchWord = frenchWords.get(i);

            Label englishLabel = createDraggableLabel(word.getEnglish());
            Label frenchLabel = createTargetLabel(frenchWord);

            englishWordsVBox.getChildren().add(englishLabel);
            frenchWordsVBox.getChildren().add(frenchLabel);

            setDragEvents(englishLabel, frenchLabel, word.getEnglish(), frenchWord);
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
        
        label.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
    
            if (db.hasString()) {
                // Check if the dragged word matches the target
                String draggedWord = db.getString();
                if (draggedWord.equals(text)) {
                    label.setText("Correct! " + draggedWord + " = " + text);
                    label.setStyle("-fx-background-color: #649a38; -fx-text-fill: white; -fx-padding: 5; -fx-font-size: 14px;");
                    success = true;
                } else {
                    label.setText("Try Again");
                    label.setStyle("-fx-background-color: #ff6666; -fx-text-fill: white; -fx-padding: 5; -fx-font-size: 14px;");
                }
            }
    
            event.setDropCompleted(success);
            event.consume();
        });
        
        
        return label;
    }

    private void setDragEvents(Label draggableLabel, Label targetLabel, String english, String correctFrench) {
        targetLabel.setOnDragOver(event -> { // when being dragged it can be moved
            if (event.getGestureSource() != targetLabel && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        targetLabel.setOnDragDropped(event -> { // when the drag is over, it is either right or wrong
            Dragboard db = event.getDragboard(); 
            boolean success = false;

            if (db.hasString() && db.getString().equals(english)) {
                targetLabel.setText("Correct! " + english + " = " + correctFrench);
                draggableLabel.setDisable(true);
                draggableLabel.setStyle("-fx-opacity: 0.5;");
                targetLabel.setStyle("-fx-background-color: #649a38; -fx-text-fill: white; -fx-padding: 5; -fx-font-size: 14px;");
                success = true;
            } else {
                targetLabel.setText("Try Again"); // prompts the user to try again
            }
            event.setDropCompleted(true);
            event.consume();
        });


    }

}
