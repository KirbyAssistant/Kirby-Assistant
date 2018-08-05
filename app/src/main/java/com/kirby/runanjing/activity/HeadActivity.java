package com.kirby.runanjing.activity;
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
import com.kirby.runanjing.untils.*;
//import com.yalantis.ucrop.*;
import java.io.*;

import com.kirby.runanjing.R;
import com.yalantis.ucrop.*;

public class HeadActivity extends BaseActivity
{
	private LocalBroadcastManager localBroadcastManager;
	private MyUser u;
	public static final int TAKE_PHOTO = 1;
	public static final int CHOOSE_PHOTO = 2;
	private ImageView userHead;

	private Uri imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Theme.setClassTheme(this);
		setContentView(R.layout.activity_head);
		localBroadcastManager = localBroadcastManager.getInstance(this);
		u = BmobUser.getCurrentUser(MyUser.class);
		userHead = (ImageView)findViewById(R.id.user_head);
		Button choose_photo=(Button)findViewById(R.id.choose_photo);
		Button take_photo = (Button)findViewById(R.id.take_photo);	
		try
		{
			if (u.getUserHead().getFileUrl() != null)
			{
				Glide
					.with(this)
					.load(u.getUserHead().getFileUrl())
					.into(userHead);
			}
		}
		catch (Exception e)
		{}
		take_photo.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					takePhoto();
					//Toast.makeText(HeadActivity.this,"未开放",Toast.LENGTH_LONG).show();
				}			
			});
		choose_photo.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					choosePhoto();
				}
			});
	}
	private void takePhoto()
	{
		// 创建File对象，用于存储拍照后的图片
		File outputImage = new File(getExternalCacheDir()+"output.jpg");
		try
		{
			if (outputImage.exists())
			{
				outputImage.delete();
			}
			outputImage.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		if (Build.VERSION.SDK_INT < 24)
		{
			imageUri = Uri.fromFile(outputImage);
		}
		else
		{
			imageUri = FileProvider.getUriForFile(this, "com.kirby.runanjing.fileprovider", outputImage);
		}
		// 启动相机程序
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, TAKE_PHOTO);

	}
	private void choosePhoto()
	{
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
	    intent.setType("image/*");
		startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
        switch (requestCode)
		{
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
                    choosePhoto();
                }
                break;
            default:
        }
    }
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        switch (requestCode)
		{
			case TAKE_PHOTO:
                    try
					{
						String path = "file://" + getExternalCacheDir() + "output.jpg";
						//启动裁剪界面，配置裁剪参数
						startUcrop(path);
                    }
					catch (Exception e)
					{
                        e.printStackTrace();
                }
				break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK)
				{
					try
					{
						if (data != null)
						{
							Uri uri = data.getData();
							if (!TextUtils.isEmpty(uri.getAuthority()))
							{
								Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
								if (null == cursor)
								{
									return;
								}
								cursor.moveToFirst();
								//拿到了照片的path
								String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
								cursor.close();
								path = "file://" + path;
								//启动裁剪界面，配置裁剪参数
								startUcrop(path);
							}
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
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
									MyUser newUser = new MyUser();
									newUser.setUserHead(headFile);
									newUser.update(u.getObjectId(), new UpdateListener() {
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
	private void startUcrop(String path)
	{
        Uri uri_crop = Uri.parse(path);
        //裁剪后保存到文件中
        Uri destinationUri = Uri.fromFile(new File(HeadActivity.this.getExternalCacheDir(), u.getUsername() + ".jpg"));
        UCrop uCrop = UCrop.of(uri_crop, destinationUri);
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
}
