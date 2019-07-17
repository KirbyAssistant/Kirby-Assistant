package cn.endureblaze.ka.utils;

import android.app.Activity;
import android.content.Intent;

public class IntentUtil
{
	public static void startActivityWithAnim(Intent intent,Activity activity){
		//activity.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
		activity.startActivity(intent);
	}
}
