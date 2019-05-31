package cn.endureblaze.ka.bean;

public abstract class BaseMainBean
{
	private String name;
	private String imageUrl;
	private String funk;
	
	public BaseMainBean(String name, String imageUrl,String funk)
	{
		this.name = name;
		this.imageUrl = imageUrl;
		this.funk=funk;
	}
	public String getName()
	{
		return name;
	}
	public String getImageUrl()
	{
		return imageUrl;
	}
	public String getPosition()
	{
		return funk;
	}
}
