package cn.endureblaze.kirby.resources.adapter;

import android.app.Activity;
import android.content.Context;
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
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import cn.ednureblaze.glidecache.GlideCache;
import cn.endureblaze.kirby.Kirby;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.bean.Emulator;
import cn.endureblaze.kirby.util.DownloadApkUtil;
import com.bumptech.glide.Glide;

import java.util.List;

public class EmulatorAdapter extends RecyclerView.Adapter<EmulatorAdapter.ViewHolder> {
    private Context mContext;
    private List<Emulator> mEmulatorlist;

    private Activity mActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View item_emulator;
        LinearLayout LinearLayout;
        CardView cardView;
        ImageView emulatorImage;
        TextView emulatorName;
        private ImageView blurImage;

        public ViewHolder(View view) {
            super(view);
            item_emulator = view;
            LinearLayout = view.findViewById(R.id.LinearLayout);
            cardView = view.findViewById(R.id.cardview);
            emulatorImage = view.findViewById(R.id.emulator_image);
            emulatorName = view.findViewById(R.id.emulator_text);
            blurImage = view.findViewById(R.id.blur_image);
        }
    }

    public EmulatorAdapter(List<Emulator> emulatorlist, Activity activity) {
        mEmulatorlist = emulatorlist;
        mActivity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emulator, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.LinearLayout.setOnClickListener(v -> {
                    int position = holder.getAdapterPosition();
                    Emulator emulator = mEmulatorlist.get(position);
                    AlertDialog.Builder dialog = new
                            AlertDialog.Builder(mContext)
                            .setTitle(emulator.getName())
                            .setMessage(R.string.download_dia_mess)
                            .setPositiveButton(R.string.dia_download, (dialog1, which) -> DownloadApkUtil.downloadAppApk(emulator.getTag(), mContext)
                            );
                    dialog.show();
                }
        );
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Emulator co = mEmulatorlist.get(position);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        holder.item_emulator.startAnimation(animation);

        AlphaAnimation item_anim = new AlphaAnimation(0.1f, 1.0f);
        item_anim.setDuration(500);
        holder.emulatorImage.setAnimation(item_anim);
        holder.emulatorName.setAnimation(item_anim);
        holder.blurImage.setAnimation(item_anim);

        holder.emulatorName.setText(co.getName());
        Glide
                .with(mContext)
                .load(co.getImageUrl())
                .apply(Kirby.getGlideRequestOptions())
                .into(holder.emulatorImage);
        GlideCache.setBlurImageViaGlideCache(mActivity, holder.blurImage, co.getImageUrl(), "8");
    }

    @Override
    public int getItemCount() {
        return mEmulatorlist.size();
    }

}
