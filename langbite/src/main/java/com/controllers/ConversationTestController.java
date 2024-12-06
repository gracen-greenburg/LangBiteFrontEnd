package com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConversationTestController {

    @FXML
    private TextField userInput;

    @FXML
    private Button submitButton;

    @FXML
    private Label progressLabel;

    private int questionNumber = 1;

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> handleSubmit());
    }

    private void handleSubmit() {
        String answer = userInput.getText().trim().toLowerCase();

        if (questionNumber == 1 && answer.equals("green")) {
            loadNextQuestion("Conversation2.fxml", "2/3");
        } else if (questionNumber == 2 && answer.equals("blue")) {
            loadNextQuestion("Conversation3.fxml", "3/3");
        } else if (questionNumber == 3 && answer.equals("red")) {
            loadFinalScreen();
        } else {
            // Optionally, show an error message if the answer is wrong
            userInput.clear();
            userInput.setPromptText("Try again!");
        }
    }

    private void loadNextQuestion(String nextFXML, String progress) {
        try {
            questionNumber++;
            progressLabel.setText(progress);

            Stage stage = (Stage) submitButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/language/" + nextFXML));
            Scene nextScene = new Scene(loader.load());
            stage.setScene(nextScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFinalScreen() {
        try {
            Stage stage = (Stage) submitButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/language/AllAnswersCorrect.fxml"));
            Scene finalScene = new Scene(loader.load());
            stage.setScene(finalScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}