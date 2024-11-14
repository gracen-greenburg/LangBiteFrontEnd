package com.model;
import java.util.List;
import java.util.Scanner;
/*
 * MultipleChoice
 * 
 * @author LangBite Team
 */
public class MutipleChoice {
    /*
     * MultipleChoice simulates a game where the user chooses an answer
     * out of a selection of choices and grades them based on how many
     * of their answers are correct
     * 
     * @param options List<String> the list of choices the user can select
     * @param correctAnswer char input that the user gives for their selection
     * @param question String the question that the user is answering
     */
    List<String> options;
    private char correctAnswer;
    String question;

    /*
     * Class MultipleChoice instantiates all variables, as well as 
     * constructing a new MultipleChoice question
     */
    public MutipleChoice(String question, List<String> options, char correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    /*
     * Class display shows the user the question and the answers that
     * they can choose from
     */
    public void display() {
        System.out.println("Question: " + question);  // Display the question
        System.out.println("Please select one of the following options:");
        char optionLetter = 'A';  // Start with 'A'
        for (String option : options) {
            System.out.println(optionLetter + ": " + option);  // Display options with letters
            optionLetter++;
        }
    }

    /*
     * Class submitAnswer submits the user's answer and checks to 
     * see if the answser is correct
     */
    public boolean submitAnswer(char choice) {
        choice = Character.toUpperCase(choice);
        if (choice < 'A' || choice >= 'A' + options.size()) {
            System.out.println("Invalid choice. Please try again.");
            return false;
        }
        return choice == correctAnswer;
    }

     // Add this method to return the correct answer
     public char getCorrectAnswer() {
        return correctAnswer;
    }
    

    // Static method to run a test of the MultipleChoice class
    public static void main(String[] args) {
        // Example usage
        List<String> options = List.of("Lyon", "Paris", "Marseille", "Nice");
        MutipleChoice question = new MutipleChoice("What is the capital of France?", options, 'B');  // 'B' is the correct answer for Paris

        question.display();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Your answer (enter the option letter): ");
        char userAnswer = scanner.next().toUpperCase().charAt(0);  

        if (question.submitAnswer(userAnswer)) {
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect. The correct answer was " + question.correctAnswer + ".");
        }

        scanner.close();
    }



}
