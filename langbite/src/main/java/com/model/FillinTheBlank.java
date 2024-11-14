package com.model;

import java.util.ArrayList;
import java.util.List;
/*
 * FillinTheBlank
 * 
 * @author LangBite Team
 */
public class FillinTheBlank {
    /*
     * FillinTheBlank simulates a game in the terminal where the user
     * has to take a phrase and fill in the missing blanks with the
     * translated words
     * 
     * @param sentenceWithBlanks String holds the string of the sentence that has the blanks
     * @param correctAnswers List<String> is the list of the correct answers for each individual blank
     * @param userAnswers List<String> is the list of the user’s answers
     */
    private String sentenceWithBlanks;
    private List<String> correctAnswers;
    private List<String> userAnswers;

    /*
     * Class FillinTheBlank instantiates all variables, as well as
     * adding in how many blanks will be in the specified sentence
     * so the user can have that number of answers in the 
     * userAnswer's list
     */
    public FillinTheBlank(String sentenceWithBlanks, List<String> correctAnswers) {
        this.sentenceWithBlanks = sentenceWithBlanks;
        this.correctAnswers = correctAnswers;
        this.userAnswers = new ArrayList<>();  

        for (int i = 0; i < correctAnswers.size(); i++) {
            userAnswers.add("");
        }
    }

    /*
     * Class display shows the user the question and the 
     * potential answers that they can give
     */
    public void display() {
       String displaySentence = sentenceWithBlanks;
       for(int i = 0; i < correctAnswers.size(); i++) {
            displaySentence = displaySentence.replaceFirst("_+", "__" + (i + 1));
       }
       System.out.println(displaySentence);
    }
    
    /*
     * Class submitAnswer takes in which blank the user is trying 
     * to fill, and then what the user believes the answer to be.
     * Ensures that they user is entering in correct information
     */
    public void submitAnswer(int blankIndex, String answer) {
         if (blankIndex >= 0 && blankIndex < userAnswers.size()) {
            userAnswers.set(blankIndex,answer);
         } else {
            System.out.println("Error: Invalid blank index");
         }
    }


    public boolean checkAnswers() {
        for(int i = 0; i < correctAnswers.size(); i++) {
            if (correctAnswers.get(i).equalsIgnoreCase(userAnswers.get(i)) == false) {
                return false;
            }
        }
        return true;
    }

    // Add this method to return the correct answers
    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    // Test Constructor in main method
    public static void main(String[] args) {
        List<String> correctAnswers = new ArrayList<>();
        correctAnswers.add("quick");
        correctAnswers.add("brown");
        correctAnswers.add("fox");

        FillinTheBlank fillin = new FillinTheBlank("The ___ ___ ___ jumps over the lazy dog.", correctAnswers);
        
        // Display the sentence with blanks
        fillin.display();
        
        // Test submitting answers
        fillin.submitAnswer(0, "quick");
        fillin.submitAnswer(1, "brown");
        fillin.submitAnswer(2, "fox");
        
        // Check answers
        if (fillin.checkAnswers()) {
            System.out.println("All answers are correct!");
        } else {
            System.out.println("Some answers are incorrect.");
        }
    }

    /*
     * Method to return the sentences that have blanks
     */
    public String getSentenceWithBlanks() {
        return sentenceWithBlanks;
    }

    /*
     * Method to return user answers
     */
    public List<String> getUserAnswers() {
        return userAnswers;
    }

}
