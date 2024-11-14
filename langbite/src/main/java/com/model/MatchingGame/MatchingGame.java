package com.model.MatchingGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * MatchingGame
 * 
 * @author LangBite Team
 */
public class MatchingGame {
    /*
     * MatchingGame simulates a game where the user matches an English
     * word and its translation together, with the system telling the 
     * user if they were correct or not
     * 
     * @param correctmatches Map<String, String> connects the correct French to English translation
     * @param userMatches Map<String, String> holds all of the user's current answers
     * @param correctUserMatches List<String> tracks the times that the user was correct
     * @param shuffledEnglishWords List<String> shuffled list of English words
     * @param shuffledFrenchWords List<String> shuffled list of French words
     */
    private Map<String, String> correctMatches;
    private Map<String, String> userMatches;
    private Set<String> correctUserMatches;
    private List<String> shuffledEnglishWords;
    private List<String> shuffledFrenchWords;

    /*
     * Class MatchingGame instantiates all variables, as well as 
     * connecting the shuffled words to what their correct 
     * translations would be by calling shuffleWords().
     * Additionally, the correctMatches map stores all words
     * in lowercase to allow case-insensitive matching.
     */
    public MatchingGame(Map<String, String> correctMatches) {
        this.correctMatches = new HashMap<>();
        for (Map.Entry<String, String> entry : correctMatches.entrySet()) {
            this.correctMatches.put(entry.getKey().toLowerCase(), entry.getValue().toLowerCase());
        }
        this.userMatches = new HashMap<>();
        this.correctUserMatches = new HashSet<>();
        shuffledEnglishWords = new ArrayList<>(this.correctMatches.keySet());
        shuffledFrenchWords = new ArrayList<>(this.correctMatches.values());
        shuffleWords();
    }

    /*
     * Class shuffleWords utilizes the Collections.shuffle tool 
     * and shuffles both lists of shuffled words.
     */
    public void shuffleWords() {
        Collections.shuffle(shuffledEnglishWords);
        Collections.shuffle(shuffledFrenchWords);
    }

    public List<String> getShuffledEnglishWords() {
        return new ArrayList<>(shuffledEnglishWords); // Returns list of shuffled English words for testing
    }

    public List<String> getShuffledFrenchWords() {
        return new ArrayList<>(shuffledFrenchWords); // Returns list of shuffled French words for testing
    }

    /*
     * Class displayItemsToMatch puts the English and French words 
     * on the console to display to the user, with the first letter capitalized.
     */
    public void displayItemsToMatch() {
        System.out.println("Match the English words to their French translations:\n");
        System.out.printf("%-20s %-20s\n", "English", "French");
        System.out.println("-------------------- --------------------");
        for (int i = 0; i < shuffledEnglishWords.size(); i++) {
            String englishWord = shuffledEnglishWords.get(i);
            String frenchWord = shuffledFrenchWords.get(i);
            System.out.printf("%-20s %-20s\n", capitalizeFirstLetter(englishWord), capitalizeFirstLetter(frenchWord));
        }
    }

    /*
     * Class makeMatch takes in an English word and a French word. 
     * Checks if the user has already tried to match the words before,
     * or if the user has already gotten the match correct. If not,
     * the program puts the pair into the userMatches,
     * and calls checkMatch() to check the user’s accuracy.
     */
    public void makeMatch(String englishWord, String frenchWord) {
        // Convert the English and French words to lowercase to ensure case-insensitive matching
        String lowercaseEnglish = englishWord.toLowerCase();
        String lowercaseFrench = frenchWord.toLowerCase();
        
        if (correctUserMatches.contains(lowercaseEnglish)) {
            System.out.println(capitalizeFirstLetter(englishWord) + " has already been correctly matched. No need to try again.");
        } else if (userMatches.containsKey(lowercaseEnglish)) {
            System.out.println("You already matched " + capitalizeFirstLetter(englishWord) + " before. Let's see if this match is correct.");
            checkMatch(lowercaseEnglish, lowercaseFrench);
        } else {
            userMatches.put(lowercaseEnglish, lowercaseFrench);
            System.out.println("You matched " + capitalizeFirstLetter(englishWord) + " with " + capitalizeFirstLetter(frenchWord));
            checkMatch(lowercaseEnglish, lowercaseFrench);
        }
    }

    /*
     * Class checkMatch checks against the correctMatches list to see
     * if the user’s selected answer is correct. If the match is
     * correct or incorrect, the user is notified. If the user 
     * got all of the answers correct, then the program 
     * congratulates them.
     */
    private void checkMatch(String englishWord, String frenchWord) {
        if (correctMatches.get(englishWord).equalsIgnoreCase(frenchWord)) {
            // If the match is correct and wasn't previously correct, count progress
            if (!correctUserMatches.contains(englishWord)) {
                correctUserMatches.add(englishWord);
                System.out.println("Good job! " + capitalizeFirstLetter(englishWord) + " is matched correctly now.");
                System.out.println(correctUserMatches.size() + "/7 correct so far.");
            }

            if (correctUserMatches.size() == correctMatches.size()) {
                System.out.println("Congratulations! You've correctly matched all 7 words!");
            }
        } else {
            System.out.println("The match for " + capitalizeFirstLetter(englishWord) + " is incorrect. Try again!");
        }
    }

    /*
     * Class submitAnswer checks if the user got all of
     * the answers correct by comparing user matches to correct matches.
     */
    public boolean submitAnswer() {
        System.out.println("\nChecking your matches...");
        boolean allCorrect = true;
        for (Map.Entry<String, String> entry : userMatches.entrySet()) {
            String correctWord = correctMatches.get(entry.getKey());
            if (!correctWord.equalsIgnoreCase(entry.getValue())) {
                allCorrect = false;
            }
        }
        return allCorrect;
    }

    /*
     * Class resetGame calls the shuffleWords() function, clears 
     * the userMatches and the correctUserMatches.
     */
    public void resetGame() {
        userMatches.clear();
        correctUserMatches.clear();
        shuffleWords();
        System.out.println("Game reset. Try again!");
    }

    /*
     * Class isGameComplete checks if the number of correctUserMatches 
     * and the number of correctMatches are equal, indicating 
     * that the game is complete.
     */
    public boolean isGameComplete() {
        return correctUserMatches.size() == correctMatches.size();
    }

    /*
     * Class getCorrectMatchesCount returns the count of how many 
     * user answers are correct.
     */
    public int getCorrectMatchesCount() {
        return correctUserMatches.size();
    }

    /*
     * Class displayScore displays to the user how 
     * many answers they got correct.
     */
    public void displayScore() {
        System.out.println("You matched " + getCorrectMatchesCount() + "/7 words.");
    }

    /*
     * Class capitalizeFirstLetter capitalizes the first letter 
     * of the given word and ensures that the rest of the word 
     * is in lowercase. 
     */
    private String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public Map<String, String> getUserMatches() {
        return new HashMap<>(userMatches); // Returns user matches
    }

    public Set<String> getCorrectUserMatches() {
        return new HashSet<>(correctUserMatches); // Returns correct user matches in a set
    }
}