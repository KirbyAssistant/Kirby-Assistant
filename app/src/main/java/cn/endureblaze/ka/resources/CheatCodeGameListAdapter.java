package cn.endureblaze.ka.resources;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.resources.cheatcode.CheatCodeActivity;
import cn.endureblaze.ka.utils.FastBlurUtil;
import cn.endureblaze.ka.utils.GlideUtil;
import cn.endureblaze.ka.utils.IntentUtil;
import com.bumptech.glide.Glide;
import java.util.List;

public class CheatCodeGameListAdapter extends RecyclerView.Adapter<CheatCodeGameListAdapter.ViewHolder>
{

	private List<Console> mCheatCodeGameList;
	private Context mContext;

	private Activity mActivity;
	static class ViewHolder extends RecyclerView.ViewHolder
	{
        LinearLayout LinearLayout;
		CardView cardView;
        ImageView gameImage;
        TextView gameName;

		private ImageView blurImage;
        public ViewHolder(View view)
		{
            super(view);
			LinearLayout = (LinearLayout)view.findViewById(R.id.LinearLayout);
            cardView = (CardView) view.findViewById(R.id.cardview);
			gameImage = (ImageView) view.findViewById(R.id.console_image);
            gameName = (TextView) view.findViewById(R.id.console_text);
			blurImage = (ImageView) view.findViewById(R.id.blur_image);
        }
    }

    public CheatCodeGameListAdapter(List<Console> cheatCodeGamelist,Activity activity)
	{
        mCheatCodeGameList = cheatCodeGamelist;
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
		holder.LinearLayout.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v)
				{
					int position=holder.getAdapterPosition();
					Console console=mCheatCodeGameList.get(position);
					Intent in=new Intent(mContext, CheatCodeActivity.class);
					String  input=console.getName().toString();	
					MainActivity m=new MainActivity();
					IntentUtil.startActivityWithAnim(in,mActivity);
					SharedPreferences.Editor t=mContext.getSharedPreferences("string", 0).edit();
					t.putString("金手指_游戏", input);
					t.apply();
				}
			}
		);
		return holder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position)
	{
		final Console co = mCheatCodeGameList.get(position);
        holder.gameName.setText(co.getName());
        Glide
			.with(mContext)
			.load(co.getImageUrl())
			//.apply(Kirby.getGlideRequestOptions())
			.asBitmap()
		    .fitCenter()
			.placeholder(R.drawable.ic_kirby_download)
			.error(R.drawable.ic_kirby_load_fail)
			.into(holder.gameImage);	
		try {
			new Thread(new Runnable() {

					String pattern="5";
					@Override
					public void run() {
						Bitmap glideBitmap=GlideUtil.getGlideBitmap(mContext, co.getImageUrl());
						int scaleRatio = 0;
						if (TextUtils.isEmpty(pattern)) {
							scaleRatio = 0;
						} else if (scaleRatio < 0) {
							scaleRatio = 10;
						} else {
							scaleRatio = Integer.parseInt(pattern);
						}
						//                        下面的这个方法必须在子线程中执行
						final Bitmap blurBitmap2 = FastBlurUtil.toBlur(glideBitmap, scaleRatio);

						//                   刷新ui必须在主线程中执行
						try {
							mActivity.runOnUiThread(new Runnable(){

									@Override
									public void run() {
										holder.blurImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
										holder.blurImage.setImageBitmap(blurBitmap2);
									}
								});
						} catch (Exception e) {}
					}
				}).start();
		} catch (Exception e) {}
		}

	@Override
	public int getItemCount()
	{
		return mCheatCodeGameList.size();
	}
	
}
