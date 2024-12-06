package com.controllers;

import java.util.Map;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConversationTestController {

    @FXML
    private TextField userInput;

    @FXML
    private Button submitButton;

    private int questionNumber = 1;

    private final Map<Integer, String> answers = Map.of(
        1, "green",
        2, "blue",
        3, "Red"
    );

    private final Map<Integer, String> nextScreens = Map.of(
        1, "conversation2.fxml",
        2, "conversation3.fxml"
    );

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> handleSubmit());
    }

    private void handleSubmit() {
        String answer = userInput.getText().trim().toLowerCase();
        if (answers.get(questionNumber).equals(answer)) {
            userInput.setStyle("-fx-border-color: green;");
            if (questionNumber < 3) {
                loadNextQuestion(nextScreens.get(questionNumber));
            } else {
                loadFinalScreen();
            }
        } else {
            userInput.setStyle("-fx-border-color: red;");
            userInput.clear();
            userInput.setPromptText("Try again!");
        }
    }

    private void loadNextQuestion(String nextFXML) {
        try {
            questionNumber++;
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