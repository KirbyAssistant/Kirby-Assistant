package cn.endureblaze.kirby.bean;

public abstract class BaseMainBean
{
	private String name;
	private String imageUrl;
	private String tag;
	
	BaseMainBean(String name, String imageUrl, String tag)
	{
		this.name = name;
		this.imageUrl = imageUrl;
		this.tag=tag;
	}
	public String getName()
	{
		return name;
	}
	public String getImageUrl()
	{
		return imageUrl;
	}
	public String getTag() { return tag; }
}
