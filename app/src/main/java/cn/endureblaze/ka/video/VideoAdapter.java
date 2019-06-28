package cn.endureblaze.ka.video;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import cn.endureblaze.ka.Kirby;
import cn.endureblaze.ka.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>
{
    private Context mContext;
    private List<Video> mVideoList;
    static class ViewHolder extends RecyclerView.ViewHolder
	{
        LinearLayout LinearLayout;
		CardView cardView;
		ImageView videoImage;
		TextView videoName;
        public ViewHolder(View view)
		{
            super(view);
			LinearLayout = view.findViewById(R.id.LinearLayout);
            cardView = view.findViewById(R.id.cardview);
			videoImage = view.findViewById(R.id.video_image);
            videoName = view.findViewById(R.id.video_text);
        }
    }

    public VideoAdapter(List<Video> videolist)
	{
        mVideoList = videolist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
        if (mContext == null)
		{
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
		final ViewHolder holder=new ViewHolder(view);
		holder.LinearLayout.setOnClickListener(v -> {
			int position=holder.getAdapterPosition();
			Video video=mVideoList.get(position);
			Intent web = new Intent();
			web.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(video.getAv());
			web.setData(content_url);
			mContext.startActivity(web);
		}
		);
		return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
	{
        Video vi = mVideoList.get(position);
        holder.videoName.setText(vi.getName());
        Glide
			.with(mContext)
			.load(vi.getImageUrl())
			.apply(Kirby.getGlideRequestOptions())
			.into(holder.videoImage);
	}

    @Override
    public int getItemCount()
	{
        return mVideoList.size();
    }
}
