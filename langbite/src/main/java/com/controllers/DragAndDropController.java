package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

public class DragAndDropController {

    @FXML
    private Label breadLabel, painLabel, milkLabel, laitLabel;

    @FXML
    private Pane breadTarget, painTarget, milkTarget, laitTarget;

    @FXML
    private Button shuffleButton, resetButton;

    public void initialize() {
        // set up drag events for each word
        setDragEvents(breadLabel, breadTarget, "Correct! Bread");
        setDragEvents(painLabel, painTarget, "Correct! Pain");
        setDragEvents(milkLabel, milkTarget, "Correct! Milk");
        setDragEvents(laitLabel, laitTarget, "Correct! Lait");

        // shuffle button logic
        shuffleButton.setOnAction(event -> shuffleWords());

        // reset button logic
        resetButton.setOnAction(event -> resetGame());
    }

    private void setDragEvents(Label wordLabel, Pane targetPane, String successMessage) {
        wordLabel.setOnDragDetected(event -> {
            wordLabel.startDragAndDrop(TransferMode.MOVE);
            event.consume();
        });

        targetPane.setOnDragOver(event -> {
            if (event.getGestureSource() == wordLabel) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        targetPane.setOnDragDropped(event -> {
            if (event.getGestureSource() == wordLabel) {
                targetPane.getChildren().clear();
                targetPane.getChildren().add(new Label(successMessage));
                wordLabel.setDisable(true);
            }
            event.setDropCompleted(true);
            event.consume();
        });
    }

    private void shuffleWords() {
        // Randomizes the order of the draggable words in the list
        System.out.println("Shuffle words - To be implemented");
    }

    private void resetGame() {
        // clear all targets and resets labels
        resetPane(breadTarget, "Translation for Bread");
        resetPane(painTarget, "Translation for Pain");
        resetPane(milkTarget, "Translation for Milk");
        resetPane(laitTarget, "Translation for Lait");

        // enable all labels
        breadLabel.setDisable(false);
        painLabel.setDisable(false);
        milkLabel.setDisable(false);
        laitLabel.setDisable(false);
    }

    private void resetPane(Pane pane, String placeholderText) {
        pane.getChildren().clear();
        pane.getChildren().add(new Label(placeholderText));
    }
}