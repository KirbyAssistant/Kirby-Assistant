package cn.endureblaze.ka.launcher;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseActivity;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.main.donate.DonateActivity;
import cn.endureblaze.ka.me.user.userhead.HeadActivity;
import cn.endureblaze.ka.utils.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 类类型:Activity
 * 名称:Launcher
 * 进入看到的第一个Activity
 * 用于显示加载动画
 */

public class Launcher extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //设置状态栏为透明
        window.setStatusBarColor(Color.TRANSPARENT);
        //设置window的状态栏不可见
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ImageView icon = findViewById(R.id.welcomeImageView1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            setupShortcuts();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void setupShortcuts() {
        ShortcutManager mShortcutManager = getSystemService(ShortcutManager.class);
        List<ShortcutInfo> infos = new ArrayList<>();

        Intent main_intent = new Intent(this, MainActivity.class);
        main_intent.setAction(Intent.ACTION_VIEW);

        Intent video_intent = new Intent(this, MainActivity.class);
        video_intent.setAction(Intent.ACTION_VIEW);
        video_intent.putExtra("function", "video");

        Intent donate_intent = new Intent(this, DonateActivity.class);
        donate_intent.setAction(Intent.ACTION_VIEW);

        Intent main_user_head_intent = new Intent(this, MainActivity.class);
        main_user_head_intent.setAction(Intent.ACTION_VIEW);
        main_user_head_intent.putExtra("function", "user");

        Intent user_head_intent = new Intent(this, HeadActivity.class);
        user_head_intent.setAction(Intent.ACTION_VIEW);

        ShortcutInfo video = new ShortcutInfo.Builder(this, "video")
                .setShortLabel(getResources().getString(R.string.video_title))
                .setLongLabel(getResources().getString(R.string.video_title))
                .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_video))
                .setIntent(video_intent)
                .build();
        infos.add(video);

        ShortcutInfo donate = new ShortcutInfo.Builder(this, "donate")
                .setShortLabel(getResources().getString(R.string.donate_title))
                .setLongLabel(getResources().getString(R.string.donate_title))
                .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_donate))
                .setIntents(new Intent[]{main_intent, donate_intent})
                .build();
        infos.add(donate);

        if (UserUtil.isUserLogin()) {
            ShortcutInfo user_head = new ShortcutInfo.Builder(this, "user_head")
                    .setShortLabel(getResources().getString(R.string.profile_photo))
                    .setLongLabel(getResources().getString(R.string.profile_photo))
                    .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_user_head))
                    .setIntents(new Intent[]{main_user_head_intent, user_head_intent})
                    .build();
            infos.add(user_head);
        }
        Objects.requireNonNull(mShortcutManager).setDynamicShortcuts(infos);
    }
}

