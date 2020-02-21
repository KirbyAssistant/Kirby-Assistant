package cn.endureblaze.kirby.video.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseFragment;
import cn.endureblaze.kirby.bean.Video;
import cn.endureblaze.kirby.bmob.BmobVideo;
import cn.endureblaze.kirby.util.ThemeUtil;
import cn.endureblaze.kirby.util.ToastUtil;
import cn.endureblaze.kirby.video.adapter.VideoAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainVideoFragment extends BaseFragment {

    private View view;
    private RecyclerView re;
    private VideoAdapter video_adapter;
    private RefreshLayout refresh;
    private List<Video> video_list = new ArrayList<>();
    private TextView video_load_fail;

    public static MainVideoFragment newInstance() {
        return new MainVideoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_video_fragment, container, false);
        initView();
        return view;
    }

    @Override
    protected void onFragmentFirstVisible() {
        initVideo();
        refresh.autoRefresh();
    }

    private void initView(){
        video_load_fail = view.findViewById(R.id.video_load_fail_text);
        //设置显示视频的列表
        re = view.findViewById(R.id.video_list);
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(), 2);
        re.setLayoutManager(layoutManager);
        video_adapter = new VideoAdapter(video_list);
    }

    private void initVideo(){
        //refresh数据
        refresh = view.findViewById(R.id.video_refresh);
        refresh.setEnableNestedScroll(true);
        MaterialHeader mMaterialHeader=(MaterialHeader) refresh.getRefreshHeader();
        Objects.requireNonNull(mMaterialHeader).setColorSchemeColors(ThemeUtil.getThemeColorById(Objects.requireNonNull(getActivity()),R.attr.colorPrimary));
        refresh.setOnRefreshListener(re -> {
            refresh.setEnableLoadMore(false);
            getVideo();
        });
    }

    private void getVideo()
    {
        video_list.clear();//清空列表
        //使用BmobQuery获取视频数据
        BmobQuery<BmobVideo> query= new BmobQuery<>();
        query.order("-createdAt");//时间降序排列
        query.findObjects(new FindListener<BmobVideo>() {

            @SuppressLint("SetTextI18n")
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
                    //video_load_fail.setText(Objects.requireNonNull(getActivity()).getResources().getString(R.string.load_fail)+e.getMessage());
                    ToastUtil.show(e.getMessage());
                    refresh.finishRefresh();
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
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
                        video_list.add(video);
                        //设置适配器
                        re.setAdapter(video_adapter);
                    }
                    //refresh回调
                    refresh.finishRefresh();
                    break;
            }
        }
    };
}
