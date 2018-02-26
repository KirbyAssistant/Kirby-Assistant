package com.kirby.runanjing.activity;

import android.os.*;
import android.support.v7.widget.*;
import android.widget.*;
import com.github.anzewei.parallaxbacklayout.*;
import com.kirby.runanjing.*;
import com.kirby.runanjing.untils.*;

import android.support.v7.widget.Toolbar;
import com.kirby.runanjing.R;
import android.view.View.*;
import android.view.*;
import android.didikee.donate.*;
import android.support.annotation.*;
import android.content.*;

@ParallaxBack
public class PayActivity extends BaseActivity
{
	private Button btAlipayCustom;
	private static String Donate_1="FKX05063B21OPGSJXR56C3";
	private static String Donate_3="FKX07188YP76LDKFVGTXA4";
	private static String Donate_7="FKX00251DU42GFI6R3SCA1";
	private static String Donate_USER_INPUT="FKX07472I7DSDDEO5UYS82";
	private static String Donate_USER_HONG="https://qr.alipay.com/c1x06587bn9js77fggyvmca";
	private int currentMoney = 0;
	private RadioGroup radioGroup;

	private Button btAlipayFree;

	private Button btAlipayHongbao;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        Theme.setClassTheme(this);
		setContentView(R.layout.activity_pay);
		Toolbar toolbar=(Toolbar)findViewById(R.id.标题栏);
		setSupportActionBar(toolbar);
        btAlipayCustom = ((Button) findViewById(R.id.bt_alipay));
        btAlipayFree = ((Button) findViewById(R.id.bt_alipay_free));
		btAlipayHongbao = ((Button) findViewById(R.id.bt_alipay_hongbao));
		radioGroup = ((RadioGroup) findViewById(R.id.radio_group));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
				{
					RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
					String text = checkedRadioButton.getText().toString().trim();
					currentMoney = Integer.valueOf(text.replace("元", "").trim());
					btAlipayCustom.setText("支付宝捐赠(" + currentMoney + "元)");
				}
			});
        radioGroup.getChildAt(0).performClick();
		btAlipayCustom.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// 自定义价格
					if (currentMoney == 1)
					{
						donateAlipay(Donate_1);
					}
					else if (currentMoney == 3)
					{
						donateAlipay(Donate_3);
					}
					else if (currentMoney == 7)
					{
						donateAlipay(Donate_7);
					}
				}
			});
		btAlipayFree.setOnClickListener(new View.OnClickListener(){

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
					Intent in=new Intent(PayActivity.this, KirbyWebActivity.class);
					in.putExtra("url",Donate_USER_HONG);
					startActivity(in);
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
}
