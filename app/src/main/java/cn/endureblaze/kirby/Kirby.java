package cn.endureblaze.kirby;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import androidx.fragment.app.FragmentActivity;
import cn.endureblaze.kirby.crash.CaptureCrash;
import cn.endureblaze.kirby.crash.CrashDialog;
import cn.endureblaze.kirby.manager.ActManager;
import com.bumptech.glide.request.RequestOptions;

public class Kirby extends Application
{
    public void onCreate()
    {
        super.onCreate();
        //registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
        CaptureCrash.init((t, e) -> toCrashActivity(e));
    }

    @SuppressLint("CheckResult")
    public static RequestOptions getGlideRequestOptions()
    {
        RequestOptions requ=new RequestOptions();
        requ.placeholder(R.drawable.ic_kirby_download)
                .error(R.drawable.ic_kirby_load_fail);
        return requ;
    }
    private void toCrashActivity(final Throwable crash)
    {
        new Handler(Looper.getMainLooper()).post(() -> {
            FragmentActivity getCrashActivity= ActManager.getCurrentFragmentActivity();
            CrashDialog.newInstance("1", crash)
                    .setTheme(R.style.OMGDialogStyle)
                    .setMargin(0)
                    .setGravity(Gravity.BOTTOM)
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