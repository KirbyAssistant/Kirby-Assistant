package com.kirby.runanjing.activity;

import android.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.github.paolorotolo.appintro.*;
import com.github.paolorotolo.appintro.model.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bmob.*;
import com.kirby.runanjing.untils.*;

import com.kirby.runanjing.R;
import android.graphics.Color;

public class KirbyIntroActivity extends AppIntro2
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

		//隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        super.onCreate(savedInstanceState);
		setFadeAnimation();
		
		SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle(getResources().getString(R.string.intro_welcome_title));
        sliderPage1.setDescription(getResources().getString(R.string.intro_welcome_text));
        sliderPage1.setImageDrawable(R.mipmap.ic_launcher);
        sliderPage1.setBgColor(0xff1976d2);
        addSlide(AppIntroFragment.newInstance(sliderPage1));
		
		SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle(getResources().getString(R.string.intro_permission_title));
        sliderPage2.setDescription(getResources().getString(R.string.intro_permission_text)+"\n"+getResources().getString(R.string.intro_permission_sd));
        sliderPage2.setBgColor(0xff1976d2);
        addSlide(AppIntroFragment.newInstance(sliderPage2));
		
		askForPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
		
		SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle(getResources().getString(R.string.intro_finish_title));
        sliderPage3.setDescription(getResources().getString(R.string.intro_finish_text));
        sliderPage3.setBgColor(0xff1976d2);
        addSlide(AppIntroFragment.newInstance(sliderPage3));
		
        showSkipButton(false);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
		Intent in=new Intent(KirbyIntroActivity.this,MainActivity.class);
		IntentUtil.startActivityWithAnim(in,KirbyIntroActivity.this);
		SharedPreferences.Editor y=getSharedPreferences("boolean", 0).edit().putBoolean("thefirst_状态", true);
		y.apply();
		finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
