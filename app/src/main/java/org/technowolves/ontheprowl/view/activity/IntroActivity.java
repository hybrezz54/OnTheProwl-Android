package org.technowolves.ontheprowl.view.activity;

import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import org.technowolves.ontheprowl.R;

public class IntroActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(AppIntroFragment.newInstance("Lions", "Lion, lion everywhere!",
                R.drawable.ic_done_white_24px, R.color.colorAccent));
        addSlide(AppIntroFragment.newInstance("Lions", "Lion, lion everywhere!",
                R.drawable.ic_done_white_24px, R.color.colorAccent));
    }

    @Override
    public void onSkipPressed() {
        finish();
    }

    @Override
    public void onDonePressed() {
        finish();
    }

    @Override
    public void onSlideChanged() {

    }

    @Override
    public void onNextPressed() {

    }
}
