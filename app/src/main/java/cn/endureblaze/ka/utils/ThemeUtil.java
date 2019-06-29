package cn.endureblaze.ka.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.TypedValue;
import cn.endureblaze.ka.R;

public class ThemeUtil 
{
    private static String FILE_NAME="theme";
	public static void setClassTheme(Context context)
	{
		SharedPreferences theme = context.getSharedPreferences(FILE_NAME, 0);
		int themeId=theme.getInt("themeId", 0);
		if (CheckSimpleModeUtil.isSimpleMode())
		{
			Theme(context, 0);
		}
		else
		{
			Theme(context, themeId);
		}
	}
	public static void setTheme(Context context, int i)
	{
		SharedPreferences theme = context.getSharedPreferences(FILE_NAME, 0);
        SharedPreferences.Editor edit = theme.edit();
		edit.putInt("themeId", i);
		edit.apply();
	}
	private static void Theme(Context context, int themeId)
	{
		switch (themeId)
		{
			case 0:
				context.setTheme(R.style.BuleTheme);
				break;
			case 1:
				context.setTheme(R.style.RedTheme);
				break;
			case 2:
				context.setTheme(R.style.PurpleTheme);
				break;
			case 3:
				context.setTheme(R.style.LindigoTheme);
				break;
			case 4:
				context.setTheme(R.style.TealTheme);
				break;
			case 5:
				context.setTheme(R.style.GreenTheme);
				break;
			case 6:
				context.setTheme(R.style.OrangeTheme);
				break;
			case 7:
				context.setTheme(R.style.BrownTheme);
				break;
			case 8:
				context.setTheme(R.style.BlueGreyTheme);
				break;
			case 9:
				context.setTheme(R.style.YellowTheme);
				break;
			case 10:
				context.setTheme(R.style.KirbyTheme);
				break;
			case 11:
				context.setTheme(R.style.WhiteTheme);
				break;
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
		activity.getTheme().resolveAttribute(R.attr.color_text, typedValue, true);
		return typedValue.data;
	}
}
