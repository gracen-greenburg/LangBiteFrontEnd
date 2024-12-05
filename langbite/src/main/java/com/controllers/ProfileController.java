package com.controllers;

import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import com.language.App;
import com.model.DataConstants;
import com.model.SessionManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;

public class ProfileController {

    // Path to User.json file
    private static final String USER_JSON_PATH = "langbite/src/main/java/com/data/User.json";

    @FXML private ProgressBar progressBar;

    @FXML public void initialize() {
        populateProgressBar();
    }

    // Method to navigate to the Home Page
    @FXML private void goHomePage() {
        try {
            App.setRoot("homepage"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateProgressBar() {
        try (FileReader reader = new FileReader(USER_JSON_PATH)) {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(reader);
            JSONArray users = (JSONArray) data.get("users");

            JSONObject currentUser = SessionManager.getCurrentUser();

            // Find the current user in the JSON file
            for (Object obj : users) {
                JSONObject user = (JSONObject) obj;
                if (user.get("username").equals(currentUser.get("username"))) {
                    JSONObject progress = (JSONObject) user.get("progress");

                    // Calculate the combined progress percentage
                    double grammarRules = ((Number) progress.get("grammarRulesCompletionPercentage")).doubleValue();
                    double connectorWords = ((Number) progress.get("connectorWordsCompletionPercentage")).doubleValue();
                    double mostCommonWords = ((Number) progress.get("mostCommonWordsCompletionPercentage")).doubleValue();

                    double totalProgress = (grammarRules + connectorWords + mostCommonWords) / 300.0; // Normalize to [0,1]
                    progressBar.setProgress(totalProgress);
                    return;
                }
            }

            showAlert(Alert.AlertType.ERROR, "Error", "Current user not found.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to populate progress bar.");
        }
    }

    @FXML private void updateUsername() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Username");
        dialog.setHeaderText("Enter a new username:");
        dialog.setContentText("Username:");

        dialog.showAndWait().ifPresent(newUsername -> {
            if (updateUserField("username", newUsername)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Username updated successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update username.");
            }
        });
    }


    @FXML private void updatePassword() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Password");
        dialog.setHeaderText("Enter a new password:");
        dialog.setContentText("Password:");

        dialog.showAndWait().ifPresent(newPassword -> {
            if (updateUserField("password", newPassword)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update password.");
            }
        });
    }

    private boolean updateUserField(String field, String newValue) {
        try (FileReader reader = new FileReader(USER_JSON_PATH)) {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(reader);
            JSONArray users = (JSONArray) data.get("users");

            // Iterate through the users 
            for (Object obj : users) {
                JSONObject user = (JSONObject) obj;
                if (user.get("username").equals(SessionManager.getCurrentUser().get("username"))) {
                    user.put(field, newValue);
                    SessionManager.getCurrentUser().put(field, newValue); // Update session data
                    break;
                }
            }

            // Write updated data back to User.json
            try (FileWriter writer = new FileWriter(USER_JSON_PATH)) {
                writer.write(data.toJSONString());
                writer.flush();
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
