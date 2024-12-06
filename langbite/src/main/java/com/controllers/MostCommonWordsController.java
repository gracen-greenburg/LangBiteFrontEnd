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

public class MostCommonWordsController {

    @FXML private VBox questionPane;
    @FXML private Label questionLabel;
    @FXML private VBox answerChoicesBox;
    @FXML private Button nextButton;

    private List<Word> commonWords;
    private int currentWordIndex = 0;
    private Word currentWord;

    @FXML
    private void initialize() {
        try {
            // All Words
            List<Word> allWords = DataLoader.loadWords();
            commonWords = new ArrayList<>();

            // 100 most common words
            for (Word word : allWords) {
                if ("100MostCommonWords".equals(word.getType())) {
                    commonWords.add(word);
                }
            }

            // Shuffle 
            Collections.shuffle(commonWords);

            if (!commonWords.isEmpty()) {
                loadQuestion(currentWordIndex);
            } else {
                questionLabel.setText("No common words found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            questionLabel.setText("Failed to load data.");
        }
    }

    @FXML
    private void goBack() {
        try {
            App.setRoot("homepage");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the homepage.");
        }
    }

    private void loadQuestion(int index) {
        if (index < commonWords.size()) {
            currentWord = commonWords.get(index);
            questionLabel.setText("What is the French word for: " + currentWord.getEnglish());
            nextButton.setDisable(true);
            loadAnswerChoices(currentWord);
        } else {
            questionLabel.setText("No more questions available.");
            nextButton.setDisable(true);
        }
    }

    private void loadAnswerChoices(Word correctWord) {
        answerChoicesBox.getChildren().clear(); // Clear

        // Prepare a list of choices including the correct answer and 3 random wrong answers
        List<Word> choices = new ArrayList<>();
        choices.add(correctWord);
        
        // Get 3 wrong answers
        List<Word> shuffledWords = new ArrayList<>(commonWords);
        Collections.shuffle(shuffledWords);
        
        for (Word word : shuffledWords) {
            if (choices.size() >= 4) break;
            if (!word.getFrench().equals(correctWord.getFrench())) {
                choices.add(word);
            }
        }
        
        // Shuffle 
        Collections.shuffle(choices);

        for (Word choice : choices) {
            Button answerButton = new Button(choice.getFrench());
            answerButton.getStyleClass().add("button");
            answerButton.setOnAction(event -> handleAnswerSelection(answerButton, choice.getFrench(), correctWord.getFrench()));
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
        updateProgress();
        if (currentWordIndex < commonWords.size()) {
            loadQuestion(currentWordIndex);
        } else {
            questionLabel.setText("You have completed all the questions!");
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

    @FXML private void updateProgress() {

        JSONObject currentUser = SessionManager.getCurrentUser();
            if (currentUser != null) {
                JSONObject progress = (JSONObject) currentUser.get("progress");

            if (progress != null) {
                // Calculate progress percentage
                int percentage = (int) Math.round(((double) (currentWordIndex + 1) / commonWords.size()) * 100);

                // Clamp value at 100% to prevent rounding issues
                if (percentage > 100) {
                    percentage = 100;
                }   
                
                // Update progress
                progress.put("mostCommonWordsCompletionPercentage", percentage);
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
