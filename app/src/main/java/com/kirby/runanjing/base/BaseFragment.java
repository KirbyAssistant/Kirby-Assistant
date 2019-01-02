package com.kirby.runanjing.base;

import com.umeng.analytics.*;
import android.widget.*;
import com.kirby.runanjing.utils.*;
import android.support.v4.app.*;

public class BaseFragment extends Fragment
{

	public void onResume() {
		super.onResume();
		//Toast.makeText(getActivity(),this.getClass().getName()+"恢复",Toast.LENGTH_SHORT).show();
		MobclickAgent.onPageStart(this.getClass().getName()); //统计页面("MainScreen"为页面名称，可自定义)
	}
	
	public void onPause() {
		super.onPause();
		//Toast.makeText(getActivity(),this.getClass().getName()+"暂停",Toast.LENGTH_SHORT).show();
		MobclickAgent.onPageEnd(this.getClass().getName()); 
	}
	
	public void onDestroy()
	{
		super.onDestroy();
		//Toast.makeText(getActivity(),this.getClass().getName()+"销毁",Toast.LENGTH_SHORT).show();
	}
}
