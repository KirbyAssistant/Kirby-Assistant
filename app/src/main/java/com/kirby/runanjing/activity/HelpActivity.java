package com.kirby.runanjing.activity;

import android.os.*;
import android.support.v7.widget.*;
import android.widget.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.utils.*;

import android.support.v7.widget.Toolbar;
import com.kirby.runanjing.R;

@ParallaxBack
public class HelpActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_help);
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		TextView helpText=(TextView)findViewById(R.id.helpText);
		StringBuilder help=new StringBuilder();
		help.append("感谢您使用Kirby Assistant，这是一个针对如何在手机上玩星之卡比而开发的软件，您可以在这里轻松下载到很多经典的星之卡比游戏，同时我们也准备了可以运行游戏的模拟器软件，可以方便的运行游戏文件。如果你喜欢该软件，可以到酷安给我们一个五星好评，或者用支付宝/微信小小的支持一下我们。你们的支持是我制作软件的最大动力！\n");
		help.append("以下我们接收到的反馈最多的一些问题，您或许可以在这里获得一些有用的帮助:\n");
		help.append("\n");
		help.append("Q:这个软件用户账号有什么用处？\n");
		help.append("A:目前如果登录用户账号可以使用软件中的“闲聊”功能，但目前还没有更多与用户账户相关的功能，还请期待今后的版本。\n");
		help.append("\n");
		help.append("Q:为什么我下载文件/软件时出现了问题（无法下载)？\n");
		help.append("A:1）请确保您的设备已经开启互联网相关服务并链接上互联网，并检查是否授权本软件允许在当前网络下联网。\n");
		help.append("2）请确保您的设备授权本软件使用储存/sd卡相关权限，我们需要这些权限下载游戏相关的文件并保存在您的设备中。\n");
		helpText.setText(help);
	}
}
