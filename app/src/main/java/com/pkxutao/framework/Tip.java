package com.pkxutao.framework;

import android.content.Context;
import android.widget.Toast;

/**
 *
 * Created by pkxutao on 15/11/8.
 */
public class Tip {

    public static void show(Context context, String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
