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
import com.kirby.runanjing.utils.*;
import java.util.*;
import android.widget.*;
import android.graphics.*;
import android.view.animation.*;
import com.kirby.runanjing.customui.*;
import com.kirby.runanjing.base.*;
import android.support.v4.view.*;

/**
 *类类型:Activity
 *名称:Launcher
 *进入看到的第一个Activity
 *用于显示加载动画
 */
public class Launcher extends BaseActivity
{

	private Handler mHandler = new Handler();
	private HTextView welcome;
	private ImageView icon;
	
	public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		icon = (ImageView)findViewById(R.id.welcomeImageView1);
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
	@Override
    public void onWindowFocusChanged(boolean hasFocus)
	{

        if (!hasFocus || animationStarted)
		{
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    private void animate()
	{
        ImageView logoImageView = (ImageView) findViewById(R.id.welcomeImageView1);

        ViewCompat.animate(logoImageView)
            .translationY(-250)
            .setStartDelay(STARTUP_DELAY)
            .setDuration(ANIM_ITEM_DURATION).setInterpolator(
			new DecelerateInterpolator(1.2f)).start();

		welcome = (HTextView)findViewById(R.id.textview);
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
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				}
            }
			, 2500);
	}
}

