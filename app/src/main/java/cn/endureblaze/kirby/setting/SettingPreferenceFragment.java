package cn.endureblaze.kirby.setting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.launcher.LauncherActivity;
import cn.endureblaze.kirby.util.AppUtil;
import cn.endureblaze.kirby.util.FileUtil;
import cn.endureblaze.kirby.util.LanguageUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class SettingPreferenceFragment extends PreferenceFragmentCompat {

    @SuppressLint("DefaultLocale")
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting);
        findPreference("version").setSummary(String.format("%s (Build %d) (%s) (%s)", AppUtil.getVersionName(getActivity()), AppUtil.getVersionCode(getActivity()), AppUtil.getPackageName(getActivity()),AppUtil.getChannel(Objects.requireNonNull(getActivity()))));
        findPreference("apkCacheClean").setSummary(String.format("%s %s",getActivity().getResources().getString(R.string.setting_apk_cache_clean_summary)+":" ,FileUtil.getFileOrFilesSize(Objects.requireNonNull(getContext()).getExternalCacheDir()+"/bmob/",3))+"M");
        findPreference("imageCacheClean").setSummary(String.format("%s %s",getActivity().getResources().getString(R.string.setting_image_cache_clean_summary)+":" ,FileUtil.getFileOrFilesSize(Objects.requireNonNull(getContext()).getCacheDir()+"/image_manager_disk_cache/",3))+"M");
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
                    // TODO: 2019/8/21
                }
                break;
            case "setLanguage":
                SharedPreferences c= Objects.requireNonNull(getActivity()).getSharedPreferences("setting", 0);
                int itemSelected=c.getInt("language_i", 0);
                String [] lan={"Auto","简体中文","繁體中文（台灣）","ENGLISH"};
                AlertDialog dialog = new MaterialAlertDialogBuilder(getActivity()).setTitle(R.string.setting_language_title)
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
                            Intent intent = new Intent(getActivity(), LauncherActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            getActivity().startActivity(intent);
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }).create();
                dialog.show();
                break;
            case "apkCacheClean":
                FileUtil.deleteDirectory(Objects.requireNonNull(getContext()).getExternalCacheDir()+"/bmob/",false);
                findPreference("apkCacheClean").setSummary(String.format("%s %s", Objects.requireNonNull(getActivity()).getResources().getString(R.string.setting_apk_cache_clean_summary)+":" ,FileUtil.getFileOrFilesSize(Objects.requireNonNull(getContext()).getExternalCacheDir()+"/bmob/",3))+"M");
                break;
            case "imageCacheClean":
                FileUtil.deleteDirectory(Objects.requireNonNull(getContext()).getCacheDir()+"/image_manager_disk_cache/",false);
                findPreference("imageCacheClean").setSummary(String.format("%s %s", Objects.requireNonNull(getActivity()).getResources().getString(R.string.setting_apk_cache_clean_summary)+":" ,FileUtil.getFileOrFilesSize(Objects.requireNonNull(getContext()).getCacheDir()+"/image_manager_disk_cache/",3))+"M");
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
                        faq.setData(Uri.parse("https://blog.endureblaze.cn/posts/ka_faq_cn/"));
                        break;
                    case "zh-TW":
                        faq.setData(Uri.parse("https://blog.endureblaze.cn/posts/ka_faq_tw/"));
                        break;
                    default:
                        faq.setData(Uri.parse("https://blog.endureblaze.cn/posts/ka_faq_en/"));
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
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_dev_coolapk_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "dev_github":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_dev_github_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "dev_weibo":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_dev_weibo_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "dev_twitter":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_dev_twitter_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "domain_name":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_domain_name_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "program":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_program_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "translation_tw":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_translation_tw_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "translation_en_1":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_translation_en_1_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "translation_en_2":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_translation_en_2_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "draw_icon_1":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_draw_icon_1_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "draw_icon_2":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_draw_icon_2_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "draw_icon_3":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_draw_icon_3_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "video_author":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_video_author_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case "writer_help_faq":
                Objects.requireNonNull(getActivity()).startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(new String(Base64.decode(getString(R.string.setting_contributor_writer_help_faq_link), Base64.DEFAULT)))).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
