package com.kirby.runanjing.utils;

import android.app.*;
import android.content.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bmob.*;
import com.kirby.runanjing.utils.*;
import java.io.*;
import java.util.*;
import com.kirby.runanjing.bean.*;

public class DownloadApkUtil
{

	private static ProgressDialog progressDialog;
	public static void downloadappApk(final String app_name,final Context context)
	{
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage(context.getResources().getString(R.string.link_bmob));
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setCancelable(false);
		progressDialog.setMax(100);
		progressDialog.show();

		BmobQuery<BmobDownloadApk> query = new BmobQuery<BmobDownloadApk>();
        query.addWhereEqualTo("name", app_name);
        query.findObjects(new FindListener<BmobDownloadApk>(){
				private BmobFile emulatorsApk;
				@Override
				public void done(List<BmobDownloadApk> p1, BmobException p2)
				{
					if (p2 == null)
					{
						for (BmobDownloadApk apk: p1)
						{
							emulatorsApk = apk.getApk();
						}
						appFileDownload(emulatorsApk, app_name,context);
					}
					else
					{
						progressDialog.dismiss();
						Toast.makeText(context,context.getResources().getString(R.string.link_fail) + p2, Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
	private static void appFileDownload(BmobFile emulatorsApk, final String app_name,final Context context)
	{
		emulatorsApk.download(new DownloadFileListener() {
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
						Toast.makeText(context, context.getResources().getString(R.string.download_success) + savePath, Toast.LENGTH_SHORT).show();
						InstallUtil.installApk(context, savePath);
					}
					else
					{
						progressDialog.dismiss();
						Toast.makeText(context, context.getResources().getString(R.string.download_fail) + e.getMessage() , Toast.LENGTH_SHORT).show();
					}
				}
				@Override
				public void onProgress(Integer value, long newworkSpeed)
				{
					progressDialog.setProgress(value);
				}
			});
	}
}
