package com.kirby.runanjing.untils;
import android.content.Intent;
import android.content.Context;
import android.app.ActivityOptions;
import android.app.Activity;

public class IntentUtil
{
	public static void startActivityWithAnim(Intent intent,Activity activity){
		activity.startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
	}
}
