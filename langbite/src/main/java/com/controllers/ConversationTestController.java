package com.controllers;

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

    @FXML
    public void initialize() {
        submitButton.setOnAction(event -> {
            String input = userInput.getText().trim().toLowerCase();
            System.out.println("User input: " + input); // Debugging
            if (input.equals("vert")) {
                System.out.println("Correct answer entered!");
                loadCorrectScreen();
            } else {
                System.out.println("Incorrect answer.");
                userInput.clear();
                userInput.setStyle("-fx-border-color: red;");
                userInput.setPromptText("Try again!");
            }
        });
    }

    private void loadCorrectScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/language/conversationscorrect.fxml"));
            Scene correctScene = new Scene(loader.load());
            Stage stage = (Stage) userInput.getScene().getWindow();
            stage.setScene(correctScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}