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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import net.semantic_error.turritype.TurriType;
import net.semantic_error.turritype.pausestrategy.LinearPauseStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TypeActivity extends AppCompatActivity {

    private Animator anim;

    @Bind(R.id.text_view) TextView myTextView;
    @Bind(R.id.is_naturally) Switch isNaturally;

    Animator.AnimatorListener listener = new Animator.AnimatorListener() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        ButterKnife.bind(this);


        final String text = getString(R.string.lorem_ipsum);

        TurriType.WriteRequest wr = TurriType.write(text)
                .speed(TurriType.FAST_SPEED)
                .withListener(listener);

        if (isNaturally.isChecked()) {
            wr = wr.naturally();
        }

        anim = wr.into(myTextView);

    }

    @OnClick(R.id.start_btn)
    public void onStartBtnClicked() {
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
