package cn.endureblaze.kirby.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import cn.endureblaze.kirby.R;

public class ThemeUtil
{
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

    public final static String FILE_NAME="theme";
    public static void setClassTheme(Context context)
    {
        SharedPreferences theme = context.getSharedPreferences(FILE_NAME, 0);
        int themeId=theme.getInt("themeId", 0);
        Theme(context, themeId);
    }
    public static void setThemeByName(Context context, int i)
    {
        SharedPreferences theme = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor edit = theme.edit();
        edit.putInt("themeId", i);
        edit.apply();
    }
    private static void Theme(Context context, int themeId)
    {
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
            default:
                throw new IllegalStateException("Unexpected value: " + themeId);
        }
    }
    /**
     * 获取主题颜色
     * @return
     */
    public static int getColorPrimary(Activity activity){
        TypedValue typedValue = new  TypedValue();
        activity.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取主题颜色
     * @return
     */
    public static int getDarkColorPrimary(Activity activity){
        TypedValue typedValue = new  TypedValue();
        activity.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取主题字体颜色
     * @return
     */
    public static int getTextColor(Activity activity){
        TypedValue typedValue = new  TypedValue();
        activity.getTheme().resolveAttribute(R.attr.text_high, typedValue, true);
        return typedValue.data;
    }
}