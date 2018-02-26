package com.kirby.runanjing.activity;

import android.os.*;
import android.support.v7.widget.*;
import com.jaeger.library.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.fragment.preference.*;
import com.kirby.runanjing.untils.*;

import com.kirby.runanjing.R;
import com.github.anzewei.parallaxbacklayout.*;

@ParallaxBack
public class AboutActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_about);
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		getFragmentManager().beginTransaction().replace(R.id.about_fragment, new AboutPreferenceFragment()).commit();
	}
}
		
