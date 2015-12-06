package net.semantic_error.turritype.pausestrategy;

import java.util.List;
import java.util.Random;

/**
 * Created by semanticer on 10. 11. 2015.
 */
public class NaturalPauseStrategy implements PauseStrategy {

    @Override
    public long getPauseAfterWord(String word, long millsPerChar) {

        // maybe writer needs to take a brake after a long word
        Random rand = new Random();
        boolean chance = rand.nextBoolean();
        if (word.length() > 5 && chance) return (millsPerChar/2) * (word.length() - 3);

        return 0;
    }

    @Override
    public long getPauseAfterSentence(long millsPerChar) {
        // static pause after every sentence
        return millsPerChar * 10;
    }
}
