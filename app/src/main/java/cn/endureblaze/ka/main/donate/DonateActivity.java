package cn.endureblaze.ka.main.donate;

import android.content.*;
import android.os.*;
import androidx.appcompat.widget.*;
import android.view.*;
import android.widget.*;
import cn.endureblaze.ka.utils.*;

import androidx.appcompat.widget.Toolbar;
import cn.endureblaze.ka.R;
import android.net.*;
import cn.endureblaze.ka.base.*;
import com.github.anzewei.parallaxbacklayout.*;

@ParallaxBack
public class DonateActivity extends BaseActivity
{
	private static String Donate_USER_INPUT="FKX07472I7DSDDEO5UYS82";
	private static String Donate_PAYPAL="https://www.paypal.me/nihaocun";
	private static String Donate_QQ="mqqapi://forward/url?url_prefix=aHR0cHM6Ly9raXJieWFzc2lzdGFudC50ay9jbi9wYXkuaHRtbA==&souce=oicqzone.com&version=1&src_type=web";
	
	private Button btAlipayUserInput;

	private Button paypal;

	private Button qq;

	private Button btc;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        ThemeUtil.setClassTheme(this);
		setContentView(R.layout.activity_donate);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(R.string.donate_title);
		btAlipayUserInput = ((Button) findViewById(R.id.bt_alipay_user_input));
		paypal=((Button)findViewById(R.id.paypal));
		qq=((Button)findViewById(R.id.qq));
		btc=((Button)findViewById(R.id.btc));
		btAlipayUserInput.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					donateAlipay(Donate_USER_INPUT);
				}
			});
		paypal.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent web = new Intent();        
					web.setAction("android.intent.action.VIEW");    
					Uri content_url = Uri.parse(Donate_PAYPAL);   
					web.setData(content_url);  
					startActivity(web);  
				}
			});
		btc.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					String btc_account="13yQa3Q95hZJR3VvmaM3XNj39AQVMgkg8P";
					ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
					cm.setText(btc_account);
					Toast.makeText(DonateActivity.this,getResources().getString(R.string.copy_success),Toast.LENGTH_SHORT).show();
				}
			});
		qq.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent web = new Intent();        
					web.setAction("android.intent.action.VIEW");    
					Uri content_url = Uri.parse(Donate_QQ);   
					web.setData(content_url);  
					startActivity(web);  
				}
			});
	}
    
	private void donateAlipay(String payCode)
	{
        PayUtil.Ailipay(payCode,DonateActivity.this);
    }
}
