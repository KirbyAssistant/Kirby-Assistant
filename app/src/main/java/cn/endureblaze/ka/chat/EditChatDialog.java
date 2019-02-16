package cn.endureblaze.ka.chat;
import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import cn.endureblaze.ka.*;

import cn.endureblaze.ka.R;
import cn.bmob.v3.*;
import android.text.*;
import cn.endureblaze.ka.bean.*;
import cn.endureblaze.ka.bmob.*;
import cn.endureblaze.ka.bottomdialog.*;
import cn.endureblaze.ka.bottomdialog.*;
import cn.endureblaze.ka.utils.*;
import android.net.*;

public class EditChatDialog extends BaseBottomDialog
{
	private EditText chat_editview;
	
	public static EditChatDialog newInstance(String type)
	{
		Bundle bundle = new Bundle();

		EditChatDialog dialog = new EditChatDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
    public int initTheme()
	{
        return theme;
    }

	public EditChatDialog setTheme(@StyleRes int theme)
	{
        this.theme = theme;
        return this;
    }

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
	}

	@Override
	public int intLayoutId()
	{
		return R.layout.dialog_editchat;
	}

	@Override
	public void convertView(ViewHolder holder, final BaseBottomDialog edit_chat_dialog)
	{
		SharedPreferences chat_share=getActivity().getSharedPreferences("string", 0);
		String chat= chat_share.getString("Chat", null);
		chat_editview = (EditText)holder.getView(R.id.chat_editview);
		chat_editview.post(new Runnable() {
				@Override
				public void run()
				{
					InputMethodManager imm =
						(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(chat_editview, 0);
				}
			});
		chat_editview.addTextChangedListener(textWatcher);
		if (chat != null)
		{
			chat_editview.setText(chat);
		}
		ImageView chat_send=holder.getView(R.id.chat_send);
		chat_send.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v)
				{
					//获取字符串转化为string数据
					//EditText 内容=(EditText)v.findViewById(R.id.内容_编辑);
					final String str_chat = chat_editview.getText().toString();
					//判断是否为空
					if (str_chat.isEmpty())
					{
						Toast.makeText(getActivity(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
					}
					else
					{
						if (CheckTextUtil.isHaveTerribleWord(str_chat))
						{
							AlertDialog.Builder dialog = new
								AlertDialog.Builder(getActivity())
								.setTitle("需要帮助吗？")
								.setMessage("这个世界虽然不完美\n我们仍可以治愈自己\n以下电话全国可拨(24小时)\n010-82951332")
								.setCancelable(false)
								.setPositiveButton("坚持发送", new
								DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog, int which)
									{
										sendChat(str_chat,edit_chat_dialog);
									}
								}
							)
								.setNegativeButton("寻求帮助", new
								DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog, int which)
									{
										Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("tel:010-82951332"));
										getActivity().startActivity(intent);
									}
								})
								.setNeutralButton("离开", new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog, int which)
									{
										edit_chat_dialog.dismiss();
										SharedPreferences y=getActivity().getSharedPreferences("string", 0);
										SharedPreferences.Editor edit=y.edit();
										edit.putString("Chat", "");
										edit.apply();
									}
								}
							);
							dialog.show();
						}
						else
						{
							sendChat(str_chat,edit_chat_dialog);
						}
					}
				}
			});
	}
	private void sendChat(String str_chat,final BaseBottomDialog edit_chat_dialog)
	{
		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage(getResources().getString(R.string.mess_upload));
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
		//自定义MessBmob发送留言
		BmobChat chat = new BmobChat();
		chat.setChat(str_chat);
		chat.setNickname(UserUtil.getCurrentUser().getUsername());
		chat.setUser(BmobUser.getCurrentUser(BmobKirbyAssistantUser.class));
		chat.save(new SaveListener<String>() {
				@Override
				public void done(String objectId, BmobException e)
				{
					progressDialog.dismiss();
					if (e == null)
					{		
						SharedPreferences y=getActivity().getSharedPreferences("string", 0);
						SharedPreferences.Editor edit=y.edit();
						edit.putString("Chat", "");
						edit.apply();
						edit_chat_dialog.dismiss();
						MainChatFragment main_chat=(MainChatFragment)edit_chat_dialog.getActivity().getSupportFragmentManager().findFragmentById(R.id.main_fragment);
						main_chat.getChat();
						Toast.makeText(getActivity(), getResources().getString(R.string.mess_true) + objectId, Toast.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(getActivity(), getResources().getString(R.string.mess_false) + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
		{

		}

		@Override
		public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
		{
			SharedPreferences y=getActivity().getSharedPreferences("string", 0);
			SharedPreferences.Editor edit=y.edit();
			edit.putString("Chat", chat_editview.getText().toString());
			edit.apply();
		}

		@Override
		public void afterTextChanged(Editable p1)
		{
			// TODO: Implement this method
		}
	};
}
