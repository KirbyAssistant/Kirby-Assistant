package com.kirby.runanjing.utils;
import android.content.res.*;
import android.content.*;
import java.util.*;
import android.app.*;
import android.util.*;
import android.os.*;

public class LanguageUtil
{
	public static void setLanguage()
	{

        //读取SharedPreferences数据，默认选中第一项
        SharedPreferences preferences =ActManager.currentActivity(). getSharedPreferences("setting", 0);
        String language = preferences.getString("language", "auto");

        //根据读取到的数据，进行设置
        Resources resources = ActManager.currentActivity().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();

        switch (language)
		{
            case "auto":
                configuration.setLocale(Locale.getDefault());
                break;
            case "zh_cn":
                configuration.setLocale(Locale.CHINA);
                break;
			case "zh_tw":
                configuration.setLocale(Locale.TAIWAN);
                break;
			case "en":
                configuration.setLocale(Locale.ENGLISH);
                break;
            default:
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }
	public static String getLanguage()
	{
		Locale locale;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{
			locale = LocaleList.getDefault().get(0);
		}
		else
		{
			locale = Locale.getDefault();
		}
		String language = locale.getLanguage() + "-" + locale.getCountry();
		return language;
	}
}
