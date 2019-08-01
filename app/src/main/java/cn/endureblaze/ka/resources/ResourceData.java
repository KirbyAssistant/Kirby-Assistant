package cn.endureblaze.ka.resources;

import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.ConsoleOld;
import cn.endureblaze.ka.manager.ActManager;

import java.util.List;

public class ResourceData
{
	public static void setEmulatorData(List<ConsoleOld> emulatorslist)
	{
		ConsoleOld[] emulator = {
			new ConsoleOld("GBA " + getemulatorText(R.string.emulator) + "\nMy Boy!", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_gba.png", "emulator_gba"),
			new ConsoleOld("SFC " + getemulatorText(R.string.emulator) + "\nSnes9x EX+", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_sfc.png", "emulator_sfc"),
			new ConsoleOld("N64 " + getemulatorText(R.string.emulator) + "\nTendo64", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_n64.png", "emulator_n64"),
			new ConsoleOld("NDS " + getemulatorText(R.string.emulator) + "\nDraStic", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_nds.png", "emulator_nds"),
			new ConsoleOld("NGC&WII " + getemulatorText(R.string.emulator) + "\nDolphin", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_wii.png", "emulator_wii"),
			new ConsoleOld("GB&GBC " + getemulatorText(R.string.emulator) + "\nMy OldBoy!", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_gb_gbc.png", "emulator_gb"),
			new ConsoleOld("FC " + getemulatorText(R.string.emulator) + "\nNES.emu", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_fc.png", "emulator_fc"),
		}; 
		int in = 0;//定义数值
		//遍历
		while (in < emulator.length)
		{       	
			//emulatorlist.add(emulator[in++]);
		}
	}
	public static void setCheatCodeGameData(List<ConsoleOld> cheatCodeGamelist)
	{
		ConsoleOld[]cheat_code={
			new ConsoleOld("星之卡比 梦之泉物语", "https://gitee.com/nihaocun/ka_image/raw/master/game/mengzhiquan.jpg", "fc_mzq"),
			new ConsoleOld("星之卡比 梦之泉DX", "https://gitee.com/nihaocun/ka_image/raw/master/game/mengzhiquandx.jpg", "gba_mzqdx"),
			new ConsoleOld("星之卡比 镜之大迷宫", "https://gitee.com/nihaocun/ka_image/raw/master/game/jingmi.jpg", "gba_jm")
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
