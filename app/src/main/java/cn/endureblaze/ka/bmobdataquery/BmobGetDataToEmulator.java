package cn.endureblaze.ka.bmobdataquery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.bean.Emulator;
import cn.endureblaze.ka.bmob.BmobResEmulator;
import cn.endureblaze.ka.manager.ActManager;
import cn.endureblaze.ka.resources.EmulatorAdapter;

import java.util.List;
import java.util.Objects;

public class BmobGetDataToEmulator {

    private Activity activity;

    //模拟器列表相关
    private List<Emulator> emulatorlist;
    private RecyclerView rlv_emulator;
    private EmulatorAdapter emulator_adapter;
    private ProgressBar progressbar_emulator;
    //模拟器列表数据获取的必须数据传入方法
    public static void setEmulatorData(Activity activity, List<Emulator> emulatorlist, RecyclerView rlv_emulator, EmulatorAdapter emulator_adapter, ProgressBar progressbar_emulator){
        new BmobGetDataToEmulator().setEmulatorDataToThis(activity,emulatorlist,rlv_emulator,emulator_adapter,progressbar_emulator);
    }

    private void setEmulatorDataToThis(Activity activity,List<Emulator> emulatorlist,RecyclerView rlv_emulator,EmulatorAdapter emulator_adapter,ProgressBar progressbar_emulator){
        this.activity = activity;
        this.emulatorlist = emulatorlist;
        this.rlv_emulator = rlv_emulator;
        this.emulator_adapter = emulator_adapter;
        this.progressbar_emulator = progressbar_emulator;
        //开始获取版本号
        getEmulatorDataVersion();
    }

    //获取缓存版本，如果能获取到就再获取网络版本对比，如果获取不到就获取网络版本进行缓存并且获取网络列表数据
    private void getEmulatorDataVersion() {
        BmobQuery<BmobResEmulator> emulator_cache_data_vesion = new BmobQuery<>();
        emulator_cache_data_vesion.order("list");//按顺序排列
        emulator_cache_data_vesion.addQueryKeys("version");//只获取版本这一列
        emulator_cache_data_vesion.setLimit(1);
        emulator_cache_data_vesion.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);//只在缓存获取
        emulator_cache_data_vesion.findObjects(new FindListener<BmobResEmulator>() {
            private Integer version;
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResEmulator> list, BmobException e) {
                if (e == null) {
                    //本地获取的到版本，然后和在线的对比
                    for (BmobResEmulator emulator_cache_version: list)
                    {
                        version = emulator_cache_version.getVersion();
                    }
                    SharedPreferences.Editor emulator_data_version_share_edit = Objects.requireNonNull(activity).getSharedPreferences("data_version", 0).edit();
                    emulator_data_version_share_edit.putInt("emulator", version);
                    emulator_data_version_share_edit.apply();
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
        BmobQuery<BmobResEmulator> emulator_online_data_vesion = new BmobQuery<>();
        emulator_online_data_vesion.order("list");//按顺序排列
        emulator_online_data_vesion.addQueryKeys("version");//只获取版本这一列
        emulator_online_data_vesion.setLimit(1);
        emulator_online_data_vesion.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ONLY);//只在缓存获取
        emulator_online_data_vesion.findObjects(new FindListener<BmobResEmulator>() {
            private Integer version_online;
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResEmulator> list, BmobException e) {
                if (e == null) {
                    for (BmobResEmulator emulator_cache_version: list)
                    {
                        version_online = emulator_cache_version.getVersion();
                    }
                    SharedPreferences int_online_share= Objects.requireNonNull(activity).getSharedPreferences("data_version", 0);
                    int version_cache= int_online_share.getInt("emulator", 0);
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
        BmobQuery<BmobResEmulator> query = new BmobQuery<>();
        query.order("list");//时间降序排列
        query.setCachePolicy(cache_policy);
        query.findObjects(new FindListener<BmobResEmulator>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void done(List<BmobResEmulator> list, BmobException e) {
                if (e == null) {
                    progressbar_emulator.setVisibility(View.GONE);
                    Message message = emulatorDataHandler.obtainMessage();
                    message.what = 0;
                    //以消息为载体
                    message.obj = list;//这里的list就是查询出list
                    //向handler发送消息
                    emulatorDataHandler.sendMessage(message);
                } else {
                    Toast.makeText(ActManager.currentActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @SuppressLint("HandlerLeak")
    private Handler emulatorDataHandler=new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    List<BmobResEmulator> list= (List<BmobResEmulator>)msg.obj;
                    for (BmobResEmulator emulator : list)
                    {
                        //从获取的数据中提取需要的数据
                        Integer emulatorList = emulator.getList();
                        Integer version = emulator.getVersion();
                        String emulator_name = emulator.getEmulatorConsoleName() + getEmulatorText(R.string.emulator) + "\n" +emulator.getEmulatorName();
                        String emulator_image_url = emulator.getEmulatorImage().getFileUrl();
                        String consoel_tag = emulator.getEmulatorTag();
                        Emulator emulator_bean = new Emulator(emulatorList,version,emulator_name,emulator_image_url,consoel_tag);
                        //将查询到的数据依次添加到列表
                        emulatorlist.add(emulator_bean);
                        rlv_emulator.setAdapter(emulator_adapter);
                    }
                    break;
            }
        }
    };
    private static String getEmulatorText(int res_id)
    {
        return ActManager.currentActivity().getResources().getString(res_id);
    }
}
