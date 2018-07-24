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
import com.kirby.runanjing.bean.*;
import java.util.*;

public class MainGameFragment extends Fragment
{
	private ViewPager mViewPager;
	private TabLayout mTabLayout;
	private LayoutInflater mInflater;
	private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view0, view1, view2,view3 ;//页卡视图
    private List<View> mViewList = new ArrayList<>();
	private List<Console> consolelist=new ArrayList<>();
	private List<Console> tjgamelist=new ArrayList<>();
	private List<Console> jszgamelist=new ArrayList<>();
	private List<Console> moniqilist=new ArrayList<>();
	private ConsoleAdapter adapter;
	private MoniqiAdapter adapter1;
	private GameAdapter adapter2;
	private JszGameAdapter adapter3;
	private TjGameAdapter adapter4;
  	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.main_game, container, false);
		MainActivity m=(MainActivity)getActivity();
		initPaper(view);
		return view;
	}
	private String getMoniqiText(int res_id)
	{	
		return getActivity().getResources().getString(res_id);
	}
	private void initPaper(View view)
	{	
		//实例化viewpager需要的
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
		mInflater = LayoutInflater.from(getActivity());
		//view0 = mInflater.inflate(R.layout.viewpaper_tj, null);
		view1 = mInflater.inflate(R.layout.viewpaper_game, null);
        view2 = mInflater.inflate(R.layout.viewpaper_moniqi, null);
    	view3 = mInflater.inflate(R.layout.viewpaper_jsz, null);	
		//添加页卡视图
		//mViewList.add(view0);
        mViewList.add(view1);
        mViewList.add(view2);
		mViewList.add(view3);
		//添加页卡标题
		//mTitleList.add(getActivity().getString(R.string.tj_title));
        mTitleList.add(getActivity().getString(R.string.game));
        mTitleList.add(getActivity().getString(R.string.moniqi));
		mTitleList.add(getActivity().getString(R.string.jsz_title));
		mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
		MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
		//主机列表和模拟器列表需要的
		RecyclerView r = (RecyclerView) view1.findViewById(R.id.主机列表); 
	//	RecyclerView tj_game=(RecyclerView)view0.findViewById(R.id.tj_game_list);
		RecyclerView r1 = (RecyclerView) view2.findViewById(R.id.模拟器列表);
		RecyclerView r2 = (RecyclerView) view3.findViewById(R.id.金手指_游戏列表);
		//主机列表配置
		GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 1);
		r.setLayoutManager(layoutManager);
		adapter = new ConsoleAdapter(consolelist,getActivity());
		r.setAdapter(adapter);
		//模拟器列表配置
		GridLayoutManager layoutManager2=new GridLayoutManager(getActivity(), 1);
		r1.setLayoutManager(layoutManager2);
		adapter1 = new MoniqiAdapter(moniqilist);
		r1.setAdapter(adapter1);
		//金手指游戏列表配置
		GridLayoutManager layoutManager3=new GridLayoutManager(getActivity(), 1);
		r2.setLayoutManager(layoutManager3);
		adapter3 = new JszGameAdapter(jszgamelist,getActivity());
		r2.setAdapter(adapter3);
		//游戏推荐列表配置
	/*	LinearLayoutManager tj_game_m=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
		tj_game.setLayoutManager(tj_game_m);
		adapter4 =new TjGameAdapter(tjgamelist);
		tj_game.setAdapter(adapter4);*/
		init();
		init2();
		init3();
		//init4();
	}
	private void init()
	{
		Console[]主机={
			new Console("gba", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/gba.png", "gba"),
			new Console("sfc", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/sfc.png", "sfc"),
			new Console("n64", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/n64.png", "n64"),
			new Console("ngc", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/ngc.png", "ngc"),
			new Console("wii", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/wii.png", "wii"),
			new Console("nds", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/nds.png", "nds"),
			new Console("gb", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/gb.png", "gb"),
			new Console("gbc", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/gbc.png", "gbc"),
			new Console("fc", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/consose/fc.png", "fc")};
		
		int index = 0;//定义数值
		//遍历
		while (index < 主机.length)
		{       	
			consolelist.add(主机[index++]);
		}
	}
	private void init2()
	{
		Console[] 模拟器 = {
			new Console("GBA " + getMoniqiText(R.string.moniqi) + "\nMy Boy!", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_gba.png", "moniqi_gba"),
			new Console("SFC " + getMoniqiText(R.string.moniqi) + "\nSnes9x EX+", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_sfc.png", "moniqi_sfc"),
			new Console("N64 " + getMoniqiText(R.string.moniqi) + "\nTendo64", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_n64.png", "moniqi_n64"),
			new Console("NDS " + getMoniqiText(R.string.moniqi) + "\nDraStic", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_nds.png", "moniqi_nds"),
			new Console("NGC&WII " + getMoniqiText(R.string.moniqi) + "\nDolphin", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_wii.png", "moniqi_wii"),
			new Console("GB&GBC " + getMoniqiText(R.string.moniqi) + "\nMy OldBoy!", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_gb_gbc.png", "moniqi_gb"),
			new Console("FC " + getMoniqiText(R.string.moniqi) + "\nNES.emu", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/moniqi/moniqi_fc.png", "moniqi_fc"),
		}; 
		int in = 0;//定义数值
		//遍历
		while (in < 模拟器.length)
		{       	
			moniqilist.add(模拟器[in++]);
		}
	}
	private void init3()
	{
		Console[]金手指_游戏={
			new Console("星之卡比 梦之泉物语", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/mengzhiquan.jpg", "fc_mzq"),
			new Console("星之卡比 梦之泉DX", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/mengzhiquandx.jpg", "gba_mzqdx"),
			new Console("星之卡比 镜之大迷宫", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/jingmi.jpg", "gba_jm")
		};
		int ind = 0;//定义数值
		//遍历
		while (ind < 金手指_游戏.length)
		{       	
			jszgamelist.add(金手指_游戏[ind++]);
		}
	}
	private void init4(){
		Console[]tj_game={
			new Console("星之卡比 梦之泉DX", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/mengzhiquandx.jpg", "gba_mzqdx"),
			new Console("星之卡比 镜之大迷宫", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/jingmi.jpg", "gba_jm"),
		    new Console("星之卡比 超究豪华版", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/kssu.jpg","nds_kssu"),
		new Console("星之卡比 呐喊团", "https://raw.githubusercontent.com/nihaocun/kirby_image/master/game/nahantuan.jpg","nds_nht")
		
		};
		int ind = 0;//定义数值
		//遍历
		while (ind < tj_game.length)
		{       	
			jszgamelist.add(tj_game[ind++]);
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
