package com.kirby.runanjing.fragment.main;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.widget.*;
import android.view.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.adapter.*;
import java.util.*;
import com.kirby.runanjing.untils.*;
import com.kirby.runanjing.bean.*;

public class MainGameFragment extends Fragment
{
	private ViewPager mViewPager;
	private TabLayout mTabLayout;
	private LayoutInflater mInflater;
	private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view1, view2 ;//页卡视图
    private List<View> mViewList = new ArrayList<>();
	private List<Console> consolelist=new ArrayList<>();
	private List<Console> moniqilist=new ArrayList<>();
	private ConsoleAdapter adapter;
	private GameAdapter adapter2;
    private Console[]主机={
		new Console("gba", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/gba.png"),
		new Console("sfc", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/sfc.png"),
		new Console("n64", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/n64.png"),
		new Console("ngc", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/ngc.png"),
		new Console("wii", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/wii.png"),
		new Console("nds", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/nds.png"),
		new Console("gb/gbc", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/gbc.png"),
		new Console("fc", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/fc.png")};
	private Console[] 模拟器 = {
		new Console("GBA模拟器\nMy Boy!", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_gba.png"),
		new Console("SFC模拟器\nSnes9x EX+", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_sfc.png"),
		new Console("N64模拟器\nTendo64", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_n64.png"),
		new Console("NDS模拟器\nDraStic", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_nds.png"),
		new Console("NGC&WII模拟器\nDolphin", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_wii.png"),
		new Console("GB&GBC模拟器\nMy OldBoy!", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_gb_gbc.png"),
		new Console("FC模拟器\nNES.emu", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_fc.png"),
	}; 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.main_game, container, false);
		MainActivity m=(MainActivity)getActivity();
		initPaper(view);
		return view;
	}

	private void initPaper(View view)
	{
		//实例化viewpager需要的
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
		mInflater = LayoutInflater.from(getActivity());
        view1 = mInflater.inflate(R.layout.viewpager_1, null);
        view2 = mInflater.inflate(R.layout.viewpager_2, null);
    	//添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
		//添加页卡标题
        mTitleList.add(getActivity().getString(R.string.game));
        mTitleList.add(getActivity().getString(R.string.moniqi));
		mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
		MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
		//主机列表和模拟器列表需要的
		RecyclerView r = (RecyclerView) view1.findViewById(R.id.主机列表); 
		RecyclerView r1 = (RecyclerView) view2.findViewById(R.id.模拟器列表);
		//主机列表配置
		GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 1);
		r.setLayoutManager(layoutManager);
		adapter = new ConsoleAdapter(consolelist);
		r.setAdapter(adapter);
		//模拟器列表配置
		GridLayoutManager layoutManager2=new GridLayoutManager(getActivity(), 1);
		r1.setLayoutManager(layoutManager2);
		adapter2 = new GameAdapter(moniqilist);
		r1.setAdapter(adapter2);
		init();
		init2();
	}
	private void init()
	{
		int index = 0;//定义数值
		//遍历
		while (index < 主机.length)
		{       	
			consolelist.add(主机[index++]);
		}
	}
	private void init2()
	{
		int in = 0;//定义数值
		//遍历
		while (in < 模拟器.length)
		{       	
			moniqilist.add(模拟器[in++]);
		}
	}
	//viewpager适配器
  class MyPagerAdapter extends PagerAdapter
	{
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList)
		{
            this.mViewList = mViewList;
        }

        @Override
        public int getCount()
		{
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
		{
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
		{
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
		{
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position)
		{
            return mTitleList.get(position);//页卡标题
        }
    }
}
