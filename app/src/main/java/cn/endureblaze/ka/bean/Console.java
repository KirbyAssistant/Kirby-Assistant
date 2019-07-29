package cn.endureblaze.ka.bean;

public class Console {

    private Integer list;
    private Integer version;
    private String ConsoleName;
    private String ConsoleImageUrl;
    private String ConsoleTag;

   public Console(Integer list,Integer version,String ConsoleName, String ConsoleImageUrl, String ConsoleTag)
    {
        this.list = list;
        this.version = version;
        this.ConsoleName = ConsoleName;
        this.ConsoleImageUrl = ConsoleImageUrl;
        this.ConsoleTag=ConsoleTag;
    }
    public Integer getList(){return list;}
    public Integer getVersion(){return version;}
    public String getConsoleName()
    {
        return ConsoleName;
    }
    public String getConsoleImageUrl()
    {
        return ConsoleImageUrl;
    }
    public String getConsoleTag() { return ConsoleTag; }

}
