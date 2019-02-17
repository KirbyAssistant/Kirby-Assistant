package cn.endureblaze.ka.me.user.userhead;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.support.annotation.*;
import android.view.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.bottomdialog.*;
import cn.endureblaze.ka.nocropper.*;
import android.provider.*;
import java.io.*;
import android.widget.*;
import cn.endureblaze.ka.utils.*;

public class CropImageDialog extends BaseBottomDialog
{
	private Uri imageUri;
	private Bitmap imageBitmap;
	//private CropImageView mCropImageView;

	public static CropImageDialog newInstance(Uri imageUri)
	{
		Bundle bundle = new Bundle();
		bundle.putString("imageuri", imageUri.toString());
		CropImageDialog dialog = new CropImageDialog();
		dialog.setArguments(bundle);
		return dialog;
	}

	@Override
    public int initTheme()
	{
        return theme;
    }

	public CropImageDialog setTheme(@StyleRes int theme)
	{
        this.theme = theme;
        return this;
    }

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		imageUri = Uri.parse(bundle.getString("imageuri"));
		try
		{
			imageBitmap = BitmapUriUtil.getBitmap(getActivity(),imageUri);
		}
		catch (IOException e)
		{}
	}

	@Override
	public int intLayoutId()
	{
		return R.layout.dialog_cropimage;
	}

	@Override
	public void convertView(final ViewHolder holder, final BaseBottomDialog dialog)
	{
		final CropperView mCropImageView=(CropperView)holder.getView(R.id.CropImageView);
		mCropImageView.setImageBitmap(imageBitmap);//为了兼容小图片，必须在代码中加载图片
		Button cropOK=(Button)holder.getView(R.id.cropimage_ok);
		cropOK.setOnClickListener(new View.OnClickListener(){

				private Uri corpImageUri;
				@Override
				public void onClick(View p1)
				{
					//Uri corpImageUri=
					Uri corpImageUriNoCompress=BitmapUriUtil.bitmap2uri(getActivity(), mCropImageView.getCroppedBitmap().getBitmap());
					try
					{
						corpImageUri = BitmapUriUtil.bitmap2uri(getActivity(), BitmapUriUtil.getCompressBitmap(ActManager.currentActivity(), corpImageUriNoCompress));
					}
					catch (IOException e)
					{}
					SharedPreferences y=getActivity().getSharedPreferences("string", 0);
					SharedPreferences.Editor edit=y.edit();
					edit.putString("image_str", corpImageUri.toString());
					edit.apply();
					HeadActivity head=(HeadActivity)getActivity();
					head.cropImageOK();
					dialog.dismiss();
				}
			});
	}
	/**
	 * @param resId
	 * @return 如果图片太小，那么就拉伸
	 */
	public Bitmap getBitmap(int resId)
	{
		WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		Bitmap bitmap= BitmapFactory.decodeResource(getResources(), resId);
		float scaleWidth = 1,scaleHeight = 1;
		if (bitmap.getWidth() < width)
		{
			scaleWidth = width / bitmap.getWidth();
			scaleHeight = scaleWidth;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()
									 , matrix, true);
		return bitmap;
	}
}