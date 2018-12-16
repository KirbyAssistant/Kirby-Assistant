package com.kirby.runanjing.base;
import android.support.v4.app.*;
import com.umeng.analytics.*;

public class BaseFragment extends Fragment
{

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(this.toString()); //统计页面("MainScreen"为页面名称，可自定义)
	}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(this.toString()); 
	}
	
}
