package com.controllers;

import java.io.IOException;
import com.language.App;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;

public class HomeController  {
    @FXML TextField usernameField;
    @FXML TextField passwordField;

    @FXML private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Login Failed", "Fill in all fields");
            return;
        }


    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void switchToSignUp() throws IOException {
        App.setRoot("signUpScreen");
    }

    @FXML
    private void username() throws IOException {

    }

    @FXML
    private void password() throws IOException {
        
    }
}

