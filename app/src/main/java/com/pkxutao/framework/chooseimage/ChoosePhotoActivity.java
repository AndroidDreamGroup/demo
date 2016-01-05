package com.pkxutao.framework.chooseimage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RelativeLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.pkxutao.framework.util.DeviceInfo;
import com.pkxutao.framework.util.LogUtil;
import com.pkxutao.framework.R;
import com.pkxutao.framework.Tip;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Created by Administrator on 2015/3/11.
 */
public class ChoosePhotoActivity extends ActionBarActivity {
    private GridView _gridView;
    private ChooseAdapter _adapter;
    private Toolbar _toolbar;
    private int _width;
    private Cursor _cursor;
    private ArrayList<ImageEntity> _returnPhotoDTOList;
    private int _count;
    private boolean _isUseCamera;
    private int _needCount;//需要的图片数目
    private boolean _isCutOut;//是否需要裁剪
    private final int USE_CAMERA = 2;
    private final int CUT_PHOTO = 3;
    private long _dataTaken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_photo);
        _dataTaken = new Date().getTime();
        _isUseCamera = getIntent().getBooleanExtra("isUseCamera",false);
        if(_isUseCamera){
            Intent intent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            //下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(),
                            _dataTaken+".jpg")));
            startActivityForResult(intent, USE_CAMERA);
        }
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        _needCount = getIntent().getIntExtra("photoCount", 10);
        _isCutOut = getIntent().getBooleanExtra("cutout", false);
        _width = (DeviceInfo.getScreenWidth(this) - 10) / 3;//获取每个图片所占据的宽度
        _cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
        _cursor.moveToFirst();
        _returnPhotoDTOList = new ArrayList<>();
        while (_cursor.moveToNext()) {
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setPath(_cursor.getString(_cursor.getColumnIndex("_data")));//从数据库中获取文件路径
            imageEntity.setDegree(_cursor.getInt(_cursor.getColumnIndex("orientation")));//获取图片是否旋转
//            LogUtil.e("==============================",_cursor.getLong(_cursor.getColumnIndex("datetaken"))+"");
            if(_cursor.getLong(_cursor.getColumnIndex("datetaken"))==0){
                File file = new File( _cursor.getString(_cursor.getColumnIndex("_data")));
                if (file.exists()){
                    long date = file.lastModified();
                    int b = 0;
                    for(ImageEntity a:_returnPhotoDTOList){
                        b++;
                        if(date>a.getDate()){
                            imageEntity.setDate(file.lastModified());//获取时间戳
                            _returnPhotoDTOList.add(b,imageEntity);
                            return;
                        }
                    }
                    LogUtil.e("------------------------------" + file, file.lastModified() + "");
                }
            }else{
                if (new File( _cursor.getString(_cursor.getColumnIndex("_data"))).exists()){
                    imageEntity.setDate(_cursor.getLong(_cursor.getColumnIndex("datetaken")));//获取时间戳
                    _returnPhotoDTOList.add(imageEntity);
                }
            }
        }

    }

    private void initView() {
        initToolBar();
        _gridView = (GridView) findViewById(R.id.gv_activity_choose_photo);
        _adapter = new ChooseAdapter();
        _gridView.setAdapter(_adapter);
    }

    private void initToolBar() {
        _toolbar = (Toolbar) findViewById(R.id.toolbar);
        _toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        _toolbar.setTitle("添加照片");
        setSupportActionBar(_toolbar);

        _toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class ChooseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return _returnPhotoDTOList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = ChoosePhotoActivity.this.getLayoutInflater().inflate(R.layout.item_choose_photo, null);
            } else {
                view = convertView;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_isCutOut) {
                        startPhotoZoom(Uri.parse("file://" + _returnPhotoDTOList.get(position).getPath()));
                    } else {
                        if (_returnPhotoDTOList.get(position).isChecked()) {
                            _returnPhotoDTOList.get(position).setChecked(false);
                            _count--;
                            ((CheckBox) v.findViewById(R.id.cb_item_choose_photo)).setChecked(false);
                            v.findViewById(R.id.iv_choosed_item_choose_photo).setVisibility(View.GONE);
                        } else {
                            if (_count < _needCount) {
                                _count++;
                                _returnPhotoDTOList.get(position).setChecked(true);
                                ((CheckBox) v.findViewById(R.id.cb_item_choose_photo)).setChecked(true);
                                v.findViewById(R.id.iv_choosed_item_choose_photo).setVisibility(View.VISIBLE);
                            } else {
                                Tip.show(ChoosePhotoActivity.this, "最多选择" + _needCount + "张图片");
                            }
                        }
                    }
                }
            });
            if (_returnPhotoDTOList.get(position).isChecked()) {//在初始化view的是否对是否选中进行判断
                ((CheckBox) view.findViewById(R.id.cb_item_choose_photo)).setChecked(true);
                view.findViewById(R.id.iv_choosed_item_choose_photo).setVisibility(View.VISIBLE);
            } else {
                ((CheckBox) view.findViewById(R.id.cb_item_choose_photo)).setChecked(false);
                view.findViewById(R.id.iv_choosed_item_choose_photo).setVisibility(View.GONE);
            }
            SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.iv_item_choose_photo);
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(_width, _width));
            view.findViewById(R.id.iv_choosed_item_choose_photo).setLayoutParams(new RelativeLayout.LayoutParams(_width, _width));
            Uri uri = Uri.parse("file://" +_returnPhotoDTOList.get(position).getPath());
            int width = 300, height = 300;
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .build();
            imageView.setAspectRatio(1);
            imageView.setController(controller);
//            ImageLoader.getInstance().displayImage("file://" +_returnPhotoDTOList.get(position).getPath(), imageView, _options);
            return view;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_choose_photo, menu);
        MenuItem mapItem = menu.findItem(R.id.action_ok);
        MenuItemCompat.setShowAsAction(mapItem,
                MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_ok:
                photoIsOK();
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void photoIsOK() {
        Intent intent = new Intent();
            ArrayList<ImageEntity> photoDTOArrayList = new ArrayList<>();
            for (ImageEntity photo : _returnPhotoDTOList) {
                if (photo.isChecked()) {
                    photoDTOArrayList.add(photo);
                }
            }
            if(photoDTOArrayList.size()==0){
                Tip.show(this, "请选择图片");
            }else {
                intent.putExtra("photoList", photoDTOArrayList);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 拍照
            case USE_CAMERA:
                if(resultCode==RESULT_OK){
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/"+_dataTaken+".jpg");
                    startPhotoZoom(Uri.fromFile(temp));
                }
                break;
            case CUT_PHOTO:
                if (data != null&&resultCode==RESULT_OK) {//非空验证一定要加
                    setPicToView(data);
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPicToView(Intent picdata) {
        setResult(Activity.RESULT_OK, picdata);
        finish();
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);//宽高比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 100);//剪裁后图片宽高
        intent.putExtra("outputY", 100);
        intent.putExtra("scale", true);//黑边
        intent.putExtra("scaleUpIfNeeded", true);//黑边
        intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                + "/face.jpg")));
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("return-data", true);//是否返回bitmap
        startActivityForResult(intent, CUT_PHOTO);
    }

}
