package com.kirby.runanjing.fragment.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kirby.runanjing.R;

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
