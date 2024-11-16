package com.model;

import org.junit.Before;
import org.junit.Test;

import com.model.DragandDrop.DragAndDrop;

import static org.junit.Assert.*;
import java.util.Arrays;

import java.util.Collections;
import java.util.List;

public class testLesson {
    private Lesson lesson;
    private List<Word> vocab;

    @Before
    public void setUp() {
        List<Word> vocabulary = Arrays.asList(
            new Word("Hello", "Bonjour", "masculine", "hello.png", false, 0, 0, "noun", "vocabulary"),
            new Word("Thank you", "Merci", "feminine", "thanks.png", false, 0, 0, "expression", "vocabulary")
        );

        List<MutipleChoice> mcQuestions = Arrays.asList(
            new MutipleChoice("What is 'Bonjour' in English?", Arrays.asList("Hello", "Goodbye", "Please"), 'A')
        );

        List<FillinTheBlank> fillInTheBlankQuestions = Arrays.asList(
            new FillinTheBlank("The ___ ___ jumps over the lazy dog.", Arrays.asList("quick", "brown", "fox"))
        );

        List<DragAndDrop> dragAndDropQuestions = Collections.emptyList();

        lesson = new Lesson(
            "French Basics", 
            vocabulary, 
            "Basic Grammar", 
            "French Culture", 
            1, 
            mcQuestions, 
            fillInTheBlankQuestions, 
            dragAndDropQuestions
        );
    }

    // Tests if the initializing in set up worked
    @Test
    public void testConstructor() {
        assertEquals("French Basics", lesson.getLessonName());
        assertEquals("Basic Grammar", lesson.getGrammar());
        assertEquals("French Culture", lesson.getCulturalNotes());
        assertEquals(1, lesson.getModuleNumber());
    }


    // Tests if displaying content works
    @Test
    public void testTeachLessonDisplaysContent() {
        lesson.teachLesson();
    }


    // Tests if the vocab list is empty or not
    @Test
    public void testVocabularyIsNotEmpty() {
        assertFalse("Vocabulary should not be empty", lesson.getVocabulary().isEmpty());
    }

    // Tests the vocab content to see if it is all correct
    @Test
    public void testVocabularyContent() {
        List<Word> vocabulary = lesson.getVocabulary();
        assertEquals(2, vocabulary.size());

        Word firstWord = vocabulary.get(0);
        assertEquals("Hello", firstWord.getEnglish());
        assertEquals("Bonjour", firstWord.getFrench());
        assertEquals("masculine", firstWord.getGender());

        Word secondWord = vocabulary.get(1);
        assertEquals("Thank you", secondWord.getEnglish());
        assertEquals("Merci", secondWord.getFrench());
        assertEquals("feminine", secondWord.getGender());
    }

    // Tests Multiple Choice
    @Test
    public void testMultipleChoiceLesson() {
        MutipleChoice mcQuestion = lesson.getMcQuestions().get(0);
        assertEquals('A', mcQuestion.getCorrectAnswer()); // Ensures correct answer setup
    }


    // Tests Fill in the Blank
    @Test
    public void testFillInTheBlankLesson() {
        FillinTheBlank fillInQuestion = lesson.getFillInTheBlankQuestions().get(0);
        assertEquals("quick", fillInQuestion.getCorrectAnswers().get(0));
        assertEquals("brown", fillInQuestion.getCorrectAnswers().get(1));
        assertEquals("fox", fillInQuestion.getCorrectAnswers().get(2));
    }

    // Tests drag and drop, which is currently empty
    @Test
    public void testEmptyDragAndDropQuestions() {
        assertTrue("Drag and Drop questions should be empty", lesson.getDragAndDropQuestions().isEmpty());
    }

    // Tests what happens when the vocab list is empty
    @Test
    public void testEmptyVocabularyList() {
        Lesson emptyVocabLesson = new Lesson("Empty Vocab Lesson", Collections.emptyList(),
                                             "Grammar", "Culture", 1,
                                             lesson.getMcQuestions(), lesson.getFillInTheBlankQuestions(),
                                             lesson.getDragAndDropQuestions());
        assertTrue("Vocabulary list should be empty", emptyVocabLesson.getVocabulary().isEmpty());
    }

    // Tests when multiple choice is empty
    @Test
    public void testEmptyMultipleChoiceList() {
        Lesson emptyMCQuestionLesson = new Lesson("Empty MC Lesson", vocab,
                                                  "Grammar", "Culture", 1,
                                                  Collections.emptyList(), lesson.getFillInTheBlankQuestions(),
                                                  lesson.getDragAndDropQuestions());
        assertTrue("Multiple Choice list should be empty", emptyMCQuestionLesson.getMcQuestions().isEmpty());
    }

    // Tests when multiple chpice has invalid input
    @Test
    public void testInvalidAnswerInLesson() {
        MutipleChoice mcQuestion = lesson.getMcQuestions().get(0);
        assertFalse("Invalid option 'Z' should return false", mcQuestion.submitAnswer('Z'));
    }

    // Tests if correct answer count works
    @Test
    public void testCorrectAnswersCountInConductLesson() {
        int correctAnswers = lesson.conductLesson();
        assertEquals("Correct answers count should match the expected result", correctAnswers, 1);  // Adjust based on actual answers given
    }

    @Test
    public void testLessonResetCorrectCounter() {
        int firstRunCorrectAnswers = lesson.conductLesson();
        int secondRunCorrectAnswers = lesson.conductLesson();  // Run again to ensure counter resets

        assertEquals("Correct answers count should reset for each run", firstRunCorrectAnswers, secondRunCorrectAnswers);
    }
}
