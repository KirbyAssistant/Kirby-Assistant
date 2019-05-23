package cn.endureblaze.ka.resources;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.utils.ActManager;
import java.util.List;

public class ResourceData
{
	public static void setConsoseData(List<Console> consolelist)
	{
		Console[]consosle={
			new Console("gba", "https://gitee.com/nihaocun/ka_image/raw/master/consose/gba.png", "gba"),
			new Console("sfc", "https://gitee.com/nihaocun/ka_image/raw/master/consose/sfc.png", "sfc"),
			new Console("n64", "https://gitee.com/nihaocun/ka_image/raw/master/consose/n64.png", "n64"),
			new Console("ngc", "https://gitee.com/nihaocun/ka_image/raw/master/consose/ngc.png", "ngc"),
			new Console("wii", "https://gitee.com/nihaocun/ka_image/raw/master/consose/wii.png", "wii"),
			new Console("nds", "https://gitee.com/nihaocun/ka_image/raw/master/consose/nds.png", "nds"),
			new Console("gb", "https://gitee.com/nihaocun/ka_image/raw/master/consose/gb.png", "gb"),
			new Console("gbc", "https://gitee.com/nihaocun/ka_image/raw/master/consose/gbc.png", "gbc"),
			new Console("fc", "https://gitee.com/nihaocun/ka_image/raw/master/consose/fc.png", "fc")};

		int index = 0;//定义数值
		//遍历
		while (index < consosle.length)
		{       	
			consolelist.add(consosle[index++]);
		}
	}
	public static void setEmulatorData(List<Console> emulatorslist)
	{
		Console[] emulator = {
			new Console("GBA " + getEmulatorsText(R.string.emulators) + "\nMy Boy!", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_gba.png", "emulators_gba"),
			new Console("SFC " + getEmulatorsText(R.string.emulators) + "\nSnes9x EX+", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_sfc.png", "emulators_sfc"),
			new Console("N64 " + getEmulatorsText(R.string.emulators) + "\nTendo64", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_n64.png", "emulators_n64"),
			new Console("NDS " + getEmulatorsText(R.string.emulators) + "\nDraStic", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_nds.png", "emulators_nds"),
			new Console("NGC&WII " + getEmulatorsText(R.string.emulators) + "\nDolphin", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_wii.png", "emulators_wii"),
			new Console("GB&GBC " + getEmulatorsText(R.string.emulators) + "\nMy OldBoy!", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_gb_gbc.png", "emulators_gb"),
			new Console("FC " + getEmulatorsText(R.string.emulators) + "\nNES.emu", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_fc.png", "emulators_fc"),
		}; 
		int in = 0;//定义数值
		//遍历
		while (in < emulator.length)
		{       	
			emulatorslist.add(emulator[in++]);
		}
	}
	public static void setCheatCodeGameData(List<Console> cheatCodeGamelist)
	{
		Console[]cheat_code={
			new Console("星之卡比 梦之泉物语", "https://gitee.com/nihaocun/ka_image/raw/master/game/mengzhiquan.jpg", "fc_mzq"),
			new Console("星之卡比 梦之泉DX", "https://gitee.com/nihaocun/ka_image/raw/master/game/mengzhiquandx.jpg", "gba_mzqdx"),
			new Console("星之卡比 镜之大迷宫", "https://gitee.com/nihaocun/ka_image/raw/master/game/jingmi.jpg", "gba_jm")
		};
		int ind = 0;//定义数值
		//遍历
		while (ind < cheat_code.length)
		{       	
			cheatCodeGamelist.add(cheat_code[ind++]);
		}
	}
	private static String getEmulatorsText(int res_id)
	{	
		return ActManager.currentActivity().getResources().getString(res_id);
	}
}
