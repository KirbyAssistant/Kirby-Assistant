package cn.endureblaze.kirby.util;

import android.app.ProgressDialog;
import android.content.Context;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.bmob.BmobDownloadApk;

import java.io.File;
import java.util.List;


public class DownloadApkUtil
{

    private static ProgressDialog progressDialog;
    public static void downloadAppApk(final String app_name, final Context context)
    {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.link_ser_now));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.show();

        BmobQuery<BmobDownloadApk> query = new BmobQuery<>();
        query.addWhereEqualTo("name", app_name);
        query.findObjects(new FindListener<BmobDownloadApk>(){
            private BmobFile emulatorApk;
            @Override
            public void done(List<BmobDownloadApk> p1, BmobException p2)
            {
                if (p2 == null)
                {
                    for (BmobDownloadApk apk: p1)
                    {
                        emulatorApk = apk.getApk();
                    }
                    //如果下载过了直接安装
                    if (FileUtil.isfFileIsExists(context.getExternalCacheDir()+"/bmob/"+ emulatorApk.getFilename()))
                    {
                        InstallApkUtil.installApk(context, context.getExternalCacheDir()+"/bmob/" + emulatorApk.getFilename());
                        progressDialog.dismiss();
                    }
                    else
                    {
                        appFileDownload(emulatorApk, app_name, context);
                    }
                }
                else
                {
                    progressDialog.dismiss();
                    ToastUtil.show(R.string.link_ser_fail);
                }
            }
        });
    }
    private static void appFileDownload(BmobFile emulatorApk, final String app_name, final Context context)
    {
        emulatorApk.download(new File(context.getExternalCacheDir()+"/bmob/"+ emulatorApk.getFilename()),new DownloadFileListener() {
            @Override
            public void onStart()
            {
                progressDialog.setMessage(context.getResources().getString(R.string.downloading) + app_name);
            }
            @Override
            public void done(String savePath, BmobException e)
            {
                if (e == null)
                {
                    progressDialog.dismiss();
                    ToastUtil.show(context.getResources().getString(R.string.download_success)+savePath);
                    InstallApkUtil.installApk(context, savePath);
                }
                else
                {
                    progressDialog.dismiss();
                    ToastUtil.show(context.getResources().getString(R.string.download_fail)+e.getMessage());
                    if (FileUtil.isfFileIsExists(context.getExternalCacheDir()+"/bmob/"+ emulatorApk.getFilename())){
                        new File(context.getExternalCacheDir()+"/bmob/"+ emulatorApk.getFilename()).delete();
                    }

                }
            }
            @Override
            public void onProgress(Integer value, long networkSpeed)
            {
                progressDialog.setProgress(value);
            }
        });
    }
}
