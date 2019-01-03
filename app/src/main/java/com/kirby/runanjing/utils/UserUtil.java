package com.kirby.runanjing.utils;

import cn.bmob.v3.*;
import com.kirby.runanjing.bmob.*;

public class UserUtil
{
	public static BmobKirbyAssistantUser getCurrentUser(){
		 return BmobUser.getCurrentUser(BmobKirbyAssistantUser.class);
	 }
}
