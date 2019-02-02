package com.kirby.runanjing.launcher;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.support.v7.app.*;
import android.util.*;
import android.view.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.utils.*;
import java.util.*;
import android.widget.*;
import android.graphics.*;
import android.view.animation.*;
import com.kirby.runanjing.customui.*;
import com.kirby.runanjing.base.*;
import android.support.v4.view.*;
import com.kirby.runanjing.main.*;
import android.animation.*;

/**
 *类类型:Activity
 *名称:Launcher
 *进入看到的第一个Activity
 *用于显示加载动画
 */
public class Launcher extends AppCompatActivity
{

	private Handler mHandler = new Handler();
	private TextView welcome;
	private ImageView icon;

	public static final int STARTUP_DELAY = 300;
    public static final int ANIM_ITEM_DURATION = 1000;
    public static final int ITEM_DELAY = 300;

    private boolean animationStarted = false;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		Window window = this.getWindow();
		//添加Flag把状态栏设为可绘制模式
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		//设置状态栏为透明
		window.setStatusBarColor(Color.TRANSPARENT);
		//设置window的状态栏不可见
		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE  
													|  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		icon = (ImageView)findViewById(R.id.welcomeImageView1);
		animate();
	}
	/*@Override
	 public void onWindowFocusChanged(boolean hasFocus)
	 {
	 if (!hasFocus || animationStarted)
	 {
	 return;
	 }
	 super.onWindowFocusChanged(hasFocus);
	 }*/

    private void animate()
	{
		welcome = (TextView)findViewById(R.id.textview);
		welcome.setText(R.string.app_name);
		ObjectAnimator animator = ObjectAnimator.ofFloat(welcome, "alpha", 0f, 1f);
        animator.setDuration(700);
        animator.start();
		mHandler.postDelayed(new Runnable() {
				@Override
				public void run()
				{
					Intent intent=new Intent(Launcher.this, MainActivity.class);
					intent.setClass(Launcher.this, MainActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);			
					//welcome.setText(getResources().getString(R.string.welcome_to));
				}
			}
			, 1000);
	}
}

