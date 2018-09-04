package com.kirby.runanjing.activity;

import android.content.*;
import android.os.*;
import android.support.v7.widget.*;
import android.widget.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.untils.*;

import android.support.v7.widget.Toolbar;
import com.kirby.runanjing.R;
import com.bumptech.glide.*;
import android.net.*;
import android.support.v7.app.*;
import android.view.View.*;
import android.view.*;

public class GameActivity extends BaseActivity
{
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_game);
		//配置toolbar
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		Intent game=getIntent();
		final String game_name=game.getStringExtra("game_name");
		String game_img_url=game.getStringExtra("game_img");
		final String game_pos=game.getStringExtra("game_pos");
		getSupportActionBar().setTitle(game_name);
		//Toast.makeText(this,game_name,Toast.LENGTH_SHORT).show();
		ImageView game_img=(ImageView)findViewById(R.id.game_img);
		TextView game_js=(TextView)findViewById(R.id.game_js);
		Button download_button=(Button)findViewById(R.id.download_button);
		Glide
			.with(this)
			.load(game_img_url)
			//.apply(Kirby.getGlideRequestOptions())
			//.placeholder(R.drawable.ic_kirby_download)
			//.error(R.drawable.ic_kirby_load_fail)
			.into(game_img);
			
		download_button.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					switch (game_pos)
					{
						case "gba_mzqdx"://"星之卡比 梦之泉DX":
							Intent gg=new Intent(GameActivity.this,KirbyWebActivity.class);
							startActivity(gg);
							showDownloadDialog(game_name, R.string.game_name, R.string.jp, R.string.us, R.string.zh, "https://eyun.baidu.com/s/3kURIBIZ", "https://eyun.baidu.com/s/3o86TXDS", "https://eyun.baidu.com/s/3dF22BWP");
							break;
						case "gba_jm"://"星之卡比 镜之大迷宫":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.zh, "https://eyun.baidu.com/s/3hs7Mjsg", "https://eyun.baidu.com/s/3c5qBl8", "https://eyun.baidu.com/s/3i5t6Z3J");
							break;
						case "sfc_x3"://"星之卡比 3":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3pKTD8EZ", "https://eyun.baidu.com/s/3gfwui2n", "");
							break;
						case "sfc_kss"://"星之卡比 超豪华版":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3qXEc4Xm", "https://eyun.baidu.com/s/3nu8IVpv", "");
							break;
						case "sfc_mhd"://"星之卡比 卡比梦幻都":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3hsvCjfI", "https://eyun.baidu.com/s/3jHCmNps", "");
							break;
						case "sfc_toybox"://"星之卡比 玩具箱合集":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.nu, R.string.nu, "https://eyun.baidu.com/s/3qZr1yry", "", "");
							break;
						case "sfc_mfqp"://"[仅美国]星之卡比 卡比魔方气泡":
							showDownloadDialog(game_name, R.string.game_name1, R.string.us , R.string.nu ,  R.string.nu, "https://eyun.baidu.com/s/3eSuusSi", "", "");
							break;
						case "sfc_bsxdx"://"[仅日本]星之卡比 卡比宝石星DX":
							showDownloadDialog(game_name, R.string.game_name2, R.string.jp, R.string.nu, R.string.nu, "https://eyun.baidu.com/s/3kVDhaS3", "", "");
							break;
						case "n64_k64"://"星之卡比 64":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3jHPKdMY", "https://eyun.baidu.com/s/3jHPKdMY", "");
							break;
						case "ngc_ft"://"星之卡比 飞天赛车":
							showDownloadDialog(game_name,  R.string.game_name, R.string.us, R.string.nu, R.string.nu, "https://eyun.baidu.com/s/3qYAoXGC", "", "");
							break;
						case "wii_cf"://"星之卡比 重返梦幻岛":
							showDownloadDialog(game_name, R.string.game_name, R.string.jp, R.string.us, R.string.zh, "https://eyun.baidu.com/s/3skEbla1", "https://eyun.baidu.com/s/3gf5Oxe7", "https://eyun.baidu.com/s/3gfqpuin");
							break;
						case "wii_mx"://"星之卡比 毛线卡比":
							showDownloadDialog(game_name, R.string.game_name, R.string.jp, R.string.us, R.string.zh, "https://eyun.baidu.com/s/3i5UCDpz", "https://eyun.baidu.com/s/3dFACfWd", "https://eyun.baidu.com/s/3eRYayD8");
							break;
						case "nds_cm"://"星之卡比 触摸卡比":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.zh, "https://eyun.baidu.com/s/3hsqS3S4", "https://eyun.baidu.com/s/3c27V89i", "https://eyun.baidu.com/s/3i5Pwsxn");
							break;
						case "nds_kssu"://"星之卡比 超究豪华版":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.zh, "https://eyun.baidu.com/s/3i4Ricbb", "https://eyun.baidu.com/s/3nvCwXlB", "https://eyun.baidu.com/s/3c2EblZi");
							break;
						case "nds_nht"://"星之卡比 呐喊团":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.zh, "https://eyun.baidu.com/s/3bo4Z5TH", "https://eyun.baidu.com/s/3czmilC", "https://eyun.baidu.com/s/3hr4PxbA");
							break;
						case "nds_jh"://"星之卡比 集合！卡比":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.zh, "https://eyun.baidu.com/s/3geO4mbx", "https://eyun.baidu.com/s/3eSijdHS", "https://eyun.baidu.com/s/3o80PA6e");
							break;
						case "gb_x1"://"星之卡比 1":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3pKN6dIz", "https://eyun.baidu.com/s/3miPUVES", "");
							break;
						case "gb_x2"://"星之卡比 2":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3i57Kjjv", "https://eyun.baidu.com/s/3jI4urlW", "");
							break;
						case "gb_bsx"://"星之卡比 卡比宝石星":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3miFgbtI", "https://eyun.baidu.com/s/3nvtzunn", "");
							break;
						case "gb_dzk"://"星之卡比 卡比打砖块":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3i5Dkqah", "https://eyun.baidu.com/s/3ge7808r", "");
							break;
						case "gb_dzt"://"星之卡比 卡比弹珠台":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3i48QqMh", "https://eyun.baidu.com/s/3eSwv1DK", "");
							break;
						case "gbc_gg"://"星之卡比 滚滚卡比":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3pKP9eav", "https://eyun.baidu.com/s/3nuQZavJ", "");
							break;
						case "fc_mzq"://"星之卡比 梦之泉物语":
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3pKXFx8n", "https://eyun.baidu.com/s/3pKZHpaF", "https://eyun.baidu.com/s/3i4HC8FN");
							break;
					}
				}
			});
		StringBuffer game_js_text=new StringBuffer();
		switch (game_pos)
		{
			case "gba_mzqdx"://"星之卡比 梦之泉DX":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.gba_mzqdx_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.gba_mzqdx_js)).append("\n");
				break;
			case "gba_jm"://"星之卡比 镜之大迷宫":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.gba_jm_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.gba_jm_js)).append("\n");
				break;
			case "sfc_x3"://"星之卡比 3":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_x3_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_x3_js)).append("\n");
				break;
			case "sfc_kss"://"星之卡比 超豪华版":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_kss_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_kss_js)).append("\n");
				break;
			case "sfc_mhd"://"星之卡比 卡比梦幻都":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_mhd_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_mhd_js)).append("\n");
				break;
			case "sfc_toybox"://"星之卡比 玩具箱合集":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_toybox_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_toybox_js)).append("\n");
				break;
			case "sfc_mfqp"://"[仅美国]星之卡比 卡比魔方气泡":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_mfqp_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_mfqp_js)).append("\n");
				break;
			case "sfc_bsxdx"://"[仅日本]星之卡比 卡比宝石星DX":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_bsxdx_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.sfc_bsxdx_js)).append("\n");
				break;
			case "n64_k64"://"星之卡比 64":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.n64_64_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.n64_64_js)).append("\n");
				break;
			case "ngc_ft"://"星之卡比 飞天赛车":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.ngc_ft_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.ngc_ft_js)).append("\n");
				break;
			case "wii_cf"://"星之卡比 重返梦幻岛":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.wii_cf_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.wii_cf_js)).append("\n");
				break;
			case "wii_mx"://"星之卡比 毛线卡比":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.wii_mx_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.wii_mx_js)).append("\n");
				break;
			case "nds_cm"://"星之卡比 触摸卡比":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.nds_cm_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.nds_cm_js)).append("\n");
				break;
			case "nds_kssu"://"星之卡比 超究豪华版":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.nds_kssu_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.nds_kssu_js)).append("\n");
				break;
			case "nds_nht"://"星之卡比 呐喊团":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.nds_nht_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.nds_nht_js)).append("\n");
				break;
			case "nds_jh"://"星之卡比 集合！卡比":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.nds_jh_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.nds_jh_js)).append("\n");
				break;
			case "gb_x1"://"星之卡比 1":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_x1_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_x1_js)).append("\n");
				break;
			case "gb_x2"://"星之卡比 2":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_x2_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_x2_js)).append("\n");
				break;
			case "gb_bsx"://"星之卡比 卡比宝石星":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_bsx_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_bsx_js)).append("\n");
				break;
			case "gb_dzk"://"星之卡比 卡比打砖块":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_dzk_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_dzk_js)).append("\n");
				break;
			case "gb_dzt"://"星之卡比 卡比弹珠台":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_dzt_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.gb_dzt_js)).append("\n");
				break;
			case "gbc_gg"://"星之卡比 滚滚卡比":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.gbc_gg_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.gbc_gg_js)).append("\n");
				break;
			case "fc_mzq"://"星之卡比 梦之泉物语":
				game_js_text.append(getResources().getString(R.string.bj)).append("\n");
				game_js_text.append(getResources().getString(R.string.fc_mzq_bj)).append("\n").append("\n");
				game_js_text.append(getResources().getString(R.string.js)).append("\n");
				game_js_text.append(getResources().getString(R.string.fc_mzq_js)).append("\n");
				break;
		}
		game_js.setText(game_js_text);
	}
	public void showDownloadDialog(String name, int mess, Integer pos, Integer neg, Integer neu, final String pos_url, final String neg_url, final String neu_url)
	{
		AlertDialog.Builder dialog = new
			AlertDialog.Builder(this)
			.setTitle(name)
			.setMessage(mess)
			.setPositiveButton(pos, new
			DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Intent web = new Intent();        
					web.setAction("android.intent.action.VIEW");    
					Uri content_url = Uri.parse(pos_url);   
					web.setData(content_url);  
					startActivity(web); 
				}
			}
		)
			.setNegativeButton(neg, new DialogInterface.OnClickListener()
			{
				@Override

				public void onClick(DialogInterface dialog, int which)
				{
					Intent web = new Intent();        
					web.setAction("android.intent.action.VIEW");    
					Uri content_url = Uri.parse(neg_url);   
					web.setData(content_url);  
					startActivity(web);  							
				}
			}
		)
			.setNeutralButton(neu, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Intent web = new Intent();        
					web.setAction("android.intent.action.VIEW");    
					Uri content_url = Uri.parse(neu_url);   
					web.setData(content_url);  
					startActivity(web);  			
				}
			}
		);
		dialog.show();
	}
}
