package net.semantic_error.turritype.pausestrategy;

/**
 * Created by semanticer on 10. 11. 2015.
 */
public class NoPauseStrategy implements PauseStrategy {
    @Override
    public long getPauseBeforeWord(String[] wordList, int currentWordIndex, long millsPerChar) {
        return 0;
    }

    @Override
    public long getPauseAfterSentence(String[] wordList, int currentWordIndex, long millsPerChar) {
        return 0;
    }
}
