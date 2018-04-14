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
import android.widget.*;
import android.support.v7.app.*;
import android.content.*;

public class MainUserFragment extends Fragment
{
	private View view;
	private MainActivity m;
	public static final int CHOOSE_PHOTO = 2;
	private MyUser u;

	private ImageView userHead;

	private String name;

	private String email;

	private String id;
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
		name = u.getUsername();
		email = u.getEmail();
		id = u.getObjectId();
		TextView userName=(TextView)view.findViewById(R.id.user_name);
		TextView userId=(TextView)view.findViewById(R.id.user_id);
		TextView userTime=(TextView)view.findViewById(R.id.user_data);
		TextView userEmail=(TextView)view.findViewById(R.id.user_email);
		Button edit_email=(Button)view.findViewById(R.id.edit_email);
		Button edit_password=(Button)view.findViewById(R.id.edit_password);
		edit_email.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					userEditEmail();
				}			
			});
		edit_password.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					userEditPassword();
				}
			});
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
		userTime.setText(getActivity().getResources().getString(R.string.register_time)+":" + u.getCreatedAt());
		userEmail.setText(getActivity().getResources().getString(R.string.user_email)+":"+u.getEmail());
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
				try
				{
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
													Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
												}
												progressDialog.dismiss();
											}
										});	
								}
								else
								{
									Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        Uri destinationUri = Uri.fromFile(new File(getActivity().getExternalCacheDir(), u.getUsername() + ".jpg"));
        UCrop uCrop = UCrop.of(uri_crop, destinationUri);
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
		uCrop.withOptions(options);
		uCrop.withAspectRatio(1, 1);
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
	private void userEditEmail()
	{
		LayoutInflater lay_1 =getActivity().getLayoutInflater();
		final View modification_email_layout = lay_1.inflate(R.layout.dialog_modification_email, null);
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.email_title)
			.setView(modification_email_layout) 
			.setPositiveButton(R.string.dia_yes, new
			DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					EditText 修改邮箱_原邮箱=(EditText)modification_email_layout.findViewById(R.id.修改邮箱_原邮箱);
					EditText 修改邮箱_新邮箱=(EditText)modification_email_layout.findViewById(R.id.修改邮箱_新邮箱);
					String edit_原邮箱=修改邮箱_原邮箱.getText().toString();
					String edit_新邮箱=修改邮箱_新邮箱.getText().toString();
					if (edit_原邮箱.isEmpty() || edit_新邮箱.isEmpty())
					{
						Toast.makeText(getActivity(), R.string.is_null, Toast.LENGTH_SHORT).show();
					}
					else
					{
						if (email.equals(edit_原邮箱))
						{
							MyUser 邮箱=new MyUser();
							邮箱.setEmail(edit_新邮箱);
							邮箱.update(id, new UpdateListener() {

									@Override
									public void done(BmobException e)
									{
										if (e == null)
										{
											Toast.makeText(getActivity(), R.string.edit_true, Toast.LENGTH_SHORT).show();
											u.logOut();
											//finish();
											m.open();
										}
										else
										{
											Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
										}
									}

								});
						}
						else
						{
							Toast.makeText(getActivity(), R.string.email_false, Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		)					
			.setNegativeButton(R.string.dia_cancel, null)
			.show();
	}
	private void userEditPassword()
	{
		LayoutInflater lay_2 =getActivity().getLayoutInflater();
		final View modification_password_layout = lay_2.inflate(R.layout.dialog_modification_password, null);
		new AlertDialog.Builder(getActivity())
			.setTitle(R.string.password_title)
			.setView(modification_password_layout) 
			.setPositiveButton(R.string.dia_yes, new
			DialogInterface.OnClickListener()
			{

				private int text;
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					EditText 修改密码_原密码=(EditText)modification_password_layout.findViewById(R.id.修改密码_原密码);
					EditText 修改密码_新密码=(EditText)modification_password_layout.findViewById(R.id.修改密码_新密码);
					EditText 修改密码_验证=(EditText)modification_password_layout.findViewById(R.id.修改密码_验证);
					String edit_原密码=修改密码_原密码.getText().toString();
					String edit_新密码=修改密码_新密码.getText().toString();
					String edit_验证=修改密码_验证.getText().toString();
					if (edit_原密码.isEmpty() || edit_新密码.isEmpty() || edit_验证.isEmpty())
					{
						Toast.makeText(getActivity(), R.string.is_null, Toast.LENGTH_SHORT).show();
					}
					else
					{
						if (edit_新密码.equals(edit_验证))
						{
							final MyUser pas = new MyUser();
							pas.updateCurrentUserPassword(edit_原密码, edit_新密码, new UpdateListener(){
									@Override
									public void done(BmobException e)
									{
										if (e == null)
										{
											Toast.makeText(getActivity(), R.string.edit_true, Toast.LENGTH_SHORT).show();
											u.logOut();
											//finish();
											m.open();
										}
										else
										{
											Toast.makeText(getActivity(), R.string.edit_false + e.getMessage(), Toast.LENGTH_SHORT).show();
										}
									}
								});
						}
						else
						{
							Toast.makeText(getActivity(), R.string.password_false, Toast.LENGTH_SHORT).show();
						}
					}
				}
			}
		)					
			.setNegativeButton(R.string.dia_cancel, null)
			.show();
	}
}
