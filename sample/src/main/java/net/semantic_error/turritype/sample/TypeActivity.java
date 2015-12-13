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
package net.semantic_error.turritype.sample;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import net.semantic_error.turritype.TurriType;
import net.semantic_error.turritype.pausestrategy.LinearPauseStrategy;
import net.semantic_error.turritype.pausestrategy.NaturalPauseStrategy;
import net.semantic_error.turritype.pausestrategy.NoPauseStrategy;
import net.semantic_error.turritype.pausestrategy.PauseStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class TypeActivity extends AppCompatActivity {

    private Animator anim;

    @Bind(R.id.text_view) TextView textView;
    @Bind(R.id.detail_settings) ViewGroup detailSettings;

    Animator.AnimatorListener toastListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            showToast("Start");
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            showToast("End");
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            showToast("Cancel");
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            showToast("Repeat");
        }
    };

    // custom settings
    private int speed = TurriType.NORMAL_SPEED;
    private TimeInterpolator interpolator = new LinearInterpolator();
    private List<TimeInterpolator> interpolatorList = null;
    private PauseStrategy pauseStrategy = new NoPauseStrategy();
    private boolean isNaturally = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        ButterKnife.bind(this);

        updateAnimator();
    }

    private void updateAnimator() {
        final String text = getString(R.string.lorem_ipsum);


        TurriType.WriteRequest wr = TurriType.write(text)
                .speed(speed)
                .withListener(toastListener);

        if (isNaturally) {
            wr = wr.naturally();
        } else {
            wr = wr.setPauseStrategy(pauseStrategy);
            if (interpolator != null) {
                wr = wr.withInterpolator(interpolator);
            } else {
                wr = wr.withWordInterpolatorList(interpolatorList);
            }
        }

        anim = wr.into(textView);
    }


    private List <TimeInterpolator> getDefaultInterpolatorList () {
        List <TimeInterpolator> interpolatorList = new ArrayList<>();
        interpolatorList.add(new LinearInterpolator());
        interpolatorList.add(new FastOutSlowInInterpolator());
        interpolatorList.add(new AccelerateDecelerateInterpolator());
        return interpolatorList;
    }

    @OnCheckedChanged(R.id.is_naturally)
    void naturallyChange(CompoundButton isNaturally) {
        this.isNaturally = isNaturally.isChecked();
        detailSettings.setVisibility(this.isNaturally ? View.GONE : View.VISIBLE);
        updateAnimator();
    }

    @OnCheckedChanged({R.id.speed_very_fast, R.id.speed_fast, R.id.speed_very_slow, R.id.speed_slow, R.id.speed_normal})
    void speedChange(CompoundButton checkBox) {
        if (!checkBox.isChecked()) return;

        switch (checkBox.getId()) {
            case R.id.speed_very_slow:
                speed = TurriType.VERY_SLOW_SPEED;
                break;
            case R.id.speed_slow:
                speed = TurriType.SLOW_SPEED;
                break;
            case R.id.speed_normal:
                speed = TurriType.NORMAL_SPEED;
                break;
            case R.id.speed_fast:
                speed = TurriType.FAST_SPEED;
                break;
            case R.id.speed_very_fast:
                speed = TurriType.VERY_FAST_SPEED;
                break;

            default:
                throw new IllegalStateException("Unsupported speed");
        }
        updateAnimator();
    }


    @OnCheckedChanged({R.id.interpolator_one, R.id.interpolator_multiple})
    void interpolatorChange(CompoundButton checkBox) {
        if (!checkBox.isChecked()) return;
        switch (checkBox.getId()) {
            case R.id.interpolator_one:
                interpolatorList = null;
                interpolator = new FastOutSlowInInterpolator();
                break;
            case R.id.interpolator_multiple:
                interpolator = null;
                interpolatorList = getDefaultInterpolatorList ();
                break;

            default: speed = TurriType.NORMAL_SPEED;
                throw new IllegalStateException("Unsupported interpolator");
        }
        updateAnimator();

    }

    @OnCheckedChanged({R.id.strategy_node, R.id.strategy_natural, R.id.strategy_linear})
    void pauseChange(CompoundButton checkBox) {
        if (!checkBox.isChecked()) return;
        switch (checkBox.getId()) {
            case R.id.strategy_node:
                pauseStrategy = new NoPauseStrategy();
                break;
            case R.id.strategy_linear:
                pauseStrategy = new LinearPauseStrategy(500, 1200);
                break;
            case R.id.strategy_natural:
                pauseStrategy = new NaturalPauseStrategy();
                break;
            default: speed = TurriType.NORMAL_SPEED;
                throw new IllegalStateException("Unsupported interpolator");
        }
        updateAnimator();
    }



    @OnClick(R.id.start_btn)
    public void onStartBtnClicked() {
        // temporary fix when starting animation again
        textView.setText("");
        anim.start();
    }

    @OnClick(R.id.pause_btn)
    public void onPauseBtnClicked() {
        anim.pause();
    }

    @OnClick(R.id.cancel_btn)
    public void onCancelBtnClicked() {
        anim.cancel();
    }

    @OnClick(R.id.end_btn)
    public void onEndBtnClicked() {
        anim.end();
    }

    @OnClick(R.id.resume_btn)
    public void onResumeBtnClicked() {
        anim.resume();
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
