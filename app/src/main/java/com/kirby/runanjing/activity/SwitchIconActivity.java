package com.kirby.runanjing.activity;
import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.untils.*;

import android.support.v7.widget.Toolbar;
import com.kirby.runanjing.R;

@ParallaxBack
public class SwitchIconActivity extends BaseActivity
{

	private Toolbar toolbar;

	private Handler mHandler = new Handler();

	private ComponentName HkComponent;

	private ComponentName MaComponent;

	private PackageManager packageManager;

	private ComponentName componentName;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_sw_icon);
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		Button sw_hk=(Button)findViewById(R.id.sw_hk);
		Button sw_ma=(Button)findViewById(R.id.sw_ma);
		sw_hk.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					changeIconToHk();
				}
			});
		sw_ma.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					changeIconToMa();
				}
			});
		//拿到当前activity注册的组件名称
        componentName = getComponentName();
        //拿到我们注册的MainActivity组件
        HkComponent = new ComponentName(getBaseContext(), "com.kirby.runanjing.activity.Launcher");  //拿到默认的组件
        //拿到我注册的别名test组件
        MaComponent = new ComponentName(getBaseContext(), "com.kirby.runanjing.activity.OtherLauncher");
        packageManager = getApplicationContext().getPackageManager();
    }

    public void changeIconToMa()
	{
        disableComponent(HkComponent);
        enableComponent(MaComponent);
		Toast.makeText(this, getResources().getString(R.string.sw_icon_finish), Toast.LENGTH_SHORT).show();
		SharedPreferences.Editor y=getSharedPreferences("icon", 0).edit();
		y.putString("icon_ver", "ma");
		y.apply();
		closeApp();
    }

    public void changeIconToHk()
	{
        enableComponent(HkComponent);
        disableComponent(MaComponent);
		Toast.makeText(this, getResources().getString(R.string.sw_icon_finish), Toast.LENGTH_SHORT).show();
		SharedPreferences.Editor y=getSharedPreferences("icon", 0).edit();
		y.putString("icon_ver", "hk");
		y.apply();
		closeApp();
    }

    /**
     * 启用组件
     *
     * @param componentName
     */
    private void enableComponent(ComponentName componentName)
	{
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
		{
            //已经启用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
												  PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
												  PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     *
     * @param componentName
     */
    private void disableComponent(ComponentName componentName)
	{
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED)
		{
            //已经禁用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
												  PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
												  PackageManager.DONT_KILL_APP);
    }

	private void closeApp()
	{
		mHandler.postDelayed(new Runnable() {
				@Override
				public void run()
				{
					ActManager.AppExit(SwitchIconActivity.this);
				}
			}
			, 2000);
	}

}
