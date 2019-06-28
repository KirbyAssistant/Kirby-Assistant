/*
 *这个App的代号
 *This App code
 *KEMU
 */
//Hi 2019
package cn.endureblaze.ka;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.fragment.app.FragmentActivity;
import cn.endureblaze.ka.crash.CaptureCrash;
import cn.endureblaze.ka.crash.CrashDialog;
import cn.endureblaze.ka.manager.ActManager;

import com.bumptech.glide.request.RequestOptions;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.oasisfeng.condom.CondomContext;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class Kirby extends Application 
{
	public void onCreate()
	{
        super.onCreate();
		registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
		CaptureCrash.init((t, e) -> {
			MobclickAgent.reportError(getApplicationContext(), e);
			toCrashActivity(e);
		});
		UMConfigure.init(CondomContext.wrap(this, "Umeng"), "5c000429b465f56fdb0005ba", "CoolApk", UMConfigure.DEVICE_TYPE_PHONE, null);
		MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }
	@SuppressLint("CheckResult")
	public static RequestOptions getGlideRequestOptions()
	 {
	 RequestOptions requ=new RequestOptions();
	 requ.placeholder(R.drawable.ic_kirby_download)
	 .error(R.drawable.ic_kirby_load_fail);
	 return requ;
	 }
    public void toCrashActivity(final Throwable crash)
	{
        new Handler(Looper.getMainLooper()).post(() -> {
			FragmentActivity getCrashActivity=ActManager.currentFragmentActivity();
			CrashDialog.newInstance("1", crash)
				.setTheme(R.style.BottomDialogStyle)
				.setMargin(0)
				.setShowBottom(true)
				.show(getCrashActivity.getSupportFragmentManager());
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
