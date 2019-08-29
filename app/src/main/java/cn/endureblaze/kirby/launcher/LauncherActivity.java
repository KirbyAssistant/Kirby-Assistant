package cn.endureblaze.kirby.launcher;

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
import androidx.annotation.RequiresApi;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseActivity;
import cn.endureblaze.kirby.main.MainActivity;
import cn.endureblaze.kirby.main.donate.DonateActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LauncherActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        //设置状态栏为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setStatusBarColor(Color.TRANSPARENT);
            //设置window的状态栏不可见
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);

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


        ShortcutInfo video = new ShortcutInfo.Builder(this, "video")
                .setShortLabel(getResources().getString(R.string.title_video))
                .setLongLabel(getResources().getString(R.string.title_video))
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

        Objects.requireNonNull(mShortcutManager).setDynamicShortcuts(infos);
    }
}
