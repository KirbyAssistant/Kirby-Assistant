package com.kirby.runanjing.activity;


import android.content.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.untils.*;

import android.support.v7.widget.Toolbar;
import android.webkit.DownloadListener;
import com.kirby.runanjing.R;
import android.net.*;
import com.just.agentweb.*;

@ParallaxBack
public class KirbyWebActivity extends BaseActivity
{
	private String url;

	private Toolbar toolbar;

	private LinearLayout webLin;

	private AgentWeb mAgentWeb;

	@Override
    protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Theme.setClassTheme(this);
		setContentView(R.layout.activity_web);
		Intent kirby_web=getIntent();
	    url = kirby_web.getStringExtra("url");
		//配置toolbar
		toolbar = (Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		toolbar.setTitle(R.string.web_loading);
		toolbar.setSubtitle(url);
		webLin=(LinearLayout)findViewById(R.id.web);
		mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
			.setAgentWebParent(webLin, new LinearLayout.LayoutParams(-1, -1))
			.useDefaultIndicator()
			.setWebChromeClient(mWebChromeClient)
			.createAgentWeb()
			.ready()
			.go(url);
	}
	private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
			toolbar.setTitle(title);
            }
    };
	
}
