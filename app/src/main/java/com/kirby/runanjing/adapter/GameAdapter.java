package com.kirby.runanjing.adapter;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.kirby.runanjing.R;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.bean.*;
import java.util.*;
import com.kirby.runanjing.utils.*;
import android.app.*;
import android.support.v4.util.*;
import cn.bmob.v3.b.*;
import android.support.v4.app.*;
import com.bumptech.glide.*;
import com.kirby.runanjing.*;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>
{
    private Context mContext;
    private List<Console> mGameList;

	private Activity mActivity;
    static class ViewHolder extends RecyclerView.ViewHolder
	{

		private CardView cardView;

		private LinearLayout LinearLayout;

		private ImageView gameImage;

		private TextView gameName;
        public ViewHolder(View view)
		{
            super(view);
			LinearLayout = (LinearLayout)view.findViewById(R.id.LinearLayout);
            cardView = (CardView) view.findViewById(R.id.cardview);
            gameImage = (ImageView) view.findViewById(R.id.console_image);
            gameName = (TextView) view.findViewById(R.id.console_text);
        }
    }
    public GameAdapter(List<Console> gamelist,Activity activity)
	{
        mGameList = gamelist;
		mActivity=activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
        if (mContext == null)
		{
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_console, parent, false);
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
					Pair<View, String> card=new Pair<View,String>(view.findViewById(R.id.cardview), "card");
					Pair<View, String> image= new Pair<View,String>(view.findViewById(R.id.console_image), "image");
					Pair<View, String> name= new Pair<View,String>(view.findViewById(R.id.console_text), "name");
					mActivity.startActivity(mm,ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,card,image,name).toBundle());
					//m.theDownload(mContext, game.getName(),game.getPosition());
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
			//.apply(Kirby.getGlideRequestOptions())d
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
