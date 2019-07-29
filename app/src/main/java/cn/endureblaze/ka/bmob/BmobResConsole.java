package cn.endureblaze.ka.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class BmobResConsole extends BmobObject {

    private Integer list;

    private Integer version;

    private String ConsoleName;

    private BmobFile ConsoleImage;

    private String ConsoleTag;

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

    public String getConsoleName(){return ConsoleName;}
    public void setConsoleName(String ConsoleName){this.ConsoleName = ConsoleName;}

    public BmobFile getConsoleImage(){
        return ConsoleImage;
    }
    public void setConsoleImage(BmobFile ConsoleImage){
        this.ConsoleImage=ConsoleImage;
    }

    public String getConsoleTag(){return ConsoleTag;}
    public void setConsoleTag(String ConsoleTag){this.ConsoleTag = ConsoleTag;}
}
