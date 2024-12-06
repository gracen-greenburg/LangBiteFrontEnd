package com.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CorrectScreenController {

    @FXML
    private Button nextQuestionButton;

    @FXML
    public void initialize() {
        if (nextQuestionButton != null) {
            nextQuestionButton.setOnAction(event -> {
                
                Stage stage = (Stage) nextQuestionButton.getScene().getWindow();
                loadNextQuestion(stage);
            });
        } else {
            System.err.println("nextQuestionButton is not initialized. Check your FXML file.");
        }
    }

    private void loadNextQuestion(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/language/next_question.fxml"));
            Scene nextScene = new Scene(loader.load());
            stage.setScene(nextScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}