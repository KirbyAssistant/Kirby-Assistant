package cn.endureblaze.kirby.resources.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseFragment;
import cn.endureblaze.kirby.bean.CheatCodeGame;
import cn.endureblaze.kirby.bean.Console;
import cn.endureblaze.kirby.bean.Emulator;
import cn.endureblaze.kirby.data.DataBus;
import cn.endureblaze.kirby.resources.adapter.CheatCodeGameAdapter;
import cn.endureblaze.kirby.resources.adapter.ConsoleAdapter;
import cn.endureblaze.kirby.resources.adapter.EmulatorAdapter;
import cn.endureblaze.kirby.resources.adapter.ResPagerAdapter;
import cn.endureblaze.kirby.resources.ResourceData;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainResFragment extends BaseFragment {

    private List<String> main_res_pager_title_list = new ArrayList<>();//页卡标题集合

    private List<View> main_res_pager_view_list = new ArrayList<>();

    private List<Console> console_list = new ArrayList<>();
    private List<CheatCodeGame> cheatCodeGame_list = new ArrayList<>();
    private List<Emulator> emulator_list = new ArrayList<>();

    private RecyclerView rlv_console;
    private RecyclerView rlv_emulator;
    private RecyclerView rlv_cheat_code_game_list;

    private ConsoleAdapter console_adapter;
    private EmulatorAdapter emulator_adapter;
    private CheatCodeGameAdapter cheat_adapter;

    private View main_res_view;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        main_res_view = inflater.inflate(R.layout.main_res_fragment, container, false);
        initView();
        return main_res_view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        initPagerData();
        initBroad();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (DataBus.isChangeTheme()) {
            initViewData();
        }
    }

    private void initBroad() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity()));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.endureblaze.kirby.CHANGE_THEME");
        ResLocalReceiver localReceiver = new ResLocalReceiver();
        //注册本地广播监听器
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    @SuppressLint("InflateParams")
    private void initView() {
        //实例化viewpager需要的
        mViewPager = main_res_view.findViewById(R.id.main_res_viewpager);
        mTabLayout = main_res_view.findViewById(R.id.main_res_tabLayout);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());

        //页卡视图
        View console_view = mInflater.inflate(R.layout.viewpager_console, null, false);
        View emulator_view = mInflater.inflate(R.layout.viewpager_emulator, null, false);
        View cheat_code_gamelist_view = mInflater.inflate(R.layout.viewpager_cheatcode, null, false);

        //添加页卡视图
        main_res_pager_view_list.add(console_view);
        main_res_pager_view_list.add(emulator_view);
        main_res_pager_view_list.add(cheat_code_gamelist_view);

        //添加页卡标题
        main_res_pager_title_list.add(Objects.requireNonNull(getActivity()).getResources().getString(R.string.tab_game));
        main_res_pager_title_list.add(Objects.requireNonNull(getActivity().getResources().getString(R.string.tab_emulator)));
        main_res_pager_title_list.add(Objects.requireNonNull(getActivity().getResources().getString(R.string.tab_cheatcode)));

        //列表需要的布局
        rlv_console = console_view.findViewById(R.id.console_list);//主机列表
        rlv_emulator = emulator_view.findViewById(R.id.emulator_list);//模拟器列表
        rlv_cheat_code_game_list = cheat_code_gamelist_view.findViewById(R.id.cheatcode_list);//金手指游戏列表

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(main_res_pager_title_list.get(0)));//添加tab选项卡
        ResPagerAdapter resPagerAdapter = new ResPagerAdapter(main_res_pager_view_list, main_res_pager_title_list);
        mViewPager.setAdapter(resPagerAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(resPagerAdapter);//给Tabs设置适配器
    }

    private void initViewData(){
        mViewPager.setCurrentItem(DataBus.getMainResViewpagerItem());
        mTabLayout.setScrollPosition(DataBus.getMainResTabLayoutItem(),0,false);
    }

    private void initPagerData() {
        //主机列表配置
        GridLayoutManager layoutManager_console = new GridLayoutManager(getActivity(), 1);
        rlv_console.setLayoutManager(layoutManager_console);
        console_adapter = new ConsoleAdapter(console_list, getActivity());
        rlv_console.setAdapter(console_adapter);
        ResourceData.setConsoseData(console_list);

        //模拟器列表配置
        GridLayoutManager layoutManager_emulator = new GridLayoutManager(getActivity(), 3);
        rlv_emulator.setLayoutManager(layoutManager_emulator);
        emulator_adapter = new EmulatorAdapter(emulator_list, getActivity());
        rlv_emulator.setAdapter(emulator_adapter);
        ResourceData.setEmulatorData(emulator_list);

        //金手指游戏列表配置
        GridLayoutManager layoutManager_cheat_code_game_list = new GridLayoutManager(getActivity(), 1);
        rlv_cheat_code_game_list.setLayoutManager(layoutManager_cheat_code_game_list);
        cheat_adapter = new CheatCodeGameAdapter(cheatCodeGame_list, getActivity());
        rlv_cheat_code_game_list.setAdapter(cheat_adapter);
        ResourceData.setCheatCodeGameData(cheatCodeGame_list);
    }

    class ResLocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            DataBus.setChangeTheme(true);
            DataBus.setMainResViewpagerItem(mViewPager.getCurrentItem());
            DataBus.setMainResTabLayoutItem(mTabLayout.getSelectedTabPosition());
        }
    }
}