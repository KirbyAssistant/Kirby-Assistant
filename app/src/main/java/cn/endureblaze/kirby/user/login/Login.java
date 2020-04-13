package cn.endureblaze.kirby.user.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.util.ToastUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

class Login {
    private LocalBroadcastManager user_login_broad;

    private Context context;
    private Activity activity;
    private TextInputEditText edit_username;
    private TextInputEditText edit_password;

    Login(Context context,
          Activity activity,
          TextInputEditText edit_username,
          TextInputEditText edit_password)
    {
        this.context = context;
        this.activity = activity;
        this.edit_username = edit_username;
        this.edit_password = edit_password;
    }

    void start() {
        user_login_broad = LocalBroadcastManager.getInstance(Objects.requireNonNull(context));
        ProgressDialog loginProgress;
        loginProgress = new ProgressDialog(activity);
        loginProgress.setCanceledOnTouchOutside(false);
        loginProgress.setMessage(activity.getResources().getString(R.string.user_login));
        loginProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loginProgress.show();
        //获取的字符串转化为string数据类型
        String str_username = Objects.requireNonNull(edit_username.getText()).toString();
        String str_passworld = Objects.requireNonNull(edit_password.getText()).toString();
        //判断是否为空
        if (str_passworld.isEmpty() || str_username.isEmpty()) {
            loginProgress.dismiss();
            ToastUtil.show(R.string.is_null);
        } else {
            //使用BmobUser类进行登陆
            final BmobUser bu2 = new BmobUser();
            bu2.setUsername(str_username);
            bu2.setPassword(str_passworld);
            bu2.login(new SaveListener<BmobUser>() {
                @Override
                public void done(BmobUser bmobUser, BmobException e) {
                    if (e == null) {
                        loginProgress.dismiss();
                        ToastUtil.show(R.string.user_login_success);
                        Intent intent = new Intent("cn.endureblaze.kirby.USER_LOGIN");
                        intent.putExtra("type", "user_login");
                        user_login_broad.sendBroadcast(intent);
                    } else {
                        loginProgress.dismiss();
                        ToastUtil.show(R.string.load_fail);
                    }
                }
            });
        }
    }
}
