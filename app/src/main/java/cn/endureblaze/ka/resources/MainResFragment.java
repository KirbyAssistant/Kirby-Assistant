package cn.endureblaze.ka.resources;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseFragment;
import cn.endureblaze.ka.bean.CheatCodeGame;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.bean.Emulator;
import cn.endureblaze.ka.helper.LayoutAnimationHelper;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.utils.PlayAnimUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainResFragment extends BaseFragment
{
	private List<String> mTitleList = new ArrayList<>();//页卡标题集合

	private List<View> mViewList = new ArrayList<>();

	private List<Console> consolelist=new ArrayList<>();
	private List<CheatCodeGame> cheatCodeGamelist=new ArrayList<>();
	private List<Emulator> emulatorlist=new ArrayList<>();

    private RecyclerView rlv_consose;
    private RecyclerView rlv_emulator;
    private RecyclerView rlv_cheat_code_game_list;

    private ConsoleAdapter console_adapter;
    private EmulatorAdapter emulator_adapter;
    private CheatCodeGameAdapter cheat_adapter;

    private View view;


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
	    view=inflater.inflate(R.layout.main_game, container, false);
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

        //页卡视图
		View console_view = mInflater.inflate(R.layout.viewpaper_console, null);
		View emulator_view = mInflater.inflate(R.layout.viewpaper_emulator, null);
		View cheat_code_gamelist_view = mInflater.inflate(R.layout.viewpaper_cheatcode, null);

		//添加页卡视图
        mViewList.add(console_view);
        mViewList.add(emulator_view);
		mViewList.add(cheat_code_gamelist_view);

		//添加页卡标题
        mTitleList.add(Objects.requireNonNull(getActivity()).getString(R.string.game));
        mTitleList.add(getActivity().getString(R.string.emulator));
		mTitleList.add(getActivity().getString(R.string.cheatCode_title));

		mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
		MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList,mTitleList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

		//列表需要的布局
        rlv_consose = console_view.findViewById(R.id.consose_list);//主机列表
        rlv_emulator = emulator_view.findViewById(R.id.emulator_list);//模拟器列表
        rlv_cheat_code_game_list = cheat_code_gamelist_view.findViewById(R.id.cheatcode_list);//金手指游戏列表

		//主机列表配置
		GridLayoutManager layoutManager_consose=new GridLayoutManager(getActivity(), 1);
		rlv_consose.setLayoutManager(layoutManager_consose);
		console_adapter = new ConsoleAdapter(consolelist, getActivity());
		rlv_consose.setAdapter(console_adapter);
		ResourceData.setConsoseData(consolelist);

        //模拟器列表配置
		GridLayoutManager layoutManager_emulator=new GridLayoutManager(getActivity(), 3);
		rlv_emulator.setLayoutManager(layoutManager_emulator);
		emulator_adapter = new EmulatorAdapter(emulatorlist, getActivity());
		rlv_emulator.setAdapter(emulator_adapter);
        ResourceData.setEmulatorData(emulatorlist);

		//金手指游戏列表配置
		GridLayoutManager layoutManager_cheat_code_game_list=new GridLayoutManager(getActivity(), 1);
		rlv_cheat_code_game_list.setLayoutManager(layoutManager_cheat_code_game_list);
		cheat_adapter = new CheatCodeGameAdapter(cheatCodeGamelist, getActivity());
		rlv_cheat_code_game_list.setAdapter(cheat_adapter);
		ResourceData.setCheatCodeGameData(cheatCodeGamelist);

		//进入时候的动画
		LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
		ViewGroup viewGroup = view.findViewById(R.id.root_view);
        viewGroup.setLayoutAnimation(controller);
        viewGroup.scheduleLayoutAnimation();
		PlayAnimUtil.playLayoutAnimationWithRecyclerView(rlv_consose,LayoutAnimationHelper.getAnimationSetFromBottom(),false);
	}
}
