package cn.endureblaze.ka.utils;
import android.graphics.Bitmap;
import com.bumptech.glide.Glide;
import android.content.Context;
import java.util.concurrent.ExecutionException;

public class GlideUtil
{

	private static Bitmap glideBitmap;
	public static Bitmap getGlideBitmap(Context context,String url){
		try {
			glideBitmap = Glide.with(context)
				.load(url)
				.asBitmap() //必须
				.centerCrop()
				.into(500, 500)
				.get();
		} catch (InterruptedException e) {} catch (ExecutionException e) {}
		return glideBitmap;
	};
}
