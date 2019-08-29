package cn.endureblaze.kirby.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class BmobDownloadApk extends BmobObject
{
    private String name;

    private BmobFile apk;

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setApk(BmobFile apk){
        this.apk=apk;
    }
    public BmobFile getApk(){
        return apk;
    }
}
