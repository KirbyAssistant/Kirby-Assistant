package com.kirby.runanjing.adapter;
import android.app.*;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.bean.*;
import java.util.*;
import com.kirby.runanjing.untils.*;
import android.support.v4.app.*;
import android.support.v4.util.*;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
	private Context mContext;
	private List<Mess> mMesslist;

	private Activity mActivity;

	static class ViewHolder extends RecyclerView.ViewHolder
	{
		RelativeLayout relativelayout;
		TextView 用户名;
		TextView 内容;
		TextView 时间;
	    TextView 查看更多;

		private ImageView 头像;
		

		public ViewHolder(View view)
		{
			super(view);
			relativelayout = (RelativeLayout)view.findViewById(R.id.messageitemRelativeLayout1);
			用户名 = (TextView)view.findViewById(R.id.用户名);
			内容 = (TextView)view.findViewById(R.id.内容);
			时间 = (TextView)view.findViewById(R.id.时间);
			查看更多 = (TextView)view.findViewById(R.id.show_all);
		}
	}
	public MessageAdapter(List<Mess>messlist,Activity activity)
	{
		mMesslist = messlist;
		mActivity=activity;
	}
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		if (mContext == null)
		{
			mContext = parent.getContext();
		}
		final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
		final ViewHolder holder=new ViewHolder(view);
        holder.relativelayout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int position = holder.getAdapterPosition();
					Mess mess = mMesslist.get(position);
					Intent intent = new Intent(mContext, MessActivity.class);
					intent.putExtra(MessActivity.USER_NAME, mess.getName());
					intent.putExtra(MessActivity.TIME, mess.getTime());
					intent.putExtra(MessActivity.MESS, mess.getFullMessage());
					Pair<View, String> card=new Pair<View,String>(view.findViewById(R.id.用户名), "userName");
					Pair<View, String> image= new Pair<View,String>(view.findViewById(R.id.时间), "timr");
					Pair<View, String> name= new Pair<View,String>(view.findViewById(R.id.内容), "mess");
					mActivity.startActivity(intent,ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,card,image,name).toBundle());
					//IntentUtil.startActivityWithAnim(intent,mActivity);
					}
			});
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position)
	{
		Mess mess=mMesslist.get(position);
		holder.用户名.setText(mess.getName());
		holder.内容.setText(mess.getMessage());
		holder.时间.setText(mess.getTime());
		if(mess.getShowAll()){
			holder.查看更多.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.查看更多.setVisibility(View.GONE);
		}
	}

	@Override
	public int getItemCount()
	{
		return mMesslist.size();
	}
}
