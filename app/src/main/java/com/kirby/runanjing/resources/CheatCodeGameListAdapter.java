package com.kirby.runanjing.resources;
import android.app.*;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.bumptech.glide.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bean.*;
import com.kirby.runanjing.utils.*;
import java.util.*;

import com.kirby.runanjing.R;
import com.kirby.runanjing.resources.cheatcode.*;
import com.kirby.runanjing.main.*;

public class CheatCodeGameListAdapter extends RecyclerView.Adapter<CheatCodeGameListAdapter.ViewHolder>
{

	private List<Console> mCheatCodeGameList;
	private Context mContext;

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

    public CheatCodeGameListAdapter(List<Console> cheatCodeGamelist,Activity activity)
	{
        mCheatCodeGameList = cheatCodeGamelist;
		mActivity=activity;
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
					int position=holder.getAdapterPosition();
					Console console=mCheatCodeGameList.get(position);
					Intent in=new Intent(mContext, CheatCodeActivity.class);
					String  input=console.getName().toString();	
					MainActivity m=new MainActivity();
					IntentUtil.startActivityWithAnim(in,mActivity);
					SharedPreferences.Editor t=mContext.getSharedPreferences("string", 0).edit();
					t.putString("金手指_游戏", input);
					t.apply();
				}
			}
		);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		Console co = mCheatCodeGameList.get(position);
        holder.gameName.setText(co.getName());
        Glide
			.with(mContext)
			.load(co.getImageUrl())
			//.apply(Kirby.getGlideRequestOptions())
			.asBitmap()
		    .fitCenter()
			.placeholder(R.drawable.ic_kirby_download)
			.error(R.drawable.ic_kirby_load_fail)
			.into(holder.gameImage);	
		}

	@Override
	public int getItemCount()
	{
		return mCheatCodeGameList.size();
	}
	
}
