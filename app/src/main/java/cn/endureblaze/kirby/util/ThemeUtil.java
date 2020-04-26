package cn.endureblaze.kirby.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.TypedValue;
import androidx.preference.PreferenceManager;
import cn.endureblaze.kirby.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ThemeUtil {
    public final static int BLUE_THEME = 0;
    public final static int RED_THEME = 1;
    public final static int PURPLE_THEME = 2;
    public final static int Indigo_THEME = 3;
    public final static int TEAL_THEME = 4;
    public final static int GREEN_THEME = 5;
    public final static int ORANGE_THEME = 6;
    public final static int BROWN_THEME = 7;
    public final static int BLUEGREY_THEME = 8;
    public final static int YELLOW_THEME = 9;
    public final static int WHITE_THEME = 10;
    public final static int DARK_THEME = 11;
    public final static int PINK_THEME = 12;

    public final static String FILE_NAME = "theme";

    public static void setClassTheme(Context context) {
        Theme(context, getThemeId(context));
    }

    public static int getThemeId(Context context) {
        SharedPreferences theme = context.getSharedPreferences(FILE_NAME, 0);
        return theme.getInt("themeId", 0);
    }

    public static void setThemeByName(Context context, int i) {
        SharedPreferences theme = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor edit = theme.edit();
        edit.putInt("themeId", i);
        edit.apply();
    }

    private static void Theme(Context context, int themeId) {
        switch (themeId) {
            case BLUE_THEME:
                context.setTheme(R.style.BlueAppTheme);
                break;
            case RED_THEME:
                context.setTheme(R.style.RedAppTheme);
                break;
            case PURPLE_THEME:
                context.setTheme(R.style.PurpleAppTheme);
                break;
            case Indigo_THEME:
                context.setTheme(R.style.IndigoAppTheme);
                break;
            case TEAL_THEME:
                context.setTheme(R.style.TealAppTheme);
                break;
            case GREEN_THEME:
                context.setTheme(R.style.GreenAppTheme);
                break;
            case ORANGE_THEME:
                context.setTheme(R.style.OrangeAppTheme);
                break;
            case BROWN_THEME:
                context.setTheme(R.style.BrownAppTheme);
                break;
            case BLUEGREY_THEME:
                context.setTheme(R.style.BlueGreyAppTheme);
                break;
            case YELLOW_THEME:
                context.setTheme(R.style.YellowAppTheme);
                break;
            case WHITE_THEME:
                context.setTheme(R.style.WhiteAppTheme);
                break;
            case DARK_THEME:
                context.setTheme(R.style.DarkAppTheme);
                break;
            case PINK_THEME:
                context.setTheme(R.style.PinkAppTheme);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + themeId);
        }
    }
    private static boolean isDarkMode(Context context){
        return getDarkModeStatus(context);
    }

    public static void sw2DarkTheme(Activity activity, final Class<?> toClass) {
        SharedPreferences sw2dark = activity.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor sw2dark_edit = sw2dark.edit();
        sw2dark_edit.putInt("before_app_theme", getThemeId(activity));
        sw2dark_edit.apply();
        sw2dark_edit.commit();
        ThemeUtil.setThemeByName(activity, DARK_THEME);
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.finish();
    }

    public static void sw2AppTheme(Activity activity, final Class<?> toClass){
        SharedPreferences sw2app = activity.getSharedPreferences(FILE_NAME, 0);
        ThemeUtil.setThemeByName(activity, sw2app.getInt("before_app_theme", 0));
        Intent intent = new Intent(activity, toClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.finish();
    }

    /**
     * 获取主题颜色
     *
     * @return
     */
    public static int getThemeColorById(Activity activity,int id) {
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(id, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取主题字体颜色
     *
     * @return
     */
    public static int getTextColor(Activity activity) {
        TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(R.attr.text_high, typedValue, true);
        return typedValue.data;
    }

    //检查当前系统是否已开启暗黑模式
    private static boolean getDarkModeStatus(Context context) {
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }
    //获取是否设置根据时间切换
    private static boolean isAutoDarkModeByTime(Context context){
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
        String hour = sdf.format(new java.util.Date());
        int k  = Integer.parseInt(hour);
        boolean by_time =  (k >= 0 && k < 6) || (k >= 22 && k < 24);

        SharedPreferences edit= PreferenceManager.getDefaultSharedPreferences(context);
        boolean by_setting = edit.getBoolean("autoDarkMode",true);

        return by_time && by_setting;
    }
}