package com.kirby.runanjing.fragment.main;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import com.kirby.runanjing.R;
import com.kirby.runanjing.activity.MainActivity;
import com.kirby.runanjing.bmob.MyUser;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import java.io.File;
import android.text.TextUtils;
import com.bumptech.glide.Glide;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.exception.BmobException;
import java.net.URL;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;
import android.app.ProgressDialog;

public class MainUserFragment extends Fragment
{
	private View view;
	private MainActivity m;
	public static final int CHOOSE_PHOTO = 2;
	private MyUser u;

	private ImageView userHead;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        view = inflater.inflate(R.layout.main_user, container, false);
		m = (MainActivity)getActivity();
		u = BmobUser.getCurrentUser(MyUser.class);
		initUser(view);
		return view;
	}

	private void initUser(View view)
	{
		TextView userName=(TextView)view.findViewById(R.id.user_name);
		TextView userId=(TextView)view.findViewById(R.id.user_id);
		TextView userTime=(TextView)view.findViewById(R.id.user_data);
		userHead = (ImageView)view.findViewById(R.id.user_head);
		try
		{
			if (u.getUserHead().getFileUrl() != null)
			{
				Glide
					.with(getContext())
					.load(u.getUserHead().getFileUrl())
					.into(userHead);
			}
		}
		catch (Exception e)
		{}
		userName.setText(u.getUsername());
		userId.setText("id:" + u.getObjectId());
		userTime.setText("注册于:" + u.getCreatedAt());
		userHead.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
					{
						ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
					}
					else
					{
						openAlbum();
					}
				}
			});
	}
	private void openAlbum()
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
                    openAlbum();
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
            case CHOOSE_PHOTO:
                if (resultCode == getActivity().RESULT_OK)
				{
					try
					{
						if (data != null)
						{
							Uri uri = data.getData();
							if (!TextUtils.isEmpty(uri.getAuthority()))
							{
								Cursor cursor = getActivity().getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
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
			    final Uri croppedFileUri = UCrop.getOutput(data);
				final ProgressDialog progressDialog = new ProgressDialog(getActivity());
				progressDialog.setMessage(getResources().getString(R.string.head_upload));
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.show();
			final BmobFile headFile=new BmobFile(new File(croppedFileUri.getPath()));
				headFile.uploadblock(new UploadFileListener(){
						@Override
						public void done(BmobException e)
						{
							if(e == null){
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
												Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
											}
											progressDialog.dismiss();
										}
									});	
							}else{
								Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
							}
						}
					});
				break;
			default:
				break;
		}
	}
	private void startUcrop(String path)
	{
        Uri uri_crop = Uri.parse(path);
        //裁剪后保存到文件中
        Uri destinationUri = Uri.fromFile(new File(getActivity().getExternalCacheDir(), u.getUsername() + ".jpg"));
        UCrop uCrop = UCrop.of(uri_crop, destinationUri);
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
		uCrop.withOptions(options);
		uCrop.start(getContext(), MainUserFragment.this);
    }
    private void displayImage(String imagePath)
	{
        if (imagePath != null)
		{
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            userHead.setImageBitmap(bitmap);
        }
		else
		{
            Toast.makeText(getActivity(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
