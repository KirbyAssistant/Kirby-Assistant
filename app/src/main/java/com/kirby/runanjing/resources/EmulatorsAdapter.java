package com.kirby.runanjing.resources;
import android.app.*;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.bumptech.glide.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bean.*;
import java.util.*;

import com.kirby.runanjing.R;
import com.kirby.runanjing.main.*;
import com.kirby.runanjing.resources.game.*;

public class EmulatorsAdapter extends RecyclerView.Adapter<EmulatorsAdapter.ViewHolder>
{
    private Context mContext;
    private List<Console> mGameList;

	private Activity mActivity;
    static class ViewHolder extends RecyclerView.ViewHolder
	{
        LinearLayout LinearLayout;
		CardView cardView;
        ImageView gameImage;
        TextView gameName;
        public ViewHolder(View view)
		{
            super(view);
			LinearLayout = (LinearLayout)view.findViewById(R.id.LinearLayout);
            cardView = (CardView) view.findViewById(R.id.cardview);
            gameImage = (ImageView) view.findViewById(R.id.console_image);
            gameName = (TextView) view.findViewById(R.id.console_text);
        }
    }
    public EmulatorsAdapter(List<Console> gamelist)
	{
        mGameList = gamelist;
		//mActivity=activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
        if (mContext == null)
		{
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_console, parent, false);
		final ViewHolder holder=new ViewHolder(view);
		holder.LinearLayout.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v)
				{
					int position = holder.getAdapterPosition();
					Console game = mGameList.get(position);
					MainActivity m=new MainActivity();
					Intent mm=new Intent(mContext,GameActivity.class);
					mm.putExtra("game_name",game.getName());
					mm.putExtra("game_img",game.getImageUrl());
					mm.putExtra("game_pos",game.getPosition());
					//mContext.startActivity(mm);
					//IntentUtil.startActivityWithAnim(mm,mActivity);
					m.theDownload(mContext, game.getName(),game.getPosition());
				}
			}
		);
		return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
	{
        Console co = mGameList.get(position);
        holder.gameName.setText(co.getName());
		Glide
			.with(mContext)
			.load(co.getImageUrl())
		  //  .apply(Kirby.getGlideRequestOptions())
			.asBitmap()
		    .fitCenter()
		    .placeholder(R.drawable.ic_kirby_download)
			.error(R.drawable.ic_kirby_load_fail)
			.into(holder.gameImage);
    }

    @Override
    public int getItemCount()
	{
        return mGameList.size();
    }

}
