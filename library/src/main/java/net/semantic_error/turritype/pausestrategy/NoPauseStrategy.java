package net.semantic_error.turritype.pausestrategy;

import java.util.List;

/**
 * Created by semanticer on 10. 11. 2015.
 */
public class NoPauseStrategy implements PauseStrategy {
    @Override
    public long getPauseAfterWord(String word, long millsPerChar) {
        return 0;
    }

    @Override
    public long getPauseAfterSentence(long millsPerChar) {
        return 0;
    }
}
