package com.kirby.runanjing.chat;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.base.*;

public class MainNullFragment extends BaseFragment
{
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view=inflater.inflate(R.layout.main_null, container, false);
		return view;
	}
}
