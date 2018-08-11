package com.kirby.runanjing.adapter;


import android.content.*;
import android.net.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.bumptech.glide.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bean.*;
import java.util.*;

import com.kirby.runanjing.R;

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
			LinearLayout = (LinearLayout)view.findViewById(R.id.LinearLayout);
            cardView = (CardView) view.findViewById(R.id.cardview);
			videoImage = (ImageView) view.findViewById(R.id.video_image);
            videoName = (TextView) view.findViewById(R.id.video_text);
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
		holder.LinearLayout.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v)
				{
					int position=holder.getAdapterPosition();
					Video video=mVideoList.get(position);
					Intent web = new Intent();        
					web.setAction("android.intent.action.VIEW");    
					Uri content_url = Uri.parse(video.getAv());   
					web.setData(content_url);  
					mContext.startActivity(web);  		
				}
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
			//.apply(Kirby.getGlideRequestOptions())
			.asBitmap()
		  //  .centerCrop()
			.placeholder(R.drawable.ic_kirby_download)
			.error(R.drawable.ic_kirby_load_fail)
			.into(holder.videoImage);
	}

    @Override
    public int getItemCount()
	{
        return mVideoList.size();
    }
}
