package com.kirby.runanjing.bmob;

import cn.bmob.v3.*;

public class MyUser extends BmobUser
{
    private Boolean gender;
	

    public Boolean getGender()
	{
        return gender;
    }
    public void setGender(Boolean gender)
	{
        this.gender = gender;
    }
}
