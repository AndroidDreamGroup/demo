package com.pkxutao.framework.http;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by pkxut on 2015/11/24.
 */
public class Params {
    List<Param> params;

    public Params() {
        params = new ArrayList<>();
    }

    public void put(String key, Object value){
        params.add(new Param(key, value));
    }

    public List<Param> getList(){
        return params;
    }


   public class Param{
        public String key;
        public Object value;

        public Param(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
