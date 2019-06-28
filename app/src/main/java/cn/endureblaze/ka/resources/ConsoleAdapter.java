package cn.endureblaze.ka.resources;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.endureblaze.ka.Kirby;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.resources.game.GameListActivity;
import cn.endureblaze.ka.utils.IntentUtil;
import com.bumptech.glide.Glide;
import java.util.List;

public class ConsoleAdapter extends RecyclerView.Adapter<ConsoleAdapter.ViewHolder>
{
    private Context mContext;
    private List<Console> mConsoleList;

	private Activity mActivity;
    static class ViewHolder extends RecyclerView.ViewHolder
	{
        LinearLayout LinearLayout;
		CardView cardView;
        ImageView consoleImage;
        TextView consoleName;

		private ImageView blurImage;
        public ViewHolder(View view)
		{
            super(view);
			LinearLayout = view.findViewById(R.id.LinearLayout);
            cardView = view.findViewById(R.id.cardview);
			consoleImage = view.findViewById(R.id.console_image);
            consoleName = view.findViewById(R.id.console_text);
			blurImage = view.findViewById(R.id.blur_image);
        }
    }

    public ConsoleAdapter(List<Console> consolelist,Activity activity)
	{
        mConsoleList = consolelist;
		mActivity=activity;
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
		holder.LinearLayout.setOnClickListener(v -> {
			int position=holder.getAdapterPosition();
			Console console=mConsoleList.get(position);
			Intent in=new Intent(mContext, GameListActivity.class);
			String name=console.getPosition();
			in.putExtra("consose_name",name);
			MainActivity m=new MainActivity();
			IntentUtil.startActivityWithAnim(in,mActivity);
		}
		);
		return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
	{
        final Console co = mConsoleList.get(position);
        holder.consoleName.setText(co.getName());
        Glide
			.with(mContext)
			.load(co.getImageUrl())
			.apply(Kirby.getGlideRequestOptions())
			.into(holder.consoleImage);		
		//GlideUtil.setBlurImageViaGlideCache(mActivity,holder.blurImage,co.getImageUrl(),"5");
		}
    @Override
    public int getItemCount()
	{
        return mConsoleList.size();
    }
}
