package cn.endureblaze.kirby.resources.game.gamelist;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseActivity;
import cn.endureblaze.kirby.bean.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameListActivity extends BaseActivity {
    private List<Console> game_list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        //配置toolbar
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.title_game_list);
        //配置列表
        RecyclerView r= findViewById(R.id.game_list);
        GridLayoutManager layoutManager=new GridLayoutManager(this, 1);
        r.setLayoutManager(layoutManager);
        GameListAdapter adapter = new GameListAdapter(game_list, this);
        r.setAdapter(adapter);
        //获取数据
        Intent intent=getIntent();
        String game=intent.getStringExtra("console_name");
        toolbar.setSubtitle(game);
        //判断数据然后处理列表
        if (Objects.requireNonNull(game).endsWith("gba"))
        {
            Console[] game_data = {
                    new Console("星之卡比 梦之泉DX", "https://api.endureblaze.cn/ka_image/game/mengzhiquandx.jpg","gba_mzqdx"),
                    new Console("星之卡比 镜之大迷宫", "https://api.endureblaze.cn/ka_image/game/jingmi.jpg","gba_jm"),
            };
            int index = 0;
            while (index < game_data.length)
            {
                game_list.add(game_data[index++]);
            }
        }
        if (game.equals("gb"))
        {
            Console[]game_data={
                    new Console("星之卡比 1", "https://api.endureblaze.cn/ka_image/game/xing1.jpg","gb_x1"),
                    new Console("星之卡比 2", "https://api.endureblaze.cn/ka_image/game/xing2.jpg","gb_x2"),
                    new Console("星之卡比 卡比宝石星", "https://api.endureblaze.cn/ka_image/game/baoshixing.jpg","gb_bsx"),
                    new Console("星之卡比 卡比打砖块", "https://api.endureblaze.cn/ka_image/game/dazhuankuai.jpg","gb_dzk"),
                    new Console("星之卡比 卡比弹珠台", "https://api.endureblaze.cn/ka_image/game/danzhutai.jpg","gb_dzt"),
            };
            int index = 0;
            while (index < game_data.length)
            {
                game_list.add(game_data[index++]);
            }
        }
        if(game.endsWith("gbc")){
            Console[]game_data={
                    new Console("星之卡比 滚滚卡比", "https://api.endureblaze.cn/ka_image/game/gungun.jpg","gbc_gg"),
            };
            int index = 0;
            while (index < game_data.length)
            {
                game_list.add(game_data[index++]);
            }
        }
        if (game.equals("sfc"))
        {
            Console[] game_data = {
                    new Console("星之卡比 3", "https://api.endureblaze.cn/ka_image/game/xing3.jpg","sfc_x3"),
                    new Console("星之卡比 超豪华版", "https://api.endureblaze.cn/ka_image/game/kss.jpg","sfc_kss"),
                    new Console("星之卡比 卡比梦幻都", "https://api.endureblaze.cn/ka_image/game/menghuandu.jpg","sfc_mhd"),
                    new Console("星之卡比 玩具箱合集", "https://api.endureblaze.cn/ka_image/game/toybox.jpg","sfc_toybox"),
                    new Console("[仅美国]星之卡比 卡比魔方气泡", "https://api.endureblaze.cn/ka_image/game/mofangqipao.jpg","sfc_mfqp"),
                    new Console("[仅日本]星之卡比 卡比宝石星DX", "https://api.endureblaze.cn/ka_image/game/baoshixingdx.jpg","sfc_bsxdx"),
            };
            int index = 0;
            while (index < game_data.length)
            {
                game_list.add(game_data[index++]);
            }
        }
        if (game.equals("n64"))
        {

            Console[] game_data = {
                    new Console("星之卡比 64", "https://api.endureblaze.cn/ka_image/game/k64.jpg","n64_k64"),
            };
            int index = 0;
            while (index < game_data.length)
            {
                game_list.add(game_data[index++]);
            }
        }
        if (game.equals("ngc"))
        {

            Console[] game_data = {
                    new Console("星之卡比 飞天赛车", "https://api.endureblaze.cn/ka_image/game/feitian.jpg","ngc_ft"),
            };
            int index = 0;
            while (index < game_data.length)
            {
                game_list.add(game_data[index++]);
            }
        }
        if (game.equals("wii"))
        {

            Console[] game_data = {
                    new Console("星之卡比 重返梦幻岛", "https://api.endureblaze.cn/ka_image/game/chongfan.jpg","wii_cf"),
                    new Console("星之卡比 毛线卡比", "https://api.endureblaze.cn/ka_image/game/maoxian.jpg","wii_mx"),
            };
            int index = 0;
            while (index < game_data.length)
            {
                game_list.add(game_data[index++]);
            }
        }
        if (game.equals("nds"))
        {

            Console[] game_data = {
                    new Console("星之卡比 触摸卡比", "https://api.endureblaze.cn/ka_image/game/chumo.jpg","nds_cm"),
                    new Console("星之卡比 超究豪华版", "https://api.endureblaze.cn/ka_image/game/kssu.jpg","nds_kssu"),
                    new Console("星之卡比 呐喊团", "https://api.endureblaze.cn/ka_image/game/nahantuan.jpg","nds_nht"),
                    new Console("星之卡比 集合！卡比", "https://api.endureblaze.cn/ka_image/game/jihe.jpg","nds_jh"),
            };
            int index = 0;
            while (index < game_data.length)
            {
                game_list.add(game_data[index++]);
            }
        }
        if (game.equals("fc"))
        {

            Console[] game_data = {
                    new Console("星之卡比 梦之泉物语", "https://api.endureblaze.cn/ka_image/game/mengzhiquan.jpg","fc_mzq"),
            };
            int index = 0;
            while (index < game_data.length)
            {
                game_list.add(game_data[index++]);
            }
        }
    }
}
