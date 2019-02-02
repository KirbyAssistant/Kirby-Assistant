package com.kirby.runanjing.setting;

import android.content.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.base.*;
import com.kirby.runanjing.utils.*;

import android.support.v7.widget.Toolbar;
import com.kirby.runanjing.R;
import com.kirby.runanjing.main.*;
import android.support.v4.content.*;

@ParallaxBack
public class SettingActivity extends BaseActivity
{
	private LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        ThemeUtil.setClassTheme(this);
		setContentView(R.layout.activity_setting);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(R.string.setting_title);
		getFragmentManager().beginTransaction().replace(R.id.about_fragment, new SettingPreferenceFragment()).commit();
	}
}
		
