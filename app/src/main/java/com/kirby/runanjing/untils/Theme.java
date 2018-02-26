package com.kirby.runanjing.untils;


import android.content.*;
import com.kirby.runanjing.*;

public class Theme
{
	private static SharedPreferences.Editor edit;
	private static String FILE_NAME="theme";
	public static void setClassTheme(Context context)
	{
		SharedPreferences theme = context.getSharedPreferences(FILE_NAME, 0);
		int themeId=theme.getInt("themeId", 0);
		Theme(context, themeId);
	}
	public static void setTheme(Context context, int i)
	{
		SharedPreferences theme = context.getSharedPreferences(FILE_NAME, 0);
		edit=theme.edit();
		edit.putInt("themeId", i);
		edit.apply();
	}
	private static void Theme(Context context, int themeId)
	{
		switch(themeId){
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
				context.setTheme(R.style.DarkTheme);
				break;
		}
	}
}
