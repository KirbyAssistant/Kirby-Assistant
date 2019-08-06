package cn.endureblaze.ka.resources;

import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.CheatCodeGame;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.bean.Emulator;
import cn.endureblaze.ka.manager.ActManager;

import java.util.List;

public class ResourceData
{
    public static void setConsoseData(List<Console> consolelist)
    {
        Console[] consosle={
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
        while (index < consosle.length)
        {
            consolelist.add(consosle[index++]);
        }
    }
	public static void setEmulatorData(List<Emulator> emulatorslist)
	{
        Emulator[] emulator = {
			new Emulator("GBA " + getemulatorText(R.string.emulator) + "\nMy Boy!", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_gba.png", "emulator_gba"),
			new Emulator("SFC " + getemulatorText(R.string.emulator) + "\nSnes9x EX+", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_sfc.png", "emulator_sfc"),
			new Emulator("N64 " + getemulatorText(R.string.emulator) + "\nTendo64", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_n64.png", "emulator_n64"),
			new Emulator("NDS " + getemulatorText(R.string.emulator) + "\nDraStic", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_nds.png", "emulator_nds"),
			new Emulator("NGC&WII " + getemulatorText(R.string.emulator) + "\nDolphin", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_wii.png", "emulator_wii"),
			new Emulator("GB&GBC " + getemulatorText(R.string.emulator) + "\nMy OldBoy!", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_gb_gbc.png", "emulator_gb"),
			new Emulator("FC " + getemulatorText(R.string.emulator) + "\nNES.emu", "https://api.endureblaze.cn/ka_image/moniqi/moniqi_fc.png", "emulator_fc"),
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
	private static String getemulatorText(int res_id)
	{	
		return ActManager.currentActivity().getResources().getString(res_id);
	}
}
