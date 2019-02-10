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

import com.kirby.runanjing.base.*;
import android.support.v4.app.*;
import android.support.annotation.*;

public class HeadActivity extends BaseActivity
{
	private LocalBroadcastManager localBroadcastManager;
	public static final int CHOOSE_PHOTO = 1;
	public static final int CROP= 0;
	private ImageView userHead;

	private Bitmap imageBitmap;

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
					choosePhoto();
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
					//startUcrop(data.getData());
					CropImageDialog.newInstance(data.getData())
						.setTheme(R.style.BottomDialogStyle)
					    .setMargin(0)
						.setShowBottom(true)   
						.show(ActManager.currentFragmentActivity().getSupportFragmentManager());
				}
				//    getImagePathOpenUcrop(resultCode,data);
				break;
			default:
				break;
		}
	}
	public void cropImageOK()
	{
		SharedPreferences image=getSharedPreferences("string", 0);
		final String image_str= image.getString("image_str", null);
		Toast.makeText(this, image_str, Toast.LENGTH_SHORT).show();
		try
		{
			final ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(getResources().getString(R.string.head_upload));
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();
			final BmobFile headFile=new BmobFile(new File(Uri.parse(image_str).getPath()));
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
											displayImage(image_str);
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
	}
    public void displayImage(String imagePath)
	{
        if (imagePath != null)
		{
			Uri imageUri = Uri.parse(imagePath);
			try
			{
				 imageBitmap=MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
			}
			catch (IOException e)
			{}
			userHead.setImageBitmap(imageBitmap);
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
