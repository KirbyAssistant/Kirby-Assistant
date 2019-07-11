package cn.endureblaze.ka.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseActivity;
import cn.endureblaze.ka.chat.MainChatFragment;
import cn.endureblaze.ka.chat.MainNullFragment;
import cn.endureblaze.ka.main.donate.DonateActivity;
import cn.endureblaze.ka.main.theme.ColorListAdapter;
import cn.endureblaze.ka.me.login.MainLoginFragment;
import cn.endureblaze.ka.me.user.MainUserFragment;
import cn.endureblaze.ka.resources.MainGameFragment;
import cn.endureblaze.ka.setting.SettingActivity;
import cn.endureblaze.ka.utils.*;
import cn.endureblaze.ka.video.MainVideoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.umeng.analytics.MobclickAgent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 类类型:Activity
 * 类名称:MainActivity
 * 加载动画完毕后显示的Activity
 * 是整个app的核心
 */
public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private Context gameContext;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtil.setClassTheme(this);
        setContentView(R.layout.activity_main);
        //配置toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (CheckSimpleModeUtil.isSimpleMode()) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.simple_mode_app_name));
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name);
        }
        toolbar.setSubtitle(R.string.ziyuan);
        replaceFragment(new MainGameFragment());
        bottomBar();
        findShortcut();
        permissionAndPrivacy();
        CheckUpdateUtil.checkUpdate(toolbar, this);
        //友盟统计
        if (UserUtil.getCurrentUser() != null) {
            MobclickAgent.onProfileSignIn(UserUtil.getCurrentUser().getUsername());
        }
    }

    private void permissionAndPrivacy() {
        SharedPreferences preferences = getSharedPreferences("boolean", 0);
        boolean pAp = preferences.getBoolean("permissionAndPrivacy", false);
        if (!pAp) {
            AlertDialog.Builder permissionAndPrivacy_dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.permissionandprivacy_title)
                    .setMessage(R.string.permissionandprivacy_cnntent)
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.dia_agree), (dialog, which) -> {
                                SharedPreferences.Editor t = getSharedPreferences("boolean", 0).edit();
                                t.putBoolean("permissionAndPrivacy", true);
                                t.apply();
                                copyrightsWarning();
                            }
                    )
                    .setNeutralButton(getResources().getString(R.string.dia_disagree), (dialog, which) -> finish()
                    );
            permissionAndPrivacy_dialog.show();
        } else {
            copyrightsWarning();
        }
    }

    private void copyrightsWarning() {
        final SharedPreferences pref = getSharedPreferences("agreements", MODE_PRIVATE);
        if (!pref.getBoolean("agree_copyrights", false)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.copyrights_warning_title)
                    .setMessage(R.string.copyrights_warning_content)
                    .setCancelable(false)
                    .setPositiveButton(R.string.dia_agree, (dialogInterface, i) -> pref.edit().putBoolean("agree_copyrights", true).apply())
                    .setNeutralButton(R.string.dia_disagree, (dialogInterface, i) -> finish()).show();
        }
    }

    /**
     * 方法名:bottomBar
     * 不需要传入参数
     * 用于显示底部导航栏的方法
     * 内部完成了所有逻辑
     */
    private void bottomBar() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        if (CheckSimpleModeUtil.isSimpleMode()) {
            bottomNavigationView.getMenu().removeItem(R.id.talk);
            bottomNavigationView.getMenu().removeItem(R.id.me);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.ziyuan:
                    if (toolbar.getSubtitle() != getResources().getString(R.string.ziyuan)) {
                        toolbar.setSubtitle(R.string.ziyuan);
                        replaceFragment(new MainGameFragment());
                    }
                    break;
                case R.id.video:
                    if (toolbar.getSubtitle() != getResources().getString(R.string.video_title)) {
                        toolbar.setSubtitle(R.string.video_title);
                        replaceFragment(new MainVideoFragment());
                    }
                    break;
                case R.id.talk:
                    if (toolbar.getSubtitle() != getResources().getString(R.string.talk)) {
                        toolbar.setSubtitle(R.string.talk);
                        if (UserUtil.isUserLogin()) {
                            replaceFragment(new MainChatFragment());
                        } else {
                            replaceFragment(new MainNullFragment());
                        }
                    }
                    break;
                case R.id.me:
                    if (UserUtil.isUserLogin()) {
                        if (toolbar.getSubtitle() != UserUtil.getCurrentUser().getUsername()) {
                            replaceFragment(new MainUserFragment());
                            toolbar.setSubtitle(UserUtil.getCurrentUser().getUsername());
                        }
                    } else {
                        if (toolbar.getSubtitle() != getResources().getString(R.string.login_title)) {
                            replaceFragment(new MainLoginFragment());
                            toolbar.setSubtitle(R.string.login_title);
                        }
                    }
                    break;
            }
            return true;
        });
    }

    private void findShortcut() {
        Intent intent = getIntent();
        if ("video".equals(intent.getStringExtra("function"))) {
            toolbar.setSubtitle(R.string.video_title);
            replaceFragment(new MainVideoFragment());
            bottomNavigationView.setSelectedItemId(R.id.video);
        }
        if ("user".equals(intent.getStringExtra("function"))) {
            toolbar.setSubtitle(R.string.user_title);
            replaceFragment(new MainUserFragment());
            bottomNavigationView.setSelectedItemId(R.id.me);
        }
    }

    /**
     * 方法名:replaceFragment
     * 需要传入参数(Fragment fragment)
     * 参数说明:需要传入一个实例化的Fragment
     * 参数举例:replaceFragment(new MainMessFragment())  MainMessFragment是对应Fragment的名称
     * 用于显示MainActivity上id为fragment的组件显示的内容
     */
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!CheckSimpleModeUtil.isSimpleMode()) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }
        transaction.replace(R.id.main_fragment, fragment);
        transaction.commit();
    }

    /**
     * 方法名:setCustomTheme
     * 需要传入参数(int i)
     * 参数说明:需要传入一个int类型数据，这个数据是主题列表item对应的id，从0开始计算
     * 参数举例:setCustomTheme(0)  表示设置列表第一个主题
     * 用于设置主题颜色参数
     */
    private void setCustomTheme(int i) {
        ThemeUtil.setTheme(MainActivity.this, i);
        SharedPreferences.Editor y = getSharedPreferences("customtheme", 0).edit();
        y.putInt("id", i);
        y.apply();
        open();
    }

    /**
     * 方法名:open
     * 不需要传入参数
     * 用于退出并再次打开MainActivity 适用于修改主题或者修改用户头像等之后使用
     */
    public void open() {
        Intent intent = getIntent();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//假装没退出过...
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    //初始化toolbar菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        if (CheckSimpleModeUtil.isSimpleMode()) {
            menu.removeItem(R.id.theme);
        }
        return true;
    }

    //按两次退出
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar.make(toolbar, R.string.two_back, Snackbar.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    //获取toolbar菜单id执行事件
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.theme:
                /*
                 *用于显示主题列表
                 *调用了方法setCustomTheme
                 */
                SharedPreferences c = getSharedPreferences("customtheme", 0);
                final int itemSelected = c.getInt("id", 0);
                AlertDialog.Builder theme = new AlertDialog.Builder(MainActivity.this);
                theme.setTitle(R.string.theme_title);
                Integer[] res = new Integer[]{
                        R.drawable.theme_blue,
                        R.drawable.theme_red,
                        R.drawable.theme_purple,
                        R.drawable.theme_lindigo,
                        R.drawable.theme_teal,
                        R.drawable.theme_green,
                        R.drawable.theme_orange,
                        R.drawable.theme_brown,
                        R.drawable.theme_bluegrey,
                        R.drawable.theme_yellow,
                        R.drawable.theme_kirby,
                        R.drawable.theme_white
                };
                List<Integer> list = Arrays.asList(res);
                ColorListAdapter adapter = new ColorListAdapter(MainActivity.this, list);
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
                                setCustomTheme(position);
                            }
                        }

                );
                break;
            case R.id.setting:
                Intent setting = new Intent(MainActivity.this, SettingActivity.class);
                IntentUtil.startActivityWithAnim(setting, MainActivity.this);
                break;
            case R.id.app:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.tj_app);
                String[] items = {"ZArchiver\n" + getResources().getString(R.string.app_ZArchiver)};
                builder.setItems(items, (dialogInterface, i) -> {
                    switch (i) {
                        case 0:
                            DownloadApkUtil.downloadappApk("ZArchiver", MainActivity.this);
                            break;
                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.donate:
                Intent donate = new Intent(MainActivity.this, DonateActivity.class);
                IntentUtil.startActivityWithAnim(donate, MainActivity.this);
                break;
            default:
        }
        return true;
    }

    /**
     * 方法名:downloadappApk
     * 需要传入参数(final String app_namee
     * 参数说明:需要传入一个String类型的应用名称
     * 参数举例:downloadappApk("kirby Assistant")  表示查询名称为kirby Assistant的app的下载链接
     * 主要用于查询模拟器和推荐应用链接
     * 调用了方法:appFileDownload
     */

    public void theDownload(Context context, String game_name, @NonNull String position) {
        gameContext = context;
        switch (position) {
            case "emulators_gba"://"GBA " + getGameText(R.string.moniqi) + "\nMy Boy!":
                showOtherDownloadDialog("gba", game_name);
                break;
            case "emulators_sfc"://"SFC "++"\nSnes9x EX+":
                showOtherDownloadDialog("sfc", game_name);
                break;
            case "emulators_n64"://"N64 "++"\nTendo64":
                showOtherDownloadDialog("n64", game_name);
                break;
            case "emulators_nds"://"NDS "++"\nDraStic":
                showOtherDownloadDialog("nds", game_name);
                break;
            case "emulators_wii"://"NGC&WII "++"\nDolphin":
                showOtherDownloadDialog("wii", game_name);
                break;
            case "emulators_gb"://"GB&GBC "++"\nMy OldBoy!":
                showOtherDownloadDialog("gb", game_name);
                break;
            case "emulators_fc"://"FC "++"\nNES.emu":
                showOtherDownloadDialog("fc", game_name);
                break;
        }
    }

    private void showOtherDownloadDialog(final String downloadName, String game_name) {
        AlertDialog.Builder dialog = new
                AlertDialog.Builder(gameContext)
                .setTitle(game_name)
                .setMessage(R.string.download_dia_mess)
                .setPositiveButton(R.string.dia_download, (dialog1, which) -> DownloadApkUtil.downloadappApk(downloadName, gameContext)
                );
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("windows_save", getWindow().saveHierarchyState());
        outState.putCharSequence("fragment", toolbar.getSubtitle());
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (getWindow() != null) {
            Bundle windowState = savedInstanceState.getBundle("windows_save");
            if (windowState != null) {
                getWindow().restoreHierarchyState(windowState);
            }
        }
        CharSequence fragment_cheak = savedInstanceState.getCharSequence("fragment");
        if (Objects.equals(fragment_cheak, getResources().getString(R.string.ziyuan))) {
            toolbar.setSubtitle(R.string.ziyuan);
            replaceFragment(new MainGameFragment());
        }
        if (Objects.equals(fragment_cheak, getResources().getString(R.string.video_title))) {
            toolbar.setSubtitle(R.string.video_title);
            replaceFragment(new MainVideoFragment());
        }
        if (Objects.equals(fragment_cheak, getResources().getString(R.string.talk))) {
            toolbar.setSubtitle(R.string.talk);
            if (null == UserUtil.getCurrentUser()) {
                replaceFragment(new MainNullFragment());
            } else {
                replaceFragment(new MainChatFragment());
            }
        }
        if (null == UserUtil.getCurrentUser()) {
            if (Objects.requireNonNull(fragment_cheak).equals(getResources().getString(R.string.login_title))) {
                replaceFragment(new MainLoginFragment());
                toolbar.setSubtitle(R.string.login_title);
            }
        } else {
            if (Objects.requireNonNull(fragment_cheak).equals(UserUtil.getCurrentUser().getUsername())) {
                replaceFragment(new MainUserFragment());
                toolbar.setSubtitle(UserUtil.getCurrentUser().getUsername());
            }
        }

    }

    @Override
    public void onResume() {
        bottomBar();
        super.onResume();
    }
}
