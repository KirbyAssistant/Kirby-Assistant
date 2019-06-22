package cn.endureblaze.ka.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseActivity;
import cn.endureblaze.ka.utils.ThemeUtil;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;

@ParallaxBack
public class SettingActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        ThemeUtil.setClassTheme(this);
		setContentView(R.layout.activity_setting);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(R.string.setting_title);
		getFragmentManager().beginTransaction().replace(R.id.about_fragment, new SettingPreferenceFragment()).commit();
	}
}
		
