package com.model;

/**
 * The LessonProgressManager class is responsible for managing the progression of a user through the lessons.
 * It updates the user's current module when a lesson is completed and ensures that the progress is saved
 * to the persistent JSON file
 * 
 * 
 * @author LangBite Team
 */
public class LessonProgressManager {

    /**
     * Advances the specified user's progress to the next module after completing the current lesson.
     * 
     * 
     * @param user The user who has completed the lesson.
     * @param lesson The lesson that the user has completed.
     */
    public void completeLesson(User user, Lesson lesson) {
        int nextModule = lesson.getModuleNumber() + 1;
        user.setCurrentModule(nextModule);  // Update module in User object
        DataWriter.updateCurrentModule(user.getUsername(), nextModule);  // Update in JSON file
        System.out.println("User " + user.getUsername() + " has advanced to module " + nextModule);
    }
}
