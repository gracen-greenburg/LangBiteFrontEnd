package com.model;
import java.util.List;

public class Story {

    private String storyTitle;
    private String storyText;
    private List<String> wordList;

    public Story(String storyTitle, String storyText, List<String> wordList) {
        this.storyTitle = storyTitle;
        this.storyText = storyText;
        this.wordList = wordList;

    }

    public String getTitle() {
        return storyTitle;
    }

    public String getText() {
        return storyText;
    }
    public List<String> getWordList() { 
        return wordList; 
    }


}