package cn.endureblaze.ka.resources;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.support.v7.widget.*;
import android.view.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.bean.*;
import java.util.*;
import cn.endureblaze.ka.helper.*;
import android.view.animation.*;
import android.widget.*;
import cn.endureblaze.ka.base.*;
import cn.endureblaze.ka.resources.game.*;
import cn.endureblaze.ka.main.*;
import cn.endureblaze.ka.resources.*;
import cn.endureblaze.ka.utils.*;

public class MainGameFragment extends BaseFragment
{
	private ViewPager mViewPager;
	private TabLayout mTabLayout;
	private LayoutInflater mInflater;
	private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view0, view1, view2,view3 ;//页卡视图
    private List<View> mViewList = new ArrayList<>();
	private List<Console> consolelist=new ArrayList<>();
	private List<Console> tjgamelist=new ArrayList<>();
	private List<Console> cheatCodeGamelist=new ArrayList<>();
	private List<Console> emulatorslist=new ArrayList<>();
	private ConsoleAdapter adapter;
	private EmulatorsAdapter adapter1;
	private GameAdapter adapter2;
	private CheatCodeGameListAdapter adapter3;
	//private TjGameAdapter adapter4;
  	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.main_game, container, false);
		MainActivity m=(MainActivity)getActivity();
		initPaper(view);
		return view;
	}
	private String getEmulatorsText(int res_id)
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
        view2 = mInflater.inflate(R.layout.viewpaper_emulators, null);
    	view3 = mInflater.inflate(R.layout.viewpaper_cheatcode, null);	
		//添加页卡视图
		//mViewList.add(view0);
        mViewList.add(view1);
        mViewList.add(view2);
		mViewList.add(view3);
		//添加页卡标题
		//mTitleList.add(getActivity().getString(R.string.tj_title));
        mTitleList.add(getActivity().getString(R.string.game));
        mTitleList.add(getActivity().getString(R.string.emulators));
		mTitleList.add(getActivity().getString(R.string.cheatCode_title));
		mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
		MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
		//主机列表和模拟器列表需要的
		RecyclerView r = (RecyclerView) view1.findViewById(R.id.consose_list); 
	//	RecyclerView tj_game=(RecyclerView)view0.findViewById(R.id.tj_game_list);
		RecyclerView r1 = (RecyclerView) view2.findViewById(R.id.emulators_list);
		RecyclerView r2 = (RecyclerView) view3.findViewById(R.id.cheatcode_list);
		//主机列表配置
		GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 1);
		r.setLayoutManager(layoutManager);
		adapter = new ConsoleAdapter(consolelist,getActivity());
		r.setAdapter(adapter);
		//模拟器列表配置
		GridLayoutManager layoutManager2=new GridLayoutManager(getActivity(), 1);
		r1.setLayoutManager(layoutManager2);
		adapter1 = new EmulatorsAdapter(emulatorslist);
		r1.setAdapter(adapter1);
		//金手指游戏列表配置
		GridLayoutManager layoutManager3=new GridLayoutManager(getActivity(), 1);
		r2.setLayoutManager(layoutManager3);
		adapter3 = new CheatCodeGameListAdapter(cheatCodeGamelist,getActivity());
		r2.setAdapter(adapter3);
		//游戏推荐列表配置
	/*	LinearLayoutManager tj_game_m=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
		tj_game.setLayoutManager(tj_game_m);
		adapter4 =new TjGameAdapter(tjgamelist);
		tj_game.setAdapter(adapter4);*/
		LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
		ViewGroup viewGroup = (ViewGroup)view.findViewById(R.id.root_view);
        viewGroup.setLayoutAnimation(controller);
        viewGroup.scheduleLayoutAnimation();
		PlayAnimUtil.playLayoutAnimationWithRecyclerView(r,LayoutAnimationHelper.getAnimationSetFromBottom(),false);
		init();
		init2();
		init3();
		//init4();
	}
	private void init()
	{
		Console[]主机={
			new Console("gba", "https://gitee.com/nihaocun/ka_image/raw/master/consose/gba.png", "gba"),
			new Console("sfc", "https://gitee.com/nihaocun/ka_image/raw/master/consose/sfc.png", "sfc"),
			new Console("n64", "https://gitee.com/nihaocun/ka_image/raw/master/consose/n64.png", "n64"),
			new Console("ngc", "https://gitee.com/nihaocun/ka_image/raw/master/consose/ngc.png", "ngc"),
			new Console("wii", "https://gitee.com/nihaocun/ka_image/raw/master/consose/wii.png", "wii"),
			new Console("nds", "https://gitee.com/nihaocun/ka_image/raw/master/consose/nds.png", "nds"),
			new Console("gb", "https://gitee.com/nihaocun/ka_image/raw/master/consose/gb.png", "gb"),
			new Console("gbc", "https://gitee.com/nihaocun/ka_image/raw/master/consose/gbc.png", "gbc"),
			new Console("fc", "https://gitee.com/nihaocun/ka_image/raw/master/consose/fc.png", "fc")};
		
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
			new Console("GBA " + getEmulatorsText(R.string.emulators) + "\nMy Boy!", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_gba.png", "emulators_gba"),
			new Console("SFC " + getEmulatorsText(R.string.emulators) + "\nSnes9x EX+", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_sfc.png", "emulators_sfc"),
			new Console("N64 " + getEmulatorsText(R.string.emulators) + "\nTendo64", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_n64.png", "emulators_n64"),
			new Console("NDS " + getEmulatorsText(R.string.emulators) + "\nDraStic", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_nds.png", "emulators_nds"),
			new Console("NGC&WII " + getEmulatorsText(R.string.emulators) + "\nDolphin", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_wii.png", "emulators_wii"),
			new Console("GB&GBC " + getEmulatorsText(R.string.emulators) + "\nMy OldBoy!", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_gb_gbc.png", "emulators_gb"),
			new Console("FC " + getEmulatorsText(R.string.emulators) + "\nNES.emu", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_fc.png", "emulators_fc"),
		}; 
		int in = 0;//定义数值
		//遍历
		while (in < 模拟器.length)
		{       	
			emulatorslist.add(模拟器[in++]);
		}
	}
	private void init3()
	{
		Console[]金手指_游戏={
			new Console("星之卡比 梦之泉物语", "https://gitee.com/nihaocun/ka_image/raw/master/game/mengzhiquan.jpg", "fc_mzq"),
			new Console("星之卡比 梦之泉DX", "https://gitee.com/nihaocun/ka_image/raw/master/game/mengzhiquandx.jpg", "gba_mzqdx"),
			new Console("星之卡比 镜之大迷宫", "https://gitee.com/nihaocun/ka_image/raw/master/game/jingmi.jpg", "gba_jm")
		};
		int ind = 0;//定义数值
		//遍历
		while (ind < 金手指_游戏.length)
		{       	
			cheatCodeGamelist.add(金手指_游戏[ind++]);
		}
	}
	private void init4(){
		Console[]tj_game={
			new Console("星之卡比 梦之泉DX", "https://gitee.com/nihaocun/ka_image/raw/master/game/mengzhiquandx.jpg", "gba_mzqdx"),
			new Console("星之卡比 镜之大迷宫", "https://gitee.com/nihaocun/ka_image/raw/master/game/jingmi.jpg", "gba_jm"),
		    new Console("星之卡比 超究豪华版", "https://gitee.com/nihaocun/ka_image/raw/master/game/kssu.jpg","nds_kssu"),
		new Console("星之卡比 呐喊团", "https://gitee.com/nihaocun/ka_image/raw/master/game/nahantuan.jpg","nds_nht")
		
		};
		int ind = 0;//定义数值
		//遍历
		while (ind < tj_game.length)
		{       	
			cheatCodeGamelist.add(tj_game[ind++]);
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
