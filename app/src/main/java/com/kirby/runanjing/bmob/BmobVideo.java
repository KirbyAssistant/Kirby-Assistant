package com.kirby.runanjing.bmob;
import cn.bmob.v3.*;

public class BmobVideo extends BmobObject
{
    private String name;
	private String av;
	private String imageUrl;
    public String getName()
	{
        return name;
    }
    public void setName(String name)
	{
        this.name = name;
    }
	public String getAv()
	{
        return av;
    }
    public void setAv(String av)
	{
        this.av = av;
    }
	public String getImageUrl(){
		return imageUrl;
	}
	public void setImageUrl(String imageUrl){
		this.imageUrl=imageUrl;
	}
}
