package cn.endureblaze.ka.me.user.userhead;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.ednureblaze.glidecache.GlideCache;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseActivity;
import cn.endureblaze.ka.bmob.BmobKirbyAssistantUser;
import cn.endureblaze.ka.manager.ActManager;
import cn.endureblaze.ka.utils.ThemeUtil;
import cn.endureblaze.ka.utils.UserUtil;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
		localBroadcastManager = LocalBroadcastManager.getInstance(this);
		userHead = findViewById(R.id.user_head);
		Button choose_photo= findViewById(R.id.choose_photo);
		try
		{
			if (UserUtil.getCurrentUser().getUserHead().getFileUrl() != null)
			{
				GlideCache.setNormalImageViaGlideCache(HeadActivity.this,userHead, UserUtil.getCurrentUser().getUserHead().getFileUrl());
			}
		}
		catch (Exception ignored)
		{}
		choose_photo.setOnClickListener(p1 -> choosePhoto());
	}
	private void choosePhoto()
	{
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
	    intent.setType("image/*");
		startActivityForResult(intent, CHOOSE_PHOTO);
    }
	@SuppressLint("MissingSuperCall")
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        switch (requestCode)
		{
            case CHOOSE_PHOTO:
				if (resultCode == RESULT_OK)
				{
					CropImageDialog.newInstance(Objects.requireNonNull(data.getData()))
						.setTheme(R.style.BottomDialogStyle)
					    .setMargin(0)
						.setShowBottom(true)
						.show(ActManager.currentFragmentActivity().getSupportFragmentManager());
				}
				break;
			default:
				break;
		}
	}
	public void cropImageOK()
	{
		SharedPreferences image=getSharedPreferences("string", 0);
		final String image_str= image.getString("image_str", null);
		try
		{
			final ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setMessage(getResources().getString(R.string.head_upload));
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.show();
			final BmobFile headFile=new BmobFile(new File(Objects.requireNonNull(Uri.parse(image_str).getPath())));
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
		catch (Exception ignored)
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
			catch (IOException ignored)
			{}
			userHead.setImageBitmap(imageBitmap);
			ActManager.finishActivity();
			Intent intent = new Intent("com.kirby.download.CHANGE_USERHEAD");
			intent.putExtra("userHead", 1);
			localBroadcastManager.sendBroadcast(intent);
        }
		else
		{
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
