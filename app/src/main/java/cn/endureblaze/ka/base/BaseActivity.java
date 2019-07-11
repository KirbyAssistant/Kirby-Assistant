package cn.endureblaze.ka.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.Bmob;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.manager.ActManager;
import cn.endureblaze.ka.utils.LanguageUtil;
import cn.endureblaze.ka.utils.ThemeUtil;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.oasisfeng.condom.CondomContext;
import com.umeng.analytics.MobclickAgent;

@SuppressLint("Registered")
@ParallaxBack
public class BaseActivity extends AppCompatActivity
{
	private final String WINDOW_HIERARCHY_TAG="window_save";
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
        setStatusBar(ThemeUtil.getDarkColorPrimary(this));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if(layoutResID== R.layout.activity_game) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void setStatusBar(int color)
	{
		getWindow().setStatusBarColor(color);
    }
	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState)
	{
		super.onSaveInstanceState(outState);	
		outState.putBundle(WINDOW_HIERARCHY_TAG, getWindow().saveHierarchyState());
	}
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)

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
