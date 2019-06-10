package cn.endureblaze.ka.video;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseFragment;
import cn.endureblaze.ka.bmob.BmobVideo;
import cn.endureblaze.ka.customui.StaggeredGridRecyclerView;
import cn.endureblaze.ka.helper.LayoutAnimationHelper;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.utils.PlayAnimUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;

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
		re = (StaggeredGridRecyclerView)view.findViewById(R.id.video_list);
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
						ViewGroup viewGroup = (ViewGroup)view.findViewById(R.id.video_list);
						viewGroup.setLayoutAnimation(controller);
						viewGroup.scheduleLayoutAnimation();
						PlayAnimUtil.playLayoutAnimationWithGridLayout(re,LayoutAnimationHelper.getAnimationSetFromBottom(),false);
					}			
					//refresh回调
					refresh.finishRefresh();
					break;
			}
		}
	};
	
}
