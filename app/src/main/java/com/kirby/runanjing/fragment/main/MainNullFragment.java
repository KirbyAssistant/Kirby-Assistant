package com.kirby.runanjing.fragment.main;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import com.kirby.runanjing.*;

public class MainNullFragment extends Fragment
{
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view=inflater.inflate(R.layout.main_null, container, false);
		return view;
	}
}
