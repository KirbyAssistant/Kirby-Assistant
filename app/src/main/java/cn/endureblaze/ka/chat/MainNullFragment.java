package cn.endureblaze.ka.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseFragment;

public class MainNullFragment extends BaseFragment
{
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        View view = inflater.inflate(R.layout.main_null, container, false);
		return view;
	}
}
