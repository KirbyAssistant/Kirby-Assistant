package cn.endureblaze.ka.utils;

import cn.bmob.v3.*;
import cn.endureblaze.ka.bmob.*;

public class UserUtil
{
	public static BmobKirbyAssistantUser getCurrentUser(){
		 return BmobUser.getCurrentUser(BmobKirbyAssistantUser.class);
	 }
}
