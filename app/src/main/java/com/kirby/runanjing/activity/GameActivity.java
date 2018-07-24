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

@ParallaxBack
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
			.placeholder(R.drawable.ic_download)
			.error(R.drawable.ic_close_circle_outline)
			.into(game_img);
			
			String gg="";
			for(int i=0;i<1000;i++){
				String aa="测试文本";
				gg = gg + aa;
			}
			game_js.setText(gg);
		download_button.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					switch(game_pos){
						case "gba_mzqdx"://"星之卡比 梦之泉DX":
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
							showDownloadDialog(game_name,  R.string.game_name, R.string.jp, R.string.us, R.string.nu, "https://eyun.baidu.com/s/3pKN6dIz", "https://eyun.baidu.com/s/3pKZHpaF", "");
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
