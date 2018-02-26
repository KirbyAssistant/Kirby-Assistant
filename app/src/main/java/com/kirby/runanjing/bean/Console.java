package com.kirby.runanjing.bean;

public class Console
{
	private String name;
	private String imageUrl;
	public Console(String name, String imageUrl)
	{
		this.name = name;
		this.imageUrl = imageUrl;
	}
	public String getName()
	{
		return name;
	}
	public String getImageUrl()
	{
		return imageUrl;
	}
}
