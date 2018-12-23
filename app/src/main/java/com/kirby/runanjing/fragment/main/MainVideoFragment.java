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
import android.widget.*;
import android.view.animation.*;
import com.kirby.runanjing.utils.*;
import com.kirby.runanjing.helper.*;
import com.kirby.runanjing.customui.*;
import com.kirby.runanjing.base.*;

public class MainVideoFragment extends BaseFragment 
{
	private View view;
	private MainActivity m;
	private StaggeredGridRecyclerView re;
	private VideoAdapter adapter;
	private RefreshLayout refresh;
	private List<Video> videolist = new ArrayList<>();

	private TextView video_load_fail;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view = inflater.inflate(R.layout.main_video, container, false);
		m = (MainActivity)getActivity();
		initVideo(view);
		refresh.autoRefresh();
		return view;
	}

	private void initVideo(View view)
	{
		video_load_fail = (TextView)view.findViewById(R.id.video_loadfail_text);
		//设置显示视频的列表
		re = (StaggeredGridRecyclerView)view.findViewById(R.id.视频);
		GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 2);
		re.setLayoutManager(layoutManager);
		adapter = new VideoAdapter(videolist);	
		//refresh数据
		refresh = (RefreshLayout)view.findViewById(R.id.refresh);
		refresh.setOnRefreshListener(new OnRefreshListener(){
				@Override
				public void onRefresh(RefreshLayout re)
				{
					refresh.setEnableLoadMore(false);
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
						refresh.setEnableLoadMore(true);
						video_load_fail.setVisibility(View.GONE);
						Message video = videoHandler.obtainMessage();
						video.what = 0;
						//以消息为载体
						video.obj = list;//这里的list就是查询出list
						//向handler发送消息
						videoHandler.sendMessage(video);
					}
					else
					{
						video_load_fail.setVisibility(View.VISIBLE);
						video_load_fail.setText(getActivity().getResources().getString(R.string.load_fail)+e.getMessage());
						Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
						refresh.finishRefresh();
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
						LayoutAnimationController controller = LayoutAnimationHelper.makeLayoutAnimationController();
						ViewGroup viewGroup = (ViewGroup)view.findViewById(R.id.视频);
						viewGroup.setLayoutAnimation(controller);
						viewGroup.scheduleLayoutAnimation();
						playLayoutAnimation(re,LayoutAnimationHelper.getAnimationSetFromBottom(),false);
					}			
					//refresh回调
					refresh.finishRefresh();
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
        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation);
        controller.setColumnDelay(0.2f);
        controller.setRowDelay(0.3f);
        controller.setOrder(isReverse ? LayoutAnimationController.ORDER_REVERSE : LayoutAnimationController.ORDER_NORMAL);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
    }
}
