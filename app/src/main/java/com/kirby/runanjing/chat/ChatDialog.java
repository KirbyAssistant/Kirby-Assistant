package com.kirby.runanjing.chat;
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
import java.util.*;

import com.kirby.runanjing.R;
import com.kirby.runanjing.utils.*;
import com.kirby.runanjing.base.*;
import com.kirby.runanjing.bottomdialog.*;
import com.kirby.runanjing.bottomdialog.*;

public class ChatDialog extends BaseBottomDialog
{
	private String type;

	private String id;

	private String s_mess;

	private String s_username;

	private String s_time;

	private ImageView userHeadImage;

	private FragmentActivity mActivity;

	private BmobKirbyAssistantUser u;

	public static ChatDialog newInstance(String type, String id, String mess, String username, String time)
	{
		Bundle bundle = new Bundle();
		bundle.putString("type", type);
		bundle.putString("id", id);
		bundle.putString("mess", mess);
		bundle.putString("username", username);
		bundle.putString("time", time);
		ChatDialog dialog = new ChatDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
    public int initTheme()
	{
        return theme;
    }

	public ChatDialog setTheme(@StyleRes int theme)
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
		u = BmobUser.getCurrentUser(BmobKirbyAssistantUser.class);
	}

	@Override
	public int intLayoutId()
	{
		return R.layout.dialog_chat;
	}

	@Override
	public void convertView(ViewHolder holder, final BaseBottomDialog mess_dialog)
	{
		mActivity = mess_dialog.getActivity();
		final TextView user_name=(TextView)holder.getView(R.id.userName);
		TextView mess=(TextView)holder.getView(R.id.mess);
		TextView time=(TextView)holder.getView(R.id.time);
		user_name.setText(s_username);
		mess.setText(s_mess);
		time.setText(s_time);
		userHeadImage = (ImageView)holder.getView(R.id.user_head);
		final ImageView mess_menu=(ImageView)holder.getView(R.id.chat_menu);
		ImageView messDialog_close = (ImageView)holder.getView(R.id.chatdia_close);
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
										BmobChat mess_del = new BmobChat();
										mess_del.setObjectId(id);
										mess_del.delete(new UpdateListener() {
												@Override
												public void done(BmobException e)
												{
													if (e == null)
													{
														mess_dialog.dismiss();
														MainChatFragment main_mess=(MainChatFragment)mess_dialog.getActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment);
														main_mess.getChat();
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
										BottomDialog.init()
											.setLayoutId(R.layout.dialog_editchat)     //设置dialog布局文件
											.setConvertListener(new ViewConvertListener() {
												private EditText chat_editview;     //进行相关View操作的回调
												@Override
												public void convertView(ViewHolder v, final BaseBottomDialog dialog)
												{
													chat_editview = (EditText)v.getView(R.id.chat_editview);
													chat_editview.setText(s_mess);
													chat_editview.post(new Runnable() {
															@Override
															public void run()
															{
																InputMethodManager imm =
																	(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
																imm.showSoftInput(chat_editview, 0);
															}
														});
													ImageView chat_send=v.getView(R.id.chat_send);
													chat_send.setOnClickListener(new View.OnClickListener() {

															@Override
															public void onClick(View v)
															{
																String str_chat = chat_editview.getText().toString();
																//判断是否为空
																if (str_chat.isEmpty())
																{
																	Toast.makeText(getContext(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
																}
																else
																{			
																	final ProgressDialog progressDialog = new ProgressDialog(getActivity());
																	progressDialog.setMessage(getResources().getString(R.string.mess_upload));
																	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
																	progressDialog.show();
																	BmobChat bmob_chat=new BmobChat();
																	bmob_chat.setChat(str_chat);
																	bmob_chat.update(id, new UpdateListener(){
																			@Override
																			public void done(BmobException e)
																			{
																				progressDialog.dismiss();
																				if (e == null)
																				{
																					dialog.dismiss();
																					mess_dialog.dismiss();
																					MainChatFragment main_mess=(MainChatFragment)dialog.getActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment);
																					main_mess.getChat();
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
		BmobQuery<BmobKirbyAssistantUser> user=new BmobQuery<>();
		user.addWhereEqualTo("username", s_username);
		user.findObjects(new FindListener<BmobKirbyAssistantUser>(){

				@Override
				public void done(List<BmobKirbyAssistantUser> p1, BmobException p2)
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
					List<BmobKirbyAssistantUser> list= (List<BmobKirbyAssistantUser>)msg.obj;
					for (BmobKirbyAssistantUser m : list)
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
