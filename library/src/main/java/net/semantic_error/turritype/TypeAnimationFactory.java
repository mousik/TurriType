package net.semantic_error.turritype;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semanticer on 10. 11. 2015.
 */
public class TypeAnimationFactory {

    private static final String TAG = TypeAnimationFactory.class.getSimpleName();

    /**
     *
     * @param wr WriteRequest for which we want to create Animation
     * @param tv TextView where we want our animation to write
     * @return
     */
    static Animator create(final TurriType.WriteRequest wr, final TextView tv) {

        // create one animation for whole text with singe interpolator
        if (wr.interpolator != null) {

            return createAddTextAnimation(tv, wr.text, wr.text.length() * wr.avgTimePerChar, wr.interpolator);

        }
        // create set of animations for every word one with interpolator
        else if( wr.wordInterpolatorList != null && wr.wordInterpolatorList.size() > 0) {

            List<Animator> animatorList = createAddTextAnimationList(wr, tv);
            
            AnimatorSet animator = new AnimatorSet();
            if (wr.animatorListener != null) {
                animator.addListener(wr.animatorListener);
            }

            animator.playSequentially(animatorList);
            return animator;
        }

        throw new IllegalArgumentException("No interpolator or interpolator list specified");
    }

    @NonNull
    private static List<Animator> createAddTextAnimationList(TurriType.WriteRequest wr, TextView tv) {
        String wordBuffer = "";
        char prevCh = '|';
        long pauseAfterPrevPart = 0;
        List<Animator> animatorList = new ArrayList<>();

        for (int i = 0; i < wr.text.length(); i++) {

            // get current letter and add it to wordBuffer
            char ch = wr.text.charAt(i);
            wordBuffer = wordBuffer + ch;

            // if this is end of the word (and not just a long chain of spaces) we want to
            // create animation for this word
            if (ch == ' ' && prevCh != ' ') {

                ValueAnimator wordAnimation = createAddTextAnimation(
                        tv,
                        wordBuffer,
                        wordBuffer.length() * wr.avgTimePerChar,
                        wr.getRandomWordInterpolator());

                // add possible delays from the previous word or sentence
                wordAnimation.setStartDelay(pauseAfterPrevPart);
                animatorList.add(wordAnimation);

                // get relevant pause after this word. If it was the end of the sentence then prefer sentence pause
                // save this pause for next animation's setStartDelay
                if (prevCh == '.' || prevCh == ','){
                    pauseAfterPrevPart = wr.pauseStrategy.getPauseAfterSentence(wr.avgTimePerChar);
                } else {
                    String wordWithoutLastSpace = wordBuffer.substring(0, wordBuffer.length() - 1);
                    Log.d("TAG", wordWithoutLastSpace);
                    pauseAfterPrevPart = wr.pauseStrategy.getPauseAfterWord(wordWithoutLastSpace, wr.avgTimePerChar);
                }

                wordBuffer = ""; // prepare buffer for next word
            }

            prevCh = ch;
        }
        return animatorList;
    }


    private static ValueAnimator createAddTextAnimation(final TextView tv, final String text, final long duration, final TimeInterpolator interpolator) {

        Log.d(TAG, "createAddTextAnimation() called with: " + "tv = [" + tv + "], text = [" + text + "], duration = [" + duration + "], interpolator = [" + interpolator + "]");

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

        Log.d(TAG, "createAddTextAnimation() returned: " + animator);

        return animator;
    }

}
