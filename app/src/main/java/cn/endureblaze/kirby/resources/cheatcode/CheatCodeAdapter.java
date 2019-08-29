package cn.endureblaze.kirby.resources.cheatcode;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.bean.CheatCode;

import java.util.List;
import java.util.Objects;

public class CheatCodeAdapter extends RecyclerView.Adapter<CheatCodeAdapter.ViewHolder>{

    private Context mContext;
    private List<CheatCode> mCheatCodeLiat;

    private Activity mActivity;
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View item_cheatcode;
        TextView cheatcode_id;
        TextView cheatcode_mess;

        ViewHolder(View view)
        {
            super(view);
            item_cheatcode = view;
            cheatcode_id = view.findViewById(R.id.cheatCode_id);
            cheatcode_mess = view.findViewById(R.id.cheatCode_mess);
        }
    }

    public CheatCodeAdapter(List<CheatCode> cheatcode_list, Activity activity)
    {
        mCheatCodeLiat = cheatcode_list;
        mActivity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null)
        {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cheatcode, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        view.setOnClickListener(view1 -> {
            int position=holder.getAdapterPosition();
            CheatCode cheatCode = mCheatCodeLiat.get(position);
            String q=cheatCode.getCheatCode();
            ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            Objects.requireNonNull(cm).setPrimaryClip(ClipData.newPlainText("cheat_code",q));
            if (cm.hasPrimaryClip()) {
                Objects.requireNonNull(cm.getPrimaryClip()).getItemAt(0).getText();
            }
            // TODO: 2019/8/19
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CheatCode co = mCheatCodeLiat.get(position);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        holder.item_cheatcode.startAnimation(animation);

        AlphaAnimation item_anim = new AlphaAnimation(0.1f, 1.0f);
        item_anim.setDuration(500);
        holder.cheatcode_id.setAnimation(item_anim);
        holder.cheatcode_mess.setAnimation(item_anim);

        holder.cheatcode_id.setText(co.getId());
        holder.cheatcode_mess.setText(co.getCheatCode());
    }

    @Override
    public int getItemCount() {
        return mCheatCodeLiat.size();
    }
}
