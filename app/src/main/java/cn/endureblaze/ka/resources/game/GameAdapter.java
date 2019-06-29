package cn.endureblaze.ka.resources.game;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
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
import cn.endureblaze.ka.utils.GlideUtil;
import com.bumptech.glide.Glide;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private Context mContext;
    private List<Console> mGameList;
	private Activity mActivity;
    static class ViewHolder extends RecyclerView.ViewHolder {

		private CardView cardView;

		private LinearLayout LinearLayout;

		private ImageView gameImage;

		private TextView gameName;
		
		private ImageView blurImage;
        public ViewHolder(View view) {
            super(view);
			LinearLayout = view.findViewById(R.id.LinearLayout);
            cardView = view.findViewById(R.id.cardview);
            gameImage = view.findViewById(R.id.console_image);
            gameName = view.findViewById(R.id.console_text);
			blurImage = view.findViewById(R.id.blur_image);
        }
    }
    public GameAdapter(List<Console> gamelist, Activity activity) {
        mGameList = gamelist;
		mActivity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_console, parent, false);
		final ViewHolder holder=new ViewHolder(view);
		holder.LinearLayout.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Console game = mGameList.get(position);
            MainActivity m=new MainActivity();
            Intent mm=new Intent(mContext, GameActivity.class);
            mm.putExtra("game_name", game.getName());
            mm.putExtra("game_img", game.getImageUrl());
            mm.putExtra("game_pos", game.getPosition());
            Pair<View, String> card= new Pair<>(view.findViewById(R.id.cardview), "card");
            Pair<View, String> image= new Pair<>(view.findViewById(R.id.console_image), "image");
            Pair<View, String> blur_image= new Pair<>(view.findViewById(R.id.blur_image), "blur_image");
            Pair<View, String> name= new Pair<>(view.findViewById(R.id.console_text), "name");
            mActivity.startActivity(mm, ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, card,blur_image, image, name).toBundle());
            //m.theDownload(mContext, game.getName(),game.getPosition());
        }
        );
		return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Console co = mGameList.get(position);
        holder.gameName.setText(co.getName());
		Glide
			.with(mContext)
			.load(co.getImageUrl())
			.apply(Kirby.getGlideRequestOptions())
			.into(holder.gameImage);
		GlideUtil.setBlurImageViaGlideCache(mActivity,holder.blurImage,co.getImageUrl(),"5");
    }

    @Override
    public int getItemCount() {
        return mGameList.size();
    }

}
