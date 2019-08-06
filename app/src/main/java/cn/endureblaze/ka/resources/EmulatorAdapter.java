package cn.endureblaze.ka.resources;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import cn.ednureblaze.glidecache.GlideCache;
import cn.endureblaze.ka.Kirby;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Emulator;
import cn.endureblaze.ka.utils.DownloadApkUtil;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class EmulatorAdapter extends RecyclerView.Adapter<EmulatorAdapter.ViewHolder>
{
    private Context mContext;
    private List<Emulator> mEmulatorlist;

	private Activity mActivity;
    static class ViewHolder extends RecyclerView.ViewHolder
	{
        LinearLayout LinearLayout;
		CardView cardView;
        ImageView emulatorImage;
        TextView emulatorName;
		private ImageView blurImage;
        public ViewHolder(View view)
		{
            super(view);
			LinearLayout = view.findViewById(R.id.LinearLayout);
            cardView = view.findViewById(R.id.cardview);
            emulatorImage = view.findViewById(R.id.emulator_image);
            emulatorName = view.findViewById(R.id.emulator_text);
			blurImage = view.findViewById(R.id.blur_image);
        }
    }
    public EmulatorAdapter(List<Emulator> emulatorlist, Activity activity)
	{
        mEmulatorlist = emulatorlist;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emulator, parent, false);
		final ViewHolder holder=new ViewHolder(view);
		holder.LinearLayout.setOnClickListener(v -> {
			int position = holder.getAdapterPosition();
			Emulator emulator = mEmulatorlist.get(position);

			//m.theDownload(mContext,game.getEmulatorName(),game.getEmulatorTag());
                    MaterialAlertDialogBuilder dialog = new
                            MaterialAlertDialogBuilder(mContext)
                            .setTitle(emulator.getName())
                            .setMessage(R.string.download_dia_mess)
                            .setPositiveButton(R.string.dia_download, (dialog1, which) -> DownloadApkUtil.downloadappApk(emulator.getPosition(), mContext)
                            );
                    dialog.show();
		}
		);
		return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
	{
        final Emulator co = mEmulatorlist.get(position);
        holder.emulatorName.setText(co.getName());
		Glide
			.with(mContext)
			.load(co.getImageUrl())
		    .apply(Kirby.getGlideRequestOptions())
			.into(holder.emulatorImage);
        GlideCache.setBlurImageViaGlideCache(mActivity,holder.blurImage,co.getImageUrl(),"8");
    }

    @Override
    public int getItemCount()
	{
        return mEmulatorlist.size();
    }

}
