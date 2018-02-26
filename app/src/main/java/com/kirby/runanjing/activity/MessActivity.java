package com.kirby.runanjing.activity;

import android.content.*;
import android.os.*;
import android.support.v7.widget.*;
import android.widget.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.untils.*;

import android.support.v7.widget.Toolbar;
import com.kirby.runanjing.R;

@ParallaxBack
public class MessActivity extends BaseActivity
{

	public static String USER_NAME="user_name";
	public static String MESS="mess";
	public static String TIME="time";

	private String s_user_name;

	private String s_time;

	private String s_mess;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_message);
		Intent intent = getIntent();
		s_user_name=intent.getStringExtra(USER_NAME);
		s_time=intent.getStringExtra(TIME);
		s_mess=intent.getStringExtra(MESS);
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		TextView user_name=(TextView)findViewById(R.id.userName);
		TextView mess=(TextView)findViewById(R.id.mess);
		TextView time=(TextView)findViewById(R.id.time);
		user_name.setText(s_user_name);
		mess.setText(s_mess);
		time.setText(s_time);
	}
}
