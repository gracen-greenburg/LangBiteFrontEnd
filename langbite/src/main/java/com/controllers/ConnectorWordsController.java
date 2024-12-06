package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONObject;

import com.language.App;
import com.model.DataLoader;
import com.model.DataWriter;
import com.model.SessionManager;
import com.model.Word;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

public class ConnectorWordsController {

    @FXML private VBox questionPane;
    @FXML private Label questionLabel;
    @FXML private VBox answerChoicesBox;
    @FXML private Button nextButton;

    private List<Word> connectorWords;
    private int currentWordIndex = 0;
    private Word currentWord;

    @FXML
    private void initialize() {
        try {
            // Load all words
            List<Word> allWords = DataLoader.loadWords();
            connectorWords = new ArrayList<>();

            // Filter words by type "ConnectorWords"
            for (Word word : allWords) {
                if ("ConnectorWords".equals(word.getType())) {
                    connectorWords.add(word);
                }
            }

            // Shuffle the words
            Collections.shuffle(connectorWords);

            if (!connectorWords.isEmpty()) {
                loadQuestion(currentWordIndex);
            } else {
                questionLabel.setText("No connector words found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            questionLabel.setText("Failed to load data.");
        }
    }

    // Method to go back
    @FXML private void goBack() {
        try {
            App.setRoot("homepage"); 
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    private void loadQuestion(int index) {
        if (index < connectorWords.size()) {
            currentWord = connectorWords.get(index);
            questionLabel.setText("What is the English word for: " + currentWord.getFrench());
            nextButton.setDisable(true);
            loadAnswerChoices(currentWord);
        } else {
            questionLabel.setText("No more questions available.");
            nextButton.setDisable(true);
        }
    }

    private void loadAnswerChoices(Word correctWord) {
        answerChoicesBox.getChildren().clear();

        // Prepare a list of choices including the correct answer and 3 random wrong answers
        List<Word> choices = new ArrayList<>();
        choices.add(correctWord);

        // Get 3 wrong answers
        List<Word> shuffledWords = new ArrayList<>(connectorWords);
        Collections.shuffle(shuffledWords);

        for (Word word : shuffledWords) {
            if (choices.size() >= 4) break;
            if (!word.getEnglish().equals(correctWord.getEnglish())) {
                choices.add(word);
            }
        }

        // Shuffle the choices
        Collections.shuffle(choices);

        for (Word choice : choices) {
            Button answerButton = new Button(choice.getEnglish());
            answerButton.getStyleClass().add("button");
            answerButton.setOnAction(event -> handleAnswerSelection(answerButton, choice.getEnglish(), correctWord.getEnglish()));
            answerChoicesBox.getChildren().add(answerButton);
        }
    }

    private void handleAnswerSelection(Button selectedButton, String selected, String correctAnswer) {
        if (selected.equals(correctAnswer)) {
            selectedButton.setStyle("-fx-background-color: green");
            questionLabel.setText("Correct!");
        } else {
            selectedButton.setStyle("-fx-background-color: red");
            questionLabel.setText("Incorrect. The correct answer is: " + correctAnswer);
        }

        for (javafx.scene.Node node : answerChoicesBox.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button != selectedButton) {
                    button.setOpacity(0.5);
                } else {
                    button.setOpacity(1.0);
                }
            }
        }

        for (javafx.scene.Node node : answerChoicesBox.getChildren()) {
            if (node instanceof Button) {
                node.setDisable(true);
            }
        }

        nextButton.setDisable(false);
    }

    @FXML
    private void handleNextQuestion(ActionEvent event) {
        // Next Word
        currentWordIndex++;
        if (currentWordIndex < connectorWords.size()) {
            loadQuestion(currentWordIndex);
        } else {
            questionLabel.setText("You have completed all the questions!");
        }
    }

    

    @FXML private void updateProgress() {
        JSONObject currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            JSONObject progress = (JSONObject) currentUser.get("progress");
            if (progress != null) {
                // Calculate progress percentage
                int percentage = (int) Math.round(((double) (currentWordIndex + 1) / connectorWords.size()) * 100);
    
                // Clamp value at 100% to prevent rounding issues
                if (percentage > 100) {
                    percentage = 100;
                }
    
                // Update progress
                progress.put("connectorWordsCompletionPercentage", percentage);
            }
            
            // Save the updated user data
            DataWriter.saveUser(currentUser);
    
            // Update SessionManager to reflect the latest progress
            SessionManager.setCurrentUser(currentUser); // Sync session with updated user data
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No user is currently logged in.");
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
