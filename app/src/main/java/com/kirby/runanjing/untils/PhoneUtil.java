package com.kirby.runanjing.untils;

import android.content.*;
import android.content.pm.*;
import android.os.*;

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

    //软件版本
    public String getAppVersion() {
        String result = "null";
        PackageManager packageManager = context.getPackageManager();
        try {
            result = packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
