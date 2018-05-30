package com.kirby.runanjing.activity;


import android.content.*;
import android.os.*;
import android.support.v7.widget.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.untils.*;

import android.support.v7.widget.Toolbar;

public class KirbyCrashActivity extends BaseActivity
{
    private Throwable crash_;

	private PhoneUtil phoneInfo;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_error);
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
		//Toast.makeText(this,getResources().getString(R.string.bug_report),Toast.LENGTH_SHORT).show();
		Intent crash=getIntent();
        crash_=(Throwable) crash.getSerializableExtra("crash");
		phoneInfo = new PhoneUtil(this);
		final TextView crashText=(TextView)findViewById(R.id.crashText);
		Button email=(Button)findViewById(R.id.email);
	  
		crashText.append("手机品牌:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getBrand() + "</font>"));
        crashText.append("\n");
        crashText.append("手机型号:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getModel() + "</font>"));
        crashText.append("\n");
        crashText.append("名称:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getProduct() + "</font>"));
        crashText.append("\n");
        crashText.append("安卓版本:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getAndroidVersion() + "</font>"));
        crashText.append("\n");
        crashText.append("软件版本:");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + phoneInfo.getAppVersion() + "</font>"));
        crashText.append("\n");
		
		crashText.append("错误信息: ");
        crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + crash_.getMessage() + "</font>"));
        crashText.append("\n");
        for (StackTraceElement stackTraceElement : crash_.getStackTrace())
		{
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            String fileName = stackTraceElement.getFileName();
            String line = stackTraceElement.getLineNumber() + "";
            crashText.append("\t\t\t\t\t\t");
            crashText.append(Html.fromHtml("<font  color=\"#E51C23\">at</font>"));
            crashText.append("\t" + className);
            crashText.append("." + methodName);
            crashText.append("(");
            crashText.append(Html.fromHtml("<font color=\"#E51C23\">" + fileName + "</font>"));
            crashText.append(":");
            crashText.append(Html.fromHtml("<u><font color=\"#5677FC\">" + line + "</font></u>"));
            crashText.append(")");
            crashText.append("\n");
        }
		email.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent email=new Intent(Intent.ACTION_SENDTO); 
					email.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {"nihaocun@163.com"});
					email.putExtra(android.content.Intent.EXTRA_SUBJECT, "Kirby Assistant Bug反馈"); 
					email.putExtra(android.content.Intent.EXTRA_TEXT, crashText.getText()); 
					email.setType("plain/text");  
					startActivity(Intent.createChooser(email, "Mail Chooser"));  
				}
			});
	}
}
