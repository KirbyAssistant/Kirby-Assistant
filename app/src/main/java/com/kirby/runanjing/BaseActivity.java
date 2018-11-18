package com.kirby.runanjing;

import android.os.*;
import android.support.v7.app.*;
import com.jaeger.library.*;
import com.kirby.runanjing.untils.*;
import android.util.*;
import cn.bmob.v3.*;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.content.Intent;
import android.app.ActivityOptions;
import android.view.*;
import com.bumptech.glide.request.*;
import android.content.*;
import android.content.res.*;
import java.util.*;

public class BaseActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//View view = this.getWindow().getDecorView();   //getDecorView 获得window最顶层的View
		//view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.ic_foreground_image_hk));
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
		Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
		Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.fade);
		getWindow().setEnterTransition(explode); //首次进入显示的动画
        getWindow().setExitTransition(slide); //启动一个新Activity,当前页的退出动画
		getWindow().setReturnTransition(slide); //调用 finishAfterTransition() 退出时，当前页退出的动画
        getWindow().setReenterTransition(explode); //重新进入的动画。即第二次进入，可以和首次进入不一样。
		super.onCreate(savedInstanceState);
		setLanguage();
		Bmob.initialize(this, "e39c2e15ca40b358b0dcc933236c1165");
	}
	
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
		StatusBarUtil.setColor(this, getDarkColorPrimary(),0);
		getWindow().setNavigationBarColor(getDarkColorPrimary());
    }
	public int getDarkColorPrimary(){
		TypedValue typedValue = new  TypedValue();
		getTheme().resolveAttribute(R.attr.colorPrimaryDark,typedValue,true);
		return typedValue.data;
	}
	private void setLanguage()
	{

        //读取SharedPreferences数据，默认选中第一项
        SharedPreferences preferences = getSharedPreferences("setting", 0);
        String language = preferences.getString("language", "auto");

        //根据读取到的数据，进行设置
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();

        switch (language)
		{
            case "auto":
                configuration.setLocale(Locale.getDefault());
                break;
            case "zh_cn":
                configuration.setLocale(Locale.CHINA);
                break;
			case "zh_tw":
                configuration.setLocale(Locale.TAIWAN);
                break;
			case "en":
                configuration.setLocale(Locale.ENGLISH);
                break;
            default:
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }
}
