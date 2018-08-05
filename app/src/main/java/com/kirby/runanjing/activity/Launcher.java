package com.kirby.runanjing.activity;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import com.hanks.htextview.base.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.untils.*;
import java.util.*;

/**
 *类类型:Activity
 *名称:Launcher
 *进入看到的第一个Activity
 *用于显示加载动画
 */
public class Launcher extends AppCompatActivity
{

	private boolean 状态_;
	private Handler mHandler = new Handler();

	private HTextView welcome;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		//隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		Theme.setClassTheme(this);
		super.onCreate(savedInstanceState);
		setLanguage();
		setContentView(R.layout.activity_welcome);
		welcome = (HTextView)findViewById(R.id.textview);
		welcome.setTextColor(getColorPrimary());
		welcome.animateText("Kirby Assistant");
		mHandler.postDelayed(new Runnable() {
				@Override
				public void run()
				{
					welcome.animateText(getResources().getString(R.string.welcome_to));
				}
			}
			, 1250);
		mHandler.postDelayed(new Runnable() {
				@Override
				public void run()
				{
					theFirst();
				}
            }
			, 2500);
	}
	/**
	 *方法名:theFirst
	 *不需要传入参数
	 *用于检测是否第一次进入app
	 */
	private void theFirst()
	{
		SharedPreferences 状态=getSharedPreferences("boolean", 0);
		状态_ = 状态.getBoolean("thefirst_状态", false);
		if (状态_ == false)
		{
			Intent intent=new Intent(Launcher.this, KirbyIntroActivity.class);
			intent.setClass(Launcher.this, KirbyIntroActivity.class);
			startActivity(intent);
			finish();
		}
		else
		{
			//跳转
			Intent intent=new Intent(Launcher.this, MainActivity.class);
			intent.setClass(Launcher.this, MainActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
		}
	}
	/**
	 *方法名:setLanguage
	 *不需要传入参数
	 *用于设置语言
	 */
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
	/**
	 *方法名:getColorPrumary
	 *不需要传入参数
	 *用于或许主题指定颜色
	 */
	public int getColorPrimary()
	{
		TypedValue typedValue = new  TypedValue();
		getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
		return typedValue.data;
	}
}
