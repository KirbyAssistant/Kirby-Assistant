package cn.endureblaze.ka.resources;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.resources.game.GameActivity;
import cn.endureblaze.ka.utils.FastBlurUtil;
import cn.endureblaze.ka.utils.GlideUtil;
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
			LinearLayout = (LinearLayout)view.findViewById(R.id.LinearLayout);
            cardView = (CardView) view.findViewById(R.id.cardview);
            gameImage = (ImageView) view.findViewById(R.id.console_image);
            gameName = (TextView) view.findViewById(R.id.console_text);
			blurImage = (ImageView)view.findViewById(R.id.blur_image);
        }
    }
    public EmulatorsAdapter(List<Console> gamelist,Activity activity)
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
					m.theDownload(mContext, game.getName(),game.getPosition());
				}
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
		  //  .apply(Kirby.getGlideRequestOptions())
			.asBitmap()
		    .fitCenter()
		    .placeholder(R.drawable.ic_kirby_download)
			.error(R.drawable.ic_kirby_load_fail)
			.into(holder.gameImage);
		GlideUtil.setBlurImageViaGlideCache(mActivity,holder.blurImage,co.getImageUrl(),"5");
    }

    @Override
    public int getItemCount()
	{
        return mGameList.size();
    }

}
