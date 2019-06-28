package cn.endureblaze.ka.utils;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bmob.BmobCheckUpdate;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CheckUpdateUtil
{
	public static void checkUpdate(final View snackBarShowView,final Context context)
	{
		BmobQuery<BmobCheckUpdate>  findCheckupdate=new BmobQuery<BmobCheckUpdate>();
		findCheckupdate.setLimit(1);
		findCheckupdate.order("-createdAt");//时间降序排列
		findCheckupdate.findObjects(new FindListener<BmobCheckUpdate>(){
				private int versionCode;
				private String versionName;
				private String changeLog;
				@Override
				public void done(List<BmobCheckUpdate> list, BmobException e)
				{
					if (e == null)
					{
						for (BmobCheckUpdate checkupdate: list)
						{
							versionCode = checkupdate.getVersionCode();
							versionName=checkupdate.getVersionName();
							changeLog=checkupdate.getChangeLog();
						}
						if(versionCode>AppUtil.getVersionCode(context)){
							Snackbar.make(snackBarShowView, R.string.find_new_version, Snackbar.LENGTH_LONG).setAction(R.string.view_new_version, new View.OnClickListener(){
									@Override
									public void onClick(View p1)
									{
										AlertDialog.Builder update_dialog=new AlertDialog.Builder(context)
											.setTitle(context.getResources().getString(R.string.find_new_version)+versionName)
											.setMessage(changeLog)
											.setCancelable(false)
											.setPositiveButton(context.getResources().getString(R.string.dia_download), (dialog, which) -> {
												try
												{
													Intent update=new Intent("android.intent.action.VIEW");
													update .setData(Uri.parse("market://details?id=cn.endureblaze.ka"));
													update.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
													update .setPackage("com.coolapk.market");
													context.startActivity(update);
												}
												catch (Exception e1)
												{
													Intent update=new Intent("android.intent.action.VIEW");
													update.setData(Uri.parse("https://www.coolapk.com/game/cn.endureblaze.ka"));
													context.startActivity(update);
												}
											}
											)
											.setNegativeButton(context.getResources().getString(R.string.dia_cancel), null);
										update_dialog.show();
									}
								}).show();
						}
					}
				}
			});
	}
}
