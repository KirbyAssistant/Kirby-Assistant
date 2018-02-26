package com.kirby.runanjing.activity;


import android.content.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.jaeger.library.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.adapter.*;
import com.kirby.runanjing.untils.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import com.kirby.runanjing.R;
import com.kirby.runanjing.bean.*;
import com.github.anzewei.parallaxbacklayout.*;

@ParallaxBack
public class JszActivity extends BaseActivity
{
	//金手指数据
	Jsz []镜迷_金手指={new Jsz("正常", "02020FE3 00"),new Jsz("喷火", "02020FE3 01"),new Jsz("冷冻", "02020FE3 02"),new Jsz("回力镖", "02020FE3 06"),new Jsz("烈焰", "02020FE3 03"),new Jsz("轮胎", "02020FE3 04"),new Jsz("射线", "02020FE3 07"),new Jsz("锤子", "02020FE3 11"),new Jsz("石头", "02020FE3 08"),new Jsz("炸弹", "02020FE3 09"),new Jsz("不明", "02020FE3 0B"),new Jsz("厨师", "02020FE3 0C"),new Jsz("镭射", "02020FE3 0D"),new Jsz("闪电", "02020FE3 0F"),new Jsz("旋风", "02020FE3 10"),new Jsz("射箭", "02020FE3 13"),new Jsz("剑士", "02020FE3 12"),new Jsz("格斗", "02020FE3 14"),new Jsz("魔术师", "02020FE3 15"),new Jsz("万能拳", "02020FE3 16"),new Jsz("强力吸收", "02020FE3 0A"),new Jsz("雨伞", "02020FE3 05"),new Jsz("爆炸", "02020FE3 18"),new Jsz("导弹", "02020FE3 19"),new Jsz("万能剑", "02020FE3 1A"),new Jsz("ufo飞碟", "02020FE3 0E"),new Jsz("体力", " 02020FE0 0F "),new Jsz("体力上限", " 02020FE1 0F "),new Jsz("人数", " 02020FE2 63 "),new Jsz("电话", " 02020FBC 03 "),new Jsz("无敌 ", "02020F3C 06"),new Jsz("BOSS一击必杀 ", "02000088 00"),new Jsz("镜片全满", " 02038970 FF"),new Jsz("传送门全开1", "42028C14 0001"), new Jsz("", "0000000F 0004")};
	Jsz []梦之泉_金手指={new Jsz("", "C:0598:ff:01:麦克使用回数满"),new Jsz("", "C:05e3:00:01:火炎"),new Jsz("", "C:05e3:01:01:火花"),new Jsz("", "C:05e3:02:01:回力标"),new Jsz("", "C:05e3:03:01:剑"),new Jsz("", "C:05e3:04:01:烈焰"),new Jsz("", "C:05e3:05:01:镭射"),new Jsz("", "C:05e3:06:01:麦克风"),new Jsz("", "C:05e3:07:01:车轮"),new Jsz("", "C:05e3:08:01:木槌"),new Jsz("", "C:05e3:09:01:阳伞"),new Jsz("", "C:05e3:0a:01:睡觉"),new Jsz("", "C:05e3:0b:01:刺"),new Jsz("", "C:05e3:0c:01:冷气"),new Jsz("", "C:05e3:0d:01:冻结"),new Jsz("", "C:05e3:0e:01:高跳"),new Jsz("", "C:05e3:0f:01:射线"),new Jsz("", "C:05e3:10:01:岩石"),new Jsz("", "C:05e3:11:01:球球"),new Jsz("", "C:05e3:12:01:旋风"),new Jsz("", "C:05e3:13:01:巨爆"),new Jsz("", "C:05e3:14:01:光明"),new Jsz("", "C:05e3:15:01:摔角"),new Jsz("", "C:05e3:16:01:投掷"),new Jsz("", "C:05e3:17:01:UFO"),new Jsz("", "C:05e3:18:01:星杖"),new Jsz("", "C:05e3:ff:01:锁死为原型"),new Jsz("", "C:05e7:02:01:混合"),new Jsz("", "C:05e0:0d:01:UFO"),new Jsz("", "C:05f9:14:01:无敌"),};
	Jsz []梦之泉DX_金手指={new Jsz("[命数]", ""),new Jsz("开启", "02007D48 63"),new Jsz("[生命]", ""),new Jsz("开启", "02005588 14"),new Jsz("[无敌]", ""),new Jsz("开启", "03002182 63"),new Jsz("[变身]", ""),new Jsz("永远普通的卡比", "0300217D 00"), new Jsz("火焰卡比", "0300217D 01"),new Jsz("闪电卡比", "0300217D 02"),new Jsz("回旋标卡比", "0300217D 03"),new Jsz("剑之卡比", "0300217D 04"),new Jsz("火焰球卡比", "0300217D 05"),new Jsz("激光卡比", "0300217D 06"),new Jsz("唱歌卡比", "0300217D 07"),new Jsz("车轮卡比", "0300217D 08"),new Jsz("锤子卡比", "0300217D 09"),new Jsz("雨伞卡比", "0300217D 0A"),new Jsz("睡觉卡比", "0300217D 0B"),new Jsz("刺猬卡比", "0300217D 0C"),new Jsz("冷气卡比", "0300217D 0D"),new Jsz("冷冻卡比", "0300217D 0E"),new Jsz("超人卡比", "0300217D 0F"),new Jsz("射线卡比", "0300217D 10"),new Jsz("石头卡比", "0300217D 11"),new Jsz("球球卡比", "0300217D 12"),new Jsz("旋风卡比", "0300217D 13"),new Jsz("巨爆卡比", "0300217D 14"),new Jsz("光明卡比", "0300217D 15"),new Jsz("摔跤卡比", "0300217D 16"),new Jsz("投掷卡比", "0300217D 17"),new Jsz("飞碟(UFO)卡比", "0300217D 18"),new Jsz("星之卡比星剑士", "0300217D 19"),new Jsz("暗夜BUG版1", "0300217D 1A"), new Jsz("暗夜BUG版2", "0300217D 1B"),  new Jsz("[使用唱歌卡比时须补填的内容]", ""), new Jsz("3级喇叭", "0300217E 03"), new Jsz("2级声波", "0300217E 02"),new Jsz("[计时赛时间(秒)]  锁定0", "02270556 00"),  new Jsz("[计时赛时间(分)]  锁定0", "02270555 00"),  new Jsz("[计时赛时间(时)]  锁定0", "02270554 00"),  new Jsz("[无限命数]", ""),  new Jsz("计时赛,BOSS赛也能", "02261158 63"),  new Jsz("[无限生命]", ""),new Jsz("普通赛", "022CAB28 34"),  new Jsz("计时赛", "025CAB68 34"),new Jsz("BOSS赛", "022CDBE8 34"),new Jsz("[无敌金手指修正版（由zhanghm11111修正]", ""),new Jsz("", "030021AF 01"),new Jsz("", "03002182 01"),new Jsz("[无敌金手指（和平模式直接穿过小怪)]", ""),new Jsz("", "030021AF 02"),new Jsz("", "03002182 01"),};
	private List<Jsz>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		Theme.setClassTheme(this);
		setContentView(R.layout.activity_jsz);
		//配置toolbar
		final Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		//设置List的适配器
		JszAdapter adapter=new JszAdapter(JszActivity.this, R.layout.item_jsz, list);
		ListView listview=(ListView)findViewById(R.id.jsz_listview);
		listview.setAdapter(adapter);
		SharedPreferences console=getSharedPreferences("string", 0);
		final String name= console.getString("金手指_游戏", "");
		//获取数据并遍历
		if (name == "星之卡比 镜之大迷宫")
		{
			int index = 0;
			while (index < 镜迷_金手指.length)
			{       	
				list.add(镜迷_金手指[index++]);
			}
		}
		if(name=="星之卡比 梦之泉物语"){
			int index = 0;
			while (index < 梦之泉_金手指.length)
			{       	
				list.add(梦之泉_金手指[index++]);
			}
		}
		if(name=="星之卡比 梦之泉DX"){
			int index = 0;
			while (index < 梦之泉DX_金手指.length)
			{       	
				list.add(梦之泉DX_金手指[index++]);
			}
		}
		if(name=="星之卡比 3"){
			
		}
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?>parent,View view,int position,long id){
				Jsz jsz=list.get(position);
				String q=jsz.getJsz();
				ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				cm.setText(q);
				Toast.makeText(JszActivity.this,getResources().getString(R.string.jsz_copy),Toast.LENGTH_SHORT).show();
			}
		});
	}
}
