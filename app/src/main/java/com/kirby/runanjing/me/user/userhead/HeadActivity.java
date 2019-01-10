package com.kirby.runanjing.me.user.userhead;
import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.database.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.v4.content.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.bumptech.glide.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bmob.*;
import com.kirby.runanjing.utils.*;
//import com.yalantis.ucrop.*;
import java.io.*;

import com.kirby.runanjing.R;
import com.yalantis.ucrop.*;
import com.kirby.runanjing.base.*;
import android.*;
import android.support.v4.app.*;
import android.support.annotation.*;

public class HeadActivity extends BaseActivity
{
	private LocalBroadcastManager localBroadcastManager;
	public static final int CHOOSE_PHOTO = 1;
	private ImageView userHead;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ThemeUtil.setClassTheme(this);
		setContentView(R.layout.activity_head);
		localBroadcastManager = localBroadcastManager.getInstance(this);
		userHead = (ImageView)findViewById(R.id.user_head);
		Button choose_photo=(Button)findViewById(R.id.choose_photo);
		try
		{
			if (UserUtil.getCurrentUser().getUserHead().getFileUrl() != null)
			{
				Glide
					.with(this)
					.load(UserUtil.getCurrentUser().getUserHead().getFileUrl())
					.into(userHead);
			}
		}
		catch (Exception e)
		{}
		choose_photo.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if (ContextCompat.checkSelfPermission(HeadActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
					{
						ActivityCompat.requestPermissions(HeadActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
					}
					else
					{
						choosePhoto();
					}
				}
			});
	}
	private void choosePhoto()
	{
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
	    intent.setType("image/*");
		startActivityForResult(intent, CHOOSE_PHOTO);
    }
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        switch (requestCode)
		{
            case CHOOSE_PHOTO:
				if (resultCode == RESULT_OK)
				{
					startUcrop(data.getData());
				}
				//    getImagePathOpenUcrop(resultCode,data);
				break;

			case UCrop.REQUEST_CROP:
				try
				{
					final Uri croppedFileUri = UCrop.getOutput(data);
					final ProgressDialog progressDialog = new ProgressDialog(this);
					progressDialog.setMessage(getResources().getString(R.string.head_upload));
					progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.show();
					final BmobFile headFile=new BmobFile(new File(croppedFileUri.getPath()));
					headFile.uploadblock(new UploadFileListener(){
							@Override
							public void done(BmobException e)
							{
								if (e == null)
								{
									BmobKirbyAssistantUser newUser = new BmobKirbyAssistantUser();
									newUser.setUserHead(headFile);
									newUser.update(UserUtil.getCurrentUser().getObjectId(), new UpdateListener() {
											@Override
											public void done(BmobException e)
											{
												if (e == null)
												{
													displayImage(croppedFileUri.getPath());
												}
												else
												{
													Toast.makeText(HeadActivity.this, R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
												}
												progressDialog.dismiss();
											}
										});	
								}
								else
								{
									Toast.makeText(HeadActivity.this, R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
								}
							}
						});
				}
				catch (Exception e)
				{}
				break;
			default:
				break;
		}
	}
	private void startUcrop(Uri uri)
	{
        //裁剪后保存到文件中
        Uri destinationUri = Uri.fromFile(new File(HeadActivity.this.getCacheDir(), UserUtil.getCurrentUser().getUsername() + ".jpg"));
        UCrop uCrop = UCrop.of(uri, destinationUri);
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
		uCrop.withOptions(options);
		uCrop.withAspectRatio(1, 1);
		uCrop.start(this);
    }
    private void displayImage(String imagePath)
	{
        if (imagePath != null)
		{
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            userHead.setImageBitmap(bitmap);
			Intent intent = new Intent("com.kirby.download.CHANGE_USERHEAD");
			intent.putExtra("userHead", 1);
			localBroadcastManager.sendBroadcast(intent);
			this.finish();
        }
		else
		{
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
	@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
	{
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1)
		{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
			{
				if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					choosePhoto();
				}
				else
				{
					Toast.makeText(this, "权限被拒绝了", Toast.LENGTH_SHORT).show();
				}

				if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
				{
                    AskForPermission();
                }
            }
        }
    }
	private void AskForPermission()
	{
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Need Permission!");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{

				}
			});
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
					startActivity(intent);
				}
			});
        builder.create().show();
    }
}
