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
		String game_name=game.getStringExtra("game_name");
		String game_img_url=game.getStringExtra("game_img");
		getSupportActionBar().setTitle(game_name);
		//Toast.makeText(this,game_name,Toast.LENGTH_SHORT).show();
		ImageView game_img=(ImageView)findViewById(R.id.game_img);
		
		Glide
			.with(this)
			.load(game_img_url)
			.placeholder(R.drawable.ic_download)
			.error(R.drawable.ic_close_circle_outline)
			.into(game_img);
	}
}
