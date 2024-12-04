package com.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.language.App;
import com.model.DataLoader;
import com.model.Word;

import javafx.scene.control.TextField;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;


public class FlashcardsController {
    @FXML private TextField answerField;
    @FXML private Label currentWord;
    //@FXML private Button submitButton;
    private ArrayList<Word> words;
    private int currentWordIndex;
    private boolean showEnglishFirst;

    @FXML private void initialize() {
        words = DataLoader.loadWords();
        currentWordIndex = 0;
        showEnglishFirst = true;
        
        if(!words.isEmpty()) {
            loadWord(currentWordIndex, showEnglishFirst);
        } else {
            currentWord.setText("No words available");
        }
    }


    @FXML private void handleToggle(MouseEvent event) {
        showEnglishFirst = !showEnglishFirst; // switches it to french
        loadWord(currentWordIndex, showEnglishFirst);
    }

    @FXML private void handleNext() {
        currentWordIndex++;
        if(currentWordIndex < words.size()) {
            loadWord(currentWordIndex, showEnglishFirst);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "ENd of Flashcards", "You have completed all Flashcards!");
            currentWordIndex++;
            loadWord(currentWordIndex,showEnglishFirst);
        }
    }

    @FXML private void handleBack() {
        currentWordIndex--;
        if(currentWordIndex < words.size()) {
            loadWord(currentWordIndex, showEnglishFirst);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "ENd of Flashcards", "You have completed all Flashcards!");
            currentWordIndex++;
            loadWord(currentWordIndex,showEnglishFirst);
        }
    }

    @FXML private void handleShuffle() {
        Collections.shuffle(words);  // Shuffle the list of words
        currentWordIndex = 0;        // Reset the index to start from the beginning
        loadWord(currentWordIndex, showEnglishFirst);
        showAlert(Alert.AlertType.INFORMATION, "Words Shuffled", "Shuffle Complete!");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML private void goHomePage(MouseEvent event) {
        try {
            App.setRoot("homepage");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to navigate to the homepage");
        }
    }

    private void loadWord(int index, boolean showEnglish) {
        if (index < words.size()) {
            Word word = words.get(index);
            if (showEnglish) {
                currentWord.setText(word.getEnglish());
            } else {
                currentWord.setText(word.getFrench());
            }
        }
    }
}

