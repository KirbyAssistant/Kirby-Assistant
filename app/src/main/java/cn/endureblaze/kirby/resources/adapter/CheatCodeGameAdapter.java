package cn.endureblaze.kirby.resources.adapter;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import cn.ednureblaze.glidecache.GlideCache;
import cn.endureblaze.kirby.Kirby;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.bean.CheatCodeGame;
import cn.endureblaze.kirby.resources.cheatcode.CheatCodeActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class CheatCodeGameAdapter extends RecyclerView.Adapter<CheatCodeGameAdapter.ViewHolder> {

    private List<CheatCodeGame> mCheatCodeGameList;
    private Context mContext;

    private Activity mActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View item_cheat_code_view;
        LinearLayout LinearLayout;
        CardView cardView;
        ImageView gameImage;
        TextView gameName;

        private ImageView blurImage;

        public ViewHolder(View view) {
            super(view);
            item_cheat_code_view = view;
            LinearLayout = view.findViewById(R.id.LinearLayout);
            cardView = view.findViewById(R.id.cardview);
            gameImage = view.findViewById(R.id.console_image);
            gameName = view.findViewById(R.id.console_text);
            blurImage = view.findViewById(R.id.blur_image);
        }
    }

    public CheatCodeGameAdapter(List<CheatCodeGame> cheatCodeGamelist, Activity activity) {
        mCheatCodeGameList = cheatCodeGamelist;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_console, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.LinearLayout.setOnClickListener(v -> {
                    int position = holder.getAdapterPosition();
                    CheatCodeGame cheat_code_game = mCheatCodeGameList.get(position);
                    Intent in = new Intent(mContext, CheatCodeActivity.class);
                    String name = cheat_code_game.getName();
                    in.putExtra("game_name", name);
                    mActivity.startActivity(in);
                }
        );
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        holder.item_cheat_code_view.startAnimation(animation);

        AlphaAnimation item_anim = new AlphaAnimation(0.1f, 1.0f);
        item_anim.setDuration(500);
        holder.gameImage.setAnimation(item_anim);
        holder.gameImage.setAnimation(item_anim);
        holder.blurImage.setAnimation(item_anim);

        final CheatCodeGame ch = mCheatCodeGameList.get(position);
        holder.gameName.setText(ch.getName());
        Glide
                .with(mContext)
                .load(ch.getImageUrl())
                .apply(Kirby.getGlideRequestOptions())
                .into(holder.gameImage);
        GlideCache.setBlurImageViaGlideCache(mActivity, holder.blurImage, ch.getImageUrl(), "5");
    }

    @Override
    public int getItemCount() {
        return mCheatCodeGameList.size();
    }

}
