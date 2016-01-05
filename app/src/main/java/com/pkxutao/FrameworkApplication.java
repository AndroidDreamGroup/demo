package com.pkxutao;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by pkxutao on 15/11/10.
 */
public class FrameworkApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
