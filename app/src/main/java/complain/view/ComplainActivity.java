package complain.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.ComplainImageAdapter;
import complain.adapter.CplIsAdapter;
import complain.bean.ComplainIssueBean;
import complain.presenter.ComplainPresenter;
import complain.presenter.IComplainPresenter;
import config.IntentConfig;
import config.PathConfig;
import config.PermissionConfig;
import pic.view.PicActivity;
import utils.Utils;
import view.CProgressDialog;

public class ComplainActivity extends AppCompatActivity implements IComplainActivity, View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout addImageRl;
    private TextView submitTv;
    private View popView;
    private PopupWindow pop;
    private TextView cameraTv, mapTv, cancelTv;
    private CProgressDialog cpd;
    private GridView gv;
    private List<Bitmap> list;
    private ComplainImageAdapter adapter;
    private String picPath;
    private List<String> upLoadImageList;
    private IComplainPresenter iComplainPresenter;
    private TextView cplIsTv;
    private View cplIsPopView;
    private PopupWindow cplIsPop;
    private ListView cplIsLv;
    private List<ComplainIssueBean> cplIsList;
    private CplIsAdapter cplIsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_complain, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    private void initView() {
        initRootView();
        initCplIsPopView();
        initPopView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_complain_return);
        addImageRl = (RelativeLayout) rootView.findViewById(R.id.rl_complain_add_image);
        submitTv = (TextView) rootView.findViewById(R.id.tv_complain_sumit);
        gv = (GridView) rootView.findViewById(R.id.gv_complain_image);
        cpd = Utils.initProgressDialog(this, cpd);
    }

    private void initCplIsPopView() {
        cplIsTv = (TextView) rootView.findViewById(R.id.tv_complain_issue);
        cplIsPopView = LayoutInflater.from(ComplainActivity.this).inflate(R.layout.pop_lv, null);
        cplIsLv = (ListView) cplIsPopView.findViewById(R.id.lv_pop_lv);
        cplIsPop = new PopupWindow(cplIsPopView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        cplIsPop.setFocusable(true);
        cplIsPop.setTouchable(true);
        cplIsPop.setOutsideTouchable(true);
        cplIsList = new ArrayList<>();
        cplIsAdapter = new CplIsAdapter(ComplainActivity.this, cplIsList);
        cplIsLv.setAdapter(cplIsAdapter);
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
                if (Utils.hasSdcard()) {
                    requestPhoto();
                } else {
                    Utils.toast(ComplainActivity.this, "Sd卡不可用");
                }
            }
        });
        mapTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                startActivityForResult(new Intent(ComplainActivity.this, PicActivity.class), IntentConfig.PIC_REQUEST);
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new ComplainImageAdapter(this, list);
        upLoadImageList = new ArrayList<>();
        iComplainPresenter = new ComplainPresenter(this);
    }

    private void setData() {
        gv.setAdapter(adapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        cplIsTv.setOnClickListener(this);
        addImageRl.setOnClickListener(this);
        submitTv.setOnClickListener(this);
        cplIsLv.setOnItemClickListener(this);
        gv.setOnItemClickListener(this);
    }

    private void loadData() {
        iComplainPresenter.loadCplIs("2");
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
        Intent cI = new Intent();
        cI.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String dateStr = sdf.format(new Date());
        picPath = PathConfig.cameraPath + "IMG_" + dateStr + ".jpg";
        File picFile = new File(picPath);
        Uri picUri = Uri.fromFile(picFile);
        cI.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        startActivityForResult(cI, PermissionConfig.CAMERA);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_complain_return:
                finish();
                break;
            case R.id.tv_complain_issue:
                cplIsPop.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                backgroundAlpha(0.5f);
                cplIsPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1.0f);
                    }
                });
                break;
            case R.id.rl_complain_add_image:
                if (list.size() < 5) {
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
                } else {
                    Utils.toast(ComplainActivity.this, "最多上传五张!");
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
        if (requestCode == PermissionConfig.CAMERA && resultCode == RESULT_OK) {
            Utils.log(ComplainActivity.this, "camera=" + picPath);
            upLoadImageList.add(picPath);
            Utils.log(ComplainActivity.this, "upLoadImageList=" + upLoadImageList.toString());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            options.inTempStorage = new byte[1024];
            Bitmap bitmap = BitmapFactory.decodeFile(picPath, options);
            list.add(bitmap);
            adapter.notifyDataSetChanged();
        }
        if (requestCode == IntentConfig.PIC_REQUEST && resultCode == IntentConfig.PIC_RESULT && data != null) {
            List<String> l = new ArrayList<>();
            l.addAll(data.getStringArrayListExtra(IntentConfig.PIC));
            upLoadImageList.addAll(l);
            Utils.log(ComplainActivity.this, "upLoadImageList=" + upLoadImageList.toString());
            Utils.log(ComplainActivity.this, "l=" + l.toString());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 10;
            options.inTempStorage = new byte[1024];
            for (int i = 0; i < l.size(); i++) {
                Bitmap bitmap = BitmapFactory.decodeFile(l.get(i), options);
                list.add(bitmap);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_pop_lv:
                cplIsTv.setText(cplIsList.get(position).getName());
                cplIsPop.dismiss();
                break;
            case R.id.gv_complain_image:
                list.remove(position);
                upLoadImageList.remove(position);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void showLoading() {
        cpd.show();
    }

    @Override
    public void hideLoading() {
        cpd.dismiss();
    }

    @Override
    public void showLoadIssueSuccess(List<ComplainIssueBean> complainIssueBeanList) {
        Log.e("CplIs", complainIssueBeanList.toString());
        cplIsList.addAll(complainIssueBeanList);
        cplIsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadIssueFailure(String failure) {

    }
}
