package com.kirby.runanjing.setting;

import android.os.*;
import android.support.v7.widget.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.utils.*;

import com.kirby.runanjing.R;
import com.kirby.runanjing.base.*;

@ParallaxBack
public class SettingActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        ThemeUtil.setClassTheme(this);
		setContentView(R.layout.activity_setting);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(R.string.about_title);
		getFragmentManager().beginTransaction().replace(R.id.about_fragment, new SettingPreferenceFragment()).commit();
	}
}
		
