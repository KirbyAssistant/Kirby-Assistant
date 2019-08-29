package cn.endureblaze.kirby.resources.cheatcode;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseActivity;
import cn.endureblaze.kirby.bean.CheatCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CheatCodeActivity extends BaseActivity
    {
        private List<CheatCode> cheatCodeArrayList=new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_cheatcode);
            //配置toolbar
            final Toolbar toolbar= findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.tab_cheatcode);

            Intent intent=getIntent();
            String name=intent.getStringExtra("game_name");

            toolbar.setSubtitle(name);

            RecyclerView rlv_cheatcode = findViewById(R.id.cheatCode_listview);
            GridLayoutManager layoutManager_console=new GridLayoutManager(this, 1);
            rlv_cheatcode.setLayoutManager(layoutManager_console);
            CheatCodeAdapter cheatcode_adapter = new CheatCodeAdapter(cheatCodeArrayList, this);
            rlv_cheatcode.setAdapter(cheatcode_adapter);
            CheatCodeData.setCheatCodeData(Objects.requireNonNull(name),cheatCodeArrayList);
        }
}
