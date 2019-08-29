package cn.endureblaze.kirby.chat.dialog;

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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseDialog;
import cn.endureblaze.kirby.bmob.BmobChat;
import cn.endureblaze.kirby.bmob.BmobKirbyAssistantUser;
import cn.endureblaze.kirby.chat.ChatMode;
import cn.endureblaze.kirby.omgdialog.OMGDialog;
import cn.endureblaze.kirby.omgdialog.ViewHolder;
import cn.endureblaze.kirby.util.CheckTextUtil;
import cn.endureblaze.kirby.util.ToastUtil;
import cn.endureblaze.kirby.util.UserUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

public class EditChatDialog extends BaseDialog {
    private EditText chat_edit_view;

    private String str_chat;

    private int mode;
    private LocalBroadcastManager chat_send_broad;
    private String id;

    public static EditChatDialog newInstance(String type,String id, String chat, int mode) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
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
        id = Objects.requireNonNull(bundle).getString("id");
        str_chat = Objects.requireNonNull(bundle).getString("str_chat");
        mode = bundle.getInt("mode");
    }

    @Override
    public int intLayoutId() {
        return R.layout.dialog_chat_edit;
    }

    @Override
    public void convertView(ViewHolder holder, final OMGDialog edit_chat_dialog) {
        chat_send_broad = LocalBroadcastManager.getInstance(Objects.requireNonNull(edit_chat_dialog.getContext()));
        SharedPreferences chat_share= Objects.requireNonNull(getActivity()).getSharedPreferences("string", 0);
        String chat= chat_share.getString("Chat", null);
        chat_edit_view = holder.getView(R.id.chat_edit_view);
        chat_edit_view.addTextChangedListener(textWatcher);
        chat_edit_view.post(() -> {
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).showSoftInput(chat_edit_view, 0);
        });

        if (mode == ChatMode.CHAT_SEND_MODE && chat != null) {
            chat_edit_view.setText(chat);
        }
        if (mode == ChatMode.CHAT_EDIT_MODE && str_chat != null) {
            chat_edit_view.setText(str_chat);
        }
        TextView chat_send = holder.getView(R.id.chat_send);
        chat_send.setOnClickListener(v -> {
            //获取字符串转化为string数据
            //EditText 内容=(EditText)v.findViewById(R.id.内容_编辑);
            final String str_chat = chat_edit_view.getText().toString();
            //判断是否为空
            if (str_chat.isEmpty()) {
            ToastUtil.show(R.string.is_null);
            } else {
                if (CheckTextUtil.isHaveTerribleWord(str_chat)) {
                    MaterialAlertDialogBuilder dialog = new
                            MaterialAlertDialogBuilder(getActivity())
                            .setTitle("需要帮助吗？")
                            .setMessage("这个世界虽然不完美\n我们仍可以治愈自己\n以下电话全国可拨(24小时)\n010-82951332")
                            .setCancelable(false)
                            .setPositiveButton("坚持发送", (dialog1, which) -> sendChat(id,str_chat, edit_chat_dialog)
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

                    sendChat(id,str_chat, edit_chat_dialog);
                }
            }
        });
    }
    private void sendChat(String id,String str_chat, final OMGDialog edit_chat_dialog) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getResources().getString(R.string.chat_sending));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        //自定义MessBmob发送留言
        BmobChat chat = new BmobChat();
        chat.setChat(str_chat);
        chat.setNickname(UserUtil.getCurrentUser().getUsername());
        chat.setUser(BmobUser.getCurrentUser(BmobKirbyAssistantUser.class));
        if(mode == ChatMode.CHAT_SEND_MODE)
        {
            //直接发送
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
                        //发送广播说明发送成功
                        Intent intent = new Intent("cn.endureblaze.kirby.CHAT_SEND");
                        intent.putExtra("chat", 1);
                        chat_send_broad.sendBroadcast(intent);
                        ToastUtil.show(R.string.chat_success);
                    } else {
                        ToastUtil.show(getResources().getString(R.string.chat_fail) + e.getMessage());
                    }
                }
            });
        }else{
            //编辑模式
            chat.update(id, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    progressDialog.dismiss();
                    if (e == null) {
                        edit_chat_dialog.dismiss();
                        //发送广播说明发送成功
                        Intent intent = new Intent("cn.endureblaze.kirby.CHAT_SEND");
                        intent.putExtra("chat", 1);
                        chat_send_broad.sendBroadcast(intent);
                        ToastUtil.show(R.string.chat_success);
                    } else {
                        ToastUtil.show(getResources().getString(R.string.chat_fail) + e.getMessage());
                    }
                }
            });
        }
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
                edit.putString("Chat", chat_edit_view.getText().toString());
                edit.apply();
            }
        }

        @Override
        public void afterTextChanged(Editable p1) {

        }
    };
}