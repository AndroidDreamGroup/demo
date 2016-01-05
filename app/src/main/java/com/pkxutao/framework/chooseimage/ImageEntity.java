package com.pkxutao.framework.chooseimage;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/3/11.
 */
public class ImageEntity implements Serializable{
    long date;
    String path;
    int degree;
    boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
