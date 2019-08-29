package cn.endureblaze.kirby.bmob;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class BmobKirbyAssistantUser extends BmobUser
{
    private BmobFile UserHead;
    public BmobFile getUserAvatar()
    {
        return UserHead;
    }
    public void setUserAvatar(BmobFile userAvatar)
    {
        this.UserHead = userAvatar;
    }
}
