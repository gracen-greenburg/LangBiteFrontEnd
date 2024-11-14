package com.model;
public class Driver {
    public static void main(String[] args) {
        StoryList storyList = new StoryList();
        
        // // Display all stories
        // storyList.displayStories();
        
        // Retrieve and display a specific story by title
        Story specificStory = storyList.getStoryByTitle("Little Red Riding Hood");
        if (specificStory != null) {
            System.out.println("Selected Story: " + specificStory.getTitle());
            System.out.println("Text: " + specificStory.getText());
            System.out.println("Vocabulary: " + specificStory.getWordList());
        } else {
            System.out.println("Story not found.");
        }
    }
}
