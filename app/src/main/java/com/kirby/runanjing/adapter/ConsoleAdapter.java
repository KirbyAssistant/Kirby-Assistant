package com.kirby.runanjing.adapter;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.bumptech.glide.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.activity.*;
import com.kirby.runanjing.untils.*;
import java.util.*;
import com.kirby.runanjing.bean.*;

public class ConsoleAdapter extends RecyclerView.Adapter<ConsoleAdapter.ViewHolder>
{
    private Context mContext;
    private List<Console> mConsoleList;
    static class ViewHolder extends RecyclerView.ViewHolder
	{
        LinearLayout LinearLayout;
		CardView cardView;
        ImageView consoleImage;
        TextView consoleName;
        public ViewHolder(View view)
		{
            super(view);
			LinearLayout = (LinearLayout)view.findViewById(R.id.LinearLayout);
            cardView = (CardView) view.findViewById(R.id.cardview);
			consoleImage = (ImageView) view.findViewById(R.id.console_image);
            consoleName = (TextView) view.findViewById(R.id.console_text);
        }
    }

    public ConsoleAdapter(List<Console> consolelist)
	{
        mConsoleList = consolelist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
        if (mContext == null)
		{
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_console, parent, false);
		final ViewHolder holder=new ViewHolder(view);
		holder.LinearLayout.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v)
				{
					int position=holder.getAdapterPosition();
					Console console=mConsoleList.get(position);
					Intent in=new Intent(mContext, GameListActivity.class);
					String  input=console.getName().toString();	
					mContext.startActivity(in);
					SharedPreferences.Editor t=mContext.getSharedPreferences("string", 0).edit();
					t.putString("主机名称", input);
					t.apply();
				}
			}
		);
		return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
	{
        Console co = mConsoleList.get(position);
        holder.consoleName.setText(co.getName());
        Glide
			.with(mContext)
			.load(co.getImageUrl())
			.placeholder(R.drawable.ic_download)
			.error(R.drawable.ic_close_circle_outline)
			.into(holder.consoleImage);
			
		}

    @Override
    public int getItemCount()
	{
        return mConsoleList.size();
    }
}
