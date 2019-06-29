package cn.endureblaze.ka.utils;

public class CheckTextUtil
{
    public static boolean isHaveTerribleWord(String chat)
	{
        boolean isHaveSuicide = chat.contains("自杀");
		if(LanguageUtil.getLanguage().equals("zh_CN")||LanguageUtil.getLanguage().equals("zh_TW")){
			isHaveSuicide =false;
		}
		return isHaveSuicide;
	}
}
