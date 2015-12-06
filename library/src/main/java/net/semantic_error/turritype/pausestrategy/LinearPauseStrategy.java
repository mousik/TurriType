package net.semantic_error.turritype.pausestrategy;

/**
 * Created by semanticer on 6. 12. 2015.
 */
public class LinearPauseStrategy implements PauseStrategy {

    private final long pauseAfterWord;
    private final long pauseAfterSentence;

    public LinearPauseStrategy(long pauseAfterWord, long pauseAfterSentence) {
        this.pauseAfterWord = pauseAfterWord;
        this.pauseAfterSentence = pauseAfterSentence;
    }

    @Override
    public long getPauseAfterWord(String word, long millsPerChar) {
        return pauseAfterWord;
    }

    @Override
    public long getPauseAfterSentence(long millsPerChar) {
        return pauseAfterSentence;
    }
}
