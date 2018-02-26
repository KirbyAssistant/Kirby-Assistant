package com.kirby.runanjing.fragment.main;

import android.os.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.util.*;
import android.view.*;
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
import okhttp3.*;
import android.widget.*;
import java.io.*;
import com.kirby.runanjing.untils.*;

public class MainVideoFragment extends Fragment 
{
	private View view;
	private MainActivity m;
	private RecyclerView re;
	private VideoAdapter adapter;
	private RefreshLayout 刷新;
	private List<Video> videolist = new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view = inflater.inflate(R.layout.main_video, container, false);
		m = (MainActivity)getActivity();
		initVideo(view);
		刷新.autoRefresh();
		return view;
	}

	private void initVideo(View view)
	{
		//设置显示视频的列表
		re = (RecyclerView)view.findViewById(R.id.视频);
		GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 1);
		re.setLayoutManager(layoutManager);
		adapter = new VideoAdapter(videolist);	
		//刷新数据
		刷新 = (RefreshLayout)view.findViewById(R.id.刷新);
		刷新.setOnRefreshListener(new OnRefreshListener(){
				@Override
				public void onRefresh(RefreshLayout re)
				{
					getVideo();
				}
			});
	}

	private void getVideo()
	{
		videolist.clear();//清空列表
		//使用BmobQuery获取视频数据
		BmobQuery<BmobVideo> query=new BmobQuery<BmobVideo>();
		query.order("-createdAt");//时间降序排列
		query.findObjects(new FindListener<BmobVideo>() {

				@Override
				public void done(List<BmobVideo> list, BmobException e)
				{
					if (e == null)
					{
						Message video = videoHandler.obtainMessage();
						video.what = 0;
						//以消息为载体
						video.obj = list;//这里的list就是查询出list
						//向handler发送消息
						videoHandler.sendMessage(video);
					}
					else
					{
						Log.e("bmob", "" + e);
						刷新.finishRefresh();
					}
				}
			});
	}

	private Handler videoHandler=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 0:
					List<BmobVideo> list= (List<BmobVideo>)msg.obj;
					for (BmobVideo m : list)
					{
						//从获取的数据中提取需要的数据
						String video_url=m.getAv();
						String video_title=m.getName();	
						String video_image_url=m.getImageUrl();
						//将查询到的数据依次添加到列表
						Video video=new Video(video_title, video_image_url , video_url);
						videolist.add(video);
						//设置适配器
						re.setAdapter(adapter);
					}			
					//刷新回调
					刷新.finishRefresh();
					break;
			}
		}
	};
}
