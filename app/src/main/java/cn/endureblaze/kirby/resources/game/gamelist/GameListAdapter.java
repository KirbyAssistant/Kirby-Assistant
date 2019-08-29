package cn.endureblaze.kirby.resources.game.gamelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import cn.ednureblaze.glidecache.GlideCache;
import cn.endureblaze.kirby.Kirby;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.bean.Console;
import cn.endureblaze.kirby.main.MainActivity;
import cn.endureblaze.kirby.resources.game.gameinfo.GameActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {
    private Context mContext;
    private List<Console> mGameList;
    private Activity mActivity;
    static class ViewHolder extends RecyclerView.ViewHolder {

        private View item_game;
        private android.widget.LinearLayout LinearLayout;
        private ImageView gameImage;
        private TextView gameName;
        private ImageView blurImage;
        public ViewHolder(View view) {
            super(view);
            item_game = view;
            LinearLayout = view.findViewById(R.id.LinearLayout);
            CardView card_view = view.findViewById(R.id.cardview);
            gameImage = view.findViewById(R.id.console_image);
            gameName = view.findViewById(R.id.console_text);
            blurImage = view.findViewById(R.id.blur_image);
        }
    }
    public GameListAdapter(List<Console> game_list, Activity activity) {
        mGameList = game_list;
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
                    mm.putExtra("game_tag", game.getTag());
                    Pair<View, String> card= new Pair<>(view.findViewById(R.id.cardview), "card");
                    Pair<View, String> image= new Pair<>(view.findViewById(R.id.console_image), "image");
                    Pair<View, String> blur_image= new Pair<>(view.findViewById(R.id.blur_image), "blur_image");
                    Pair<View, String> name= new Pair<>(view.findViewById(R.id.console_text), "name");
                    mActivity.startActivity(mm, ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, card,blur_image, image, name).toBundle());
                }
        );
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        holder.item_game.startAnimation(animation);

        AlphaAnimation item_anim = new AlphaAnimation(0.1f, 1.0f);
        item_anim.setDuration(500);
        holder.gameImage.setAnimation(item_anim);
        holder.gameName.setAnimation(item_anim);
        holder.blurImage.setAnimation(item_anim);

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
    public int getItemCount() {
        return mGameList.size();
    }

}

