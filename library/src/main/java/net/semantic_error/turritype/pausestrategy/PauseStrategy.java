package net.semantic_error.turritype.pausestrategy;

import java.util.List;

/**
 * Created by semanticer on 10. 11. 2015.
 */
public interface PauseStrategy {
    long getPauseAfterWord(String word, long millsPerChar);
    long getPauseAfterSentence(long millsPerChar);
}
