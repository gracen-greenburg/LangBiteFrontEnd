package com.model;

public abstract class DataConstants {

    // File paths for loading/saving JSON data
   
    protected static final String LESSONS_FILE_NAME = "LangBite/.vscode/lessons.json";
    protected static final String WORD_FILE_NAME = "langbite/src/main/java/com/data/Words.json";
    protected static final String STORY_FILE_NAME = "langbite/src/main/java/com/data/Story.json"; 
    protected static final String CURRENT_WORDS_FILE = "langbite/src/main/java/com/data/CurrentWords.json";
    protected static final String GRAMMAR_RULES_FILE_NAME = "langbite/src/main/java/com/data/Grammar.json";
    protected static final String USER_FILE = "langbite/src/main/java/com/data/User.json";
    //protected static final String USER_FILE_NAME_JUNIT = "langbite/src/main/java/com/data/lessons.json";
    // protected static final String LESSONS_FILE_NAME_JUNIT = "langbite/src/main/java/com/data/lessons.json";



    // User-related JSON fields
    protected static final String USER = "user";
    protected static final String USER_ID = "userId";
    protected static final String USER_FIRST_NAME = "firstName";
    protected static final String USER_LAST_NAME = "lastName";
    protected static final String USER_USERNAME = "username";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_PROGRESS = "progress";
    protected static final String USER_CURRENT_MODULE = "currentModule";

    // Lesson-related JSON fields
    protected static final String LESSON_MODULE_NUMBER = "moduleNumber";
    protected static final String LESSON_NAME = "lessonName";
    protected static final String LESSON_VOCABULARY = "vocabulary";
    protected static final String LESSON_GRAMMAR = "grammar";
    protected static final String LESSON_CULTURAL_NOTES = "culturalNotes";
    protected static final String LESSON_QUESTIONS = "questions";

    // Vocabulary-related JSON fields
    protected static final String VOCAB_WORD = "word";
    protected static final String VOCAB_MEANING = "meaning";

    // Question-related JSON fields
    protected static final String QUESTION_TYPE = "type";
    protected static final String QUESTION_MULTIPLE_CHOICE = "multipleChoice";
    protected static final String QUESTION_FILL_IN_THE_BLANK = "fillInTheBlank";
    protected static final String QUESTION_DRAG_AND_DROP = "dragAndDrop";

    // Multiple Choice Question Fields
    protected static final String MC_QUESTION = "question";
    protected static final String MC_OPTIONS = "options";
    protected static final String MC_CORRECT_ANSWER = "correctAnswer";

    // Fill-in-the-Blank Question Fields
    protected static final String FILL_BLANK_SENTENCE = "sentence";
    protected static final String FILL_BLANK_CORRECT_ANSWERS = "correctAnswers";

    // Drag-and-Drop Question Fields
    protected static final String DRAG_DROP_ITEMS = "draggableItems";
    protected static final String DRAG_DROP_TARGETS = "dropTargets";
    protected static final String DRAG_DROP_CORRECT_MATCHES = "correctMatches";
    
    // Word-related JSON fields
    protected static final String WORD_ENGLISH = "english";
    protected static final String WORD_FRENCH = "french";
    protected static final String WORD_GENDER = "gender";
    protected static final String WORD_IMAGE = "image";
    protected static final String WORD_MASTERED = "mastered";
    protected static final String WORD_CORRECT_COUNT = "correctCount";
    protected static final String WORD_ATTEMPTS = "attempts";
    protected static final String WORD_PART_OF_SPEECH = "partOfSpeech";
    protected static final String WORD_TYPE = "type";


    // Story-related JSON fields
    protected static final String STORY_TITLE = "storyTitle";                      
    protected static final String STORY_TEXT = "storyText";                        
    protected static final String STORY_WORDLIST = "wordList";
    

    // Progress Tracker-related fields
    protected static final String PROGRESS_PERCENTAGE = "progressPercentage";
    protected static final String COMPLETED_LESSONS = "completedLessons";


    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName() .startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
    
}