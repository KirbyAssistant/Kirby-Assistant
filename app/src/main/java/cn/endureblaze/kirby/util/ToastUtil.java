package cn.endureblaze.kirby.util;

import android.widget.Toast;
import androidx.annotation.StringRes;
import cn.endureblaze.kirby.manager.ActManager;

public class ToastUtil {
    //传入字符串，时长默认
    public static void show(CharSequence charSequence){
        int duration = Toast.LENGTH_SHORT;
        show(charSequence,duration);
    }
    //传入StringID，时长默认
    public static void show(@StringRes int stringID){
        int duration = Toast.LENGTH_SHORT;
        CharSequence charSequence = ActManager.getCurrentActivity().getString(stringID);
        show(charSequence,duration);
    }
    //传入StringID，时长自定义
    public static void show(@StringRes int stringID,int duration){
        CharSequence charSequence = ActManager.getCurrentActivity().getString(stringID);
        show(charSequence,duration);
    }
    //传入字符串，时长自定义
    public static void show(CharSequence charSequence, int duration){
        Toast.makeText(ActManager.getCurrentActivity(), charSequence,duration).show();
    }
}
