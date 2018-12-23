package com.kirby.runanjing.dialog;
import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.bumptech.glide.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bmob.*;
import com.kirby.runanjing.fragment.main.*;
import java.util.*;

import com.kirby.runanjing.R;
import com.shehuan.nicedialog.*;
import com.kirby.runanjing.utils.*;

public class MessDialog extends BaseNiceDialog
{
	private String type;

	private String id;

	private String s_mess;

	private String s_username;

	private String s_time;

	private ImageView userHeadImage;

	private FragmentActivity mActivity;

	private MyUser u;

	public static MessDialog newInstance(String type, String id, String mess, String username, String time)
	{
		Bundle bundle = new Bundle();
		bundle.putString("type", type);
		bundle.putString("id", id);
		bundle.putString("mess", mess);
		bundle.putString("username", username);
		bundle.putString("time", time);
		MessDialog dialog = new MessDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
    public int initTheme()
	{
        return theme;
    }

	public MessDialog setTheme(@StyleRes int theme)
	{
        this.theme = theme;
        return this;
    }

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		type = bundle.getString("type");
		id = bundle.getString("id");
		s_mess = bundle.getString("mess");
		s_username = bundle.getString("username");
		s_time = bundle.getString("time");
		u = BmobUser.getCurrentUser(MyUser.class);
	}

	@Override
	public int intLayoutId()
	{
		return R.layout.dialog_message;
	}

	@Override
	public void convertView(ViewHolder holder, final BaseNiceDialog mess_dialog)
	{
		mActivity = mess_dialog.getActivity();
		final TextView user_name=(TextView)holder.getView(R.id.userName);
		TextView mess=(TextView)holder.getView(R.id.mess);
		TextView time=(TextView)holder.getView(R.id.time);
		user_name.setText(s_username);
		mess.setText(s_mess);
		time.setText(s_time);
		userHeadImage = (ImageView)holder.getView(R.id.user_head);
		final ImageView mess_menu=(ImageView)holder.getView(R.id.mess_menu);
		ImageView messDialog_close = (ImageView)holder.getView(R.id.messdia_close);
		mess_menu.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					PopupMenu pop = new PopupMenu(mess_dialog.getActivity(), mess_menu);
					if (u.getUsername().equals(s_username))
					{
						pop.getMenuInflater().inflate(R.menu.mess_menu_ex, pop.getMenu());
					}
					else
					{
						pop.getMenuInflater().inflate(R.menu.mess_menu, pop.getMenu());
					}
					pop.show();
					pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

							@Override
							public boolean onMenuItemClick(MenuItem item)
							{
								switch (item.getItemId())
								{
									case R.id.mess_copy:
										ClipboardManager cm = (ClipboardManager) mess_dialog.getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
										cm.setText(s_mess);
										Toast.makeText(mess_dialog.getActivity(), getResources().getString(R.string.copy_success), Toast.LENGTH_SHORT).show();
										break;
									case R.id.mess_del:
										MessageBmob mess_del = new MessageBmob();
										mess_del.setObjectId(id);
										mess_del.delete(new UpdateListener() {
												@Override
												public void done(BmobException e)
												{
													if (e == null)
													{
														mess_dialog.dismiss();
														MainMessFragment main_mess=(MainMessFragment)mess_dialog.getActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment);
														main_mess.getMessage();
														Toast.makeText(getActivity(), getResources().getString(R.string.mess_del_success), Toast.LENGTH_SHORT).show();
													}
													else
													{
														Toast.makeText(getActivity(), getResources().getString(R.string.mess_del_fail) + e.getMessage(), Toast.LENGTH_SHORT).show();
													}
												}
											});
										break;
									case R.id.mess_edit:
										NiceDialog.init()
											.setLayoutId(R.layout.dialog_editmess)     //设置dialog布局文件
											.setConvertListener(new ViewConvertListener() {
												private EditText edit_编辑;     //进行相关View操作的回调
												@Override
												public void convertView(ViewHolder v, final BaseNiceDialog dialog)
												{
													edit_编辑 = (EditText)v.getView(R.id.内容_编辑);
													edit_编辑.setText(s_mess);
													edit_编辑.post(new Runnable() {
															@Override
															public void run()
															{
																InputMethodManager imm =
																	(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
																imm.showSoftInput(edit_编辑, 0);
															}
														});
													ImageView 发送=v.getView(R.id.发送);
													发送.setOnClickListener(new View.OnClickListener() {

															@Override
															public void onClick(View v)
															{
																String edit_内容 = edit_编辑.getText().toString();
																//判断是否为空
																if (edit_内容.isEmpty())
																{
																	Toast.makeText(getContext(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
																}
																else
																{			
																	final ProgressDialog progressDialog = new ProgressDialog(getActivity());
																	progressDialog.setMessage(getResources().getString(R.string.mess_upload));
																	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
																	progressDialog.show();
																	MessageBmob mess_edit=new MessageBmob();
																	mess_edit.setMessage(edit_内容);
																	mess_edit.update(id, new UpdateListener(){
																			@Override
																			public void done(BmobException e)
																			{
																				progressDialog.dismiss();
																				if (e == null)
																				{
																					dialog.dismiss();
																					mess_dialog.dismiss();
																					MainMessFragment main_mess=(MainMessFragment)dialog.getActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment);
																					main_mess.getMessage();
																					Toast.makeText(getActivity(), getResources().getString(R.string.mess_edit_success), Toast.LENGTH_SHORT).show();
																				}
																				else
																				{
																					Toast.makeText(getActivity(), getResources().getString(R.string.mess_edit_fail) + e.getMessage(), Toast.LENGTH_SHORT).show();
																				}
																			}

																		});
																}
															}
														});
												}
											})
											.setDimAmount(0.5f)     //调节灰色背景透明度[0-1]，默认0.5f
											.setShowBottom(true)     //是否在底部显示dialog，默认flase
											.show(getActivity().getSupportFragmentManager());
										break;
								}
								return true;
							}
						});
				}
			});
		messDialog_close.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mess_dialog.dismiss();
				}
			});
		BmobQuery<MyUser> user=new BmobQuery<>();
		user.addWhereEqualTo("username", s_username);
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
						Toast.makeText(mess_dialog.getActivity(), p2.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
	private Handler userHandler=new Handler(){

		private String userHeadUrl;

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 0:
					List<MyUser> list= (List<MyUser>)msg.obj;
					for (MyUser m : list)
					{
						try
						{
							Glide
								.with(mActivity)
								.load(m.getUserHead().getFileUrl())
								//.apply(Kirby.getGlideRequestOptions())
								.asBitmap()
								.fitCenter()
								.placeholder(R.drawable.buletheme)
								.error(R.drawable.buletheme)
								.into(userHeadImage);
						}
						catch (Exception e)
						{}
					}			
					break;
			}
		}
	};
}
