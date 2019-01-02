package com.kirby.runanjing.utils;

public class CheckTextUtil
{
	private static  boolean isHaveSuicide;
	public static boolean isHaveTerribleWord(String chat)
	{
	 isHaveSuicide = chat.contains("自杀");
		if(LanguageUtil.getLanguage().equals("zh_CN")||LanguageUtil.getLanguage().equals("zh_TW")){
			isHaveSuicide=false;
		}
		return isHaveSuicide;
	};
}
