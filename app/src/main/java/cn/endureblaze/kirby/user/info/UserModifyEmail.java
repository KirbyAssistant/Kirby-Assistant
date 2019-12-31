package cn.endureblaze.kirby.user.info;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.util.EmailUtil;
import cn.endureblaze.kirby.util.ToastUtil;
import cn.endureblaze.kirby.util.UserUtil;

import java.util.Objects;

class UserModifyEmail {
    private Activity activity;

    UserModifyEmail(Activity activity){
        this.activity = activity;
    }

    void start(){
        LayoutInflater lay_1 = Objects.requireNonNull(activity).getLayoutInflater();
        @SuppressLint("InflateParams") final View modification_email_layout = lay_1.inflate(R.layout.dialog_modify_email, null);
        new AlertDialog.Builder(activity)
                .setTitle(R.string.user_modify_email)
                .setView(modification_email_layout)
                .setPositiveButton(R.string.dia_yes, new
                        DialogInterface.OnClickListener()
                        {
                            private ProgressDialog modificationEmailProgress;
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                modificationEmailProgress = new ProgressDialog(activity);
                                modificationEmailProgress.setCanceledOnTouchOutside(false);
                                modificationEmailProgress.setMessage(activity.getResources().getString(R.string.user_modify_email));
                                modificationEmailProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                modificationEmailProgress.show();

                                EditText modification_email_old= modification_email_layout.findViewById(R.id.modification_email_old);
                                EditText modification_email_new= modification_email_layout.findViewById(R.id.modification_email_new);
                                String str_modification_email_old=modification_email_old.getText().toString();
                                String str_modification_email_new=modification_email_new.getText().toString();

                                if (str_modification_email_old.isEmpty() || str_modification_email_new.isEmpty()) {

                                    modificationEmailProgress.dismiss();
                                    ToastUtil.show(R.string.is_null);

                                } else {
                                    if (EmailUtil.checkEmail(str_modification_email_old) || EmailUtil.checkEmail(str_modification_email_new)) {

                                        modificationEmailProgress.dismiss();
                                        ToastUtil.show(R.string.user_modify_email_verify_fail);

                                    } else {

                                        if (UserUtil.getCurrentUser().getEmail().equals(str_modification_email_old)) {

                                            BmobUser modification_email=new BmobUser();
                                            modification_email.setEmail(str_modification_email_new);
                                            modification_email.update(UserUtil.getCurrentUser().getObjectId(), new UpdateListener() {

                                                @Override
                                                public void done(BmobException e) {
                                                    if (e == null) {

                                                        modificationEmailProgress.dismiss();
                                                        ToastUtil.show(R.string.modify_success);
                                                        BmobUser.logOut();
                                                        // TODO: 2019/8/14

                                                    } else {

                                                        modificationEmailProgress.dismiss();
                                                        ToastUtil.show(activity.getResources().getString(R.string.modify_fail)+e.getMessage());

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
                )
                .setNegativeButton(R.string.dia_cancel, null)
                .show();
    }
}
