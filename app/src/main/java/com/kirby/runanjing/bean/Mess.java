package com.kirby.runanjing.bean;
import java.net.*;
import org.apache.http.conn.util.*;

public class Mess
{
	private String name;
	private String message;
	private String time;
	private String message_full;
	private boolean show_all;
	public Mess(String name,  String message, String time,String message_full,boolean show_all)
	{
		this.name = name;
		this.time = time;
		this.message = message;
		this.message_full=message_full;
		this.show_all=show_all;
	}
	public String getName()
	{
		return name;
	}
	public String getTime()
	{
		return time;
	}
	public String getMessage()
	{
		return message;
	}
	public String getFullMessage()
	{
		return message_full;
	}
	public boolean getShowAll(){
		return show_all;
	}
}
