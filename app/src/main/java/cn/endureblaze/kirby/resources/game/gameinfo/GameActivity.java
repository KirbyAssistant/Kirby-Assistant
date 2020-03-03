package cn.endureblaze.kirby.resources.game.gameinfo;

import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import cn.ednureblaze.glidecache.GlideCache;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseActivity;
import cn.endureblaze.kirby.util.PermissionUtils;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.Objects;

public class GameActivity extends BaseActivity {
    private FloatingActionButton download_button;
    private String game_name;
    private String game_tag;

    private String JP_VERSION;
    private String US_VERSION;
    private String CN_VERSION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        BottomAppBar bottom_bar = findViewById(R.id.game_bottom_bar);
        setSupportActionBar(bottom_bar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        JP_VERSION = this.getString(R.string.dia_jp);
        US_VERSION = this.getString(R.string.dia_us);
        CN_VERSION = this.getString(R.string.dia_cn);

        Intent game = getIntent();
        game_name = game.getStringExtra("game_name");
        String game_img_url = game.getStringExtra("game_img");
        game_tag = game.getStringExtra("game_tag");
        TextView game_title = findViewById(R.id.game_bottom_bar_title);
        game_title.setText(game_name);
        ImageView game_img = findViewById(R.id.game_img);
        final ImageView blur_game_img = findViewById(R.id.blur_game_img);
        TextView game_js = findViewById(R.id.game_js);
        //下載按鈕
        download_button = findViewById(R.id.game_download_fab);
        GlideCache.setNormalImageViaGlideCache(GameActivity.this, game_img, game_img_url);
        GlideCache.setBlurImageViaGlideCache(GameActivity.this, blur_game_img, game_img_url, "5");
        initDownload();
        //initInfo();
        CardView image_card = findViewById(R.id.image_cardview);
        image_card.setOnLongClickListener(view -> {
            return false;
        });
    }

