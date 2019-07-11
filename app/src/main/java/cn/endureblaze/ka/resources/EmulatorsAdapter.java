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

import cn.ednureblaze.glidecache.GlideCache;
import cn.endureblaze.ka.Kirby;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.resources.game.GameActivity;
import com.bumptech.glide.Glide;
import java.util.List;

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
    public EmulatorsAdapter(List<Console> gamelist,Activity activity)
	{
        mGameList = gamelist;
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
			int position = holder.getAdapterPosition();
			Console game = mGameList.get(position);
			MainActivity m=new MainActivity();
			Intent mm=new Intent(mContext,GameActivity.class);
			mm.putExtra("game_name",game.getName());
			mm.putExtra("game_img",game.getImageUrl());
			mm.putExtra("game_pos",game.getPosition());
			m.theDownload(mContext,game.getName(),game.getPosition());
		}
		);
		return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
	{
        final Console co = mGameList.get(position);
        holder.gameName.setText(co.getName());
		Glide
			.with(mContext)
			.load(co.getImageUrl())
		    .apply(Kirby.getGlideRequestOptions())
			.into(holder.gameImage);
		GlideCache.setBlurImageViaGlideCache(mActivity,holder.blurImage,co.getImageUrl(),"5");
    }

    @Override
    public int getItemCount()
	{
        return mGameList.size();
    }

}
