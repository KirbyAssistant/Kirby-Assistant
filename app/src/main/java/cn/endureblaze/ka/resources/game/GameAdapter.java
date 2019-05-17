package cn.endureblaze.ka.resources.game;
import android.content.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.*;
import java.util.*;
import cn.endureblaze.ka.utils.*;
import android.app.*;
import android.support.v4.util.*;
import cn.bmob.v3.b.*;
import android.support.v4.app.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.main.*;
import com.bumptech.glide.*;
import android.graphics.Bitmap;
import android.text.TextUtils;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private Context mContext;
    private List<Console> mGameList;

	private Activity mActivity;
    static class ViewHolder extends RecyclerView.ViewHolder {

		private CardView cardView;

		private LinearLayout LinearLayout;

		private ImageView gameImage;

		private TextView gameName;

		private ImageView blurImage;
        public ViewHolder(View view) {
            super(view);
			LinearLayout = (LinearLayout)view.findViewById(R.id.LinearLayout);
            cardView = (CardView) view.findViewById(R.id.cardview);
            gameImage = (ImageView) view.findViewById(R.id.console_image);
            gameName = (TextView) view.findViewById(R.id.console_text);
			blurImage = (ImageView) view.findViewById(R.id.blur_image);
        }
    }
    public GameAdapter(List<Console> gamelist, Activity activity) {
        mGameList = gamelist;
		mActivity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_console, parent, false);
		final ViewHolder holder=new ViewHolder(view);
		holder.LinearLayout.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					int position = holder.getAdapterPosition();
					Console game = mGameList.get(position);
					MainActivity m=new MainActivity();
					Intent mm=new Intent(mContext, GameActivity.class);
					mm.putExtra("game_name", game.getName());
					mm.putExtra("game_img", game.getImageUrl());
					mm.putExtra("game_pos", game.getPosition());
					Pair<View, String> card=new Pair<View,String>(view.findViewById(R.id.cardview), "card");
					Pair<View, String> image= new Pair<View,String>(view.findViewById(R.id.console_image), "image");
					Pair<View, String> name= new Pair<View,String>(view.findViewById(R.id.console_text), "name");
					mActivity.startActivity(mm, ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, card, image, name).toBundle());
					//m.theDownload(mContext, game.getName(),game.getPosition());
				}
			}
		);
		return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Console co = mGameList.get(position);
        holder.gameName.setText(co.getName());
		Glide
			.with(mContext)
			.load(co.getImageUrl())
			//.apply(Kirby.getGlideRequestOptions())d
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
    public int getItemCount() {
        return mGameList.size();
    }

}
