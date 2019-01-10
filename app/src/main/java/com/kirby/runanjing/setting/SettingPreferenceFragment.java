package com.kirby.runanjing.setting;

import android.content.*;
import android.net.*;
import android.os.*;
import android.preference.*;
import android.support.v7.app.*;
import android.widget.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.launcher.*;
import com.kirby.runanjing.main.help.*;

public class SettingPreferenceFragment extends PreferenceFragment
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);	
	}
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference)
	{
		switch (preference.getKey())
		{
			case "star":
				try
				{
					Intent star=new Intent("android.intent.action.VIEW");
					star .setData(Uri.parse("market://details?id=com.kirby.runanjing"));
					star .setPackage("com.coolapk.market");
					getActivity().startActivity(star);
				}
				catch (Exception e)
				{
					Toast.makeText(getActivity(), getActivity().getString(R.string.not_install_CoolApk), Toast.LENGTH_SHORT).show();
				}		
				break;
			case "setLanguage":
				SharedPreferences c=getActivity().getSharedPreferences("setting", 0);
				int itemSelected=c.getInt("language_i", 0);
				String [] lan={"Auto","简体中文","繁體中文（台灣）","ENGLISH"};
				AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle(R.string.language)
					.setSingleChoiceItems(lan, itemSelected, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i)
						{
							SharedPreferences lan=getContext().getSharedPreferences("setting",0);
							SharedPreferences.Editor edit=lan.edit();
							edit.putInt("language_i",i);
							edit.apply();
							switch (i)
							{
								case 0:
									edit.putString("language","auto" );
									edit.apply();
									break;
								case 1:
									edit.putString("language","zh_cn" );
									edit.apply();
									break;
								case 2:
									edit.putString("language","zh_tw" );
									edit.apply();
									break;
								case 3:
									edit.putString("language","en" );
									edit.apply();
									break;
							}
							edit.commit();
							dialog.dismiss();
							Intent intent = new Intent(getActivity(), Launcher.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
							getActivity().startActivity(intent);
							android.os.Process.killProcess(android.os.Process.myPid()); 
						}
					}).create();
				dialog.show();
				break;
			case "help1":
				Intent help=new Intent(getActivity(), HelpActivity.class);
				getActivity().startActivity(help);
				break;
			case "help2":
				String key="6j76WE8N9l378jnsWzmmUDv5HohOteHu";
				joinQQGroup(key);
				break;
			case "WeChat":
				Intent WeChat=new Intent("android.intent.action.VIEW");
				WeChat .setData(Uri.parse("https://github.com/nihaocun/pay/blob/master/WX.png"));
				getActivity().startActivity(WeChat);
				break;
			case "Alibaba":
				/*Intent Alibaba=new Intent("android.intent.action.VIEW");
				Alibaba .setData(Uri.parse("https://github.com/nihaocun/pay/blob/master/ZFB.jpg"));
				getActivity().startActivity(Alibaba);*/
				break;
			case "github":
				Intent github=new Intent("android.intent.action.VIEW");
				github.setData(Uri.parse("https://github.com/nihaocun/Kirby-Assistant"));
				getActivity().startActivity(github);
				break;
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	public boolean joinQQGroup(String key)
	{
		Intent intent = new Intent();
		intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
		// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try
		{
			getActivity().startActivity(intent);
			return true;
		}
		catch (Exception e)
		{
			// 未安装手Q或安装的版本不支持
			return false;
		}
	}	
}
