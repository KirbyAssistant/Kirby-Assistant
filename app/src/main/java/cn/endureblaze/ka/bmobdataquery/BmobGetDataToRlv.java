package cn.endureblaze.ka.bmobdataquery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.endureblaze.ka.bean.Console;
import cn.endureblaze.ka.bean.Emulator;
import cn.endureblaze.ka.bmob.BmobResConsole;
import cn.endureblaze.ka.manager.ActManager;
import cn.endureblaze.ka.resources.ConsoleAdapter;
import cn.endureblaze.ka.resources.EmulatorAdapter;

import java.util.List;
import java.util.Objects;
public class BmobGetDataToRlv {

    //这个公用
    private Activity activity;
    //主机列表相关
    private List<Console> consolelist;
    private RecyclerView rlv_consose;
    private ConsoleAdapter console_adapter;
    //模拟器列表相关
    private List<Emulator> emulatorslist;
    private RecyclerView rlv_emulator;
    private EmulatorAdapter emulator_adapter;


    //主机列表数据获取的必须数据传入方法
    public static void setConsoleData(Activity activity,List<Console> consolelist,RecyclerView rlv_console,ConsoleAdapter console_adapter){
        new BmobGetDataToRlv().setConsoleDataToThis(activity,consolelist,rlv_console,console_adapter);
    }

    private void setConsoleDataToThis(Activity activity,List<Console> consolelist,RecyclerView rlv_console,ConsoleAdapter console_adapter){
        this.activity = activity;
        this.consolelist = consolelist;
        this.rlv_consose = rlv_console;
        this.console_adapter = console_adapter;
        //开始获取版本号
        getConsoleDataVersion();
    }

    //模拟器列表数据获取的必须数据传入方法
    public static void setEmulatorData(Activity activity, List<Emulator> emulatorslist, RecyclerView rlv_emulator, EmulatorAdapter emulator_adapter){
        new BmobGetDataToRlv().setEmulatorDataToThis(activity,emulatorslist,rlv_emulator,emulator_adapter);
    }

    private void setEmulatorDataToThis(Activity activity,List<Emulator> emulatorslist,RecyclerView rlv_emulator,EmulatorAdapter emulator_adapter){
        this.activity = activity;
        this.emulatorslist = emulatorslist;
        this.rlv_emulator = rlv_emulator;
        this.emulator_adapter = emulator_adapter;
        //开始获取版本号
       // getConsoleDataVersion();
    }

