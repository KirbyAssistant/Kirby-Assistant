package com.kirby.runanjing.fragment.main;

import android.os.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.adapter.*;
import com.kirby.runanjing.untils.*;
import java.util.*;
import com.kirby.runanjing.bean.*;

public class MainJszFragment extends Fragment
{

	private JszGameAdapter adapter;
	private List<Console> jszgamelist=new ArrayList<>();
	private Console[]金手指_游戏={
		new Console("星之卡比 梦之泉物语", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/mengzhiquan.jpg"),
		new Console("星之卡比 梦之泉DX", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/mengzhiquandx.jpg"),
		new Console("星之卡比 镜之大迷宫", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/jingmi.jpg")
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.main_jsz, container, false);
		MainActivity m=(MainActivity)getActivity();
		initJsz(view);
		return view;
	}
	private void initJsz(View view)
	{
		RecyclerView r = (RecyclerView) view.findViewById(R.id.金手指_游戏列表);
		GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 1);
		r.setLayoutManager(layoutManager);
		adapter = new JszGameAdapter(jszgamelist);
		r.setAdapter(adapter);
		init();
	}
	private void init()
	{
		int index = 0;//定义数值
		//遍历
		while (index < 金手指_游戏.length)
		{       	
			jszgamelist.add(金手指_游戏[index++]);
		}
	}
}
