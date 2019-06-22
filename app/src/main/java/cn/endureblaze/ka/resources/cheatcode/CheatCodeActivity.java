package cn.endureblaze.ka.resources.cheatcode;

import android.content.*;
import android.os.*;
import androidx.appcompat.widget.*;
import android.view.*;
import android.widget.*;
import cn.endureblaze.ka.bean.*;
import cn.endureblaze.ka.utils.*;
import java.util.*;

import androidx.appcompat.widget.Toolbar;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.*;
import com.github.anzewei.parallaxbacklayout.*;

@ParallaxBack
public class CheatCodeActivity extends BaseActivity
{
	private List<CheatCode>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		ThemeUtil.setClassTheme(this);
		setContentView(R.layout.activity_cheatcode);
		//配置toolbar
		final Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(R.string.cheatCode_title);
		//设置List的适配器
		CheatCodeAdapter adapter=new CheatCodeAdapter(CheatCodeActivity.this, R.layout.item_cheatcode, list);
		ListView listview=(ListView)findViewById(R.id.cheatCode_listview);
		listview.setAdapter(adapter);
		Intent intent=getIntent();
		String name=intent.getStringExtra("game_name");
		Toast.makeText(this,name,Toast.LENGTH_LONG).show();
		CheatCodeData.setCheatCodeData(name,list);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?>parent,View view,int position,long id){
				CheatCode cheatCode=list.get(position);
				String q=cheatCode.getCheatCode();
				ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
				cm.setText(q);
				Toast.makeText(CheatCodeActivity.this,getResources().getString(R.string.copy_success),Toast.LENGTH_SHORT).show();
			}
		});
	}
}
