package cn.endureblaze.kirby.user.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.util.EmailUtil;
import cn.endureblaze.kirby.util.ToastUtil;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Register {
    private Activity activity;
    private TextInputEditText edit_username,edit_email,edit_password,edit_password_again,login_username,login_password;
    private CardView login_card,register_card;
    private TextView loginOrRegister;

    Register(Context context,
             Activity activity,
             TextInputEditText edit_username,
             TextInputEditText edit_email ,
             TextInputEditText edit_password,
             TextInputEditText edit_password_again,
             TextInputEditText login_username,
             TextInputEditText login_password ,
             CardView login_card,
             CardView register_card,
             TextView loginOrRegister){

        this.activity = activity;
        this.edit_email = edit_email;
        this.edit_username = edit_username;
        this.edit_password = edit_password;
        this.edit_password_again = edit_password_again;
        this.login_username = login_username;
        this.login_password = login_password;
        this.login_card = login_card;
        this.register_card = register_card;
        this.loginOrRegister = loginOrRegister;

    }
    void start(){
        ProgressDialog registerProgress;
        registerProgress = new ProgressDialog(activity);
        registerProgress.setCanceledOnTouchOutside(false);
        registerProgress.setMessage(activity.getResources().getString(R.string.user_register));
        registerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        registerProgress.show();
        //从实例化布局的edittext中获取字符串并转化为string数据

        final String str_username = Objects.requireNonNull(edit_username.getText()).toString();
        String str_email = Objects.requireNonNull(edit_email.getText()).toString();
        final String str_password = Objects.requireNonNull(edit_password.getText()).toString();
        String str_password_again = Objects.requireNonNull(edit_password_again.getText()).toString();
        //判断是否为空
        if (str_username.isEmpty() || str_email.isEmpty() || str_password.isEmpty() || str_password_again.isEmpty()) {
            registerProgress.dismiss();
            ToastUtil.show(R.string.is_null);
        } else {
            if (EmailUtil.checkEmail(str_email)) {
                registerProgress.dismiss();
                ToastUtil.show(R.string.user_email_fail);
            } else {
                //判断两次的密码是否一样
                if (str_password.equalsIgnoreCase(str_password_again)) {
                    //使用BmobUser类进行注册
                    BmobUser myUser = new BmobUser();
                    myUser.setUsername(str_username);
                    myUser.setPassword(str_password);
                    myUser.setEmail(str_email);
                    myUser.signUp(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser s, BmobException e) {
                            if (e == null) {
                                registerProgress.dismiss();
                                login_username.setText(str_username);
                                login_password.setText(str_password);
                                login_card.setVisibility(View.VISIBLE);
                                register_card.setVisibility(View.GONE);
                                loginOrRegister.setText(Objects.requireNonNull(activity).getResources().getString(R.string.user_not_have_user));
                                ToastUtil.show(R.string.user_register_success);
                            } else {
                                registerProgress.dismiss();
                                ToastUtil.show(R.string.user_register_fail);
                            }
                        }
                    });
                } else {
                    ToastUtil.show(R.string.user_modify_email_verify_fail);
                }
            }
        }
    }
}
