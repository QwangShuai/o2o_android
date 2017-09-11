package activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ComplainImageAdapter;
import config.PermissionConfig;
import utils.Utils;

public class ComplainActivity extends CommonActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout addImageRl;
    private TextView submitTv;
    private View popView;
    private PopupWindow pop;
    private TextView cameraTv, mapTv, cancelTv;

    private GridView gv;
    private List<String> list;
    private ComplainImageAdapter adapter;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_complain, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initPopView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_complain_return);
        addImageRl = (RelativeLayout) rootView.findViewById(R.id.rl_complain_add_image);
        submitTv = (TextView) rootView.findViewById(R.id.tv_complain_sumit);
        gv = (GridView) rootView.findViewById(R.id.gv_complain_image);
    }

    private void initPopView() {
        popView = LayoutInflater.from(this).inflate(R.layout.pop_add_image, null);
        pop = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setTouchable(true);
        pop.setOutsideTouchable(true);
        cameraTv = (TextView) popView.findViewById(R.id.tv_pop_add_image_camera);
        mapTv = (TextView) popView.findViewById(R.id.tv_pop_add_image_map);
        cancelTv = (TextView) popView.findViewById(R.id.tv_pop_add_image_cancel);
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                requestPhoto();
            }
        });
        mapTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast(ComplainActivity.this, "图库");
                pop.dismiss();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new ComplainImageAdapter(this, list);
    }

    @Override
    protected void setData() {
        gv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        addImageRl.setOnClickListener(this);
        submitTv.setOnClickListener(this);
        gv.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    private void requestPhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int p = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (p != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PermissionConfig.CAMERA);
            } else {
                takePhoto();
            }
        } else {
            takePhoto();
        }
    }

    private void takePhoto() {
        if (list.size() < 5) {
            Intent cI = new Intent();
            cI.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cI, PermissionConfig.CAMERA);
        } else {
            Utils.toast(this, "最多上传五张");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_complain_return:
                finish();
                break;
            case R.id.rl_complain_add_image:
                if (pop.isShowing()) {
                    pop.dismiss();
                } else {
                    pop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                    backgroundAlpha(0.5f);
                    pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            backgroundAlpha(1.0f);
                        }
                    });
                }
                break;
            case R.id.tv_complain_sumit:
                Utils.toast(this, "提交投诉");
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionConfig.CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    Utils.toast(ComplainActivity.this, "请在系统设置里打开相机功能");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PermissionConfig.CAMERA:
                if (resultCode == RESULT_OK) {
//                    Bundle b = data.getExtras();
//                    Bitmap bm = (Bitmap) b.get("data");
//                    list.add(bm);
                    //TODO
                    Uri cameraUri = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Utils.log(ComplainActivity.this, "filePathColumn:" + filePathColumn);
                    Cursor cursor = getContentResolver().query(cameraUri, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    Utils.log(ComplainActivity.this, "picturePath:" + picturePath);
                    cursor.close();
                    list.add(picturePath);
                    adapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        list.remove(position);
        adapter.notifyDataSetChanged();
    }
}
