package cn.endureblaze.ka.chat;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Chat;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>
{
	private Context mContext;
	private List<Chat> mChatlist;

    private FragmentManager mFragmentManager;

	static class ViewHolder extends RecyclerView.ViewHolder
	{
		RelativeLayout relativelayout;
		TextView chat_username;
		TextView chat;
		TextView chat_time;
	    TextView lookmore;

		private ImageView user_head;
		

		public ViewHolder(View view)
		{
			super(view);
			relativelayout = view.findViewById(R.id.chatitemRelativeLayout1);
			chat_username = view.findViewById(R.id.chat_username);
			chat = view.findViewById(R.id.chat);
			chat_time = view.findViewById(R.id.chat_time);
			lookmore = view.findViewById(R.id.show_all);
		}
	}
	public ChatAdapter(List<Chat>chatlist,Activity activity,FragmentManager fragmentManager)
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
        holder.relativelayout.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            Chat chat = mChatlist.get(position);
            ChatDialog.newInstance("0",chat.getId(),chat.getFullChat(),chat.getName(),chat.getTime())
            .setTheme(R.style.BottomDialogStyle)
            .setMargin(0)
            .setShowBottom(true)
            .show(mFragmentManager);
        });
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
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
