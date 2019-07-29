package cn.endureblaze.ka.resources;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.bean.ConsoleOld;
import cn.endureblaze.ka.bmob.BmobResConsole;
import cn.endureblaze.ka.manager.ActManager;

import java.util.List;

public class ResourceData
{
private static List<Console> consolelist1;
    public static void setConsoseData(List<Console> consolelist)
	{
        consolelist1=consolelist;
        BmobQuery<BmobResConsole> query= new BmobQuery<>();
        query.order("-list");//时间降序排列
        query.findObjects(new FindListener<BmobResConsole>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResConsole> list, BmobException e)
            {
                if (e == null)
                {
                    Message message = consoleHandler.obtainMessage();
                    message.what = 0;
                    //以消息为载体
                    message.obj = list;//这里的list就是查询出list
                    //向handler发送消息
                    consoleHandler.sendMessage(message);
                }
                else
                {
                    Toast.makeText(ActManager.currentActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
	}

    @SuppressLint("HandlerLeak")
    private static Handler consoleHandler=new Handler(){

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    List<BmobResConsole> list= (List<BmobResConsole>)msg.obj;
                    for (BmobResConsole console : list)
                    {
                        //从获取的数据中提取需要的数据
                        Integer consoleList = console.getList();
                        Integer version = console.getVersion();
                        String console_name = console.getConsoleName();
                        String console_image_url = console.getConsoleImage().getFileUrl();
                        String consoel_tag = console.getConsoleTag();
                        Console console_bean = new Console(consoleList,version,console_name,console_image_url,consoel_tag);
                        //将查询到的数据依次添加到列表
                        consolelist1.add(console_bean);
                    }
                    break;
            }
        }
    };

	public static void setEmulatorData(List<ConsoleOld> emulatorslist)
	{
		ConsoleOld[] emulator = {
			new ConsoleOld("GBA " + getEmulatorsText(R.string.emulators) + "\nMy Boy!", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_gba.png", "emulators_gba"),
			new ConsoleOld("SFC " + getEmulatorsText(R.string.emulators) + "\nSnes9x EX+", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_sfc.png", "emulators_sfc"),
			new ConsoleOld("N64 " + getEmulatorsText(R.string.emulators) + "\nTendo64", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_n64.png", "emulators_n64"),
			new ConsoleOld("NDS " + getEmulatorsText(R.string.emulators) + "\nDraStic", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_nds.png", "emulators_nds"),
			new ConsoleOld("NGC&WII " + getEmulatorsText(R.string.emulators) + "\nDolphin", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_wii.png", "emulators_wii"),
			new ConsoleOld("GB&GBC " + getEmulatorsText(R.string.emulators) + "\nMy OldBoy!", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_gb_gbc.png", "emulators_gb"),
			new ConsoleOld("FC " + getEmulatorsText(R.string.emulators) + "\nNES.emu", "https://gitee.com/nihaocun/ka_image/raw/master/moniqi/moniqi_fc.png", "emulators_fc"),
		}; 
		int in = 0;//定义数值
		//遍历
		while (in < emulator.length)
		{       	
			emulatorslist.add(emulator[in++]);
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
	private static String getEmulatorsText(int res_id)
	{	
		return ActManager.currentActivity().getResources().getString(res_id);
	}
}
