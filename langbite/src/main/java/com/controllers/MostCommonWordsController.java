package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONObject;

import com.model.DataLoader;
import com.model.Word;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class  MostCommonWordsController{

    @FXML private Label lblFrenchWord;
    @FXML private Button btnOption1;
    @FXML private Button btnOption2;
    @FXML private Button btnOption3;
    @FXML private Button btnOption4;

    private ArrayList<Word> words;
    private Word currentWord;

    @FXML
    public void initialize() {
        // Load words of type "100MostCommonWords"
        words = DataLoader.loadWords();
        words.removeIf(word -> !word.getType().equals("100MostCommonWords"));

        // Shuffle the words for randomness
        Collections.shuffle(words);

        // Display the first word
        displayNextWord();
    }

    private void displayNextWord() {
        if (words.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, "Congratulations!", "You have mastered all 100MostCommonWords!");
            return;
        }

        // Get the next word
        currentWord = words.remove(0);

        // Set the French word in the label
        lblFrenchWord.setText(currentWord.getFrench());

        // Randomize options for the buttons
        ArrayList<String> options = new ArrayList<>();
        options.add(currentWord.getEnglish()); // Add correct answer

        // Add random incorrect options
        for (Word word : words) {
            if (options.size() >= 4) break;
            if (!options.contains(word.getEnglish())) {
                options.add(word.getEnglish());
            }
        }

        // Shuffle the options
        Collections.shuffle(options);

        // Assign options to buttons
        btnOption1.setText(options.get(0));
        btnOption2.setText(options.get(1));
        btnOption3.setText(options.get(2));
        btnOption4.setText(options.get(3));
    }

    @FXML
    private void handleOption1() {
        checkAnswer(btnOption1.getText());
    }

    @FXML
    private void handleOption2() {
        checkAnswer(btnOption2.getText());
    }

    @FXML
    private void handleOption3() {
        checkAnswer(btnOption3.getText());
    }

    @FXML
    private void handleOption4() {
        checkAnswer(btnOption4.getText());
    }

    private void checkAnswer(String selectedOption) {
        if (selectedOption.equals(currentWord.getEnglish())) {
            showAlert(Alert.AlertType.INFORMATION, "Correct!", "Great job!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Incorrect", "The correct answer was: " + currentWord.getEnglish());
        }

        // Display the next word
        displayNextWord();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
