package com.kirby.runanjing.fragment.main;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.animation.*;
import android.view.inputmethod.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.adapter.*;
import com.kirby.runanjing.bean.*;
import com.kirby.runanjing.bmob.*;
import com.kirby.runanjing.helper.*;
import com.scwang.smartrefresh.layout.api.*;
import com.scwang.smartrefresh.layout.listener.*;
import java.util.*;

import android.support.v4.app.Fragment;
import com.kirby.runanjing.R;
import com.kirby.runanjing.customui.*;
import com.kirby.runanjing.base.*;
import com.shehuan.nicedialog.*;
import com.kirby.runanjing.dialog.*;

public class MainMessFragment extends BaseFragment
{
	private List<Mess> messlist = new ArrayList<>();
	private MessageAdapter adapter;
	private RecyclerView re;
	private RefreshLayout 刷新;
	private String name;
	private FloatingActionButton edit_mess_button;
	private View view;
	private MainActivity m;
	private int messItem;
	private EditText edit_编辑;

	private RippleLayout rippleBackground;

	private TextView mess_load_fail;  
	//private BottomDialog mess_dia;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view = inflater.inflate(R.layout.main_mess, container, false);
		m = (MainActivity)getActivity();
		initMess(view);
		刷新.autoRefresh();
		return view;
	}
	
	private void initMess(View view)
	{
		mess_load_fail = (TextView)view.findViewById(R.id.mess_loadfail_text);
		//设置显示留言的列表
		re = (RecyclerView)view.findViewById(R.id.留言);
		GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 1);
		re.setLayoutManager(layoutManager);
		adapter = new MessageAdapter(messlist,getActivity(),getActivity().getSupportFragmentManager());	
		//律动动画
	    rippleBackground=(RippleLayout)view.findViewById(R.id.content);
		//刷新数据
		刷新 = (RefreshLayout)view.findViewById(R.id.刷新);
		刷新.setOnRefreshListener(new OnRefreshListener(){
				@Override
				public void onRefresh(RefreshLayout re)
				{
					edit_mess_button.setVisibility(View.GONE);
					mess_load_fail.setVisibility(View.GONE);
					rippleBackground.stopRippleAnimation();
					getMessage();
				}
			});
		刷新.setOnLoadMoreListener(new OnLoadMoreListener(){
				@Override
				public void onLoadMore(RefreshLayout re)
				{
					getMoreMessage();
				}
			});
		//使用BmobUser类获取部分用户数据
		final MyUser u = BmobUser.getCurrentUser(MyUser.class);
		name = u.getUsername();
		edit_mess_button = (FloatingActionButton)view.findViewById(R.id.FAB_编辑);
		edit_mess_button.setOnClickListener(new View.OnClickListener(){			
				@Override
				public void onClick(View v)			
				{
					EditMessDialog.newInstance("0")
					.setTheme(R.style.NiceDialogStyle)
					.setMargin(0)
					.setShowBottom(true)
					.show(getActivity().getSupportFragmentManager());
				}
			});
	}
	public void  getMessage()
	{
		messlist.clear();//清空列表
		//使用BmobQuery获取留言数据
		BmobQuery<MessageBmob> query=new BmobQuery<MessageBmob>();
		query.order("-createdAt");//时间降序排列
		query.setLimit(20);
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
						messItem=20;
						edit_mess_button.setVisibility(View.VISIBLE);
						ScaleAnimation mess_fab_anim = (ScaleAnimation) AnimationUtils.loadAnimation(getActivity(), R.transition.mess_fab);
						edit_mess_button.startAnimation(mess_fab_anim);
						rippleBackground.startRippleAnimation();
					}
					else
					{
						mess_load_fail.setVisibility(View.VISIBLE);
						mess_load_fail.setText(getActivity().getResources().getString(R.string.load_fail)+e.getMessage());
						刷新.finishRefresh();
					}
				}
			});
	}
	public void getMoreMessage(){
		//使用BmobQuery获取留言数据
		BmobQuery<MessageBmob> query=new BmobQuery<MessageBmob>();
		query.order("-createdAt");//时间降序排列
		query.setSkip(messItem);
		query.setLimit(20);
		query.findObjects(new FindListener<MessageBmob>() {
				@Override
				public void done(List<MessageBmob> list, BmobException e)
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
						刷新.finishLoadMore();
					}
				}
			});
	}
	private Handler messHandler=new Handler(){
		
		private String message;
		private boolean show_all;
		private String userHead;
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
						String id=m.getObjectId();
						String user=m.getNickname();
						String message_full=m.getMessage();
						if(message_full.length()>40){
							 message=message_full.substring(0,40)+"...";
							 show_all=true;
						}
						else
						{
							 message=message_full;
							 show_all=false;
						}
						String time_=m.getCreatedAt();
						String time = time_.substring(0, 16);
						Mess mess=new Mess(id,user,userHead,message,time,message_full,show_all);
						//将查询到的数据依次添加到列表
						messlist.add(mess);
						//设置适配器
						re.setAdapter(adapter);
						LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
						ViewGroup viewGroup = (ViewGroup)view.findViewById(R.id.留言);
						viewGroup.setLayoutAnimation(controller);
						viewGroup.scheduleLayoutAnimation();
						playLayoutAnimation(re,LayoutAnimationHelper.getAnimationSetFromBottom(),false);
					}			
					//刷新回调
					刷新.finishRefresh();
					break;
			}
		}
	};
	private Handler moreMessHandler=new Handler(){

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
						String id=m.getObjectId();
						String user=m.getNickname();
						String userHead=null;
						String message_full=m.getMessage();
						if(message_full.length()>40){
							message=message_full.substring(0,40)+"...";
							show_all=true;
						}
						else
						{
							message=message_full;
							show_all=false;
						}
						String time_=m.getCreatedAt();
						String time = time_.substring(0, 16);
						Mess mess=new Mess(id,user,userHead,message,time,message_full,show_all);
						//将查询到的数据依次添加到列表
						messlist.add(mess);
						re.getAdapter().notifyItemChanged(messItem);
					}			
					//刷新回调
					刷新.finishLoadMore();
					break;
			}
		}
	};
	/**
     * 播放RecyclerView动画
     *
     * @param animation
     * @param isReverse
     */
    public void playLayoutAnimation(RecyclerView mRecyclerView,Animation animation, boolean isReverse) {
        LayoutAnimationController controller = new LayoutAnimationController(animation);
		controller.setDelay(0.1f);
        controller.setOrder(isReverse ? LayoutAnimationController.ORDER_REVERSE : LayoutAnimationController.ORDER_NORMAL);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
    }
}
