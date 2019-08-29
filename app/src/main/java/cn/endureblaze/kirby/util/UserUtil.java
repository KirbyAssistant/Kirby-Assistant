package cn.endureblaze.kirby.util;

import cn.bmob.v3.BmobUser;
import cn.endureblaze.kirby.bmob.BmobKirbyAssistantUser;

public class UserUtil
{
    public static BmobKirbyAssistantUser getCurrentUser(){
        return BmobUser.getCurrentUser(BmobKirbyAssistantUser.class);
    }
    public static boolean isUserLogin(){
        return BmobUser.getCurrentUser(BmobKirbyAssistantUser.class) != null;
    }
}
