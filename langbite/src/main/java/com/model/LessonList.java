package com.model;
import java.util.List;


public class LessonList {
    private List<Lesson> lessons;

    public LessonList() {
        lessons = DataLoader.loadLessons();  // Load lessons from JSON file
    }

    public Lesson getLesson(int moduleNumber) {
        for (Lesson lesson : lessons) {
            if (lesson.getModuleNumber() == moduleNumber) {
                return lesson;
            }
        }
        return null;
    }

}
