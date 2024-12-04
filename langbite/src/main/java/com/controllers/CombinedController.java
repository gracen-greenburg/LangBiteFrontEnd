package com.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.model.DataLoader;
import com.model.GrammarRule;
import com.model.GrammarQuestion;
import com.model.Question;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class CombinedController {

    @FXML private VBox questionPane;
    @FXML private VBox explanationPane;
    @FXML private Label questionLabel;
    @FXML private VBox answerChoicesBox; // holds answers
    @FXML private Label ruleExplanationLabel, resultLabel;

    private List<GrammarRule> grammarRules = new ArrayList<>();
    private int currentGrammarRuleIndex = 0;
    private int currentQuestionIndex = 0;

    private String selectedAnswer = null;

    @FXML
    private void initialize() {
        try {
            // Loading Grammar Rules
            JSONArray grammarRulesArray = DataLoader.loadGrammarRulesJSON();
            
            // Creating Grammar Rules Objects
            for (Object obj : grammarRulesArray) {
                JSONObject ruleObject = (JSONObject) obj;

                String ruleText = ruleObject.get("rule-text") != null ? (String) ruleObject.get("rule-text") : "No rule text provided";
                String explanation = ruleObject.get("explanation") != null ? (String) ruleObject.get("explanation") : "No explanation provided";
                String examples = ruleObject.get("examples") != null ? (String) ruleObject.get("examples") : "No examples provided";

                int attempts = ruleObject.get("attempts") != null ? ((Long) ruleObject.get("attempts")).intValue() : 0;
                int correctAttempts = ruleObject.get("correctAttempts") != null ? ((Long) ruleObject.get("correctAttempts")).intValue() : 0;

                GrammarRule rule = new GrammarRule(
                    ruleText,
                    explanation,
                    examples,
                    attempts,
                    correctAttempts,
                    parseQuestions((JSONArray) ruleObject.get("questions"))
                );
                grammarRules.add(rule);
            }

            // Shuffling the rules
            Collections.shuffle(grammarRules);

            if (!grammarRules.isEmpty()) {
                loadGrammarRule(currentGrammarRuleIndex);
            } else {
                questionLabel.setText("No grammar rules found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            questionLabel.setText("Failed to load data.");
        }
    }

    @FXML
    private void handleSubmit() {
        if (selectedAnswer == null) {
            showAlert("Please select an answer!");
            return;
        }

        GrammarQuestion currentQuestion = grammarRules.get(currentGrammarRuleIndex).getQuestions().get(currentQuestionIndex);
        boolean isCorrect = selectedAnswer.equals(currentQuestion.getOptions().get(currentQuestion.getCorrectAnswer()));
        
        if (isCorrect) {
            resultLabel.setText("Correct! Here's Why:");
            resultLabel.setStyle("-fx-text-fill: green;");      
        } else {
            resultLabel.setText("Incorrect. Here's Why:");
            resultLabel.setStyle("-fx-text-fill: red;");
        }

        // Show explanation pane
        showExplanationPane();
        loadExplanation(currentGrammarRuleIndex);
    }

    @FXML
    private void handleNextQuestion() {
        currentQuestionIndex++;
        if (hasNextQuestion()) {
            loadQuestion(currentQuestionIndex);
            showQuestionPane();
        } else {
            currentGrammarRuleIndex++;
            if (hasNextGrammarRule()) {
                currentQuestionIndex = 0;
                loadGrammarRule(currentGrammarRuleIndex);
                showQuestionPane();
            } else {
                showAlert("You have completed all explanations!");
            }
        }
    }

    private void loadGrammarRule(int index) {
        GrammarRule grammarRule = grammarRules.get(index);

        //Shuffling the questions
        Collections.shuffle(grammarRule.getQuestions());

        loadExplanation(index); // Loading explanation
        loadQuestion(0); // Loading the first question with grammar rule
    }

    private void loadQuestion(int index) {
        GrammarRule grammarRule = grammarRules.get(currentGrammarRuleIndex);

        if (index < grammarRule.getQuestions().size()) {
            GrammarQuestion question = grammarRule.getQuestions().get(index);
            questionLabel.setText(question.getText());
            loadAnswerChoices(question);
        } else {
            questionLabel.setText("No questions available.");
        }
    }

    private void loadAnswerChoices(GrammarQuestion question) {
        answerChoicesBox.getChildren().clear(); // Clear any previous answer choices

        List<String> options = question.getOptions();
        for (String option : options) {
            Button answerButton = new Button(option);
            answerButton.getStyleClass().add("button");
            answerButton.setOnAction(event -> handleAnswerSelection(option));
            answerChoicesBox.getChildren().add(answerButton);
        }
    }

    private void handleAnswerSelection(String answer) {
        selectedAnswer = answer;

        for (javafx.scene.Node node : answerChoicesBox.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setStyle("button");  // Reset style for all buttons
            }
        }

        // Highlight the selected button
        for (javafx.scene.Node node : answerChoicesBox.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().equals(answer)) {
                    button.setStyle("-fx-background-color: lightblue;"); // Highlight selected button
                }
            }
        }
    }

    private void loadExplanation(int grammarRuleIndex) {
        GrammarRule grammarRule = grammarRules.get(grammarRuleIndex);
        ruleExplanationLabel.setText(grammarRule.getExplanation() != null ? stripHtmlTags(grammarRule.getExplanation()) : "No explanation provided");
    }
    
    // Getting rid of HTML within the JSON
    private String stripHtmlTags(String html) {
        return html.replaceAll("<[^>]*>", "");
    }
    
    private void showExplanationPane() {
        questionPane.setVisible(false);
        questionPane.setManaged(false);
        explanationPane.setVisible(true);
        explanationPane.setManaged(true);
    }

    private void showQuestionPane() {
        explanationPane.setVisible(false);
        explanationPane.setManaged(false);
        questionPane.setVisible(true);
        questionPane.setManaged(true);
    }

    private boolean hasNextGrammarRule() {
        return currentGrammarRuleIndex < grammarRules.size() - 1;
    }

    private boolean hasNextQuestion() {
        GrammarRule grammarRule = grammarRules.get(currentGrammarRuleIndex);
        return currentQuestionIndex < grammarRule.getQuestions().size() - 1;
    }

    private void showAlert(String message) {
        System.out.println(message);  // Replace with Alert?
    }

    private List<GrammarQuestion> parseQuestions(JSONArray questionsArray) {
        List<GrammarQuestion> questions = new ArrayList<>();
        for (Object obj : questionsArray) {
            JSONObject questionObject = (JSONObject) obj;
    
            // Handling possible null values for questions
            String text = questionObject.get("text") != null ? (String) questionObject.get("text") : "No question text provided";
            String type = questionObject.get("type") != null ? (String) questionObject.get("type") : "undefined";
            String expectedAnswer = questionObject.get("expectedAnswer") != null ? (String) questionObject.get("expectedAnswer") : "";
    
            List<String> options = new ArrayList<>();
            JSONArray optionsArray = (JSONArray) questionObject.get("options");
            if (optionsArray != null) {
                for (Object option : optionsArray) {
                    options.add((String) option);
                }
            }
    
            int correctAnswerIndex = questionObject.get("correctAnswer") != null ? ((Long) questionObject.get("correctAnswer")).intValue() : -1;
            
            GrammarQuestion question = new GrammarQuestion(
                text,
                options,
                correctAnswerIndex,
                type,
                expectedAnswer
            );
            
            questions.add(question);
        }
        return questions;
    }
}
