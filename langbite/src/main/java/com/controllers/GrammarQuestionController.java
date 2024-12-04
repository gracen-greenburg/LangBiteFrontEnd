// package com.controllers;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.List;
// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import com.language.App;
// import com.model.DataLoader;
// import com.model.GrammarQuestion;
// import com.model.GrammarRule;

// import javafx.scene.control.TextField;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;

// public class GrammarQuestionController {
//     @FXML private TextField answerField;
//     @FXML private Label questionLabel;
//     @FXML private Button submitButton;
//     @FXML private Label ruleExplanationLabel;
//     private List<JSONObject> questions = new ArrayList<>();
//     private int currentQuestionIndex;
//     private GrammarRule currentGrammarRule;

//     @FXML private void initialize() {
//         try {
//             JSONArray grammarRulesArray = DataLoader.loadGrammarRulesJSON();

//             if (!grammarRulesArray.isEmpty()) {
//                 JSONObject ruleObject = (JSONObject) grammarRulesArray.get(0);

//                 // Properly initialize currentGrammarRule
//                 currentGrammarRule = new GrammarRule(
//                     (String) ruleObject.get("rule-text"),
//                     (String) ruleObject.get("explanation"),
//                     (String) ruleObject.get("examples"),
//                     ((Long) ruleObject.get("attempts")).intValue(),
//                     ((Long) ruleObject.get("correctAttempts")).intValue(),
//                     new ArrayList<>()
//                 );

//                 // Now safely set the explanation label
//                 ruleExplanationLabel.setText(currentGrammarRule.getExplanation());

//                 // Load questions from the current grammar rule
//                 questions.clear();
//                 for (GrammarQuestion question : currentGrammarRule.getQuestions()) {
//                     JSONObject questionObject = new JSONObject();
//                     questionObject.put("text", question.getText());
//                     questionObject.put("type", question.getType());
//                     questionObject.put("options", question.getOptions());
//                     questionObject.put("correctAnswer", question.getCorrectAnswer());
//                     questions.add(questionObject);
//                 }

//                 // Load the current question
//                 if (!questions.isEmpty() && currentQuestionIndex != 0) {
//                     loadQuestion(currentQuestionIndex);
//                 } else {
//                     questionLabel.setText("Unable to load question");
//                 }

//             } else {
//                 questionLabel.setText("Grammar Rules not Found");
//             }

//         } catch (Exception e) {
//             e.printStackTrace();
//             questionLabel.setText("Failed to load data");
//         }
//     }

//     @FXML private void handleSubmit() {
//         String answer = answerField.getText().trim();
//         if (answer.isEmpty()) {
//             showAlert(Alert.AlertType.ERROR, "Submission Failed", "Provide an Answer");
//             return;
//         }

//         // Checking if the answer submitted was correct
//         JSONObject currentQuestion = questions.get(currentQuestionIndex);
//         JSONArray options = (JSONArray) currentQuestion.get("options");
//         int correctAnswerIndex = ((Long) currentQuestion.get("correctAnswer")).intValue();
//         String correctAnswer = (String) options.get(correctAnswerIndex);

//         if (answer.equalsIgnoreCase(correctAnswer)) {
//             showAlert(Alert.AlertType.INFORMATION, "Correct!", "Your answer is correct!");
//         } else {
//             showAlert(Alert.AlertType.ERROR, "Incorrect", "The correct answer is: " + correctAnswer);
//         }

//         moveToExplanationScreen();
//     }

//     private void moveToExplanationScreen() {
//         try {
//             FXMLLoader loader = new FXMLLoader(App.class.getResource("explanations.fxml"));
//             Parent root = loader.load();

//             // Get the controller of the explanation screen
//             explanationController explainationController = loader.getController();

//             // Pass the current grammar rule to the explanation screen
//             explainationController.setGrammarRule(currentGrammarRule);

//             // Set the new scene
//             Scene scene = new Scene(root);
//             App.getPrimaryStage().setScene(scene);

//         } catch (IOException e) {
//             e.printStackTrace();
//             showAlert(Alert.AlertType.ERROR, "Error", "Failed to navigate to the explanation screen");
//         }
//     }

//     private void showAlert(Alert.AlertType alertType, String title, String message) {
//         Alert alert = new Alert(alertType);
//         alert.setTitle(title);
//         alert.setHeaderText(null);
//         alert.setContentText(message);
//         alert.showAndWait();
//     }

//     private void loadQuestion(int index) {
//         if (index < questions.size()) {
//             JSONObject questionObject = questions.get(index);
//             String questionText = (String) questionObject.get("text");

//             questionLabel.setText(questionText);
//             answerField.clear();
//         }
//     }
// }