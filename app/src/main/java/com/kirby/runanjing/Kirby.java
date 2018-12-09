/*
*这个App的代号
*This App code
*KEMU
*/
package com.kirby.runanjing;

//import android.support.multidex.*;

import android.app.*;
import android.content.*;
import android.os.*;
import com.bumptech.glide.request.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.untils.*;

import com.kirby.runanjing.R;
import com.umeng.commonsdk.*;
import com.umeng.analytics.*;
public class Kirby extends Application
{
	private static Kirby instance;
	public void onCreate()
	{
        super.onCreate();
		Theme.setClassTheme(this);
		registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
	    CaptureCrash.init(new CaptureCrash.CrashHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e)
				{	
					MobclickAgent.reportError(getApplicationContext(), e);
					toCrashActivity(e);
				}
			});
		UMConfigure.init(this,"5c000429b465f56fdb0005ba", "CoolApk",UMConfigure.DEVICE_TYPE_PHONE, null);
    }
	/*public static RequestOptions getGlideRequestOptions()
	{
		RequestOptions requ=new RequestOptions();
		requ.placeholder(R.drawable.ic_kirby_download)
			.error(R.drawable.ic_kirby_load_fail);
		return requ;
	}*/
	/*@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}*/
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

//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖保佑             永无BUG 
//          佛曰:  
//                  写字楼里写字间，写字间里程序员；  
//                  程序人员写程序，又拿程序换酒钱。  
//                  酒醒只在网上坐，酒醉还来网下眠；  
//                  酒醉酒醒日复日，网上网下年复年。  
//                  但愿老死电脑间，不愿鞠躬老板前；  
//                  奔驰宝马贵者趣，公交自行程序员。  
//                  别人笑我忒疯癫，我笑自己命太贱；  
//                  不见满街漂亮妹，哪个归得程序员？ 
