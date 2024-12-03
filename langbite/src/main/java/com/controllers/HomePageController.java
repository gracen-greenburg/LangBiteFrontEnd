package com.controllers;

import java.io.IOException;

import org.json.simple.JSONObject;

import com.language.App;
import com.model.DataLoader;
import com.model.SessionManager;
import com.narriation.Narriator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class HomePageController {
    
    @FXML private void switchToFlashcards() throws IOException {
        App.setRoot("flashcards");
    }

    @FXML private void switchToConversations() throws IOException {
        App.setRoot("conversations");
    }

    @FXML private void switchToStories() throws IOException {
        App.setRoot("stories");
    }

    @FXML private void switchToMiniGames() throws IOException {
        App.setRoot("minigameSelect");
    }

    @FXML private void continueToModule() throws IOException {
        // Get the current user from SessionManager
        JSONObject currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            // Show an error alert if no user is logged in
            showAlert(Alert.AlertType.ERROR, "Error", "No user is logged in.");
            return;
        }

        // Get progress data
        JSONObject progress = (JSONObject) currentUser.get("progress");
        if (progress == null) {
            // Show an error  if progress is missing
            showAlert(Alert.AlertType.ERROR, "Error", "User progress data is missing.");
            return;
        }

        int masteredMostCommonWords = ((Long) progress.getOrDefault("mostCommonWordsCompletionPercentage", 0L)).intValue();
        if (masteredMostCommonWords == 100) {
            App.setRoot("ConnectorWords");
        } else {
            App.setRoot("HundredMostCommonWords");
        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
