package cn.endureblaze.ka.chat;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bmob.BmobChat;
import cn.endureblaze.ka.bmob.BmobKirbyAssistantUser;
import cn.endureblaze.ka.bottomdialog.BaseBottomDialog;
import cn.endureblaze.ka.bottomdialog.ViewHolder;
import cn.endureblaze.ka.utils.CheckTextUtil;
import cn.endureblaze.ka.utils.UserUtil;

import java.util.Objects;

public class EditChatDialog extends BaseBottomDialog {
	private EditText chat_editview;

	private String str_chat;

	private int mode;

    public static EditChatDialog newInstance(String type, String chat, int mode) {
		Bundle bundle = new Bundle();
		bundle.putString("str_chat", chat);
		bundle.putInt("mode", mode);
		EditChatDialog dialog = new EditChatDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
    public int initTheme() {
        return theme;
    }

	public EditChatDialog setTheme(@StyleRes int theme) {
        this.theme = theme;
        return this;
    }

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
	    str_chat = Objects.requireNonNull(bundle).getString("str_chat");
		mode = bundle.getInt("mode");
	}

	@Override
	public int intLayoutId() {
		return R.layout.dialog_editchat;
	}

	@Override
	public void convertView(ViewHolder holder, final BaseBottomDialog edit_chat_dialog) {
		SharedPreferences chat_share= Objects.requireNonNull(getActivity()).getSharedPreferences("string", 0);
		String chat= chat_share.getString("Chat", null);
	    chat_editview = holder.getView(R.id.chat_editview);
		chat_editview.addTextChangedListener(textWatcher);
		chat_editview.post(() -> {
            InputMethodManager imm =
                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).showSoftInput(chat_editview, 0);
        });
		
		if (mode == ChatMode.CHAT_SEND_MODE && chat != null) {
			chat_editview.setText(chat);
		}
		if (mode == ChatMode.CHAT_EDIT_MODE && str_chat != null) {
			chat_editview.setText(str_chat);
		}
        TextView chat_send = holder.getView(R.id.chat_send);
		chat_send.setOnClickListener(v -> {
			//获取字符串转化为string数据
			//EditText 内容=(EditText)v.findViewById(R.id.内容_编辑);
			final String str_chat = chat_editview.getText().toString();
			//判断是否为空
			if (str_chat.isEmpty()) {
				Toast.makeText(getActivity(), getActivity().getString(R.string.is_null), Toast.LENGTH_SHORT).show();
			} else {
				if (CheckTextUtil.isHaveTerribleWord(str_chat)) {
					AlertDialog.Builder dialog = new
						AlertDialog.Builder(getActivity())
						.setTitle("需要帮助吗？")
						.setMessage("这个世界虽然不完美\n我们仍可以治愈自己\n以下电话全国可拨(24小时)\n010-82951332")
						.setCancelable(false)
						.setPositiveButton("坚持发送", (dialog1, which) -> sendChat(str_chat, edit_chat_dialog)
						)
						.setNegativeButton("寻求帮助", (dialog12, which) -> {
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-82951332"));
							getActivity().startActivity(intent);
						})
						.setNeutralButton("离开", (dialog13, which) -> {
							edit_chat_dialog.dismiss();
							SharedPreferences y=getActivity().getSharedPreferences("string", 0);
							SharedPreferences.Editor edit=y.edit();
							edit.putString("Chat", "");
							edit.apply();
						}
						);
					dialog.show();
				} else {
					sendChat(str_chat, edit_chat_dialog);
				}
			}
		});
	}
	private void sendChat(String str_chat, final BaseBottomDialog edit_chat_dialog) {
		final ProgressDialog progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage(getResources().getString(R.string.chat_upload));
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();
		//自定义MessBmob发送留言
		BmobChat chat = new BmobChat();
		chat.setChat(str_chat);
		chat.setNickname(UserUtil.getCurrentUser().getUsername());
		chat.setUser(BmobUser.getCurrentUser(BmobKirbyAssistantUser.class));
		chat.save(new SaveListener<String>() {
				@Override
				public void done(String objectId, BmobException e) {
					progressDialog.dismiss();
					if (e == null) {		
						SharedPreferences y= Objects.requireNonNull(getActivity()).getSharedPreferences("string", 0);
						SharedPreferences.Editor edit=y.edit();
						edit.putString("Chat", "");
						edit.apply();
						edit_chat_dialog.dismiss();
						MainChatFragment main_chat=(MainChatFragment) Objects.requireNonNull(edit_chat_dialog.getActivity()).getSupportFragmentManager().findFragmentById(R.id.main_fragment);
						Objects.requireNonNull(main_chat).getChat();
						Toast.makeText(getActivity(), getResources().getString(R.string.chat_true), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.chat_false) + e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
			
		}

		@Override
		public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
			if (mode == ChatMode.CHAT_SEND_MODE) {
				SharedPreferences y= Objects.requireNonNull(getActivity()).getSharedPreferences("string", 0);
				SharedPreferences.Editor edit=y.edit();
				edit.putString("Chat", chat_editview.getText().toString());
				edit.apply();
			}
		}

		@Override
		public void afterTextChanged(Editable p1) {
			
		}
	};
}
