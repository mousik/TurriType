package net.semantic_error.turritype.pausestrategy;

import java.util.Random;

/**
 * Created by semanticer on 10. 11. 2015.
 */
public class NaturalPauseStrategy implements PauseStrategy {
    @Override
    public long getPauseBeforeWord(String[] wordList, int currentWordIndex, long millsPerChar) {
        // if next word is long we will pause to prepare
        String currentWord = wordList[currentWordIndex];
        Random rand = new Random();
        boolean chance = rand.nextBoolean();
        if (currentWord.length() > 5 && chance) return (millsPerChar/2) * (currentWord.length() - 3);

        return 0;
    }

    @Override
    public long getPauseAfterSentence(String[] wordList, int currentWordIndex, long millsPerChar) {
        return millsPerChar * 10;
    }
}
