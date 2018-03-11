package com.kirby.runanjing.bmob;

import cn.bmob.v3.*;
import java.io.File;
import cn.bmob.v3.datatype.BmobFile;

public class MyUser extends BmobUser
{
    private BmobFile UserHead;
    public BmobFile getUserHead()
	{
        return UserHead;
    }
    public void setUserHead(BmobFile userHead)
	{
        this.UserHead = userHead;
    }
}
