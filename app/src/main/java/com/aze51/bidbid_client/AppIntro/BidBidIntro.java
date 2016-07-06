package com.aze51.bidbid_client.AppIntro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.aze51.bidbid_client.MainActivity;
import com.github.paolorotolo.appintro.AppIntro;

/**
 * Created by ChoiJunsung on 2016. 7. 6..
 */
public class BidBidIntro extends AppIntro{
    public BidBidIntro() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        FragmentManager fragmentManager = getSupportFragmentManager();

        IntroFragment fragment1 = new IntroFragment();
        IntroFragment fragment2 = new IntroFragment();
        IntroFragment fragment3 = new IntroFragment();

//        fragmentManager.beginTransaction().add(R.id.app_intro_1,fragment1).commit();

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.

        addSlide(fragment1);
        addSlide(fragment2);
        addSlide(fragment3);




//        addSlide(AppIntroFragment.newInstance("Intro_first", "1111", R.drawable.intro_1, Color.WHITE));
//        addSlide(AppIntroFragment.newInstance("Intro_second", "1111", R.drawable.intro_1, Color.WHITE));
//        addSlide(AppIntroFragment.newInstance("Intro_third", "1111", R.drawable.intro_1, Color.WHITE));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#eeeeee"));
        setSeparatorColor(Color.parseColor("#eeeeee"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);

        setColorDoneText(Color.parseColor("#744644"));
        setNextArrowColor(Color.parseColor("#744644"));
        setColorSkipButton(Color.parseColor("#744644"));
        setIndicatorColor(Color.parseColor("#744644"), Color.parseColor("#c19f9f"));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    protected int getLayoutId() {
        return super.getLayoutId();
    }

    @Override
    public void setBarColor(@ColorInt int color) {
        super.setBarColor(color);
    }

    @Override
    public void setNextArrowColor(@ColorInt int color) {
        super.setNextArrowColor(color);
    }

    @Override
    public void setSeparatorColor(@ColorInt int color) {
        super.setSeparatorColor(color);
    }

    @Override
    public void setSkipText(@Nullable CharSequence text) {
        super.setSkipText(text);
    }

    @Override
    public void setDoneText(@Nullable CharSequence text) {
        super.setDoneText(text);
    }

    @Override
    public void setColorDoneText(@ColorInt int colorDoneText) {
        super.setColorDoneText(colorDoneText);
    }

    @Override
    public void setColorSkipButton(@ColorInt int colorSkipButton) {
        super.setColorSkipButton(colorSkipButton);
    }

    @Override
    public void setImageNextButton(@DrawableRes Drawable imageNextButton) {
        super.setImageNextButton(imageNextButton);
    }

    @Override
    public void showSkipButton(boolean showButton) {
        super.showSkipButton(showButton);
    }

    @Override
    public void showDoneButton(boolean showDone) {
        super.showDoneButton(showDone);
    }
}
