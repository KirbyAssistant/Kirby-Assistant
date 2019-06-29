package cn.endureblaze.ka.me.user.userhead;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bottomdialog.BaseBottomDialog;
import cn.endureblaze.ka.bottomdialog.ViewHolder;
import cn.endureblaze.ka.manager.ActManager;
import cn.endureblaze.ka.nocropper.CropperView;
import cn.endureblaze.ka.utils.BitmapUriUtil;
import java.io.IOException;
import java.util.Objects;

public class CropImageDialog extends BaseBottomDialog
{
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
	public void onCreate(@Nullable Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
        Uri imageUri = Uri.parse(Objects.requireNonNull(bundle).getString("imageuri"));
        try {
            imageBitmap = BitmapUriUtil.getBitmap(Objects.requireNonNull(getActivity()), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public int intLayoutId()
	{
		return R.layout.dialog_cropimage;
	}

	@Override
	public void convertView(final ViewHolder holder, final BaseBottomDialog dialog)
	{
		final CropperView mCropImageView= holder.getView(R.id.CropImageView);
		mCropImageView.setImageBitmap(imageBitmap);//为了兼容小图片，必须在代码中加载图片
		Button cropOK= holder.getView(R.id.cropimage_ok);
		cropOK.setOnClickListener(new View.OnClickListener(){

				private Uri corpImageUri;
				@Override
				public void onClick(View p1)  {
					Uri corpImageUriNoCompress=BitmapUriUtil.bitmap2uri(Objects.requireNonNull(getActivity()), mCropImageView.getCroppedBitmap().getBitmap());
                    try {
                        corpImageUri = BitmapUriUtil.bitmap2uri(getActivity(), BitmapUriUtil.getCompressBitmap(ActManager.currentActivity(), corpImageUriNoCompress));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
		WindowManager wm = (WindowManager) Objects.requireNonNull(getActivity()).getSystemService(Context.WINDOW_SERVICE);
		int width = Objects.requireNonNull(wm).getDefaultDisplay().getWidth();
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
