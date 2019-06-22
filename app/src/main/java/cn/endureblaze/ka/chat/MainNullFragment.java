package cn.endureblaze.ka.chat;

import android.os.*;
import androidx.core.app.*;
import android.view.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.base.*;

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
