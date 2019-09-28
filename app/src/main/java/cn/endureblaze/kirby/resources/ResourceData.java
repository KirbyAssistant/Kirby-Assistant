package cn.endureblaze.kirby.resources;

import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.bean.CheatCodeGame;
import cn.endureblaze.kirby.bean.Console;
import cn.endureblaze.kirby.bean.Emulator;
import cn.endureblaze.kirby.manager.ActManager;

import java.util.List;

public class ResourceData
{
    public static void setConsoleData(List<Console> consolelist)
    {
        Console[] console={
                new Console("gba", "https://api.endureblaze.cn/ka_image/consose/gba.png", "gba"),
                new Console("sfc", "https://api.endureblaze.cn/ka_image/consose/sfc.png", "sfc"),
                new Console("n64", "https://api.endureblaze.cn/ka_image/consose/n64.png", "n64"),
                new Console("ngc", "https://api.endureblaze.cn/ka_image/consose/ngc.png", "ngc"),
                new Console("wii", "https://api.endureblaze.cn/ka_image/consose/wii.png", "wii"),
                new Console("nds", "https://api.endureblaze.cn/ka_image/consose/nds.png", "nds"),
                new Console("gb", "https://api.endureblaze.cn/ka_image/consose/gb.png", "gb"),
                new Console("gbc", "https://api.endureblaze.cn/ka_image/consose/gbc.png", "gbc"),
                new Console("fc", "https://api.endureblaze.cn/ka_image/consose/fc.png", "fc")};

        int index = 0;//定义数值
        //遍历
        while (index < console.length)
        {
            consolelist.add(console[index++]);
        }
    }
	public static void setEmulatorData(List<Emulator> emulatorslist)
	{
        Emulator[] emulator = {
			new Emulator("GBA " + getEmulatorText(R.string.tab_emulator) + "\nMy Boy!", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_gba.png", "emulator_gba"),
			new Emulator("SFC " + getEmulatorText(R.string.tab_emulator) + "\nSnes9x EX+", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_sfc.png", "emulator_sfc"),
			new Emulator("N64 " + getEmulatorText(R.string.tab_emulator) + "\nTendo64", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_n64.png", "emulator_n64"),
			new Emulator("NDS " + getEmulatorText(R.string.tab_emulator) + "\nDraStic", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_nds.png", "emulator_nds"),
			new Emulator("NGC&WII " + getEmulatorText(R.string.tab_emulator) + "\nDolphin", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_wii.png", "emulator_wii"),
			new Emulator("GB&GBC " + getEmulatorText(R.string.tab_emulator) + "\nMy OldBoy!", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_gb_gbc.png", "emulator_gb"),
			new Emulator("FC " + getEmulatorText(R.string.tab_emulator) + "\nNES.emu", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_fc.png", "emulator_fc"),
		}; 
		int in = 0;//定义数值
		//遍历
		while (in < emulator.length)
		{       	
			emulatorslist.add(emulator[in++]);
		}
	}
	public static void setCheatCodeGameData(List<CheatCodeGame> cheatCodeGamelist)
	{
        CheatCodeGame[]cheat_code={
			new CheatCodeGame("星之卡比 梦之泉物语", "https://api.endureblaze.cn/ka_image/game/mengzhiquan.jpg", "fc_mzq"),
			new CheatCodeGame("星之卡比 梦之泉DX", "https://api.endureblaze.cn/ka_image/game/mengzhiquandx.jpg", "gba_mzqdx"),
			new CheatCodeGame("星之卡比 镜之大迷宫", "https://api.endureblaze.cn/ka_image/game/jingmi.jpg", "gba_jm")
		};
		int ind = 0;//定义数值
		//遍历
		while (ind < cheat_code.length)
		{       	
			cheatCodeGamelist.add(cheat_code[ind++]);
		}
	}
	private static String getEmulatorText(int res_id)
	{	
		return ActManager.getCurrentActivity().getResources().getString(res_id);
	}
}
