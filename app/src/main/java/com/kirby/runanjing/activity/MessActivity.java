package com.kirby.runanjing.activity;

import android.content.*;
import android.os.*;
import android.support.v7.widget.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bmob.*;
import com.kirby.runanjing.untils.*;
import java.util.*;

import android.support.v7.widget.Toolbar;
import com.kirby.runanjing.R;
import android.util.*;
import com.bumptech.glide.*;

@ParallaxBack
public class MessActivity extends BaseActivity
{

	public static String USER_NAME="user_name";
	public static String MESS="mess";
	public static String TIME="time";

	private String s_user_name;

	private String s_time;

	private String s_mess;

	private ImageView userHeadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_message);
		Intent intent = getIntent();
		s_user_name = intent.getStringExtra(USER_NAME);
		s_time = intent.getStringExtra(TIME);
		s_mess = intent.getStringExtra(MESS);
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		TextView user_name=(TextView)findViewById(R.id.userName);
		TextView mess=(TextView)findViewById(R.id.mess);
		TextView time=(TextView)findViewById(R.id.time);
		user_name.setText(s_user_name);
		mess.setText(s_mess);
		time.setText(s_time);
		userHeadImage = (ImageView)findViewById(R.id.user_head);

		BmobQuery<MyUser> user=new BmobQuery<>();
		user.addWhereEqualTo("username", s_user_name);
		user.findObjects(new FindListener<MyUser>(){

				@Override
				public void done(List<MyUser> p1, BmobException p2)
				{
					if (p2 == null)
					{
						Message message = userHandler.obtainMessage();
						message.what = 0;
						//以消息为载体
						message.obj = p1;//这里的list就是查询出list
						//向handler发送消息
						userHandler.sendMessage(message);
					}
					else
					{
						Toast.makeText(MessActivity.this, p2.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
	private Handler userHandler=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 0:
					List<MyUser> list= (List<MyUser>)msg.obj;
					for (MyUser m : list)
					{
						String userHeadUrl=m.getUserHead().getFileUrl();
						Toast.makeText(MessActivity.this, userHeadUrl, Toast.LENGTH_SHORT).show();
						Glide
							.with(MessActivity.this)
							.load(userHeadUrl)
							.placeholder(R.drawable.ic_download)
							.error(R.drawable.ic_close_circle_outline)
							.into(userHeadImage);
					}			
					break;
			}
		}
	};
}
