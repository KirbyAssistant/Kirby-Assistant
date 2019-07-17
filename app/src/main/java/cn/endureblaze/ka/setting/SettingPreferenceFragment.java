package cn.endureblaze.ka.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.launcher.Launcher;
import cn.endureblaze.ka.utils.AppUtil;
import cn.endureblaze.ka.utils.CheckSimpleModeUtil;
import cn.endureblaze.ka.utils.LanguageUtil;

import java.util.Objects;

public class SettingPreferenceFragment extends PreferenceFragmentCompat
{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting);
        findPreference("version").setSummary(String.format("%s (Build %d) (%s) (%s)", AppUtil.getVersionName(getActivity()), AppUtil.getVersionCode(getActivity()), AppUtil.getPackageName(getActivity()),AppUtil.getChannel(getActivity())));
    }

    @Override
	public boolean onPreferenceTreeClick(Preference preference)
	{
		switch (preference.getKey())
		{
			case "star":
				try
				{
					Intent star=new Intent("android.intent.action.VIEW");
					star .setData(Uri.parse("market://details?id=cn.endureblaze.ka"));
					star.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					star .setPackage("com.coolapk.market");
					Objects.requireNonNull(getActivity()).startActivity(star);
				}
				catch (Exception e)
				{
					Toast.makeText(getActivity(), getActivity().getString(R.string.not_install_CoolApk), Toast.LENGTH_SHORT).show();
				}		
				break;
			case "setLanguage":
				SharedPreferences c= Objects.requireNonNull(getActivity()).getSharedPreferences("setting", 0);
				int itemSelected=c.getInt("language_i", 0);
				String [] lan={"Auto","简体中文","繁體中文（台灣）","ENGLISH"};
				AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle(R.string.language)
					.setSingleChoiceItems(lan, itemSelected, (dialog1, i) -> {
						SharedPreferences lan1 =getActivity().getSharedPreferences("setting", 0);
						SharedPreferences.Editor edit= lan1.edit();
						edit.putInt("language_i", i);
						edit.apply();
						switch (i)
						{
							case 0:
								edit.putString("language", "auto");
								edit.apply();
								break;
							case 1:
								edit.putString("language", "zh_cn");
								edit.apply();
								break;
							case 2:
								edit.putString("language", "zh_tw");
								edit.apply();
								break;
							case 3:
								edit.putString("language", "en");
								edit.apply();
								break;
						}
						edit.apply();
						dialog1.dismiss();
						Intent intent = new Intent(getActivity(), Launcher.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
						getActivity().startActivity(intent);
						android.os.Process.killProcess(android.os.Process.myPid());
					}).create();
				dialog.show();
				break;
			case "simple":
				if (CheckSimpleModeUtil.isSimpleMode())
				{
					AlertDialog.Builder simple_mode_disable_dialog=new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
						.setTitle(R.string.simple_mode_is_enable)
						.setMessage(R.string.simple_mode_to_disable)
						.setPositiveButton(getResources().getString(R.string.dia_disable), (dialog12, which) -> {
							SharedPreferences geek_shared=getActivity().getSharedPreferences("setting", 0);
							SharedPreferences.Editor geek_shared_edit=geek_shared.edit();
							geek_shared_edit.putBoolean("simple_mode", false);
							geek_shared_edit.apply();
							geek_shared_edit.apply();
							Intent intent = new Intent(getActivity(), Launcher.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
							getActivity().startActivity(intent);
							android.os.Process.killProcess(android.os.Process.myPid());
						}
						)
						.setNegativeButton(getResources().getString(R.string.dia_cancel), null);
					simple_mode_disable_dialog.show();
				}
				else
				{
					AlertDialog.Builder simple_mode_enable_dialog=new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
						.setTitle(R.string.simple_mode_is_disable)
						.setMessage(R.string.simple_mode_to_enable)
						.setPositiveButton(getResources().getString(R.string.dia_enable), (dialog13, which) -> {
							SharedPreferences geek_shared=getActivity().getSharedPreferences("setting", 0);
							SharedPreferences.Editor geek_shared_edit=geek_shared.edit();
							geek_shared_edit.putBoolean("simple_mode", true);
							geek_shared_edit.apply();
							geek_shared_edit.commit();
							Intent intent = new Intent(getActivity(), Launcher.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
							getActivity().startActivity(intent);
							android.os.Process.killProcess(android.os.Process.myPid());
						}
						)
						.setNegativeButton(getResources().getString(R.string.dia_cancel), null);
					simple_mode_enable_dialog.show();
				}
				break;
			case "greenapps":
				Intent greenapps=new Intent("android.intent.action.VIEW");
				greenapps.setData(Uri.parse("https://green-android.org/"));
				Objects.requireNonNull(getActivity()).startActivity(greenapps);
				break;
			case "faq":
				Intent faq=new Intent("android.intent.action.VIEW");
				switch (LanguageUtil.getLanguage())
				{
					case "zh-CN":
						faq.setData(Uri.parse("https://github.com/EndureBlaze/Kirby-Assistant-FAQ/blob/master/FAQ_zh_CN.md"));
						break;
					case "zh-TW":
						faq.setData(Uri.parse("https://github.com/EndureBlaze/Kirby-Assistant-FAQ/blob/master/FAQ_zh_TW.md"));
						break;
                    default:
						faq.setData(Uri.parse("https://github.com/EndureBlaze/Kirby-Assistant-FAQ/blob/master/FAQ_en.md"));
						break;
				}
				Objects.requireNonNull(getActivity()).startActivity(faq);
				break;
			case "qq_group":
				String key="6j76WE8N9l378jnsWzmmUDv5HohOteHu";
				joinQQGroup(key);
				break;
			case "tg_channel":
				Intent tg_channel=new Intent("android.intent.action.VIEW");
				tg_channel.setData(Uri.parse("https://t.me/kirbyassistant"));
				Objects.requireNonNull(getActivity()).startActivity(tg_channel);
				break;
			case "github":
				Intent github=new Intent("android.intent.action.VIEW");
				github.setData(Uri.parse("https://github.com/EndureBlaze/Kirby-Assistant"));
				Objects.requireNonNull(getActivity()).startActivity(github);
				break;
			case "dev_coolapk":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.dev_coolapk_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "dev_github":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.dev_github_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "dev_weibo":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.dev_weibo_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "dev_twitter":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.dev_twitter_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "program_github_lxfly2000":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.program_github_lxfly2000_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "translation_tw_github_longxk2017":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.translation_tw_github_longxk2017_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "translation_en_tieba_nannannan550":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.translation_en_tieba_nannannan550_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "translation_en_tieba_guangzhiyaoxi":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.translation_en_tieba_guangzhiyaoxi_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "draw_icon_coolapk_hkliuxing":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.draw_icon_coolapk_hkliuxing_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "draw_icon_coolapk_markuss":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.draw_icon_coolapk_markuss_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "draw_icon_tieba_xiaoyibu":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.draw_icon_tieba_xiaoyibu_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "video_author_bilibili_xige":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.video_author_bilibili_xige_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
			case "writer_help_faq_coolapk_talinhu":
				Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.writer_help_faq_coolapk_talinhu_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				break;
		}
		return super.onPreferenceTreeClick(preference);
	}

	private void joinQQGroup(String key)
	{
		Intent intent = new Intent();
		intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try
		{
			Objects.requireNonNull(getActivity()).startActivity(intent);
        }
		catch (Exception e)
		{
			// 未安装手Q或安装的版本不支持
        }
	}	
}
