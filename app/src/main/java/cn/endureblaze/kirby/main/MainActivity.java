package cn.endureblaze.kirby.main;

import android.annotation.SuppressLint;
import android.content.*;
import android.os.Bundle;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseActivity;
import cn.endureblaze.kirby.chat.ChatMode;
import cn.endureblaze.kirby.chat.dialog.EditChatDialog;
import cn.endureblaze.kirby.chat.fragment.MainChatFragment;
import cn.endureblaze.kirby.customview.NoScrollViewPager;
import cn.endureblaze.kirby.data.DataBus;
import cn.endureblaze.kirby.main.adapter.ThemeListAdapter;
import cn.endureblaze.kirby.main.adapter.ViewPagerAdapter;
import cn.endureblaze.kirby.main.donate.DonateActivity;
import cn.endureblaze.kirby.resources.fragment.MainResFragment;
import cn.endureblaze.kirby.setting.SettingActivity;
import cn.endureblaze.kirby.user.info.MainUserInfoFragment;
import cn.endureblaze.kirby.user.login.MainLoginFragment;
import cn.endureblaze.kirby.util.DownloadApkUtil;
import cn.endureblaze.kirby.util.ThemeUtil;
import cn.endureblaze.kirby.util.UserUtil;
import cn.endureblaze.kirby.video.fragment.MainVideoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> page_title = new ArrayList<>();
    private NoScrollViewPager main_fragment_viewpager;
    //碎片
    private MainResFragment mainResFragment;
    private MainVideoFragment mainVideoFragment;
    private MainChatFragment mainChatFragment;
    private MainLoginFragment mainLoginFragment;
    private MainUserInfoFragment mainUserInfoFragment;

    private ViewPagerAdapter viewpager_adapter;

    private int fragment_position = 0;
    private BottomNavigationView bottomNavigationView;
    private LocalBroadcastManager localBroadcastManager;

    private boolean isVide = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
        findShortcut();
        //配置toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(R.string.title_res);
        //处理碎片刷新的广播
        initBroad();
        //初始化ViewPager
        main_fragment_viewpager = findViewById(R.id.main_fragment_viewpager);
        main_fragment_viewpager.setNoScroll(true);
        main_fragment_viewpager.setOffscreenPageLimit(4);
        initFragmentViewPager();
        Intent intent = getIntent();
        if(intent.getBooleanExtra("theme",false)) {
            int tag = intent.getIntExtra("fragment", 0);
            bottomNavigationView.getMenu().getItem(tag).setChecked(true);
            main_fragment_viewpager.setCurrentItem(tag);
            toolbar.setSubtitle(page_title.get(tag));
        }
    }
    private void initBroad(){
        localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(this));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.endureblaze.kirby.USER_LOGIN");
        intentFilter.addAction("cn.endureblaze.kirby.USER_LOGOUT");
        MainLocalReceiver localReceiver = new MainLocalReceiver();
        //注册本地广播监听器
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }
    //配置碎片显示
    private void initFragmentViewPager(){
        //FAB获取
        FloatingActionButton fab = findViewById(R.id.fab_main);
        @SuppressLint("ResourceType")
        ScaleAnimation mess_fab_show_anim = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.transition.mess_fab_show);
        @SuppressLint("ResourceType")
        ScaleAnimation mess_fab_hide_anim = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.transition.mess_fab_hide);
        //底栏设置
        bottomNavigationView = findViewById(R.id.main_bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.res:
                    main_fragment_viewpager.setCurrentItem(0);
                    toolbar.setSubtitle(page_title.get(0));

                    if (fab.getVisibility() == View.VISIBLE) {
                        fab.setVisibility(View.GONE);
                        fab.setAnimation(mess_fab_hide_anim);
                    }
                    break;
                case R.id.video:
                    main_fragment_viewpager.setCurrentItem(1);
                    toolbar.setSubtitle(page_title.get(1));

                    if (fab.getVisibility() == View.VISIBLE) {
                        fab.setVisibility(View.GONE);
                        fab.setAnimation(mess_fab_hide_anim);
                    }
                    break;
                case R.id.chat:
                    main_fragment_viewpager.setCurrentItem(2);
                    toolbar.setSubtitle(page_title.get(2));

                    fab.setVisibility(View.VISIBLE);
                    fab.startAnimation(mess_fab_show_anim);
                    showEditChatDialog(fab);
                    break;
                case R.id.me:
                    main_fragment_viewpager.setCurrentItem(3);
                    toolbar.setSubtitle(page_title.get(3));

                    if (fab.getVisibility() == View.VISIBLE) {
                        fab.setVisibility(View.GONE);
                        fab.setAnimation(mess_fab_hide_anim);
                    }
                    break;
            }
            return true;
        });
        // 为ViewPager添加页面改变事件
        main_fragment_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 将当前的页面对应的底部标签设为选中状态
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                fragment_position = position;

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //创建碎片
        mainResFragment = new MainResFragment();
        mainVideoFragment = new MainVideoFragment();
        mainChatFragment = new MainChatFragment();
        mainLoginFragment = new MainLoginFragment();
        mainUserInfoFragment = new MainUserInfoFragment();
        //构造适配器
        //资源
        fragments.add(mainResFragment);
        page_title.add(getResources().getString(R.string.title_res));
        //视频
        fragments.add(mainVideoFragment);
        page_title.add(getResources().getString(R.string.title_video));
        //闲聊
        fragments.add(mainChatFragment);
        page_title.add(getResources().getString(R.string.title_chat));
        //判断是否登录
        if(UserUtil.isUserLogin())
        {
            fragments.add(mainUserInfoFragment);
            page_title.add(UserUtil.getCurrentUser().getUsername());
        }else {
            fragments.add(mainLoginFragment);
            page_title.add(getResources().getString(R.string.title_login));
        }
        //设定适配器
        viewpager_adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, page_title);
        main_fragment_viewpager.setAdapter(viewpager_adapter);

        if(isVide){
            toolbar.setSubtitle(R.string.title_login);
            main_fragment_viewpager.setCurrentItem(1);
            bottomNavigationView.setSelectedItemId(R.id.video);
        }
    }

    //快捷方式跳转
    private void findShortcut() {
        Intent intent = getIntent();
        if ("video".equals(intent.getStringExtra("function"))) {
            isVide = true;
        }
    }
    //设置闲聊界面的FAB显示
    private void showEditChatDialog(FloatingActionButton fab){
        fab.setOnClickListener(v -> EditChatDialog.newInstance("0",null,null, ChatMode.CHAT_SEND_MODE)
                .setTheme(R.style.OMGDialogStyle)
                .setMargin(0)
                .setGravity(Gravity.BOTTOM)
                .setOutCancel(true)
                .show(getSupportFragmentManager()));
    }
    //ToolBar菜单配置
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    //菜单选项
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.theme:
                Intent broad = new Intent("cn.endureblaze.kirby.CHANGE_THEME");
                broad.putExtra("type", "change_theme");
                localBroadcastManager.sendBroadcast(broad);
                /*
                 *用于显示主题列表
                 */
                SharedPreferences theme_id = getSharedPreferences(ThemeUtil.FILE_NAME, 0);
                final int itemSelected = theme_id.getInt("themeId", 0);
                MaterialAlertDialogBuilder theme = new MaterialAlertDialogBuilder(MainActivity.this);
                theme.setTitle(R.string.toolbar_menu_theme);
                Integer[] res = new Integer[]{
                        R.drawable.theme_blue,
                        R.drawable.theme_red,
                        R.drawable.theme_purple,
                        R.drawable.theme_indigo,
                        R.drawable.theme_teal,
                        R.drawable.theme_green,
                        R.drawable.theme_orange,
                        R.drawable.theme_brown,
                        R.drawable.theme_bluegrey,
                        R.drawable.theme_yellow,
                        R.drawable.theme_white,
                        R.drawable.theme_dark
                };
                List<Integer> list = Arrays.asList(res);
                ThemeListAdapter adapter = new ThemeListAdapter(MainActivity.this, list);
                adapter.setCheckItem(itemSelected);
                @SuppressLint("InflateParams") GridView gridView = (GridView) LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_sw_theme, null);
                gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
                gridView.setCacheColorHint(0);
                gridView.setAdapter(adapter);
                theme.setView(gridView);
                final AlertDialog dialog = theme.show();
                gridView.setOnItemClickListener(
                        (parent, view, position, id) -> {
                            dialog.dismiss();
                            if (itemSelected != position) {
                                ThemeUtil.setThemeByName(MainActivity.this, position);
                                changeTheme();
                            }
                        }
                );
                break;
            case R.id.setting:
                Intent setting = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(setting);
                break;
            case R.id.app:
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                builder.setTitle(R.string.toolbar_menu_app);
                String[] items = {"ZArchiver\n" + getResources().getString(R.string.toolbar_menu_app_ZArchiver)};
                builder.setItems(items, (dialogInterface, i) -> {
                    switch (i) {
                        case 0:
                            DownloadApkUtil.downloadAppApk("ZArchiver", MainActivity.this);
                            break;
                        default:
                            break;
                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.donate:
                Intent donate = new Intent(MainActivity.this, DonateActivity.class);
                startActivity(donate);
                break;
            default:
        }
        return true;
    }
    /**
     * 方法名:changeTheme
     * 不需要传入参数
     * 用于退出并再次打开MainActivity 适用于修改主题或者修改用户头像等之后使用
     */
    private void changeTheme() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("theme",true);
        intent.putExtra("fragment",fragment_position);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//假装没退出过...
        finish();
    }
    //按两次退出
    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar.make(findViewById(R.id.toolbar), R.string.two_back, Snackbar.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //广播处理
    class MainLocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            changeFragment(intent);
        }
    }
    private void changeFragment(Intent intent){
        String type = intent.getStringExtra("type");
        switch (Objects.requireNonNull(type)){
            case "user_login":
                //替换登录到用户详情
                fragments.remove(3);
                fragments.add(3,mainUserInfoFragment);

                page_title.remove(3);
                page_title.add(3,UserUtil.getCurrentUser().getUsername());
                toolbar.setSubtitle(UserUtil.getCurrentUser().getUsername());

                viewpager_adapter.notifyDataSetChanged();
                break;
            case "user_logout":
                //替换用户详情到登录
                fragments.remove(3);
                fragments.add(3,mainLoginFragment);
                //顺便刷新一下闲聊
                fragments.remove(2);
                fragments.add(2,mainChatFragment);

                page_title.remove(3);
                page_title.add(3,getResources().getString(R.string.title_login));
                toolbar.setSubtitle(R.string.title_login);

                viewpager_adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(DataBus.isChangeUserAvatar()) {
            DataBus.setChangeUserAvatar(false);

            fragments.remove(3);
            fragments.add(3, new MainUserInfoFragment());

            viewpager_adapter.notifyDataSetChanged();
        }
    }

    //保存状态
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("windows_save", getWindow().saveHierarchyState());
        outState.putCharSequence("fragment", toolbar.getSubtitle());
    }
    //恢复状态

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setSubtitle(savedInstanceState.getString("fragment"));
    }
}
