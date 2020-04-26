package cn.endureblaze.kirby.util;

import android.content.Context;
import android.os.Build;

public class PhoneUtil
{
    private Context context;//mContext

    public PhoneUtil(Context context) {
        this.context = context;
    }

    //手机品牌
    public String getBrand() {
        return Build.BRAND;
    }

    //手机型号
    public String getModel() {
        return Build.MODEL;
    }

    //名称
    public String getProduct() {
        return Build.PRODUCT;
    }

    //安卓版本
    public String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }
}
