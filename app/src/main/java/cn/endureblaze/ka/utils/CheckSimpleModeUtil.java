package cn.endureblaze.ka.utils;
import android.content.SharedPreferences;
import cn.endureblaze.ka.manager.ActManager;

public class CheckSimpleModeUtil
{
	public static boolean isSimpleMode(){
		SharedPreferences simple =ActManager.currentActivity(). getSharedPreferences("setting", 0);
        boolean is_simple = simple.getBoolean("simple_mode", false);
		return true;
	}
}
