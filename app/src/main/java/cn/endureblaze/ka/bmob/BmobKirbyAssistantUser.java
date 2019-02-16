package cn.endureblaze.ka.bmob;

import cn.bmob.v3.*;
import java.io.File;
import cn.bmob.v3.datatype.BmobFile;

public class BmobKirbyAssistantUser extends BmobUser
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
