package cn.endureblaze.kirby.setting;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseActivity;
import cn.endureblaze.kirby.util.ThemeUtil;

import java.util.Objects;

public class SettingActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ThemeUtil.setClassTheme(this);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.setting_title);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_fragment, new SettingPreferenceFragment())
                .commit();
    }
}