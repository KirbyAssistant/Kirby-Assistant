package cn.endureblaze.ka.main;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.annotation.*;
import android.support.design.widget.*;
import android.support.v4.app.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import cn.bmob.v3.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.base.*;
import cn.endureblaze.ka.chat.*;
import cn.endureblaze.ka.helper.*;
import cn.endureblaze.ka.main.donate.*;
import cn.endureblaze.ka.main.theme.*;
import cn.endureblaze.ka.me.user.*;
import cn.endureblaze.ka.resources.*;
import cn.endureblaze.ka.utils.*;
import cn.endureblaze.ka.video.*;
import java.util.*;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import cn.endureblaze.ka.setting.*;
import cn.endureblaze.ka.me.login.*;
import cn.endureblaze.ka.bmob.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;
import cn.endureblaze.ka.main.MainActivity.*;
import android.support.v4.content.*;
import com.umeng.analytics.*;
/**
 *类类型:Activity
 *类名称:MainActivity
 *加载动画完毕后显示的Activity
 *是整个app的核心
 */
public class MainActivity extends BaseActivity
{
	private Toolbar toolbar;
	private Context gameContext;
	private ProgressDialog progressDialog;

	private IntentFilter intentFilter;

	//private CosXmlService cosXmlService;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		ThemeUtil.setClassTheme(this);
		setContentView(R.layout.activity_main);
		//跳转GameListActivity要用的数据
		setApply();	
		//配置toolbar
		toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		if (CheckSimpleModeUtil.isSimpleMode())
		{
			getSupportActionBar().setTitle(R.string.simple_mode_app_name);
		}
		else
		{
			getSupportActionBar().setTitle(R.string.app_name);
		}
		toolbar.setSubtitle(R.string.ziyuan);
		replaceFragment(new MainGameFragment());
	    bottomBar();
		//initTencentCloud();
		permissionAndPrivacy();
		CheckUpdateUtil.checkUpdate(toolbar, this);
		//友盟统计
		if (UserUtil.getCurrentUser() != null)
		{
			MobclickAgent.onProfileSignIn(UserUtil.getCurrentUser().getUsername());
		}
	}
	private void permissionAndPrivacy()
	{
		SharedPreferences preferences = getSharedPreferences("boolean", 0);
        boolean pAp = preferences.getBoolean("permissionAndPrivacy", false);
		if (pAp == false)
		{
			AlertDialog.Builder permissionAndPrivacy_dialog=new AlertDialog.Builder(this)
				.setTitle(R.string.permissionandprivacy_title)
				.setMessage(R.string.permissionandprivacy_cnntent)
				.setCancelable(false)
				.setPositiveButton(getResources().getString(R.string.dia_agree), new
				DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						SharedPreferences.Editor t=getSharedPreferences("boolean", 0).edit();
						t.putBoolean("permissionAndPrivacy", true);
						t.apply();
					}
				}
			)
				.setNeutralButton(getResources().getString(R.string.dia_disagree), new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						finish();
					}
				}
			);
			permissionAndPrivacy_dialog.show();
		}
	}
	/**
	 *方法名:bottomBar
	 *不需要传入参数
	 *用于显示底部导航栏的方法
	 *内部完成了所有逻辑
	 */
	private void bottomBar()
	{
	    BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
		if (CheckSimpleModeUtil.isSimpleMode())
		{
			bottomNavigationView.getMenu().removeItem(R.id.talk);
			bottomNavigationView.getMenu().removeItem(R.id.me);
		}
		BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
				@Override
				public boolean onNavigationItemSelected(@NonNull MenuItem item)
				{
					switch (item.getItemId())
					{
						case R.id.ziyuan:
							if (toolbar.getSubtitle() != getResources().getString(R.string.ziyuan))
							{
								toolbar.setSubtitle(R.string.ziyuan);
								replaceFragment(new MainGameFragment());
							}
							break;
						case R.id.video:
							if (toolbar.getSubtitle() != getResources().getString(R.string.video_title))
							{
								toolbar.setSubtitle(R.string.video_title);
								replaceFragment(new MainVideoFragment());
							}
							break;
						case R.id.talk:
							if (toolbar.getSubtitle() != getResources().getString(R.string.talk))
							{
								toolbar.setSubtitle(R.string.talk);
								if (null == UserUtil.getCurrentUser())
								{
									replaceFragment(new MainNullFragment());
								}
								else
								{
									replaceFragment(new MainChatFragment());
								}
							}
							break;
						case R.id.me:
							if (null == UserUtil.getCurrentUser())
							{
								if (toolbar.getSubtitle() != getResources().getString(R.string.login_title))
								{
									replaceFragment(new MainLoginFragment());
									toolbar.setSubtitle(R.string.login_title);
								}
							}
							else
							{
								if (toolbar.getSubtitle() != UserUtil.getCurrentUser().getUsername())
								{
									replaceFragment(new MainUserFragment());
									toolbar.setSubtitle(UserUtil.getCurrentUser().getUsername());
								}
							}
							break;
					}
					return true;
				}
			});
	}

	/*private void initTencentCloud()
	 {
	 String appid = "1253475863";
	 String region = "ap-shanghai"; 

	 String secretId = "AKIDt4utx89lfgtCZyuqpJ0tpozDUqbLqKTg";
	 String secretKey ="7jaWYfoaUTWftA57GUrM51FgJs3S3pel";
	 long keyDuration = 600; //SecretKey 的有效时间，单位秒

	 //创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
	 CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
	 .isHttps(true)
	 .setAppidAndRegion(appid, region)
	 .setDebuggable(true)
	 .builder();

	 //创建获取签名类(请参考下面的生成签名示例，或者参考 sdk中提供的ShortTimeCredentialProvider类）
	 ShortTimeCredentialProvider localCredentialProvider = new ShortTimeCredentialProvider(secretId, secretKey, keyDuration);

	 //创建 CosXmlService 对象，实现对象存储服务各项操作.
	 Context context = getApplicationContext(); //应用的上下文

	 cosXmlService = new CosXmlService(context,serviceConfig, localCredentialProvider);
	 }*/

	/**
	 *方法名:replaceFragment
	 *需要传入参数(Fragment fragment)
	 *参数说明:需要传入一个实例化的Fragment
	 *参数举例:replaceFragment(new MainMessFragment())  MainMessFragment是对应Fragment的名称
	 *用于显示MainActivity上id为fragment的组件显示的内容
	 */
	public void replaceFragment(Fragment fragment)
	{
		FragmentManager fragmentManager=getSupportFragmentManager();
		FragmentTransaction transaction=fragmentManager.beginTransaction();
		if (CheckSimpleModeUtil.isSimpleMode() == false)
		{
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		}
		transaction.replace(R.id.main_fragment, fragment);
		transaction.commit();
	}
	/**
	 *方法名:setApply
	 *不需要传入参数
	 *用于初始化参数
	 */
	private void setApply()
	{
		SharedPreferences.Editor y=getSharedPreferences("string", 0).edit();
		y.putString("主机名称", "0");
		y.putString("游戏或模拟器名称", "0");
		y.apply();
	}
	@Override
	protected void onDestroy()//在退出程序时恢复数据
	{	
		super.onDestroy();
		SharedPreferences.Editor y=getSharedPreferences("string", 0).edit();
		y.putString("主机名称", "0");
		y.putString("游戏或模拟器名称", "0");
		y.apply();
    }
	/**
	 *方法名:setCustomTheme
	 *需要传入参数(int i)
	 *参数说明:需要传入一个int类型数据，这个数据是主题列表item对应的id，从0开始计算
	 *参数举例:setCustomTheme(0)  表示设置列表第一个主题
	 *用于设置主题颜色参数
	 */
	public void setCustomTheme(int i)
	{
		ThemeUtil.setTheme(MainActivity.this, i);
		SharedPreferences.Editor y=getSharedPreferences("customtheme", 0).edit();
		y.putInt("id", i);
		y.apply();
		open();
	}
	/**
	 *方法名:open
	 *不需要传入参数
	 *用于退出并再次打开MainActivity 适用于修改主题或者修改用户头像等之后使用
	 */
	public void open()
	{
		Intent intent = getIntent();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//假装没退出过...
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
	    startActivity(intent);
	}
	//初始化toolbar菜单
	public boolean onCreateOptionsMenu(Menu menu)
	{
        getMenuInflater().inflate(R.menu.toolbar, menu);
		if (CheckSimpleModeUtil.isSimpleMode())
		{
			menu.removeItem(R.id.theme);
		}
        return true;
    }
	//按两次退出
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK
			&& event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if ((System.currentTimeMillis() - exitTime) > 2000)
			{
				Snackbar.make(toolbar, R.string.two_back, Snackbar.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			}
			else
			{
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	//获取toolbar菜单id执行事件
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.theme:
				/**
				 *用于显示主题列表
				 *调用了方法setCustomTheme
				 */
				SharedPreferences c=getSharedPreferences("customtheme", 0);
				final int itemSelected=c.getInt("id", 0);
				AlertDialog.Builder theme = new AlertDialog.Builder(MainActivity.this);
				theme.setTitle(R.string.theme_title);
				Integer[] res = new Integer[]{
					R.drawable.buletheme,
					R.drawable.redtheme,
					R.drawable.purpletheme,
					R.drawable.lindigotheme,
					R.drawable.tealtheme,
					R.drawable.greentheme,
					R.drawable.orangetheme,
					R.drawable.browntheme,
					R.drawable.bluegreytheme,
					R.drawable.yellowtheme,
					R.drawable.kirbytheme,
					R.drawable.whitetheme
				};
				List<Integer> list = Arrays.asList(res);
				ColorListAdapter adapter = new ColorListAdapter(MainActivity.this, list);
				adapter.setCheckItem(itemSelected);
				GridView gridView = (GridView) LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_sw_theme, null);
				gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
				gridView.setCacheColorHint(0);
				gridView.setAdapter(adapter);
				theme.setView(gridView);
				final AlertDialog dialog = theme.show();
				gridView.setOnItemClickListener(
					new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id)
						{
							dialog.dismiss();
							if (itemSelected != position)
							{
								setCustomTheme(position);
							}
						}
					}

				);
				break;
			case R.id.setting:
				Intent setting=new Intent(MainActivity.this, SettingActivity.class);
				IntentUtil.startActivityWithAnim(setting, MainActivity.this);
				break;
			case R.id.app:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(R.string.tj_app);
				String[] items={"ZArchiver\n" + getResources().getString(R.string.app_ZArchiver)};
				builder.setItems(items, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i)
						{
							switch (i)
							{
								case 0:
									DownloadApkUtil.downloadappApk("ZArchiver", MainActivity.this);
									break;
							}
						}
					});
				builder.create();
				builder.show();
				break;
			case R.id.pay:
				Intent pay=new Intent(MainActivity.this, PayActivity.class);
				IntentUtil.startActivityWithAnim(pay, MainActivity.this);
				break;
			default:
		}
		return true;	
	}
	/**
	 *方法名:downloadappApk
	 *需要传入参数(final String app_namee
	 *参数说明:需要传入一个String类型的应用名称
	 *参数举例:downloadappApk("kirby Assistant")  表示查询名称为kirby Assistant的app的下载链接
	 *主要用于查询模拟器和推荐应用链接
	 *调用了方法:appFileDownload
	 */

	public void theDownload(Context con, String game_name, String position)
	{
		gameContext = con;
		switch (position)
		{
			case "emulators_gba"://"GBA " + getGameText(R.string.moniqi) + "\nMy Boy!":
				showOtherDownloadDialog("gba", game_name);
				break;
			case "emulators_sfc"://"SFC "++"\nSnes9x EX+":
				showOtherDownloadDialog("sfc", game_name);
				break;
			case "emulators_n64"://"N64 "++"\nTendo64":
				showOtherDownloadDialog("n64", game_name);
				break;
			case "emulators_nds"://"NDS "++"\nDraStic":
				showOtherDownloadDialog("nds", game_name);
				break;
			case "emulators_wii"://"NGC&WII "++"\nDolphin":
				showOtherDownloadDialog("wii", game_name);
				break;
			case "emulators_gb"://"GB&GBC "++"\nMy OldBoy!":
				showOtherDownloadDialog("gb", game_name);
				break;
			case "emulators_fc"://"FC "++"\nNES.emu":
				showOtherDownloadDialog("fc", game_name);
				break;
		}
	}
	private void showOtherDownloadDialog(final String downloadName, String game_name)
	{
		AlertDialog.Builder dialog = new
			AlertDialog.Builder(gameContext)
			.setTitle(game_name)
			.setMessage(R.string.download_dia_mess)
			.setPositiveButton(R.string.dia_download, new
			DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					DownloadApkUtil.downloadappApk(downloadName, gameContext);
				}			
			}
		);dialog.show();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// TODO: Implement this method
		super.onSaveInstanceState(outState);	
		outState.putBundle("windows_save", getWindow().saveHierarchyState());
		outState.putCharSequence("fragment", toolbar.getSubtitle());
	}
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		if (getWindow() != null)	
		{
			Bundle windowState = savedInstanceState.getBundle("windows_save");
			if (windowState != null)
			{
				getWindow().restoreHierarchyState(windowState);	
			}
		}
		CharSequence fragment_cheak=savedInstanceState.getCharSequence("fragment");
		if (fragment_cheak.equals(getResources().getString(R.string.ziyuan)))
		{
			toolbar.setSubtitle(R.string.ziyuan);
			replaceFragment(new MainGameFragment());
		}
		if (fragment_cheak.equals(getResources().getString(R.string.video_title)))
		{
			toolbar.setSubtitle(R.string.video_title);
			replaceFragment(new MainVideoFragment());
		}
		if (fragment_cheak.equals(getResources().getString(R.string.talk)))
		{
			toolbar.setSubtitle(R.string.talk);
			if (null == UserUtil.getCurrentUser())
			{
				replaceFragment(new MainNullFragment());
			}
			else
			{
				replaceFragment(new MainChatFragment());
			}
		}
		if (null == UserUtil.getCurrentUser())
		{
			if (fragment_cheak.equals(getResources().getString(R.string.login_title)))
			{
				replaceFragment(new MainLoginFragment());
				toolbar.setSubtitle(R.string.login_title);
			}
		}
		else
		{
			if (fragment_cheak.equals(UserUtil.getCurrentUser().getUsername()))
			{
				replaceFragment(new MainUserFragment());
				toolbar.setSubtitle(UserUtil.getCurrentUser().getUsername());
			}
		}

	}

	@Override
	public void onResume()
	{
		bottomBar();
		super.onResume();
	}
}
