package cn.endureblaze.ka.chat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseFragment;
import cn.endureblaze.ka.bean.Chat;
import cn.endureblaze.ka.bmob.BmobChat;
import cn.endureblaze.ka.helper.LayoutAnimationHelper;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.utils.PlayAnimUtil;
import cn.endureblaze.ka.utils.ThemeUtil;
import cn.endureblaze.ka.utils.UserUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainChatFragment extends BaseFragment
{
	private List<Chat> chatlist = new ArrayList<>();
	private ChatAdapter adapter;
	private RecyclerView re;
	private RefreshLayout refresh;
    private FloatingActionButton edit_mess_button;
	private View view;
    private int messItem;
	private EditText edit_edit;
	private TextView mess_load_fail;
	//private BottomDialog mess_dia;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view = inflater.inflate(R.layout.main_chat, container, false);
        MainActivity m = (MainActivity) getActivity();
		initMess(view);
		refresh.autoRefresh();
		return view;
	}
	
	private void initMess(View view)
	{
		mess_load_fail = view.findViewById(R.id.mess_loadfail_text);
		//设置显示留言的列表
		re = view.findViewById(R.id.chat_list);
		GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 1);
		re.setLayoutManager(layoutManager);
		adapter = new ChatAdapter(chatlist,getActivity(), Objects.requireNonNull(getActivity()).getSupportFragmentManager());
		//refresh数据
		refresh = view.findViewById(R.id.refresh);
		MaterialHeader mMaterialHeader=(MaterialHeader) refresh.getRefreshHeader();
		Objects.requireNonNull(mMaterialHeader).setColorSchemeColors(ThemeUtil.getColorPrimary(getActivity()));
		refresh.setOnRefreshListener(re -> {
			refresh.setEnableLoadMore(false);
			edit_mess_button.setVisibility(View.GONE);
			getChat();
		});
		refresh.setOnLoadMoreListener(re -> getMoreChat());
		//使用BmobUser类获取部分用户数据
        String name = UserUtil.getCurrentUser().getUsername();
		edit_mess_button = view.findViewById(R.id.fab_chat_edit);
		edit_mess_button.setOnClickListener(v -> EditChatDialog.newInstance("0",null,ChatMode.CHAT_SEND_MODE)
        .setTheme(R.style.BottomDialogStyle)
        .setMargin(0)
        .setShowBottom(true)
        .show(getActivity().getSupportFragmentManager()));
	}
	public void  getChat()
	{
		chatlist.clear();//清空列表
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
						edit_mess_button.setVisibility(View.VISIBLE);
						@SuppressLint("ResourceType") ScaleAnimation mess_fab_anim = (ScaleAnimation) AnimationUtils.loadAnimation(getActivity(), R.transition.mess_fab);
						edit_mess_button.startAnimation(mess_fab_anim);
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
	public void getMoreChat(){
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
						Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
						refresh.finishLoadMore();
					}
				}
			});
	}
	@SuppressLint("HandlerLeak")
	private Handler messHandler=new Handler(){
		
		private String chat;
		private boolean show_all;
		private String userHead;
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
						String chat_full=m.getChat();
						if(chat_full.length()>40){
							 chat=chat_full.substring(0,40)+"...";
							 show_all=true;
						}
						else
						{
							 chat=chat_full;
							 show_all=false;
						}
						String time_=m.getCreatedAt();
						String time = time_.substring(0, 16);
						Chat mess=new Chat(id,user,userHead,chat,time,chat_full,show_all);
						//将查询到的数据依次添加到列表
						chatlist.add(mess);
						//设置适配器
						re.setAdapter(adapter);
						LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
						ViewGroup viewGroup = view.findViewById(R.id.chat_list);
						viewGroup.setLayoutAnimation(controller);
						viewGroup.scheduleLayoutAnimation();
						PlayAnimUtil.playLayoutAnimationWithRecyclerView(re,LayoutAnimationHelper.getAnimationSetFromBottom(),false);
					}			
					//refresh回调
					refresh.finishRefresh();
					break;
			}
		}
	};
	@SuppressLint("HandlerLeak")
	private Handler moreMessHandler=new Handler(){

		private String chat;

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
						String userHead=null;
						String chat_full=m.getChat();
						if(chat_full.length()>40){
							chat=chat_full.substring(0,40)+"...";
							show_all=true;
						}
						else
						{
							chat=chat_full;
							show_all=false;
						}
						String time_=m.getCreatedAt();
						String time = time_.substring(0, 16);
						Chat mess=new Chat(id,user, null,chat,time,chat_full,show_all);
						//将查询到的数据依次添加到列表
						chatlist.add(mess);
						Objects.requireNonNull(re.getAdapter()).notifyItemChanged(messItem);
					}			
					//refresh回调
					refresh.finishLoadMore();
					break;
			}
		}
	};
}
