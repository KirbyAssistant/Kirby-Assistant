package cn.endureblaze.ka.setting;

import android.content.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.base.*;
import cn.endureblaze.ka.utils.*;

import android.support.v7.widget.Toolbar;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.main.*;
import android.support.v4.content.*;
import com.github.anzewei.parallaxbacklayout.*;

@ParallaxBack
public class SettingActivity extends BaseActivity
{
	private LocalBroadcastManager localBroadcastManager;
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
		
