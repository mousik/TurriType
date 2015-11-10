package net.semantic_error.turritype;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semanticer on 10. 11. 2015.
 */
public class TypeAnimationFactory {

    // TODO refactor this method
    static Animator create(final TurriType.WriteRequest wr, final TextView tv) {

        // create one animation for whole text with singe interpolator
        if (wr.interpolator != null) {

            return createAddTextAnimation(tv, wr.text, wr.text.length() * wr.avgTimePerChar, wr.interpolator);

        }
        // create set of animations for every word one with interpolator
        else if( wr.wordInterpolatorList != null) {


            // TODO this removes multiple spaces and replaces them with single space
            // TODO Implement better split, not removing anything just separating to array
            String[] words = wr.text.split(" ");
            List<Animator> animatorList = new ArrayList<>();
            String lastWord = " ";
            for (int i = 0; i < words.length; i++) {

                String word = words[i];
                String wordWithSpc = word + " ";

                ValueAnimator wordAnimation = createAddTextAnimation(
                        tv,
                        wordWithSpc,
                        wordWithSpc.length() * wr.avgTimePerChar,
                        wr.getRandomWordInterpolator());

                long pause = 0;

                // natural random pauses after sentences
                if (lastWord.charAt(lastWord.length()-1) == '.' || lastWord.charAt(lastWord.length()-1) == ','){
                    pause = wr.pauseStrategy.getPauseAfterSentence(words, i, wr.avgTimePerChar);
                }
                // and after words
                else {
                    pause = wr.pauseStrategy.getPauseBeforeWord(words, i, wr.avgTimePerChar);
                }

                wordAnimation.setStartDelay(pause);
                lastWord = word;
                animatorList.add(wordAnimation);
            }

            AnimatorSet animator = new AnimatorSet();
            animator.addListener(wr.animatorListener);
            animator.playSequentially(animatorList);
            return animator;

        } else {
            throw new IllegalArgumentException("No interpolator for the animation available");
        }
    }

    private static ValueAnimator createAddTextAnimation(final TextView tv, final String text, final long duration, final TimeInterpolator interpolator) {

        ValueAnimator animator = ValueAnimator.ofInt(1, text.length());
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private int lastIndex = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int index = (int) animation.getAnimatedValue();
                if (index != lastIndex) {
                    tv.append(text, lastIndex, index);
                }
                lastIndex = index;
            }
        });

        return animator;
    }
}
