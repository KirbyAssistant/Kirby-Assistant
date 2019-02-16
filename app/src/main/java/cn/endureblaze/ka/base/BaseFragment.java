package cn.endureblaze.ka.base;

import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import com.umeng.analytics.*;

public class BaseFragment extends Fragment
{
	public void onResume()
	{
		super.onResume();
	    MobclickAgent.onPageStart(this.getClass().getName()); //统计页面("MainScreen"为页面名称，可自定义)
	}

	public void onPause()
	{
		super.onPause();
		MobclickAgent.onPageEnd(this.getClass().getName()); 
	}

	public void onDestroy()
	{
		super.onDestroy();
	}
}
