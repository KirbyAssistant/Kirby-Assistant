package cn.endureblaze.ka.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class BmobResEmulator extends BmobObject {

    private Integer list;

    private Integer version;

    private String EmulatorConsoleName;

    private String EmulatorName;

    private BmobFile EmulatorImage;

    private String EmulatorTag;

    public Integer getList()
    {
        return list;
    }
    public void setList(Integer list)
    {
        this.list = list;
    }

    public Integer getVersion()
    {
        return version;
    }
    public void setVersion(Integer version)
    {
        this.version = version;
    }

    public String getEmulatorConsoleName(){return EmulatorConsoleName;}
    public void setEmulatorConsoleName(String EmulatorConsoleName){this.EmulatorConsoleName = EmulatorConsoleName;}

    public String getEmulatorName(){return EmulatorName;}
    public void setEmulatorName(String EmulatorName){this.EmulatorName = EmulatorName;}

    public BmobFile getEmulatorImage(){
        return EmulatorImage;
    }
    public void setEmulatorImage(BmobFile EmulatorImage){
        this.EmulatorImage=EmulatorImage;
    }

    public String getEmulatorTag(){return EmulatorTag;}
    public void setEmulatorTag(String EmulatorTag){this.EmulatorTag = EmulatorTag;}
}