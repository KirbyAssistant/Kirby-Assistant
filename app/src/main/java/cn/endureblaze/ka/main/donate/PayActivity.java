package cn.endureblaze.ka.main.donate;

import android.content.*;
import android.didikee.donate.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import cn.endureblaze.ka.*;
import cn.endureblaze.ka.utils.*;

import android.support.v7.widget.Toolbar;
import cn.endureblaze.ka.R;
import android.net.*;
import java.io.*;
import android.graphics.*;
import cn.endureblaze.ka.base.*;
import com.github.anzewei.parallaxbacklayout.*;

@ParallaxBack
public class PayActivity extends BaseActivity
{
	private Button btAlipayCustom;
	//private static String Donate_1="FKX05063B21OPGSJXR56C3";
	//private static String Donate_3="FKX07188YP76LDKFVGTXA4";
	//private static String Donate_7="FKX00251DU42GFI6R3SCA1";
	private static String Donate_USER_INPUT="FKX07472I7DSDDEO5UYS82";
	private static String Donate_USER_HONG="https://qr.alipay.com/c1x06587bn9js77fggyvmca";
	private static String Donate_PAYPAL="https://www.paypal.me/nihaocun";
	private static String Donate_QQ="mqqapi://forward/url?url_prefix=aHR0cHM6Ly9raXJieWFzc2lzdGFudC50ay9jbi9wYXkuaHRtbA==&souce=oicqzone.com&version=1&src_type=web";
	private int currentMoney = 0;
	private RadioGroup radioGroup;

	private Button btAlipayUserInput;

	private Button btAlipayHongbao;

	private Button paypal;

	private Button wechat;

	private Button qq;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        ThemeUtil.setClassTheme(this);
		setContentView(R.layout.activity_pay);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle(R.string.pay_title);
		//  btAlipayCustom = ((Button) findViewById(R.id.bt_alipay));
        btAlipayUserInput = ((Button) findViewById(R.id.bt_alipay_user_input));
		btAlipayHongbao = ((Button) findViewById(R.id.bt_alipay_hongbao));
		wechat=((Button)findViewById(R.id.wechat));
		paypal=((Button)findViewById(R.id.paypal));
		qq=((Button)findViewById(R.id.qq));
		btAlipayUserInput.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					donateAlipay(Donate_USER_INPUT);
				}
			});
		btAlipayHongbao.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent web = new Intent();        
					web.setAction("android.intent.action.VIEW");    
					Uri content_url = Uri.parse(Donate_USER_HONG);   
					web.setData(content_url);  
					startActivity(web);  
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
		wechat.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					donateWeixin();
				}
			});
	}
    
	private void donateAlipay(String payCode)
	{
        boolean hasInstalledAlipayClient = AlipayDonate.hasInstalledAlipayClient(PayActivity.this);
        if (hasInstalledAlipayClient)
		{
            AlipayDonate.startAlipayClient(PayActivity.this, payCode);
        }
    }
	private void donateWeixin() {
        InputStream weixinQrIs = getResources().openRawResource(R.raw.wechat);
        String qrPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "AndroidDonateSample" + File.separator +
			"kirby_assisstant_wechat.png";
        WeiXinDonate.saveDonateQrImage2SDCard(qrPath, BitmapFactory.decodeStream(weixinQrIs));
        WeiXinDonate.donateViaWeiXin(this, qrPath);
    }
}
