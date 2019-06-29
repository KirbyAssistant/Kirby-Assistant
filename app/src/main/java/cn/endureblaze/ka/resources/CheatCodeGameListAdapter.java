package cn.endureblaze.ka.resources;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.endureblaze.ka.Kirby;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.resources.cheatcode.CheatCodeActivity;
import cn.endureblaze.ka.utils.GlideUtil;
import cn.endureblaze.ka.utils.IntentUtil;
import com.bumptech.glide.Glide;
import java.util.List;

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

		private ImageView blurImage;
        public ViewHolder(View view)
		{
            super(view);
			LinearLayout = view.findViewById(R.id.LinearLayout);
            cardView = view.findViewById(R.id.cardview);
			gameImage = view.findViewById(R.id.console_image);
            gameName = view.findViewById(R.id.console_text);
			blurImage = view.findViewById(R.id.blur_image);
        }
    }

    public CheatCodeGameListAdapter(List<Console> cheatCodeGamelist,Activity activity)
	{
        mCheatCodeGameList = cheatCodeGamelist;
		mActivity=activity;
    }
	@NonNull
    @Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		if (mContext == null)
		{
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_console, parent, false);
		final ViewHolder holder=new ViewHolder(view);
		holder.LinearLayout.setOnClickListener(v -> {
			int position=holder.getAdapterPosition();
			Console console=mCheatCodeGameList.get(position);
			Intent in=new Intent(mContext, CheatCodeActivity.class);
			String name= console.getName();
			in.putExtra("game_name",name);
			MainActivity m=new MainActivity();
			IntentUtil.startActivityWithAnim(in,mActivity);
		}
		);
		return holder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position)
	{
		final Console co = mCheatCodeGameList.get(position);
        holder.gameName.setText(co.getName());
        Glide
			.with(mContext)
			.load(co.getImageUrl())
			.apply(Kirby.getGlideRequestOptions())
			.into(holder.gameImage);	
		GlideUtil.setBlurImageViaGlideCache(mActivity,holder.blurImage,co.getImageUrl(),"5");
		}

	@Override
	public int getItemCount()
	{
		return mCheatCodeGameList.size();
	}
	
}
