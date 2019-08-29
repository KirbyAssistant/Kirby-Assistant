package cn.endureblaze.kirby.main.donate;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;
import cn.endureblaze.kirby.Kirby;
import cn.endureblaze.kirby.R;
import cn.endureblaze.kirby.base.BaseActivity;
import cn.endureblaze.kirby.util.PayUtil;
import cn.endureblaze.kirby.util.ThemeUtil;
import com.bumptech.glide.Glide;

import java.util.Objects;

public class DonateActivity extends BaseActivity {
    private static String Donate_ALIPAY="FKX07472I7DSDDEO5UYS82";
    private static String Donate_PAYPAL="https://www.paypal.me/nihaocun";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ThemeUtil.setClassTheme(this);
        setContentView(R.layout.activity_donate);
        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.donate_title);

        ImageView donate_other_image = findViewById(R.id.donate_other_image);
        Button btAlipayUserInput = findViewById(R.id.bt_alipay_user_input);
        Button paypal = findViewById(R.id.paypal);

        btAlipayUserInput.setOnClickListener(p1 -> donateAlipay(Donate_ALIPAY));

        paypal.setOnClickListener(p1 -> {
            Intent web = new Intent();
            web.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(Donate_PAYPAL);
            web.setData(content_url);
            startActivity(web);
        });

        String donate_OTHER = "https://sapi.k780.com/?app=qr.get&data=https%3A%2F%2Fkirby.endureblaze.cn%2Fpay";
        Glide.with(this)
                .load(donate_OTHER)
                .apply(Kirby.getGlideRequestOptions())
                .into(donate_other_image);
    }

    private void donateAlipay(String payCode)
    {
        PayUtil.Ailipay(payCode,DonateActivity.this);
    }
}