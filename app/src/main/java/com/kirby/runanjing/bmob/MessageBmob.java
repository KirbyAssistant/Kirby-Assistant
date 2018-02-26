package com.kirby.runanjing.bmob;

import cn.bmob.v3.*;

public class MessageBmob extends BmobObject
{
    private String message;
	private String nickname;
	
    public String getMessage()
	{
        return message;
    }
    public void setMessage(String message)
	{
        this.message = message;
    }
	public String getNickname()
	{
        return nickname;
    }
    public void setNickname(String nickname)
	{
        this.nickname = nickname;
    }
}
