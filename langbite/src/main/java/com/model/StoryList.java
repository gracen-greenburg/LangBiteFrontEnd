package com.model;
import java.util.ArrayList;
import java.util.List;

/**
 * The StoryList class manages a list of Story objects, providing functionality 
 * 
 * @author LangBite Team
 */
public class StoryList {
    private ArrayList<Story> stories;

    /**
     * Constructs a StoryList object and loads stories from the data source.
     */
    public StoryList() {
        this.stories = DataLoader.loadStories();
        if (this.stories == null) {
            this.stories = new ArrayList<>();
        }
    }

    /**
     * Retrieves all stories in the list.
     * 
     * @return A list of all Story objects.
     */
    public List<Story> getAllStories() {
        return stories;
    }

    /**
     * Retrieves a specific story by title.
     * 
     * @param title The title of the story to retrieve.
     * @return The Story object with the specified title, or null if not found.
     */
    public Story getStoryByTitle(String title) {
        for (Story story : stories) {
            if (story.getTitle().equalsIgnoreCase(title)) {
                return story;
            }
        }
        return null; // Story not found
    }

    /**
     * Adds a new story to the list.
     * 
     * @param story The Story object to add.
     */
    public void addStory(Story story) {
        stories.add(story);
    }

    /**
     * Displays all stories titles and summaries.
     */
    public void displayStories() {
        for (Story story : stories) {
            System.out.println("Title: " + story.getTitle());
            System.out.println("Text: " + story.getText());
            System.out.println("Vocabulary: " + story.getWordList());
            System.out.println("----------------------------------");
        }
    }

    /**
     * Reloads stories from the JSON file, replacing the current list of stories.
     */
    public void reloadStories() {
        this.stories = DataLoader.loadStories();
    }
}
