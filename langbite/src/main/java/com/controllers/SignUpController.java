package com.controllers;

import java.io.IOException;
import com.language.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

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

    @FXML private void switchToLanguageSelect() throws IOException {
        App.setRoot("languageselection");
    }
}
