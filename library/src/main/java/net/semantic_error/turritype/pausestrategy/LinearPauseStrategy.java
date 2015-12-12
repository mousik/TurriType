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
