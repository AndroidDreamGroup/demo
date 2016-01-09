package com.pkxutao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.pkxutao.framework.chooseimage.ChoosePhotoActivity;
import com.pkxutao.framework.http.Params;
import com.pkxutao.framework.http.callback.HttpUtil;
import com.pkxutao.framework.util.LogUtil;
import com.pkxutao.framework.R;
import com.squareup.okhttp.Request;

public class MainActivity extends Activity implements View.OnClickListener {
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById(R.id.main_get).setOnClickListener(this);
        findViewById(R.id.main_post).setOnClickListener(this);
        findViewById(R.id.main_choose_photo).setOnClickListener(this);
        findViewById(R.id.main_test_display).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_get:
                get();
                break;
            case R.id.main_post:
                post();
                break;
            case R.id.main_choose_photo:
                choosePhoto();
                break;
            case R.id.main_test_display:
                testDisplay();
                break;
        }

    }
    private void testDisplay(){
        Intent intent = new Intent(this, TestDPActivity.class);
        startActivity(intent);
    }

    private void choosePhoto(){
        Intent intent = new Intent(this, ChoosePhotoActivity.class);
        intent.putExtra("photoCount", 8);
        startActivity(intent);
    }

    private void get() {
        Params params = new Params();
        params.put("Client",1);
        params.put("Channelkey", "xiaomi");
        new HttpUtil<VersionDTO>(this).post(params, VersionDTO.class, "Base/AppGetNewVersion", new HttpUtil.HttpCallBack<VersionDTO>() {
            @Override
            public void onFail(Request request, Exception e) {
                LogUtil.e(TAG, "check version fail: "   );
                e.printStackTrace();
            }

            @Override
            public void onSuccess(VersionDTO entity) {
                if (entity != null){
                    LogUtil.e(TAG, entity.getMsg());
                }else{
                    LogUtil.e(TAG, "entity is null");
                }
            }
        });
    }
//
    private void post() {
//        new httputil

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
