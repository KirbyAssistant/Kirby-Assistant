package com.kirby.runanjing;
import android.app.*;
import com.github.anzewei.parallaxbacklayout.*;
import android.widget.*;
import android.os.*;
import com.king.thread.nevercrash.*;
import android.util.*;
import android.content.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.untils.*;
public class Kirby extends Application
{
	@Override
    public void onCreate()
	{
        super.onCreate();
        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
	    NeverCrash.init(new NeverCrash.CrashHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e)
				{		
					toCrashActivity(e);
				}
			});
    }
	
    public void toCrashActivity(final Throwable crash)
	{
        new Handler(Looper.getMainLooper()).post(new Runnable() {
				@Override
				public void run()
				{
					Intent crash_=new Intent(Kirby.this, KirbyCrashActivity.class);
					crash_.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					crash_.putExtra("crash",crash);
					startActivity(crash_);	
					android.os.Process.killProcess(android.os.Process.myPid());  
				}
			});
	}
}
