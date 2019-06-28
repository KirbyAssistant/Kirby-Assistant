package cn.endureblaze.ka.bmob;
import cn.bmob.v3.*;

public class BmobCheckUpdate extends BmobObject
{
	private String versionName;
	private Integer versionCode;
	private String changeLog;
	
	public String getVersionName(){
		return versionName;
	}
	
	public Integer getVersionCode(){
		return versionCode;
	}
	
	public String getChangeLog(){
		return changeLog;
	}
	
	public void setVersionName(String versionName){
		this.versionName=versionName;
	}
	
	public void setVersionCode(Integer versionCode){
		this.versionCode=versionCode;
	}
	
	public void setVersion(String changeLog){
		this.changeLog=changeLog;
	}
}
