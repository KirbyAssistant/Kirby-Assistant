package cn.endureblaze.ka.utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.concurrent.ExecutionException;
import android.app.Activity;

public class GlideUtil {
	private static Bitmap glideBitmap;
	public static Bitmap getGlideBitmap(Context context, String url) {
		try {
			glideBitmap = Glide.with(context)
				.load(url)
				.asBitmap() //必须
				.into(500, 500)
				.get();
		} catch (InterruptedException e) {} catch (ExecutionException e) {}
		return glideBitmap;
	};

	public static void setNormalImageVuaGlideCache(final Activity activity, final ImageView image, final String imageUrl) {
			new Thread(new Runnable() {
					@Override
					public void run() {
						try {
						final Bitmap glideBitmap=GlideUtil.getGlideBitmap(activity, imageUrl);
						
							activity.runOnUiThread(new Runnable(){
									@Override
									public void run() {
										image.setImageBitmap(glideBitmap);
									}
								});
						} catch (Exception e) {}
					}
				}).start();
	}

	public static void setBlurImageViaGlideCache(final Activity activity, final ImageView blurImage, final String imageUrl, final String pattern) {

		new Thread(new Runnable() {
				@Override
				public void run() {
					try{
					Bitmap glideBitmap=GlideUtil.getGlideBitmap(activity, imageUrl);
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
						activity.runOnUiThread(new Runnable(){

								@Override
								public void run() {
									blurImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
									blurImage.setImageBitmap(blurBitmap2);
								}
							});
					} catch (Exception e) {}
				}
			}).start();
	}
}