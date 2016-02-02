package com.pkxutao;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.net.CookieHandler;
import java.net.CookiePolicy;

/**
 * Created by pkxutao on 15/11/10.
 */
public class FrameworkApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init(){

        Fresco.initialize(this);


        // enable cookies
        java.net.CookieManager cookieManager = new java.net.CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookieManager);

    }


}
