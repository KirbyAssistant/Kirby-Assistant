package cn.endureblaze.ka.base;

import androidx.core.app.*;
import androidx.fragment.app.Fragment;
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
