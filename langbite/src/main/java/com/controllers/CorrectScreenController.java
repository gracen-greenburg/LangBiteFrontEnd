package com.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CorrectScreenController {

    @FXML
    private Button nextQuestionButton;

    @FXML
    public void initialize() {
        if (nextQuestionButton != null) {
            nextQuestionButton.setOnAction(event -> {
                System.out.println("Navigating to home page...");
                loadHomePage();
            });
        }
    }

    private void loadHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/language/homepage.fxml")); 
            Scene nextScene = new Scene(loader.load());
            Stage stage = (Stage) nextQuestionButton.getScene().getWindow();
            stage.setScene(nextScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}