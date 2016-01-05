package com.pkxutao;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.pkxutao.framework.R;

public class TestDPActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dp);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        float density = displayMetrics.density;
        int desityDpi = displayMetrics.densityDpi;

        ((TextView)findViewById(R.id.width)).setText(""+width);
        ((TextView)findViewById(R.id.height)).setText(""+height);
        ((TextView)findViewById(R.id.density)).setText(""+density);
        ((TextView)findViewById(R.id.densityDpi)).setText(""+desityDpi);
    }
}
