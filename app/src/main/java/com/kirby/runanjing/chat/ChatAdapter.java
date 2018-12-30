package com.kirby.runanjing.chat;
import android.app.*;
import android.content.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bean.*;
import java.util.*;

import android.support.v4.app.FragmentManager;
import com.kirby.runanjing.utils.*;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>
{
	private Context mContext;
	private List<Chat> mChatlist;

	private Activity mActivity;

	private FragmentManager mFragmentManager;

	static class ViewHolder extends RecyclerView.ViewHolder
	{
		RelativeLayout relativelayout;
		TextView chat_username;
		TextView chat;
		TextView chat_time;
	    TextView lookmore;

		private ImageView 头像;
		

		public ViewHolder(View view)
		{
			super(view);
			relativelayout = (RelativeLayout)view.findViewById(R.id.chatitemRelativeLayout1);
			chat_username = (TextView)view.findViewById(R.id.chat_username);
			chat = (TextView)view.findViewById(R.id.chat);
			chat_time = (TextView)view.findViewById(R.id.chat_time);
			lookmore = (TextView)view.findViewById(R.id.show_all);
		}
	}
	public ChatAdapter(List<Chat>chatlist,Activity activity,FragmentManager fragmentManager)
	{
		mChatlist = chatlist;
		mActivity=activity;
		mFragmentManager=fragmentManager;
	}
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		if (mContext == null)
		{
			mContext = parent.getContext();
		}
		final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
		final ViewHolder holder=new ViewHolder(view);
        holder.relativelayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = holder.getAdapterPosition();
					Chat chat = mChatlist.get(position);			
					ChatDialog.newInstance("0",chat.getId(),chat.getFullChat(),chat.getName(),chat.getTime())
					.setTheme(R.style.BottomDialogStyle)
					.setMargin(0)
					.setShowBottom(true)   
					.show(mFragmentManager);
				}
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
