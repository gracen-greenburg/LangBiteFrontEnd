package com.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.language.App;
import com.model.DataLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;


public class GrammarQuestionController {
    @FXML private TextField answerField;
    @FXML private Label questionLabel;
    @FXML private Button submitButton;
    private List<JSONObject> questions = new ArrayList<>();
    private int currentQuestionIndex;

    @FXML private void initialize() {
        try {
            JSONArray grammarRulesArray = DataLoader.loadGrammarRulesJSON();
            if (!grammarRulesArray.isEmpty()) {
                JSONObject ruleObject = (JSONObject) grammarRulesArray.get(0);
                JSONArray questionsArray = (JSONArray) ruleObject.get("questions");
                
                for(Object question : questionsArray) {
                    questions.add((JSONObject) question);
                }
                
                // Load the current question
                if(!questions.isEmpty()) {
                    loadQuestion(currentQuestionIndex);
                } else {
                    questionLabel.setText("Unable to load quesstion");
                }

                
            } else {
                questionLabel.setText("Grammar Rules not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            questionLabel.setText("Failed to load data");
        }
    }

    @FXML private void handleSubmit() {
        String answer = answerField.getText().trim(); // no extra whitespace 
        if (answer.isEmpty() ) {
            showAlert(Alert.AlertType.ERROR,"Submission Failed", "Provide an Answer");
            return;
        }

        // Checking if the answer submitted was correct
        JSONObject currentQuestion = questions.get(currentQuestionIndex);
        JSONArray options = (JSONArray) currentQuestion.get("options");
        int correctAnswerIndex = ((Long) currentQuestion.get("correctAnswer")).intValue();
        String correctAnswer = (String) options.get(correctAnswerIndex);

        if (answer.equalsIgnoreCase(correctAnswer)) {
            showAlert(Alert.AlertType.INFORMATION, "Correct!", "Your answer is correct!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Incorrect", "The correct answer is: " + correctAnswer);
        }

        //move down question list
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            loadQuestion(currentQuestionIndex);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Complete", "You have completed all Grammar Plural Questions!");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML private void switchPage() {
        try {
            App.setRoot("homepage");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to navigate to the homepage");
        }
    }

    private void loadQuestion(int index) {
        if (index < questions.size()) {
            JSONObject questionObject = questions.get(index);
            String questionText = (String) questionObject.get("text");

            questionLabel.setText(questionText);
            answerField.clear();
        }
    }
}

