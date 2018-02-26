package com.kirby.runanjing.activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.kirby.runanjing.BaseActivity;
import com.kirby.runanjing.R;
import com.kirby.runanjing.adapter.MessageAdapter;
import com.kirby.runanjing.bean.Mess;
import com.kirby.runanjing.bmob.MessageBmob;
import com.kirby.runanjing.bmob.MyUser;
import com.kirby.runanjing.untils.Theme;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.ArrayList;
import java.util.List;

@ParallaxBack
public class UserActivity extends BaseActivity
{
	private CollapsingToolbarLayout coll;
	private List<Mess> messlist = new ArrayList<>();
	private MyUser u;
	private int messItem=0;
	private RecyclerView re;
	private RefreshLayout 刷新;
	private MessageAdapter adapter;

	private TextView user_id;

	private ImageView bg_image;

	private String name;

	private String email;

	private String id;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_user);
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		u = BmobUser.getCurrentUser(MyUser.class);
		coll = (CollapsingToolbarLayout)findViewById(R.id.折叠);
		user_id = (TextView)findViewById(R.id.user_id);
		bg_image = (ImageView)findViewById(R.id.bg_image);
		coll.setTitle(getUserName());
		user_id.setText(getUserId());
		bg_image.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					Toast.makeText(UserActivity.this, "图片", Toast.LENGTH_SHORT).show();
				}
			});
		initMess();
		getMessage();
	}
	private void initMess()
	{
//设置显示留言的列表
		re = (RecyclerView)findViewById(R.id.动态列表);
		GridLayoutManager layoutManager=new GridLayoutManager(this, 1);
		re.setLayoutManager(layoutManager);
		adapter = new MessageAdapter(messlist);	
	}
	public void  getMessage()
	{
		messlist.clear();//清空列表
		//使用BmobQuery获取留言数据
		BmobQuery<MessageBmob> query=new BmobQuery<MessageBmob>();
		query.order("-createdAt");//时间降序排列
		query.addWhereEqualTo("nickname", getUserName());
		query.findObjects(new FindListener<MessageBmob>() {

				@Override
				public void done(List<MessageBmob> list, BmobException e)
				{
					if (e == null)
					{
						Message message = messHandler.obtainMessage();
						message.what = 0;
						//以消息为载体
						message.obj = list;//这里的list就是查询出list
						//向handler发送消息
						messHandler.sendMessage(message);
						messItem = 20;
					}
					else
					{
						Log.e("bmob", "" + e);
						刷新.finishRefresh();
					}
				}
			});
	}
	private Handler messHandler=new Handler(){

		private String message;

		private boolean show_all;
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 0:
					List<MessageBmob> list= (List<MessageBmob>)msg.obj;
					for (MessageBmob m : list)
					{
						//从获取的数据中提取需要的数据
						String user=m.getNickname();
						String message_full=m.getMessage();
						if (message_full.length() > 40)
						{
							message = message_full.substring(0, 40) + "...";
							show_all = true;
						}
						else
						{
							message = message_full;
							show_all = false;
						}
						String time_=m.getCreatedAt();
						String time = time_.substring(0, 16);
						Mess mess=new Mess(user, message, time, message_full, show_all);
						//将查询到的数据依次添加到列表
						messlist.add(mess);
						//设置适配器
						re.setAdapter(adapter);
					}			
					break;
			}
		}
	};
	//初始化toolbar菜单
	public boolean onCreateOptionsMenu(Menu menu)
	{
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }
	@Override
	//获取toolbar菜单id执行事件
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.user:
				initUser();
				break;
		}
		return true;
	};
	private void initUser()
	{
		name = u.getUsername();
		email = u.getEmail();
		id = u.getObjectId();
		new AlertDialog.Builder(UserActivity.this)
			.setTitle(R.string.edit_email)
			.setMessage(getResources().getString(R.string.user_name) + name + "\n" + getResources().getString(R.string.user_email) + email)
			.setPositiveButton(R.string.user_email, new
			DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					LayoutInflater lay_1 = getLayoutInflater();
					final View modification_email_layout = lay_1.inflate(R.layout.dialog_modification_email, null);
					new AlertDialog.Builder(UserActivity.this)
						.setTitle(R.string.email_title)
						.setView(modification_email_layout) 
						.setPositiveButton(R.string.dia_yes, new
						DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								EditText 修改邮箱_原邮箱=(EditText)modification_email_layout.findViewById(R.id.修改邮箱_原邮箱);
								EditText 修改邮箱_新邮箱=(EditText)modification_email_layout.findViewById(R.id.修改邮箱_新邮箱);
								String edit_原邮箱=修改邮箱_原邮箱.getText().toString();
								String edit_新邮箱=修改邮箱_新邮箱.getText().toString();
								if (edit_原邮箱.isEmpty() || edit_新邮箱.isEmpty())
								{
									Toast.makeText(UserActivity.this, R.string.is_null, Toast.LENGTH_SHORT).show();
								}
								else
								{
									if (email.equals(edit_原邮箱))
									{
										MyUser 邮箱=new MyUser();
										邮箱.setEmail(edit_新邮箱);
										邮箱.update(id, new UpdateListener() {

												@Override
												public void done(BmobException e)
												{
													if (e == null)
													{
														Toast.makeText(UserActivity.this, R.string.edit_true, Toast.LENGTH_SHORT).show();
														u.logOut();
														finish();
														//open();
													}
													else
													{
														Toast.makeText(UserActivity.this, R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
													}
												}

											});
									}
									else
									{
										Toast.makeText(UserActivity.this, R.string.email_false, Toast.LENGTH_SHORT).show();
									}
								}
							}
						}
					)					
						.setNegativeButton(R.string.dia_cancel, null)
						.show();
				}
			}
		)
			.setNegativeButton(R.string.edit_password, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					LayoutInflater lay_2 = getLayoutInflater();
					final View modification_password_layout = lay_2.inflate(R.layout.dialog_modification_password, null);
					new AlertDialog.Builder(UserActivity.this)
						.setTitle(R.string.password_title)
						.setView(modification_password_layout) 
						.setPositiveButton(R.string.dia_yes, new
						DialogInterface.OnClickListener()
						{

							private int text;
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								EditText 修改密码_原密码=(EditText)modification_password_layout.findViewById(R.id.修改密码_原密码);
								EditText 修改密码_新密码=(EditText)modification_password_layout.findViewById(R.id.修改密码_新密码);
								EditText 修改密码_验证=(EditText)modification_password_layout.findViewById(R.id.修改密码_验证);
								String edit_原密码=修改密码_原密码.getText().toString();
								String edit_新密码=修改密码_新密码.getText().toString();
								String edit_验证=修改密码_验证.getText().toString();
								if (edit_原密码.isEmpty() || edit_新密码.isEmpty() || edit_验证.isEmpty())
								{
									Toast.makeText(UserActivity.this, R.string.is_null, Toast.LENGTH_SHORT).show();
								}
								else
								{
									if (edit_新密码.equals(edit_验证))
									{
										final MyUser pas = new MyUser();
										pas.updateCurrentUserPassword(edit_原密码, edit_新密码, new UpdateListener(){
												@Override
												public void done(BmobException e)
												{
													if (e == null)
													{
														Toast.makeText(UserActivity.this, R.string.edit_true, Toast.LENGTH_SHORT).show();
														u.logOut();
														finish();
														//open();
													}
													else
													{
														Toast.makeText(UserActivity.this, R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
													}
												}
											});
									}
									else
									{
										Toast.makeText(UserActivity.this, R.string.password_false, Toast.LENGTH_SHORT).show();
									}
								}
							}
						}
					)					
						.setNegativeButton(R.string.dia_cancel, null)
						.show();
				}
			}
		)
			.setNeutralButton(R.string.user_logout, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					u.logOut();
					Toast.makeText(UserActivity.this, R.string.logout_true, Toast.LENGTH_SHORT).show();
					finish();
					//open();
				}
			}
		).show();
	}
	private String getUserId()
	{
		return "ID:" + u.getObjectId();
	}

	private String getUserName()
	{
		return u.getUsername();
	}

}
