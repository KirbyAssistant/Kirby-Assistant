package cn.endureblaze.kirby.user.info.avatar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.ednureblaze.glidecache.GlideCache;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseActivity;
import cn.endureblaze.kirby.bmob.BmobKirbyAssistantUser;
import cn.endureblaze.kirby.data.DataBus;
import cn.endureblaze.kirby.manager.ActManager;
import cn.endureblaze.kirby.util.ThemeUtil;
import cn.endureblaze.kirby.util.ToastUtil;
import cn.endureblaze.kirby.util.UserUtil;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UserAvatarActivity extends BaseActivity {
    private LocalBroadcastManager localBroadcastManager;
    public static final int CHOOSE_PHOTO = 1;
    public static final int CROP= 0;
    private ImageView userAvatar;
    private Bitmap imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ThemeUtil.setClassTheme(this);
        setContentView(R.layout.activity_user_avatar);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        userAvatar = findViewById(R.id.user_avatar);
        Button choose_photo= findViewById(R.id.choose_photo);
        try
        {
            if (UserUtil.getCurrentUser().getUserAvatar().getFileUrl() != null)
            {
                String url = UserUtil.getCurrentUser().getUserAvatar().getFileUrl();
                GlideCache.setNormalImageViaGlideCache(UserAvatarActivity.this,userAvatar, url);
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
                            .setTheme(R.style.OMGDialogStyle)
                            .setMargin(0)
                            .setGravity(Gravity.BOTTOM)
                            .show(ActManager.getCurrentFragmentActivity().getSupportFragmentManager());
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
            progressDialog.setMessage(getResources().getString(R.string.user_avatar_upload));
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
                        newUser.setUserAvatar(headFile);
                        newUser.update(UserUtil.getCurrentUser().getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e)
                            {
                                if (e == null)
                                {
                                    displayImage(image_str);
                                    DataBus.setChangeUserAvatar(true);
                                    ToastUtil.show(R.string.modify_success);
                                }
                                else
                                {
                                    ToastUtil.show(R.string.modify_fail);
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                    else
                    {
                        ToastUtil.show(R.string.modify_fail);
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
                imageBitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            }
            catch (IOException ignored)
            {}
            userAvatar.setImageBitmap(imageBitmap);
            Intent intent = new Intent("kirby.CHANGE_USER_AVATAR");
            intent.putExtra("type", "user_avatar");
            localBroadcastManager.sendBroadcast(intent);
        }
        else
        {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
