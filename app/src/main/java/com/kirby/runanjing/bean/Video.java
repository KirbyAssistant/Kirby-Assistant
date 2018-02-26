package com.kirby.runanjing.bean;
import java.io.*;
import java.lang.annotation.*;

public class Video
{
	private String name;
	private String imageUrl;
	private String av;
	public Video(String name, String imageUrl, String av)
	{
		this.name = name;
		this.imageUrl = imageUrl;
		this.av=av;
	}
	public String getName()
	{
		return name;
	}
	public String getImageUrl()
	{
		return imageUrl;
	}
	public String getAv(){
		return av;
	}
}
