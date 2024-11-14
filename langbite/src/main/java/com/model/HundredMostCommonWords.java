package com.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import org.json.simple.JSONObject;

public class HundredMostCommonWords {
    private static final Set<String> printedWords = new HashSet<>();
    private ProgressTracker progressTracker;
    private ArrayList<Word> wordList;
    private JSONObject currentUser;

    public HundredMostCommonWords(JSONObject currentUser) {
        this.currentUser = currentUser;

        // Load all words from words.json to count the total "100MostCommonWords"
        ArrayList<Word> allWords = DataLoader.loadWords();
        int totalMostCommonWords = (int) allWords.stream()
                .filter(word -> "100MostCommonWords".equals(word.getType())).count();

        // Retrieve progress data or set defaults
        JSONObject progressData = (JSONObject) currentUser.get("progress");
        int totalRules = progressData != null ? ((Long) progressData.getOrDefault("totalRules", 0L)).intValue() : 0;
        int masteredConnectorWords = progressData != null ? ((Long) progressData.getOrDefault("totalConnectorWords", 0L)).intValue() : 0;
        int masteredMostCommonWords = progressData != null ? ((Long) progressData.getOrDefault("totalMostCommonWords", 0L)).intValue() : 0;

        // Initialize ProgressTracker with parsed data
        this.progressTracker = new ProgressTracker(totalRules, masteredConnectorWords, totalMostCommonWords);

        // Load only "100MostCommonWords" type words for current session
        this.wordList = DataLoader.loadCurrentWords();
        this.wordList.removeIf(word -> !word.getType().equals("100MostCommonWords"));

        if (wordList.isEmpty()) {
            this.wordList = DataLoader.loadNextWordsOfType(new ArrayList<>(), "100MostCommonWords");
            DataWriter.saveWordsToCurrentWords(wordList);
            printNewWords(wordList);
        }
    }

    public void start(Scanner scanner) {
        System.out.println("Starting the Hundred Most Common Words module...");

        Random random = new Random();
        Word previousWord = null;
        boolean loadingFromCurrentWords = true;

        while (true) {
            if (allWordsMastered()) {
                if (loadingFromCurrentWords) {
                    wordList = DataLoader.loadNextWordsOfType(wordList, "100MostCommonWords");
                    if (wordList.isEmpty()) {
                        System.out.println("All words from both files have been mastered!");
                        break;
                    } else {
                        DataWriter.saveWordsToCurrentWords(wordList);
                        printNewWords(wordList);
                        loadingFromCurrentWords = false;
                    }
                } else {
                    ArrayList<Word> nextWords = DataLoader.loadNextWordsOfType(wordList, "100MostCommonWords");
                    if (nextWords.isEmpty()) {
                        System.out.println("All words have been mastered!");
                        break;
                    }
                    wordList.addAll(nextWords);
                    DataWriter.saveWordsToCurrentWords(nextWords);
                    printNewWords(nextWords);
                }
            }

            Word word;
            do {
                word = wordList.get(random.nextInt(wordList.size()));
            } while (word == previousWord && !allWordsMastered());

            if (word.isMastered()) {
                continue;
            }

            previousWord = word;

            System.out.println("French Word: " + word.getFrench());
            System.out.println("[DEBUG] Correct Count: " + word.getCorrectCount() + ", Mastered: " + word.isMastered());

            if (!wordListContainsWord(word)) {
                System.out.println("Image: " + word.getImage());
                DataWriter.saveWordToCurrentWords(word);
            }

            List<String> options = generateOptions(word.getEnglish(), wordList);
            char optionLabel = 'a';
            for (String option : options) {
                System.out.println(optionLabel + ") " + option);
                optionLabel++;
            }

            String userInput = scanner.nextLine();
            String correctOption = String.valueOf((char) ('a' + options.indexOf(word.getEnglish())));

            if (userInput.equalsIgnoreCase(correctOption) || userInput.equalsIgnoreCase(word.getEnglish())) {
                System.out.println("Correct! " + word.getFrench() + " is French for " + word.getEnglish() + ". It is a " + word.getGender() + " " + word.getPartOfSpeech() + ".");
                if (!word.isMastered()) {
                    word.incrementCorrectCount();
                    if (word.getCorrectCount() >= 1) {
                        word.setMastered(true);
                        progressTracker.incrementMasteredMostCommonWords(); // Update progress
                    }
                    DataWriter.saveWordsToCurrentWords(wordList);
                    DataWriter.updateWordsFile(word);
                }
            } else {
                System.out.println("That is incorrect. " + word.getFrench() + " is French for " + word.getEnglish() + ". It is a " + word.getGender() + " " + word.getPartOfSpeech() + ".");
            }

            // Display and save progress after each word attempt
            progressTracker.displayMostCommonWordsProgress();
            saveProgress();
        }
    }

    public void saveProgress() {
        // Update the user's progress with percentages and save
        currentUser.put("progress", progressTracker.toProgressPercentageData());
        DataWriter.saveUser(currentUser);
    }

    public void printNewWords(ArrayList<Word> words) {
        System.out.println("New words added to wordList:");
        for (Word w : words) {
            if (!printedWords.contains(w.getFrench())) {
                System.out.println(w.getFrench());
                printedWords.add(w.getFrench());
            }
        }
    }

    public List<String> generateOptions(String correctAnswer, ArrayList<Word> wordList) {
        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        Random random = new Random();
        while (options.size() < 4) {
            String randomWord = wordList.get(random.nextInt(wordList.size())).getEnglish();
            if (!options.contains(randomWord)) {
                options.add(randomWord);
            }
        }

        Collections.shuffle(options);
        return options;
    }

    public boolean allWordsMastered() {
        for (Word word : wordList) {
            if ("100MostCommonWords".equals(word.getType()) && !word.isMastered()) {
                return false;
            }
        }
        return true;
    }

    public boolean wordListContainsWord(Word word) {
        return wordList.stream().anyMatch(w -> w.getFrench().equals(word.getFrench()));
    }
}
