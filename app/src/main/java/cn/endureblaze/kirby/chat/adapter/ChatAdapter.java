package cn.endureblaze.kirby.chat.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.bean.Chat;
import cn.endureblaze.kirby.chat.dialog.ShowChatDialog;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>
{
    private Context mContext;
    private List<Chat> mChatlist;

    private FragmentManager mFragmentManager;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View item_chat;
        TextView chat_username;
        TextView chat;
        TextView chat_time;
        TextView lookmore;

        private ImageView user_avatar;
        public ViewHolder(View view)
        {
            super(view);
            item_chat = view;
            chat_username = view.findViewById(R.id.chat_username);
            chat = view.findViewById(R.id.chat);
            chat_time = view.findViewById(R.id.chat_time);
            lookmore = view.findViewById(R.id.show_all);
        }
    }
    public ChatAdapter(List<Chat>chatlist, Activity activity, FragmentManager fragmentManager)
    {
        mChatlist = chatlist;
        mFragmentManager=fragmentManager;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (mContext == null)
        {
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        final ViewHolder holder=new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Chat chat = mChatlist.get(position);
            ShowChatDialog.newInstance("0",chat.getId(),chat.getFullChat(),chat.getName(),chat.getTime())
                    .setTheme(R.style.OMGDialogStyle)
                    .setMargin(0)
                    .setGravity(Gravity.BOTTOM)
                    .show(mFragmentManager);
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_recycler_item_show);
        holder.item_chat.startAnimation(animation);

        AlphaAnimation item_anim = new AlphaAnimation(0.1f, 1.0f);
        item_anim.setDuration(500);
        holder.chat_username.setAnimation(item_anim);
        holder.chat_time.setAnimation(item_anim);
        holder.chat_username.setAnimation(item_anim);
        holder.lookmore.setAnimation(item_anim);

        Chat chat=mChatlist.get(position);
        holder.chat_username.setText(chat.getName());
        holder.chat.setText(chat.getChat());
        holder.chat_time.setText(chat.getTime());
        if(chat.getShowAll()){
            holder.lookmore.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.lookmore.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount()
    {
        return mChatlist.size();
    }
}
