package cn.endureblaze.ka.resources.cheatcode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import cn.endureblaze.ka.R;
import cn.endureblaze.ka.base.BaseActivity;
import cn.endureblaze.ka.bean.CheatCode;
import cn.endureblaze.ka.utils.ThemeUtil;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		final Toolbar toolbar= findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.cheatCode_title);
		//设置List的适配器
		CheatCodeAdapter adapter=new CheatCodeAdapter(CheatCodeActivity.this, R.layout.item_cheatcode, list);
		ListView listview= findViewById(R.id.cheatCode_listview);
		listview.setAdapter(adapter);
		Intent intent=getIntent();
		String name=intent.getStringExtra("game_name");
		CheatCodeData.setCheatCodeData(Objects.requireNonNull(name),list);
		listview.setOnItemClickListener((parent, view, position, id) -> {
			CheatCode cheatCode=list.get(position);
			String q=cheatCode.getCheatCode();
			ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            Objects.requireNonNull(cm).setPrimaryClip(ClipData.newPlainText("cheat_code",q));
            if (cm.hasPrimaryClip()) {
                Objects.requireNonNull(cm.getPrimaryClip()).getItemAt(0).getText();
            }
			Toast.makeText(CheatCodeActivity.this,getResources().getString(R.string.copy_success),Toast.LENGTH_SHORT).show();
		});
	}
}
