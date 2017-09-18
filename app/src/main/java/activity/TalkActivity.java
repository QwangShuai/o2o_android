package activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.gjzg.R;

import config.PermissionConfig;
import config.VarConfig;
import fragment.TalkQrkgFragment;
import fragment.TalkWyzgFragment;

//详谈
public class TalkActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private TextView topTitleTv, noLocationTipTv;
    private ImageView iconIv, phoneIv;
    private MapView mapView;

    private View cancelWorkerPopView;
    private PopupWindow cancelWorkerPopWindow;
    private TextView cancelWorkerNoTv, cancelWorkerYesTv;

    private BaiduMap baiduMap;
    private LatLng point;
    private BitmapDescriptor bitmap;
    private OverlayOptions option;
    private MapStatusUpdate mapStatusUpdate;
    private double latitude, longitude;

    private int state = VarConfig.WYZG;
    private Fragment wyzgFragment, qrkgFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_talk, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
        checkLocPermisson();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void initView() {
        initRootView();
        initPopView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_talk_return);
        topTitleTv = (TextView) rootView.findViewById(R.id.tv_talk_top_title);
        mapView = (MapView) rootView.findViewById(R.id.mv_worker);
        noLocationTipTv = (TextView) rootView.findViewById(R.id.tv_talk_no_location_tip);
        iconIv = (ImageView) rootView.findViewById(R.id.iv_talk_icon);
        phoneIv = (ImageView) rootView.findViewById(R.id.iv_talk_phone);
        phoneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelWorkerPopWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
            }
        });
    }

    private void initPopView() {
        cancelWorkerPopView = LayoutInflater.from(this).inflate(R.layout.pop_cancel_worker, null);
        cancelWorkerNoTv = (TextView) cancelWorkerPopView.findViewById(R.id.tv_pop_cancel_worker_no);
        cancelWorkerYesTv = (TextView) cancelWorkerPopView.findViewById(R.id.tv_pop_cancel_worker_yes);
        cancelWorkerNoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelWorkerPopWindow.dismiss();
            }
        });
        cancelWorkerYesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelWorkerPopWindow.dismiss();
            }
        });
        cancelWorkerPopWindow = new PopupWindow(cancelWorkerPopView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void initData() {
        latitude = 39.963175;
        longitude = 116.400244;
        wyzgFragment = new TalkWyzgFragment();
        qrkgFragment = new TalkQrkgFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        switch (state) {
            case VarConfig.WYZG:
                t.replace(R.id.ll_talk_sit, wyzgFragment);
                break;
            case VarConfig.YYQQ:
                t.replace(R.id.ll_talk_sit, wyzgFragment);
                break;
            case VarConfig.QRKG:
                t.replace(R.id.ll_talk_sit, qrkgFragment);
                break;
            case VarConfig.DDGR:
                t.replace(R.id.ll_talk_sit, qrkgFragment);
                break;
            case VarConfig.GGJS:
                t.replace(R.id.ll_talk_sit, qrkgFragment);
                break;
            default:
                break;
        }
        t.commit();
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        iconIv.setOnClickListener(this);
    }

    private void checkLocPermisson() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permisson = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permisson != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PermissionConfig.LOCATION);
            } else {
                loadLocation();
            }
        } else {
            loadLocation();
        }
    }

    private void loadLocation() {
        baiduMap = mapView.getMap();
        point = new LatLng(latitude, longitude);
        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.point_blue);
        option = new MarkerOptions().position(point).icon(bitmap);
        mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(point,
                Float.parseFloat("19"));
        baiduMap.animateMapStatus(mapStatusUpdate);
        baiduMap.addOverlay(option);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_talk_return:
                finish();
                break;
            case R.id.iv_talk_icon:
                startActivity(new Intent(this, PersonDetailActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionConfig.LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadLocation();
                } else {
                    mapView.setVisibility(View.INVISIBLE);
                    noLocationTipTv.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
