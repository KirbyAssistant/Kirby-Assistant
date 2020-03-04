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
import cn.endureblaze.kirby.Kirby;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.bean.Console;
import cn.endureblaze.kirby.resources.game.gamelist.GameListActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class ConsoleAdapter extends RecyclerView.Adapter<ConsoleAdapter.ViewHolder> {
    private Context mContext;
    private List<Console> mConsoleList;

    private Activity mActivity;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View item_console;
        LinearLayout LinearLayout;
        CardView cardView;
        ImageView consoleImage;
        TextView consoleName;

        ViewHolder(View view) {
            super(view);
            item_console = view;
            LinearLayout = view.findViewById(R.id.LinearLayout);
            cardView = view.findViewById(R.id.cardview);
            consoleImage = view.findViewById(R.id.console_image);
            consoleName = view.findViewById(R.id.console_text);
        }
    }

    public ConsoleAdapter(List<Console> console_list, Activity activity) {
        mConsoleList = console_list;
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
            Console console = mConsoleList.get(position);
            Intent in = new Intent(mContext, GameListActivity.class);
            String name = console.getTag();
            in.putExtra("console_name", name);
            mActivity.startActivity(in);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Console co = mConsoleList.get(position);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        holder.item_console.startAnimation(animation);

        AlphaAnimation item_anim = new AlphaAnimation(0.1f, 1.0f);
        item_anim.setDuration(500);
        holder.consoleImage.setAnimation(item_anim);
        holder.consoleName.setAnimation(item_anim);

        holder.consoleName.setText(co.getName());
        Glide
                .with(mContext)
                .load(co.getImageUrl())
                .apply(Kirby.getGlideRequestOptions())
                .centerInside()
                .into(holder.consoleImage);
    }

    @Override
    public int getItemCount() {
        return mConsoleList.size();
    }
}
