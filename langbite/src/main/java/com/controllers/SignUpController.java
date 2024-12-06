package com.controllers;

import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.language.App;
import com.model.DataLoader;
import com.model.DataWriter;
import com.model.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class SignUpController {
    @FXML private TextField txt_firstName;
    @FXML private TextField txt_lastName;
    @FXML private TextField txt_email;
    @FXML private TextField txt_password;

    @FXML private void handleSignUp() throws IOException {
        String firstName  = txt_firstName.getText();
        String lastName = txt_lastName.getText();
        String email = txt_email.getText();
        String password = txt_password.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Sign Up failed", "Fill in all fields");
            return;
        }

        // Load existing users
        JSONArray users = DataLoader.loadUsers();

        // Check if email or username already exists
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.get("email").equals(email)) {
                showAlert(Alert.AlertType.ERROR, "Sign-Up Failed", "Email already in use.");
                return;
            }
        }

        // Create a new user
        JSONObject newUser = new JSONObject();
        newUser.put("id", java.util.UUID.randomUUID().toString());
        newUser.put("first_name", firstName);
        newUser.put("last_name", lastName);
        newUser.put("email", email);
        newUser.put("username", email.split("@")[0]); // makes the username whatever is in front of the @ in the email
        newUser.put("password", password);

        // Add default progress
        JSONObject progress = new JSONObject();
        progress.put("grammarRulesCompletionPercentage", 0L); 
        progress.put("connectorWordsCompletionPercentage", 0L); 
        progress.put("mostCommonWordsCompletionPercentage", 0L); 
        newUser.put("progress", progress);

        // Add new user to the list
        users.add(newUser);

        // Save updated user data
        DataWriter.saveUsers(users);

        // Set new user as the current user
        SessionManager.setCurrentUser(newUser);

        // Navigate to language selection screen
        showAlert(Alert.AlertType.INFORMATION, "Sign Up Successful", "Welcome, " + firstName + "!");
        switchToLanguageSelect();

    }

    // Method to navigate back to login
    @FXML private void switchToLogin() {
        try {
            App.setRoot("loginScreen"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML private void switchToLanguageSelect() throws IOException {
        App.setRoot("languageselection");
    }
}
