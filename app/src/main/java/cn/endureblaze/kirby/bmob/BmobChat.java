package cn.endureblaze.kirby.bmob;

import cn.bmob.v3.BmobObject;

public class BmobChat extends BmobObject
{
    private String chat;

    private String nickname;

    private BmobKirbyAssistantUser user;

    public String getChat()
    {
        return chat;
    }
    public void setChat(String chat)
    {
        this.chat = chat;
    }
    public BmobKirbyAssistantUser getUser() {
        return user;
    }

    public BmobChat setUser(BmobKirbyAssistantUser user) {
        this.user = user;
        return this;
    }

    public String getNickname()
    {
        return nickname;
    }
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }
}
