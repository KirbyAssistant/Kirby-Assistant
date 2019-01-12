package com.kirby.runanjing.utils;

import android.content.*;
import android.graphics.*;
import android.net.*;
import java.io.*;
import android.graphics.BitmapFactory.*;
import android.util.*;
import android.app.*;

public class BitmapUriUtil
{
	public static Uri bitmap2uri(Context c, Bitmap b)
	{
		File path = new File(c.getExternalCacheDir() + File.separator + UserUtil.getCurrentUser().getUsername() + ".jpg");
		try
		{
			OutputStream os = new FileOutputStream(path);
			b.compress(Bitmap.CompressFormat.JPEG, 100, os);
			os.close();
			return Uri.fromFile(path);
		}
		catch (Exception ignored)
		{
		}
		return null;
	}
	public static final Bitmap getBitmap(Context ctx, Uri url) throws FileNotFoundException, IOException{
	InputStream stream =ctx.getContentResolver().openInputStream(url);
		//BitmapFactory.Options options = null;
		Bitmap bitmap = BitmapFactory.decodeStream(stream);
		stream.close();
		return bitmap;
	}
	/**
     * 得到byte[]
     * LeanCloud上传文件是需要byte[]数组的
     * 这里对传入的图片Uri压缩，并转换为byte[]后返回
     *
     * @param activity 上下文
     * @param uri      传入图片的Uri
     * @return byte[]
     */
    public static Bitmap getCompressBitmap(Activity activity, Uri uri) throws IOException {
        //先进行尺寸压缩
        Bitmap bitmap = getBitmapFormUri(activity, uri);

        //再进行质量压缩
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);//100表示不压缩，直接放到out里面
        int options = 90;//压缩比例
        while (out.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            out.reset(); // 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, out);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        Log.e("压缩-提交", out.toByteArray().length + "");

		ByteArrayInputStream isBm = new ByteArrayInputStream(out.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap2 = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		
        return bitmap2;
    }


    /**
     * 图片尺寸压缩
     *
     * 宽度高度不一样：依靠规定的高或宽其一最大值来做界限
     * 高度宽度一样：依照规定的宽度压缩
     *
     * @param uri
     */
    public static Bitmap getBitmapFormUri(Activity ac, Uri uri) throws FileNotFoundException, IOException {
        InputStream input = ac.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以750x450为标准
        float hh = 500f;//这里设置高度为750f
        float ww = 500f;//这里设置宽度为450f
        float sq = 500f;//这里设置正方形为300f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        Log.e("缩放", originalWidth + "..." + originalHeight);
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大，根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高，根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        } else if (originalWidth == originalHeight && originalWidth > sq) {//如果高度和宽度一样，根据任意一边大小缩放
            //be = (int) (originalHeight / sq);
            be = (int) (originalWidth / sq);
        }
        if (be <= 0) {//如果缩放比比1小，那么保持原图不缩放
            be = 1;
        }
        Log.e("缩放", be + "");
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = ac.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return bitmap;//再进行质量压缩
    }
	
}
