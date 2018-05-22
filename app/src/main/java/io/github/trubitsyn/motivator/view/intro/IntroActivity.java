package io.github.trubitsyn.motivator.view.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

public class IntroActivity extends AppIntro {
    private IntroFragment introFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introFragment = IntroFragment.newInstance();
        addSlide(introFragment);

        showSkipButton(false);
        showStatusBar(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        introFragment.applySettings(this);
        setResult(RESULT_OK);
        finish();
        super.onDonePressed(currentFragment);
    }
}
