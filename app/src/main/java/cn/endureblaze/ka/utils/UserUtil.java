package cn.endureblaze.ka.utils;

import cn.bmob.v3.*;
import cn.endureblaze.ka.bmob.*;
import android.content.Context;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.exception.BmobException;
import cn.endureblaze.ka.main.MainActivity;
import cn.endureblaze.ka.me.user.MainUserFragment;

public class UserUtil
{
	public static BmobKirbyAssistantUser getCurrentUser(){
		 return BmobUser.getCurrentUser(BmobKirbyAssistantUser.class);
	}
	public static boolean isUserLogin(){
		if(BmobUser.getCurrentUser(BmobKirbyAssistantUser.class)==null){
			return false;
		}
		return true;
	}
}
