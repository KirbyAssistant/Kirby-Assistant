package com.kirby.runanjing.activity;
import android.os.*;
import android.support.v7.widget.*;
import android.webkit.*;
import android.widget.*;

import com.kirby.runanjing.*;
import com.kirby.runanjing.utils.*;

import android.support.v7.widget.Toolbar;
import com.kirby.runanjing.R;
import android.graphics.*;
import com.kirby.runanjing.base.*;

public class KirbyWebActivity extends BaseActivity
{

	//protected AgentWeb mAgentWeb;

	private LinearLayout mLinearLayout;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_web);
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		mLinearLayout=(LinearLayout)findViewById(R.id.web);
		initinitweb();
	}

	private void initinitweb()
	{
		/*mAgentWeb = AgentWeb.with(this)
			.setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))                
			.useDefaultIndicator()
			.setWebViewClient(mWebViewClient)
			.createAgentWeb()
			.ready()
			.go("https://eyun.baidu.com/s/3kURIBIZ");*/
	 }
	private WebViewClient mWebViewClient=new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Toast.makeText(KirbyWebActivity.this,""+url,Toast.LENGTH_SHORT).show();
        }
    };
}