    private void initDownload() {
        download_button.setOnClickListener(p1 -> {
            switch (Objects.requireNonNull(game_tag)) {
                case "gba_mzqdx"://"星之卡比 梦之泉DX"：
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION, CN_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3kURIBIZ", "https://eyun.baidu.com/s/3o86TXDS", "https://eyun.baidu.com/s/3dF22BWP"});
                    break;
                case "gba_jm"://"星之卡比 镜之大迷宫":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION, CN_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3hs7Mjsg", "https://eyun.baidu.com/s/3c5qBl8", "https://eyun.baidu.com/s/3i5t6Z3J"});
                    break;
                case "sfc_x3"://"星之卡比 3":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3pKTD8EZ", "https://eyun.baidu.com/s/3gfwui2n"});
                    break;
                case "sfc_kss"://"星之卡比 超豪华版":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3qXEc4Xm", "https://eyun.baidu.com/s/3nu8IV"});
                    break;
                case "sfc_mhd"://"星之卡比 卡比梦幻都":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3hsvCjfI", "https://eyun.baidu.com/s/3jHCmNps"});
                    break;
                case "sfc_toybox"://"星之卡比 玩具箱合集":
                    showDownloadDialog(game_name, new String[]{JP_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3qZr1yry"});
                    break;
                case "sfc_mfqp"://"[仅美国]星之卡比 卡比魔方气泡":
                    showDownloadDialog(game_name, new String[]{US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3eSuusSi"});
                    break;
                case "sfc_bsxdx"://"[仅日本]星之卡比 卡比宝石星DX":
                    showDownloadDialog(game_name, new String[]{JP_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3kVDhaS3"});
                    break;
                case "n64_k64"://"星之卡比 64":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3jHPKdMY", "https://eyun.baidu.com/s/3jHPKdMY"});
                    break;
                case "ngc_ft"://"星之卡比 飞天赛车":
                    showDownloadDialog(game_name, new String[]{US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3qYAoXGC"});
                    break;
                case "wii_cf"://"星之卡比 重返梦幻岛":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION, CN_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3skEbla1", "https://eyun.baidu.com/s/3gf5Oxe7", "https://eyun.baidu.com/s/3gfqpuin"});
                    break;
                case "wii_mx"://"星之卡比 毛线卡比":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION, CN_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3i5UCDpz", "https://eyun.baidu.com/s/3dFACfWd", "https://eyun.baidu.com/s/3eRYayD8"});
                    break;
                case "nds_cm"://"星之卡比 触摸卡比":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION, CN_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3hsqS3S4", "https://eyun.baidu.com/s/3c27V89i", "https://eyun.baidu.com/s/3i5Pwsxn"});
                    break;
                case "nds_kssu"://"星之卡比 超究豪华版":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION, CN_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3i4Ricbb", "https://eyun.baidu.com/s/3nvCwXlB", "https://eyun.baidu.com/s/3c2EblZi"});
                    break;
                case "nds_nht"://"星之卡比 呐喊团":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION, CN_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3bo4Z5TH", "https://eyun.baidu.com/s/3czmilC", "https://eyun.baidu.com/s/3hr4PxbA"});
                    break;
                case "nds_jh"://"星之卡比 集合！卡比":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION, "「那个」汉化组 " + CN_VERSION, CN_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3geO4mbx", "https://eyun.baidu.com/s/3eSijdHS", "https://eyun.baidu.com/s/3gfXg9JP", "https://eyun.baidu.com/s/3o80PA6e"});
                    break;
                case "gb_x1"://"星之卡比 1":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3pKN6dIz", "https://eyun.baidu.com/s/3miPUVES"});
                    break;
                case "gb_x2"://"星之卡比 2":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3i57Kjjv", "https://eyun.baidu.com/s/3jI4urlW"});
                    break;
                case "gb_bsx"://"星之卡比 卡比宝石星":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3miFgbtI", "https://eyun.baidu.com/s/3nvtzunn"});
                    break;
                case "gb_dzk"://"星之卡比 卡比打砖块":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3i5Dkqah", "https://eyun.baidu.com/s/3ge7808r"});
                    break;
                case "gb_dzt"://"星之卡比 卡比弹珠台":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3i48QqMh", "https://eyun.baidu.com/s/3eSwv1DK"});
                    break;
                case "gbc_gg"://"星之卡比 滚滚卡比":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3pKP9eav", "https://eyun.baidu.com/s/3qZZMtZY"});
                    break;
                case "fc_mzq"://"星之卡比 梦之泉物语":
                    showDownloadDialog(game_name, new String[]{JP_VERSION, US_VERSION, CN_VERSION},
                            new String[]{"https://eyun.baidu.com/s/3pKXFx8n", "https://eyun.baidu.com/s/3pKZHpaF", "https://eyun.baidu.com/s/3i4HC8FN"});
                    break;
            }
        });
    }

    //显示下载窗口
    private void showDownloadDialog(String name, String[] version, String[] url) {
        new AlertDialog.Builder(this)
                .setTitle(name)
                .setItems(version, (dialogInterface, i) -> {
                    Intent web = new Intent();
                    web.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url[i]);
                    web.setData(content_url);
                    startActivity(web);
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_bottom_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            sendKeyCode(KeyEvent.KEYCODE_BACK);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 传入需要的键值即可
     *
     * @param keyCode 模拟按键的keyCode
     */
    private void sendKeyCode(final int keyCode) {
        new Thread() {
            public void run() {
                try {
                    //调用内部方法模拟按键按下的过程
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            }
        }.start();
    }

    public void saveImage(String game_img_url) {
        Bitmap bitmap = GlideCache.getGlideBitmap(this, game_img_url);
        String fileName = game_name;
        //系统相册目录
        File mPicDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "KirbyAssistant");
        mPicDir.mkdirs();
        String mPicPath = new File(mPicDir, fileName).getAbsolutePath();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.ImageColumns.TITLE, game_name);
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, game_name);
        contentValues.put(MediaStore.Images.ImageColumns.DATE_TAKEN, System.currentTimeMillis());
        contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "Picture/KirbyAssistant");
        } else {
            contentValues.put(MediaStore.Images.ImageColumns.DATA, mPicPath);
        }
        //插入数据，返回所插入数据对应的Uri
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        this.sendBroadcast(new Intent("com.android.camera.NEW_PICTURE", uri));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
