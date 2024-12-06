package com.controllers;

import java.io.IOException;

import org.json.simple.JSONObject;

import com.language.App;
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
        App.setRoot("storiesselections");
    }

    @FXML private void switchToMiniGames() throws IOException {
        App.setRoot("minigameSelect");
    }

    @FXML private void goToProfile() {
        try {
            App.setRoot("profile"); // Navigate to profile.fxml
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load profile page.");
        }
    }

    @FXML private void goToHomepage() {
        try {
            App.setRoot("homepage"); // Navigate to homepage.fxml
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load homepage.");
        }
    }

    @FXML private void continueToModule() throws IOException {
        // Get the current user from SessionManager
        JSONObject currentUser = SessionManager.getCurrentUser();
        System.out.println("Current User: " + currentUser); // Debugging output

        if (currentUser == null) {
            // Show an error alert if no user is logged in
            showAlert(Alert.AlertType.ERROR, "Error", "No user is logged in.");
            return;
        }

        // Get progress data
        JSONObject progress = (JSONObject) currentUser.get("progress");
        System.out.println("Progress Data: " + progress); // Debugging output

        if (progress == null) {
            // Show an error  if progress is missing
            showAlert(Alert.AlertType.INFORMATION, "Welcome!", "Starting with the most common words module.");
            App.setRoot("mostcommonwords");
            return;
        }

        // Get the completion percentage for most common words
        Number mostCommonWordsCompletion = (Number) progress.getOrDefault("mostCommonWordsCompletionPercentage", 0);
        Number connectorWordsCompletion = (Number) progress.getOrDefault("connectorWordsCompletionPercentage", 0);
        
        System.out.println("Most Common Words Completion Percentage: " + mostCommonWordsCompletion); // Debugging output
        System.out.println("Connector Words Completion Percentage: " + connectorWordsCompletion); // Debugging output



        // Check progress and navigate to the appropriate module
        if (mostCommonWordsCompletion.intValue() >= 100) {
            System.out.println("Navigating to ConnectorWords module"); // Debugging output
            App.setRoot("connectorwords");
        } else if (connectorWordsCompletion.intValue() < 100) {
            System.out.println("Navigating to HundredMostCommonWords module"); // Debugging output
            App.setRoot("mostcommonwords");
        } else {
            System.out.println("Navigating to Grammar and Explanation module");
            App.setRoot("grammarAndExplanation");
        }

    }

    @FXML private void gotoProfile() {
        try {
            App.setRoot("profile"); // Navigate to homepage.fxml
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load homepage.");
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
