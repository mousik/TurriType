/*
 * Copyright (C) 2015 Tomáš Valenta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.semantic_error.turritype.pausestrategy;

import java.util.List;
import java.util.Random;

/**
 * Pause strategy with some random pauses to imitate human like writing
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
