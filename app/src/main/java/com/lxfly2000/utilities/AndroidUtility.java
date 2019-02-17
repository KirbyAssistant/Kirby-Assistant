package com.lxfly2000.utilities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Process;

import java.util.List;

public class AndroidUtility {
    //检查权限，有返回true，无返回false并显示提示信息并关闭当前Activity
    public static boolean CheckPermissionWithFinishOnDenied(final Activity activity, String permission, String deniedMessage){
        if(activity.checkCallingOrSelfPermission(permission)!= PackageManager.PERMISSION_GRANTED){
            new AlertDialog.Builder(activity)
                    .setMessage(deniedMessage)
                    .setPositiveButton(android.R.string.ok,null)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            activity.finish();
                        }
                    }).show();
            return false;
        }
        return true;
    }

    public static void MessageBox(Context activity, String msg){
        MessageBox(activity,msg,null);
    }
    public static void MessageBox(Context activity, String msg, String title){
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok,null)
                .show();
    }

    public static void OpenUri(Context ctx,String uriString)throws ActivityNotFoundException {
        ctx.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(uriString)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void KillProcess(Context ctx,String packageName){
        ((ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE)).killBackgroundProcesses(packageName);
    }

    public static void StartApplication(Context ctx,String packageName)throws NullPointerException {
        ctx.startActivity(ctx.getPackageManager().getLaunchIntentForPackage(packageName));
    }
}