    //获取缓存版本，如果能获取到就再获取网络版本对比，如果获取不到就获取网络版本进行缓存并且获取网络列表数据
    private void getConsoleDataVersion() {
        BmobQuery<BmobResConsole> console_cache_data_vesion = new BmobQuery<>();
        console_cache_data_vesion.order("list");//按顺序排列
        console_cache_data_vesion.addQueryKeys("version");//只获取版本这一列
        console_cache_data_vesion.setLimit(1);
        console_cache_data_vesion.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);//只在缓存获取
        console_cache_data_vesion.findObjects(new FindListener<BmobResConsole>() {
            private Integer version;
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResConsole> list, BmobException e) {
                if (e == null) {
                    //本地获取的到版本，然后和在线的对比
                    for (BmobResConsole console_cache_version: list)
                    {
                        version = console_cache_version.getVersion();
                    }
                    SharedPreferences.Editor console_data_version_share_edit = Objects.requireNonNull(activity).getSharedPreferences("data_version", 0).edit();
                    console_data_version_share_edit.putInt("console", version);
                    console_data_version_share_edit.apply();
                    getConsoleOnlineVersion();
                } else {
                    getConsoleOnlineVersion();
                    getConsoleData(BmobQuery.CachePolicy.NETWORK_ONLY);
                }
            }
        });
    }
    //获取网络版本，如果获取成功就和缓存版本对比，获取失败就会显示缓存的列表数据
    private void getConsoleOnlineVersion() {
        BmobQuery<BmobResConsole> console_online_data_vesion = new BmobQuery<>();
        console_online_data_vesion.order("list");//按顺序排列
        console_online_data_vesion.addQueryKeys("version");//只获取版本这一列
        console_online_data_vesion.setLimit(1);
        console_online_data_vesion.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);//只在缓存获取
        console_online_data_vesion.findObjects(new FindListener<BmobResConsole>() {
            private Integer version_online;
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResConsole> list, BmobException e) {
                if (e == null) {
                    for (BmobResConsole console_cache_version: list)
                    {
                        version_online = console_cache_version.getVersion();
                    }
                    SharedPreferences int_online_share= Objects.requireNonNull(activity).getSharedPreferences("data_version", 0);
                    int version_cache= int_online_share.getInt("console", 0);
                    if(version_cache<version_online)
                    {
                        getConsoleData(BmobQuery.CachePolicy.NETWORK_ONLY);
                    }else{
                        getConsoleData(BmobQuery.CachePolicy.CACHE_ONLY);
                    }
                } else {
                    getConsoleData(BmobQuery.CachePolicy.CACHE_ONLY);
                }
            }
        });
    }
    //获取列表数据
    private void getConsoleData(BmobQuery.CachePolicy cache_policy) {
        BmobQuery<BmobResConsole> query = new BmobQuery<>();
        query.order("list");//时间降序排列
        query.setCachePolicy(cache_policy);
        query.findObjects(new FindListener<BmobResConsole>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResConsole> list, BmobException e) {
                if (e == null) {
                    Message message = consoleDataHandler.obtainMessage();
                    message.what = 0;
                    //以消息为载体
                    message.obj = list;//这里的list就是查询出list
                    //向handler发送消息
                    consoleDataHandler.sendMessage(message);
                } else {
                    Toast.makeText(ActManager.currentActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @SuppressLint("HandlerLeak")
    private Handler consoleDataHandler=new Handler(){
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
                        consolelist.add(console_bean);
                        rlv_consose.setAdapter(console_adapter);
                    }
                    break;
            }
        }
    };

    //获取缓存版本，如果能获取到就再获取网络版本对比，如果获取不到就获取网络版本进行缓存并且获取网络列表数据
    private void getEmulatorDataVersion() {
        BmobQuery<BmobResConsole> console_cache_data_vesion = new BmobQuery<>();
        console_cache_data_vesion.order("list");//按顺序排列
        console_cache_data_vesion.addQueryKeys("version");//只获取版本这一列
        console_cache_data_vesion.setLimit(1);
        console_cache_data_vesion.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);//只在缓存获取
        console_cache_data_vesion.findObjects(new FindListener<BmobResConsole>() {
            private Integer version;
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResConsole> list, BmobException e) {
                if (e == null) {
                    //本地获取的到版本，然后和在线的对比
                    for (BmobResConsole console_cache_version: list)
                    {
                        version = console_cache_version.getVersion();
                    }
                    SharedPreferences.Editor console_data_version_share_edit = Objects.requireNonNull(activity).getSharedPreferences("data_version", 0).edit();
                    console_data_version_share_edit.putInt("console", version);
                    console_data_version_share_edit.apply();
                    getEmulatorOnlineVersion();
                } else {
                    getEmulatorOnlineVersion();
                    getEmulatorData(BmobQuery.CachePolicy.NETWORK_ONLY);
                }
            }
        });
    }
    //获取网络版本，如果获取成功就和缓存版本对比，获取失败就会显示缓存的列表数据
    private void getEmulatorOnlineVersion() {
        BmobQuery<BmobResConsole> console_online_data_vesion = new BmobQuery<>();
        console_online_data_vesion.order("list");//按顺序排列
        console_online_data_vesion.addQueryKeys("version");//只获取版本这一列
        console_online_data_vesion.setLimit(1);
        console_online_data_vesion.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);//只在缓存获取
        console_online_data_vesion.findObjects(new FindListener<BmobResConsole>() {
            private Integer version_online;
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResConsole> list, BmobException e) {
                if (e == null) {
                    for (BmobResConsole console_cache_version: list)
                    {
                        version_online = console_cache_version.getVersion();
                    }
                    SharedPreferences int_online_share= Objects.requireNonNull(activity).getSharedPreferences("data_version", 0);
                    int version_cache= int_online_share.getInt("console", 0);
                    if(version_cache<version_online)
                    {
                        getEmulatorData(BmobQuery.CachePolicy.NETWORK_ONLY);
                    }else{
                        getEmulatorData(BmobQuery.CachePolicy.CACHE_ONLY);
                    }
                } else {
                    getEmulatorData(BmobQuery.CachePolicy.CACHE_ONLY);
                }
            }
        });
    }
    //获取列表数据
    private void getEmulatorData(BmobQuery.CachePolicy cache_policy) {
        BmobQuery<BmobResConsole> query = new BmobQuery<>();
        query.order("list");//时间降序排列
        query.setCachePolicy(cache_policy);
        query.findObjects(new FindListener<BmobResConsole>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResConsole> list, BmobException e) {
                if (e == null) {
                    Message message = consoleDataHandler.obtainMessage();
                    message.what = 0;
                    //以消息为载体
                    message.obj = list;//这里的list就是查询出list
                    //向handler发送消息
                    consoleDataHandler.sendMessage(message);
                } else {
                    Toast.makeText(ActManager.currentActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @SuppressLint("HandlerLeak")
    private Handler EmulatorDataHandler=new Handler(){
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
                        consolelist.add(console_bean);
                        rlv_consose.setAdapter(console_adapter);
                    }
                    break;
            }
        }
    };
}