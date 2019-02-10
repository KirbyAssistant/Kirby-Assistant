package com.kirby.runanjing.base;

import android.os.*;
import android.support.v7.app.*;
import android.transition.*;
import android.util.*;
import android.view.*;
import cn.bmob.v3.*;
import com.jaeger.library.*;
import com.jaeger.library.R.*;
import com.kirby.runanjing.utils.*;
import com.oasisfeng.condom.*;
import com.umeng.analytics.*;
import com.kirby.runanjing.R;
import android.content.*;

public class BaseActivity extends AppCompatActivity
{

	private String WINDOW_HIERARCHY_TAG="window_save";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    ActManager.addActivity(this);
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
		Transition slide_in = TransitionInflater.from(this).inflateTransition(R.transition.slide_in);
		Transition slide_out = TransitionInflater.from(this).inflateTransition(R.transition.slide_out);
		Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.fade);
	//极简模式检测
		if (CheckSimpleModeUtil.isSimpleMode() == false)
		{
			getWindow().setEnterTransition(slide_in); //首次进入显示的动画
			getWindow().setExitTransition(slide_out); //启动一个新Activity,当前页的退出动画
			getWindow().setReturnTransition(slide_out); //调用 finishAfterTransition() 退出时，当前页退出的动画
			getWindow().setReenterTransition(fade); //重新进入的动画。即第二次进入，可以和首次进入不一样。
		}
		super.onCreate(savedInstanceState);
		LanguageUtil.setLanguage();
		Bmob.initialize(CondomContext.wrap(this, "Bmob"), "e39c2e15ca40b358b0dcc933236c1165");
		MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
	}
    @Override
    public void setContentView(int layoutResID)
	{
        super.setContentView(layoutResID);
        setStatusBar(getDarkColorPrimary());
    }

    public void setStatusBar(int color)
	{
		StatusBarUtil.setColor(this, color, 0);
		getWindow().setNavigationBarColor(color);
    }
	public int getDarkColorPrimary()
	{
		TypedValue typedValue = new  TypedValue();
		getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
		return typedValue.data;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// TODO: Implement this method	
		super.onSaveInstanceState(outState);	
		outState.putBundle(WINDOW_HIERARCHY_TAG, getWindow().saveHierarchyState());
	}
	protected void onRestoreInstanceState(Bundle savedInstanceState)

	{
		if (getWindow() != null)	
		{
			Bundle windowState = savedInstanceState.getBundle(WINDOW_HIERARCHY_TAG);
			if (windowState != null)
			{
				getWindow().restoreHierarchyState(windowState);	
			}
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		MobclickAgent.onPageStart(this.getClass().getName());
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		MobclickAgent.onPageEnd(this.getClass().getName());
		MobclickAgent.onPause(this);
	}
}
