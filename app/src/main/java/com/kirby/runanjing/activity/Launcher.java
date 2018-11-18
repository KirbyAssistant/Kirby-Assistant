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
import android.widget.*;
import android.graphics.*;

/**
 *类类型:Activity
 *名称:Launcher
 *进入看到的第一个Activity
 *用于显示加载动画
 */
public class Launcher extends AppCompatActivity
{

	private Handler mHandler = new Handler();

	private HTextView welcome;

	private ImageView icon;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		//隐藏状态栏
       /* getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);*/
		Window window = this.getWindow();
		//添加Flag把状态栏设为可绘制模式
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			//取消设置Window半透明的Flag
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			//设置状态栏为透明
			window.setStatusBarColor(Color.TRANSPARENT);
			//设置window的状态栏不可见
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		//Theme.setClassTheme(this);
		super.onCreate(savedInstanceState);
		setLanguage();
		setContentView(R.layout.activity_welcome);
		icon=(ImageView)findViewById(R.id.welcomeImageView1);
		SharedPreferences preferences = getSharedPreferences("icon", 0);
        String icon_ver = preferences.getString("icon_ver", "hk");
		if(icon_ver.equals("hk")){
			icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_foreground_image_hk));
		}else{
			icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_foreground_image_ma));
		}
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
					//跳转
					Intent intent=new Intent(Launcher.this, MainActivity.class);
					intent.setClass(Launcher.this, MainActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
				}
            }
			, 2500);
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
