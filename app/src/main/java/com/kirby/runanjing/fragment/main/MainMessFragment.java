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
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.adapter.*;
import com.kirby.runanjing.bean.*;
import com.kirby.runanjing.bmob.*;
import com.scwang.smartrefresh.layout.api.*;
import com.scwang.smartrefresh.layout.listener.*;
import java.util.*;

import android.support.v4.app.Fragment;
import com.kirby.runanjing.R;
import android.view.inputmethod.*;
import com.othershe.nicedialog.*;
import android.view.animation.*;

public class MainMessFragment extends Fragment
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
		//设置显示留言的列表
		re = (RecyclerView)view.findViewById(R.id.留言);
		GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 1);
		re.setLayoutManager(layoutManager);
		adapter = new MessageAdapter(messlist,getActivity(),getActivity().getSupportFragmentManager());	
		//刷新数据
		刷新 = (RefreshLayout)view.findViewById(R.id.刷新);
		刷新.setOnRefreshListener(new OnRefreshListener(){
				@Override
				public void onRefresh(RefreshLayout re)
				{
					getMessage();
				}
			});
		刷新.setOnLoadmoreListener(new OnLoadmoreListener(){
				@Override
				public void onLoadmore(RefreshLayout re)
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
					NiceDialog.init()
						.setLayoutId(R.layout.filter_sample_view)     //设置dialog布局文件
						.setConvertListener(new ViewConvertListener() {     //进行相关View操作的回调
							@Override
							public void convertView(ViewHolder v, final BaseNiceDialog dialog) {
								SharedPreferences mess_=getActivity().getSharedPreferences("string", 0);
								String mess= mess_.getString("Message", null);
								edit_编辑=(EditText)v.getView(R.id.内容_编辑);
								edit_编辑.post(new Runnable() {
										@Override
										public void run() {
											InputMethodManager imm =
												(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
											imm.showSoftInput(edit_编辑, 0);
										}
									});
								edit_编辑.addTextChangedListener(textWatcher);
								if (mess != null)
								{
									edit_编辑.setText(mess);
								}
								ImageView 发送=v.getView(R.id.发送);
								发送.setOnClickListener(new View.OnClickListener() {

										@Override
										public void onClick(View v)
										{
											//获取字符串转化为string数据
											//EditText 内容=(EditText)v.findViewById(R.id.内容_编辑);
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
												//自定义MessBmob发送留言
												MessageBmob mess = new MessageBmob();
												mess.setMessage(edit_内容);
												mess.setNickname(u.getUsername());
												mess.save(new SaveListener<String>() {
														@Override
														public void done(String objectId, BmobException e)
														{
															progressDialog.dismiss();
															if (e == null)
															{		
																SharedPreferences y=getActivity().getSharedPreferences("string", 0);
																SharedPreferences.Editor edit=y.edit();
																edit.putString("Message", "");
																edit.apply();
																dialog.dismiss();
																getMessage();
																Toast.makeText(getActivity(), getResources().getString(R.string.mess_true) + objectId, Toast.LENGTH_SHORT).show();
															}
															else
															{
																Toast.makeText(getActivity(), getResources().getString(R.string.mess_false) + e.getMessage(), Toast.LENGTH_SHORT).show();
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
					}
					else
					{
						Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
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
						Log.e("bmob", "" + e);
						刷新.finishLoadmore();
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
					刷新.finishLoadmore();
					break;
			}
		}
	};
	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
		{

		}

		@Override
		public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
		{
			SharedPreferences y=getContext().getSharedPreferences("string", 0);
			SharedPreferences.Editor edit=y.edit();
			edit.putString("Message", edit_编辑.getText().toString());
			edit.apply();
		}

		@Override
		public void afterTextChanged(Editable p1)
		{
			// TODO: Implement this method
		}
	};
}
