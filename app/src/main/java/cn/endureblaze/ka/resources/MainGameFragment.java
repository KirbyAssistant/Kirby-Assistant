package cn.endureblaze.ka.resources;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseFragment;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.helper.LayoutAnimationHelper;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.utils.PlayAnimUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainGameFragment extends BaseFragment
{
	private List<String> mTitleList = new ArrayList<>();//页卡标题集合
	private List<View> mViewList = new ArrayList<>();
	private List<Console> consolelist=new ArrayList<>();
	private List<Console> cheatCodeGamelist=new ArrayList<>();
	private List<Console> emulatorslist=new ArrayList<>();

	//private TjGameAdapter adapter4;
  	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.main_game, container, false);
		MainActivity m=(MainActivity)getActivity();
		initPaper(view);
		return view;
	}
    @SuppressLint("InflateParams")
    private void initPaper(View view)
	{	
		//实例化viewpager需要的
		ViewPager mViewPager = view.findViewById(R.id.viewpager);
		TabLayout mTabLayout = view.findViewById(R.id.tabLayout);
		LayoutInflater mInflater = LayoutInflater.from(getActivity());

		View consose_view = mInflater.inflate(R.layout.viewpaper_game, null);
		View emulator_view = mInflater.inflate(R.layout.viewpaper_emulators, null);
		//页卡视图
		View cheat_code_gamelist_view = mInflater.inflate(R.layout.viewpaper_cheatcode, null);
		//添加页卡视图
		//mViewList.add(view0);
        mViewList.add(consose_view);
        mViewList.add(emulator_view);
		mViewList.add(cheat_code_gamelist_view);
		//添加页卡标题
		//mTitleList.add(getActivity().getString(R.string.tj_title));
        mTitleList.add(Objects.requireNonNull(getActivity()).getString(R.string.game));
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
		RecyclerView rlv_consose = consose_view.findViewById(R.id.consose_list);
	    RecyclerView rlv_emulator = emulator_view.findViewById(R.id.emulators_list);
		RecyclerView rlv_cheat_code_game_list = cheat_code_gamelist_view.findViewById(R.id.cheatcode_list);
		//主机列表配置
		GridLayoutManager layoutManager_consose=new GridLayoutManager(getActivity(), 1);
		rlv_consose.setLayoutManager(layoutManager_consose);
		ConsoleAdapter adapter = new ConsoleAdapter(consolelist, getActivity());
		rlv_consose.setAdapter(adapter);
		ResourceData.setConsoseData(consolelist);
		//模拟器列表配置
		GridLayoutManager layoutManager_emulator=new GridLayoutManager(getActivity(), 1);
		rlv_emulator.setLayoutManager(layoutManager_emulator);
		EmulatorsAdapter adapterlv_emulator = new EmulatorsAdapter(emulatorslist, getActivity());
		rlv_emulator.setAdapter(adapterlv_emulator);
		ResourceData.setEmulatorData(emulatorslist);
		//金手指游戏列表配置
		GridLayoutManager layoutManager_cheat_code_game_list=new GridLayoutManager(getActivity(), 1);
		rlv_cheat_code_game_list.setLayoutManager(layoutManager_cheat_code_game_list);
		CheatCodeGameListAdapter adapter3 = new CheatCodeGameListAdapter(cheatCodeGamelist, getActivity());
		rlv_cheat_code_game_list.setAdapter(adapter3);
		ResourceData.setCheatCodeGameData(cheatCodeGamelist);
		
		LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
		ViewGroup viewGroup = view.findViewById(R.id.root_view);
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
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
		{
            return view == object;//官方推荐写法
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position)
		{
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object)
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
