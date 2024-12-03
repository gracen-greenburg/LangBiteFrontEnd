package com.controllers;

import java.io.IOException;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.language.App;
import com.model.DataLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import com.model.SessionManager;


public class LoginController  {
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    @FXML private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Login Failed", "Fill in all fields");
            return;
        }

        // Load users from the JSON file
        JSONArray users = DataLoader.loadUsers();

        // Validate username and password
        JSONObject user = findUserByUsername(users, username);
        if (user != null && user.get("password").equals(password)) {
            // Set current user
            SessionManager.setCurrentUser(user);

            // Show sucess and go to home page
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + user.get("first_name") + "!");
            switchToHomePage(); 
        } else {
            // Show error
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username or password");
        }

    }

    private JSONObject findUserByUsername(JSONArray users, String username) {
        for (Object obj : users) {
            JSONObject user = (JSONObject) obj;
            if (user.get("username").equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // @FXML
    // private void switchToSignUp() throws IOException {
    //     App.setRoot("signUpScreen");
    // }

    // @FXML private void switchToHomePage() throws IOException {
    //     App.setRoot("homepage"); // to switch to user that already has data
    // }


    @FXML private void switchToHomePage() {
        try {
            App.setRoot("homepage");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to navigate to the homepage");
        }
    }

    @FXML private void switchToSignUp() {
        try {
            App.setRoot("signUpScreen");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to navigate to the sign-up screen");
        }
    }

    // @FXML
    // private void username() throws IOException {

    // }

    // @FXML
    // private void password() throws IOException {
        
    // }
}

