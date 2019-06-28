package cn.endureblaze.ka.bean;

public class Chat
{
	private String id;
	private String name;
	private String chat;
	private String time;
	private String chat_full;
	private boolean show_all;
	
	public Chat(String id,String name,String userHead  ,String chat, String time,String chat_full,boolean show_all)
	{
		this.id = id;
		this.name = name;
		this.time = time;
		this.chat = chat;
		this.chat_full=chat_full;
		this.show_all=show_all;
	}
	public String getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public String getTime()
	{
		return time;
	}
	public String getChat()
	{
		return chat;
	}
	public String getFullChat()
	{
		return chat_full;
	}
	public boolean getShowAll(){
		return show_all;
	}
}
