package net.semantic_error.turritype;

import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TypeActivity extends AppCompatActivity {

    private Animator anim;

    @Bind(R.id.text_view) TextView myTextView;

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

        anim = TurriType.write(text)
                .naturally()
                .during(TurriType.FAST_SPEED)
                .withListener(listener)
                .into(myTextView);
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
