package org.technowolves.ontheprowl.view.activity;

import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import org.technowolves.ontheprowl.R;
import org.technowolves.ontheprowl.view.fragment.TextFragment;

public class IntroActivity extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(TextFragment.newInstance("Enter event name from settings."));
        addSlide(TextFragment.newInstance("Click on Cloud button \nto download teams for event."));
        addSlide(TextFragment.newInstance("Click on team in Teams tab \nand enter info in other tabs."));
        addSlide(TextFragment.newInstance("Once finished entering info, \nclick on check button to save."));
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
