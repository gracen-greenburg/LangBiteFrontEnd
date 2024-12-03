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


public class GrammarQuestionController {
    @FXML private TextField answerField;

    @FXML private void handleSubmit() {
        String answer = answerField.getText();

        if (answer.isEmpty() ) {
            showAlert(Alert.AlertType.ERROR,"Login Failed", "Fill in all fields");
            return;
        }

        /// Load answer JSON and check if the answer is correct. 
        /// If correct, move to correct page. If incorrect, move to incorrect page // try again


    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

