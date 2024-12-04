package com.model;
/*
 * Class Word
 * 
 * @author LangBite Team
 */

import java.util.UUID;

public class Word {
    private String id;
    private String word;
    private String meaning;
    private String english;
    private String french;
    private String gender;         
    private String image;
    private boolean mastered;
    private int correctCount;
    private int attempts;
    private String partOfSpeech;     
    private String type;

    public Word(String english, String french, String gender, String image, boolean mastered, int correctCount, int attempts, String partOfSpeech, String type) {
        this.id = UUID.randomUUID().toString();
        this.english = english;
        this.french = french;
        this.gender = gender;
        this.image = image;
        this.mastered = mastered;
        this.correctCount = correctCount;
        this.attempts = attempts;
        this.partOfSpeech = partOfSpeech;
        this.type = type;
    }

    public String getId() {
        return id;
    }
    // Getter for the word
    public String getWord() {
        return word;
    }
    // Getter for the meaning
    public String getMeaning() {
        return meaning;
    }
    public String getGender() {
        return gender;
    }
    public String getPartOfSpeech(){
        return partOfSpeech;
    }
    public String getType() { 
        return type; 
    }
    public String getFrench() { 
        return french; 
    }
    public String getEnglish() { 
        return english; 
    }
    public String getImage() { 
        return image; 
    }
    public int getAttempts() { 
        return attempts; 
    }
    public int getCorrectCount() {
        return correctCount;
     }
    public boolean isMastered() { 
        return mastered; 
    }


    public void incrementAttempts() { 
        attempts++; 
    }
    public void incrementCorrectCount() { 
        correctCount++; 
    }
    public void setMastered(boolean mastered) { 
        this.mastered = mastered; 
    }
    public void setCorrectCount(int count) { 
        this.correctCount = count;
     }

    
}

