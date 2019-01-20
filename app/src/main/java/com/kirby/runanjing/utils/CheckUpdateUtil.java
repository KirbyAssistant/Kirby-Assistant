package com.kirby.runanjing.utils;
import android.content.*;
import android.support.design.widget.*;
import android.support.v7.app.*;
import android.view.*;
import cn.bmob.v3.*;
import cn.bmob.v3.exception.*;
import cn.bmob.v3.listener.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.bmob.*;
import com.kirby.runanjing.main.*;
import java.util.*;

public class CheckUpdateUtil
{
	public static void checkUpdate(final View snackBarShowView,final Context context)
	{
		BmobQuery<BmobCheckUpdate>  findCheckupdate=new BmobQuery<BmobCheckUpdate>();
		findCheckupdate.setLimit(1);
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
						if(versionCode>0){
							Snackbar.make(snackBarShowView, R.string.find_new_version, Snackbar.LENGTH_LONG).setAction("查看", new View.OnClickListener(){
									@Override
									public void onClick(View p1)
									{
										AlertDialog.Builder update_dialog=new AlertDialog.Builder(context)
											.setTitle(R.string.find_new_version+versionName)
											.setMessage(changeLog)
											.setCancelable(false)
											.setPositiveButton(context.getResources().getString(R.string.dia_download), new
											DialogInterface.OnClickListener()
											{
												@Override
												public void onClick(DialogInterface dialog, int which)
												{

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
