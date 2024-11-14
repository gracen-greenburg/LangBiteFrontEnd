package com.model;

import java.util.UUID;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private int currentModule;
    private ProgressTracker progressTracker;

    public User(String firstName, String lastName, String email, String username, String password, int initialModule, ProgressTracker progressTracker) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.currentModule = initialModule;
        this.progressTracker = progressTracker;
    }

    public User(String id, String firstName, String lastName, String email, String username, String password, ProgressTracker progressTracker) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.progressTracker = progressTracker;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    public void setProgressTracker(ProgressTracker progressTracker) {
        this.progressTracker = progressTracker;
    }

    public void setCurrentModule(int module) {
        this.currentModule = module;
    }

    public int getCurrentModule() {
        return currentModule;
    }

    // Method to display user's progress
    public void displayUserProgress() {
        System.out.println("Progress for " + username + ":");
        progressTracker.displayTotalGrammarProgress();
        progressTracker.displayConnectorWordsProgress();
        progressTracker.displayMostCommonWordsProgress();
    }

    // Method to reset user's progress
    public void resetProgress() {
        this.progressTracker = new ProgressTracker(
            progressTracker.getGrammarRulesCompletionPercentage(),
            progressTracker.getConnectorWordsCompletionPercentage(),
            progressTracker.getMostCommonWordsCompletionPercentage()
        );
    }

}
