package cn.endureblaze.kirby.chat.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseFragment;
import cn.endureblaze.kirby.bean.Chat;
import cn.endureblaze.kirby.bmob.BmobChat;
import cn.endureblaze.kirby.chat.adapter.ChatAdapter;
import cn.endureblaze.kirby.util.ThemeUtil;
import cn.endureblaze.kirby.util.ToastUtil;
import cn.endureblaze.kirby.util.UserUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainChatFragment extends BaseFragment
{
    private List<Chat> chat_list = new ArrayList<>();
    private ChatAdapter adapter;
    private RecyclerView re;
    private RefreshLayout refresh;
    private View view;
    private int messItem;
    private TextView mess_load_fail;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.main_chat_fragment, container, false);
        initView();
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        if(UserUtil.isUserLogin()){
            initChat();
            initBroad();
            refresh.autoRefresh();
        }else{
            mess_load_fail.setVisibility(View.VISIBLE);
            mess_load_fail.setText(R.string.chat_not_login);
        }
    }

    private void initView(){
        mess_load_fail = view.findViewById(R.id.mess_loadfail_text);
        //设置显示留言的列表
        re = view.findViewById(R.id.chat_list);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 1);
        re.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(chat_list,getActivity(), Objects.requireNonNull(getActivity()).getSupportFragmentManager());
    }

    private class ChatSendLocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            getChat();
        }
    }

    private void initBroad(){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity()));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.endureblaze.kirby.CHAT_SEND");
        intentFilter.addAction("cn.endureblaze.kirby.CHAT_DEL");
        ChatSendLocalReceiver localReceiver = new ChatSendLocalReceiver();
        //注册本地广播监听器
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    private void initChat() {
        //refresh数据
        refresh = view.findViewById(R.id.chat_refresh);
        MaterialHeader mMaterialHeader = (MaterialHeader) refresh.getRefreshHeader();
        Objects.requireNonNull(mMaterialHeader).setColorSchemeColors(ThemeUtil.getThemeColorById(Objects.requireNonNull(getActivity()),R.attr.colorPrimary));
        refresh.setOnRefreshListener(re -> {
            refresh.setEnableLoadMore(false);
            getChat();
        });
        refresh.setOnLoadMoreListener(re -> getMoreChat());
        //使用BmobUser类获取部分用户数据
        String name = UserUtil.getCurrentUser().getUsername();
    }

    private void  getChat()
    {
        chat_list.clear();//清空列表
        //使用BmobQuery获取留言数据
        BmobQuery<BmobChat> query= new BmobQuery<>();
        query.order("-createdAt");//时间降序排列
        query.setLimit(20);
        query.findObjects(new FindListener<BmobChat>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobChat> list, BmobException e)
            {
                if (e == null)
                {
                    refresh.setEnableLoadMore(true);
                    mess_load_fail.setVisibility(View.GONE);
                    Message message = messHandler.obtainMessage();
                    message.what = 0;
                    //以消息为载体
                    message.obj = list;//这里的list就是查询出list
                    //向handler发送消息
                    messHandler.sendMessage(message);
                    messItem=20;
                }
                else
                {
                    mess_load_fail.setVisibility(View.VISIBLE);
                    mess_load_fail.setText(Objects.requireNonNull(getActivity()).getResources().getString(R.string.load_fail)+e.getMessage());
                    refresh.finishRefresh();
                }
            }
        });
    }

    private void getMoreChat(){
        //使用BmobQuery获取留言数据
        BmobQuery<BmobChat> query= new BmobQuery<>();
        query.order("-createdAt");//时间降序排列
        query.setSkip(messItem);
        query.setLimit(20);
        query.findObjects(new FindListener<BmobChat>() {
            @Override
            public void done(List<BmobChat> list, BmobException e)
            {
                if (e == null)
                {
                    Message message = moreMessHandler.obtainMessage();
                    message.what = 0;
                    //以消息为载体
                    message.obj = list;//这里的list就是查询出list
                    //向handler发送消息
                    moreMessHandler.sendMessage(message);
                    messItem=messItem+20;
                }
                else
                {
                    ToastUtil.show(e.getMessage());
                    refresh.finishLoadMore();
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler messHandler=new Handler(){

        private String userAvatar;
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0) {
                List<BmobChat> list = (List<BmobChat>) msg.obj;
                for (BmobChat m : list) {
                    //从获取的数据中提取需要的数据
                    String id = m.getObjectId();
                    String user = m.getNickname();
                    String chat_full = m.getChat();
                    String chat;
                    boolean show_all;
                    if (chat_full.length() > 40) {
                        chat = chat_full.substring(0, 40) + "...";
                        show_all = true;
                    } else {
                        chat = chat_full;
                        show_all = false;
                    }
                    String time_ = m.getCreatedAt();
                    String time = time_.substring(0, 16);
                    Chat mess = new Chat(id, user, userAvatar, chat, time, chat_full, show_all);
                    //将查询到的数据依次添加到列表
                    chat_list.add(mess);
                    //设置适配器
                    re.setAdapter(adapter);
                }
                //refresh回调
                refresh.finishRefresh();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler moreMessHandler=new Handler(){

        private boolean show_all;

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    List<BmobChat> list= (List<BmobChat>)msg.obj;
                    for (BmobChat m : list)
                    {
                        //从获取的数据中提取需要的数据
                        String id=m.getObjectId();
                        String user=m.getNickname();
                        String userAvatar=null;
                        String chat_full=m.getChat();
                        String chat;
                        if(chat_full.length()>40){
                            chat =chat_full.substring(0,40)+"...";
                            show_all=true;
                        }
                        else
                        {
                            chat =chat_full;
                            show_all=false;
                        }
                        String time_=m.getCreatedAt();
                        String time = time_.substring(0, 16);
                        Chat mess=new Chat(id,user, null, chat,time,chat_full,show_all);
                        //将查询到的数据依次添加到列表
                        chat_list.add(mess);
                        Objects.requireNonNull(re.getAdapter()).notifyItemChanged(messItem);
                    }
                    //refresh回调
                    refresh.finishLoadMore();
                    break;
            }
        }
    };
}