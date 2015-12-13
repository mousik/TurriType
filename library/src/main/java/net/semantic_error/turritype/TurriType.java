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
package net.semantic_error.turritype;


import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import net.semantic_error.turritype.pausestrategy.NaturalPauseStrategy;
import net.semantic_error.turritype.pausestrategy.NoPauseStrategy;
import net.semantic_error.turritype.pausestrategy.PauseStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main class of the library. It's write method is the only way to create WriteRequest
 * and it also holds useful public constants
 */
public class TurriType {

    public static final int VERY_SLOW_SPEED = 300;
    public static final int SLOW_SPEED = 200;
    public static final int NORMAL_SPEED = 100;
    public static final int FAST_SPEED = 50;
    public static final int VERY_FAST_SPEED = 25;

    /**
     * @param text text you want to be written into TextView
     * @return write request with default settings - with NORMAL_SPEED, new LinearInterpolator() and NoPauseStrategy
     */
    public static WriteRequest write(@NonNull String text) {
        return getDefaultWriteRequest(text);
    }

    private static WriteRequest getDefaultWriteRequest(@NonNull String text) {
        checkNotNull(text, "text == null");
        return new WriteRequest(text, NORMAL_SPEED, new LinearInterpolator(), null, null, new NoPauseStrategy());
    }


    /**
     * This class works as a specification for future writing animation creation
     */
    public static class WriteRequest {

        final String text;
        final long avgTimePerChar;
        final TimeInterpolator interpolator;
        final List<TimeInterpolator> wordInterpolatorList;
        final Animator.AnimatorListener animatorListener;
        final PauseStrategy pauseStrategy;


        private WriteRequest(@NonNull String text,
                             @IntRange(from=1) long avgTimePerChar,
                             @NonNull TimeInterpolator interpolator,
                             @Nullable List<TimeInterpolator> wordInterpolatorList,
                             @Nullable Animator.AnimatorListener animatorListener,
                             @NonNull PauseStrategy pauseStrategy) {

            checkNotNull(text, "text == null");
            checkNotNull(interpolator, "interpolator == null");
            checkNotNull(pauseStrategy, "pauseStrategy == null");

            this.text = text;
            this.avgTimePerChar = avgTimePerChar;
            this.interpolator = interpolator;
            this.wordInterpolatorList = wordInterpolatorList;
            this.animatorListener = animatorListener;
            this.pauseStrategy = pauseStrategy;
        }

        /**
         * Sets speed of the animation
         * @param avgMillisPerChar set average time for single character to appear. Because of the
         *                         interpolation every char appearance differs
         * @return new WriteRequest with new speed
         */
        public WriteRequest speed(@IntRange(from=1) long avgMillisPerChar) {
            return new WriteRequest(text, avgMillisPerChar, interpolator, wordInterpolatorList, animatorListener, pauseStrategy);
        }


        /**
         * @param animatorListener animation listener
         * @return new WriteRequest with animatorListener
         */
        public WriteRequest withListener(@NonNull Animator.AnimatorListener animatorListener) {
            checkNotNull(animatorListener, "animatorListener == null");
            return new WriteRequest(text, avgTimePerChar, interpolator, wordInterpolatorList, animatorListener, pauseStrategy);
        }

        /**
         * @param interpolator interpolator fo the whole animation
         * @return new WriteRequest with interpolator
         */
        public WriteRequest withInterpolator(@NonNull TimeInterpolator interpolator) {
            checkNotNull(interpolator, "interpolator == null");
            return new WriteRequest(text, avgTimePerChar, interpolator, new ArrayList<TimeInterpolator>(), animatorListener, pauseStrategy);
        }

        /**
         * @param wordInterpolator interpolator applied to every single word
         * @return new WriteRequest with wordInterpolator
         */
        public WriteRequest withWordInterpolator(@NonNull TimeInterpolator wordInterpolator) {
            checkNotNull(wordInterpolator, "wordInterpolator == null");

            List<TimeInterpolator> wordInterpolatorList = new ArrayList<>();
            wordInterpolatorList.add(wordInterpolator);
            return new WriteRequest(text, avgTimePerChar, null, wordInterpolatorList, animatorListener, pauseStrategy);
        }

        /**
         * @param wordInterpolatorList list of interpolator for use on individual word animations
         * @return new WriteRequest with wordInterpolatorList
         */
        public WriteRequest withWordInterpolatorList(@Size(min = 1) List<TimeInterpolator> wordInterpolatorList) {
            checkNotNull(wordInterpolatorList, "wordInterpolatorList == null");
            if (wordInterpolatorList.size() > 0) {
                throw new IllegalArgumentException("wordInterpolatorList.size() must be > 0");
            }
            return new WriteRequest(text, avgTimePerChar, null, wordInterpolatorList, animatorListener, pauseStrategy);
        }


        /**
         * Convenient method for setting set of word interpolators and NaturalPauseStrategy
         * to mimic random chaotic human-like tiping
         * @return new WriteRequest
         */
        public WriteRequest naturally() {
            ArrayList<TimeInterpolator> naturalWordInterpolatorList = new ArrayList<>();

            naturalWordInterpolatorList.add(new AccelerateDecelerateInterpolator()); // This one works best so far
            naturalWordInterpolatorList.add(new DecelerateInterpolator());
            naturalWordInterpolatorList.add(new FastOutSlowInInterpolator());
            naturalWordInterpolatorList.add(new FastOutLinearInInterpolator());
            naturalWordInterpolatorList.add(new LinearInterpolator());

            return this.setPauseStrategy(new NaturalPauseStrategy()).withWordInterpolatorList(naturalWordInterpolatorList);
        }

        /**
         *
         * @param pauseStrategy custom pause strategy to be used during the animation for brakes betwen words and sentences
         * @return new WriteRequest with your strategy
         */
        public WriteRequest setPauseStrategy(@NonNull PauseStrategy pauseStrategy) {
            checkNotNull(pauseStrategy, "pauseStrategy == null");

            return new WriteRequest(text, avgTimePerChar, interpolator, wordInterpolatorList, animatorListener, pauseStrategy);
        }

        /**
         * @param tv TextView where you want to put your text
         * @return new Animator created for your WriteRequest
         */
        public Animator into(@NonNull TextView tv) {
            checkNotNull(tv, "tv == null");
            return TypeAnimationFactory.create(this, tv);
        }

        @NonNull
        TimeInterpolator getRandomWordInterpolator() {
            if (wordInterpolatorList == null || wordInterpolatorList.size() == 0) {
                throw new IllegalStateException("No word interpolator available");

            } else if (wordInterpolatorList.size() == 1) {
                return wordInterpolatorList.get(0);

            } else {
                Random rand = new Random();
                int randomIndex = rand.nextInt(wordInterpolatorList.size());
                return wordInterpolatorList.get(randomIndex);
            }
        }

    }


    private static void checkNotNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }
}
