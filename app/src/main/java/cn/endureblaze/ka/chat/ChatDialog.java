package cn.endureblaze.ka.chat;
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
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.bmob.*;
import java.util.*;

import cn.endureblaze.ka.R;
import cn.endureblaze.ka.utils.*;
import cn.endureblaze.ka.base.*;
import cn.endureblaze.ka.bottomdialog.*;
import cn.endureblaze.ka.bottomdialog.*;
import com.bumptech.glide.*;

public class ChatDialog extends BaseBottomDialog
{
	private String type;

	private String id;

	private String s_mess;

	private String s_username;

	private String s_time;

	private ImageView userHeadImage;

	private FragmentActivity mActivity;

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
					if (UserUtil.getCurrentUser().getUsername().equals(s_username))
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
														Toast.makeText(getActivity(), getResources().getString(R.string.chat_del_success), Toast.LENGTH_SHORT).show();
													}
													else
													{
														Toast.makeText(getActivity(), getResources().getString(R.string.chat_del_fail) + e.getMessage(), Toast.LENGTH_SHORT).show();
													}
												}
											});
										break;
									case R.id.mess_edit:
										EditChatDialog.newInstance("0",s_mess,ChatMode.CHAT_EDIT_MODE)
											.setTheme(R.style.BottomDialogStyle)
											.setMargin(0)
											.setShowBottom(true)
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
