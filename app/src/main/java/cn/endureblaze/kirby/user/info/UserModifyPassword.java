package cn.endureblaze.kirby.user.info;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.util.ToastUtil;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

class UserModifyPassword {

    private Activity activity;
    private LocalBroadcastManager localBroadcastManager;

    UserModifyPassword(Activity activity, LocalBroadcastManager localBroadcastManager){
        this.activity = activity;
        this.localBroadcastManager = localBroadcastManager;
    }
    void start(){
        LayoutInflater lay_2 = Objects.requireNonNull(activity).getLayoutInflater();
        @SuppressLint("InflateParams") final View modification_password_layout = lay_2.inflate(R.layout.dialog_modify_password, null);
        new MaterialAlertDialogBuilder(activity)
                .setTitle(R.string.user_modify_password)
                .setView(modification_password_layout)
                .setPositiveButton(R.string.dia_yes, new
                        DialogInterface.OnClickListener()
                        {

                            private int text;

                            private ProgressDialog changepasswordProgress;
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                changepasswordProgress = new ProgressDialog(activity);
                                changepasswordProgress.setCanceledOnTouchOutside(false);
                                changepasswordProgress.setMessage(activity.getResources().getString(R.string.user_modify_password));
                                changepasswordProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                changepasswordProgress.show();
                                EditText modification_password_old= modification_password_layout.findViewById(R.id.modification_password_old);
                                EditText modification_password_new= modification_password_layout.findViewById(R.id.modification_password_new);
                                EditText modification_password_new_again= modification_password_layout.findViewById(R.id.modification_password_new_again);
                                String str_modification_password_old=modification_password_old.getText().toString();
                                String str_modification_password_new=modification_password_new.getText().toString();
                                String str_modification_password_new_again=modification_password_new_again.getText().toString();
                                if (str_modification_password_old.isEmpty() || str_modification_password_new.isEmpty() || str_modification_password_new_again.isEmpty()) {
                                    changepasswordProgress.dismiss();
                                    ToastUtil.show(R.string.is_null);
                                } else {
                                    if (str_modification_password_new.equals(str_modification_password_new_again)) {
                                        final BmobUser pas = new BmobUser();
                                        BmobUser.updateCurrentUserPassword(str_modification_password_old, str_modification_password_new, new UpdateListener(){
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    changepasswordProgress.dismiss();
                                                    ToastUtil.show(R.string.modify_success);
                                                    BmobUser.logOut();

                                                    Intent intent = new Intent("cn.endureblaze.kirby.USER_LOGOUT");
                                                    intent.putExtra("type", "user_logout");
                                                    localBroadcastManager.sendBroadcast(intent);
                                                } else {
                                                    changepasswordProgress.dismiss();
                                                    ToastUtil.show(activity.getResources().getString(R.string.modify_fail)+e.getMessage());
                                                }
                                            }
                                        });
                                    } else {
                                        ToastUtil.show(R.string.user_modify_password_verify_fail);
                                    }
                                }
                            }
                        }
                )
                .setNegativeButton(R.string.dia_cancel, null)
                .show();
    }
}
