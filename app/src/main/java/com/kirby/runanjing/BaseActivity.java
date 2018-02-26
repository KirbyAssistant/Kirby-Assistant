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

public class BaseActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
		Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
		getWindow().setEnterTransition(explode); //首次进入显示的动画
        getWindow().setExitTransition(slide); //启动一个新Activity,当前页的退出动画
		getWindow().setReturnTransition(slide); //调用 finishAfterTransition() 退出时，当前页退出的动画
        getWindow().setReenterTransition(explode); //重新进入的动画。即第二次进入，可以和首次进入不一样。
		super.onCreate(savedInstanceState);
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
}
