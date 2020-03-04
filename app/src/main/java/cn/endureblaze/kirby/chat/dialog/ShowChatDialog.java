package cn.endureblaze.kirby.chat.dialog;

import android.annotation.SuppressLint;
import android.content.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.endureblaze.kirby.Kirby;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseDialog;
import cn.endureblaze.kirby.bmob.BmobChat;
import cn.endureblaze.kirby.bmob.BmobKirbyAssistantUser;
import cn.endureblaze.kirby.chat.ChatMode;
import cn.endureblaze.kirby.manager.ActManager;
import cn.endureblaze.kirby.omgdialog.OMGDialog;
import cn.endureblaze.kirby.omgdialog.ViewHolder;
import cn.endureblaze.kirby.util.ToastUtil;
import cn.endureblaze.kirby.util.UserUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Objects;

public class ShowChatDialog extends BaseDialog {

    private String id;
    private String str_chat;
    private String str_username;
    private String str_time;
    private ImageView userAvatarImage;
    private FragmentActivity fragmentActivity;
    private MaterialButton chat_menu;
    private LocalBroadcastManager chat_del_broad;

    public static ShowChatDialog newInstance(String type,String id,String chat,String username,String time){
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("id", id);
        bundle.putString("chat", chat);
        bundle.putString("username", username);
        bundle.putString("time", time);
        ShowChatDialog dialog = new ShowChatDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public int initTheme()
    {
        return theme;
    }

    public ShowChatDialog setTheme(@StyleRes int theme)
    {
        this.theme = theme;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        String type = Objects.requireNonNull(bundle).getString("type");
        id = bundle.getString("id");
        str_chat = bundle.getString("chat");
        str_username = bundle.getString("username");
        str_time = bundle.getString("time");
        initBroad();
    }

    @Override
    public int intLayoutId()
    {
        return R.layout.dialog_chat_show;
    }

    @Override
    public void convertView(ViewHolder holder, OMGDialog chat_dialog) {
        chat_del_broad = LocalBroadcastManager.getInstance(Objects.requireNonNull(chat_dialog.getContext()));

        fragmentActivity = chat_dialog.getActivity();
        final TextView user_name= holder.getView(R.id.userName);
        TextView mess= holder.getView(R.id.mess);
        TextView time= holder.getView(R.id.time);
        userAvatarImage = holder.getView(R.id.user_avatar);
        user_name.setText(str_username);
        mess.setText(str_chat);
        time.setText(str_time);

        chat_menu= holder.getView(R.id.chat_menu);
        MaterialButton chatDialog_close = holder.getView(R.id.chatdia_close);
        chatDialog_close.setOnClickListener(p1 -> chat_dialog.dismiss());

        chat_menu.setOnClickListener(p1 -> initMenu(chat_dialog));

        BmobQuery<BmobKirbyAssistantUser> user=new BmobQuery<>();
        user.addWhereEqualTo("username", str_username);
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
                    ToastUtil.show(p2.getMessage());
                }
            }
        });
    }
    private void initBroad() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity()));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.endureblaze.kirby.CHAT_SEND");
        ShowChatLocalReceiver localReceiver = new ShowChatLocalReceiver();
        //注册本地广播监听器
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }
    //配置闲聊菜单
    private void initMenu(OMGDialog chat_dialog){
        PopupMenu pop = new PopupMenu(chat_dialog.getActivity(), chat_menu);
        if (UserUtil.getCurrentUser().getUsername().equals(str_username))
        {
            pop.getMenuInflater().inflate(R.menu.mess_menu_ex, pop.getMenu());
        }
        else
        {
            pop.getMenuInflater().inflate(R.menu.mess_menu, pop.getMenu());
        }
        pop.show();
        pop.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.chat_copy:
                            chatCopy();
                            break;
                        case R.id.chat_del:
                            chatDel(chat_dialog);
                            break;
                        case R.id.chat_edit:
                            EditChatDialog.newInstance("0",id,str_chat, ChatMode.CHAT_EDIT_MODE)
                                    .setTheme(R.style.OMGDialogStyle)
                                    .setMargin(0)
                                    .setGravity(Gravity.BOTTOM)
                                    .setOutCancel(true)
                                    .show(Objects.requireNonNull(getActivity()).getSupportFragmentManager());
                            break;

                    }
                    return true;
                }
        );
    }
    //复制闲聊
    private void chatCopy(){
        ClipboardManager cm = (ClipboardManager) Objects.requireNonNull(ActManager.getCurrentActivity().getSystemService(Context.CLIPBOARD_SERVICE));
        Objects.requireNonNull(cm).setPrimaryClip(ClipData.newPlainText("chat",str_chat));
        if (cm.hasPrimaryClip()) {
            Objects.requireNonNull(cm.getPrimaryClip()).getItemAt(0).getText();
        }
        ToastUtil.show(R.string.copy_success);
    }
    //删除闲聊
    private void chatDel(OMGDialog chat_dialog){
        BmobChat mess_del = new BmobChat();
        mess_del.setObjectId(id);
        mess_del.delete(new UpdateListener() {
            @Override
            public void done(BmobException e)
            {
                if (e == null)
                {
                    chat_dialog.dismiss();
                    Intent intent = new Intent("cn.endureblaze.kirby.CHAT_DEL");
                    intent.putExtra("chat", 1);
                    chat_del_broad.sendBroadcast(intent);
                    ToastUtil.show(R.string.chat_del_success);
                }
                else
                {
                    ToastUtil.show(getResources().getString(R.string.chat_del_fail) + e.getMessage());
                }
            }
        });
    }
    @SuppressLint("HandlerLeak")
    private Handler userHandler=new Handler(){

        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0) {
                List<BmobKirbyAssistantUser> list = (List<BmobKirbyAssistantUser>) msg.obj;
                for (BmobKirbyAssistantUser m : list) {
                    try {
                        Glide
                                .with(fragmentActivity)
                                .load(m.getUserAvatar().getFileUrl())
                                .apply(Kirby.getGlideRequestOptions())
                                .into(userAvatarImage);
                    } catch (Exception ignored) {
                    }
                }
            } else {
                throw new IllegalStateException("Unexpected value: " + msg.what);
            }
        }
    };
    class ShowChatLocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            dismiss();
        }
    }
}
