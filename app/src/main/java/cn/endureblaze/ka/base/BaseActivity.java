package cn.endureblaze.ka.base;

import android.os.*;
import android.support.v7.app.*;
import android.transition.*;
import android.util.*;
import android.view.*;
import cn.bmob.v3.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.utils.*;
import com.jaeger.library.*;
import com.oasisfeng.condom.*;
import com.umeng.analytics.*;

import cn.endureblaze.ka.R;

public class BaseActivity extends AppCompatActivity
{

	private String WINDOW_HIERARCHY_TAG="window_save";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	    ActManager.addActivity(this);
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
