package net.semantic_error.turritype.pausestrategy;

/**
 * Created by semanticer on 10. 11. 2015.
 */
public interface PauseStrategy {
    long getPauseBeforeWord(String[] wordList, int currentWordIndex, long millsPerChar);
    long getPauseAfterSentence(String[] wordList, int currentWordIndex, long millsPerChar);
}
