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
import javafx.scene.control.TextInputDialog;

public class ProfileController {

    // Path to User.json file
    private static final String USER_JSON_PATH = "src/main/resources/com/language/User.json";

    // Method to navigate to the Home Page
    @FXML
    private void goHomePage() {
        try {
            App.setRoot("homepage"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void updateUsername() {
        // Show input dialog to get new username
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
        // Show input dialog to get new password
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

            // Find and update the logged-in user
            Iterator<?> iterator = users.iterator();
            while (iterator.hasNext()) {
                JSONObject user = (JSONObject) iterator.next();
                if (user.get("username").equals(SessionManager.getCurrentUser().get("username"))) {
                    user.put(field, newValue);
                    SessionManager.getCurrentUser().put(field, newValue); // Update session data
                    break;
                }
            }

            // Write updated data back to file
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
