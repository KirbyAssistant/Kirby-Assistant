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
    private View consose_view, emulator_view,cheat_code_gamelist_view ;//页卡视图
    private List<View> mViewList = new ArrayList<>();
	private List<Console> consolelist=new ArrayList<>();
	private List<Console> cheatCodeGamelist=new ArrayList<>();
	private List<Console> emulatorslist=new ArrayList<>();
	private ConsoleAdapter adapter;
	private EmulatorsAdapter adapterlv_emulator;
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
	private void initPaper(View view)
	{	
		//实例化viewpager需要的
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
		mInflater = LayoutInflater.from(getActivity());
		
		consose_view = mInflater.inflate(R.layout.viewpaper_game, null);
        emulator_view = mInflater.inflate(R.layout.viewpaper_emulators, null);
    	cheat_code_gamelist_view = mInflater.inflate(R.layout.viewpaper_cheatcode, null);	
		//添加页卡视图
		//mViewList.add(view0);
        mViewList.add(consose_view);
        mViewList.add(emulator_view);
		mViewList.add(cheat_code_gamelist_view);
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
		RecyclerView rlv_consose = (RecyclerView) consose_view.findViewById(R.id.consose_list); 
	    RecyclerView rlv_emulator = (RecyclerView) emulator_view.findViewById(R.id.emulators_list);
		RecyclerView rlv_cheat_code_game_list = (RecyclerView) cheat_code_gamelist_view.findViewById(R.id.cheatcode_list);
		//主机列表配置
		GridLayoutManager layoutManager_consose=new GridLayoutManager(getActivity(), 1);
		rlv_consose.setLayoutManager(layoutManager_consose);
		adapter = new ConsoleAdapter(consolelist,getActivity());
		rlv_consose.setAdapter(adapter);
		ResourceData.setConsoseData(consolelist);
		//模拟器列表配置
		GridLayoutManager layoutManager_emulator=new GridLayoutManager(getActivity(), 1);
		rlv_emulator.setLayoutManager(layoutManager_emulator);
		adapterlv_emulator = new EmulatorsAdapter(emulatorslist,getActivity());
		rlv_emulator.setAdapter(adapterlv_emulator);
		ResourceData.setEmulatorData(emulatorslist);
		//金手指游戏列表配置
		GridLayoutManager layoutManager_cheat_code_game_list=new GridLayoutManager(getActivity(), 1);
		rlv_cheat_code_game_list.setLayoutManager(layoutManager_cheat_code_game_list);
		adapter3 = new CheatCodeGameListAdapter(cheatCodeGamelist,getActivity());
		rlv_cheat_code_game_list.setAdapter(adapter3);
		ResourceData.setCheatCodeGameData(cheatCodeGamelist);
		
		LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
		ViewGroup viewGroup = (ViewGroup)view.findViewById(R.id.root_view);
        viewGroup.setLayoutAnimation(controller);
        viewGroup.scheduleLayoutAnimation();
		PlayAnimUtil.playLayoutAnimationWithRecyclerView(rlv_consose,LayoutAnimationHelper.getAnimationSetFromBottom(),false);
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
