package talk.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import persondetailinfo.view.PersonDetailActivity;
import config.PermissionConfig;
import config.VarConfig;
import fragment.TalkQrkgFragment;
import fragment.TalkWyzgFragment;
import utils.Utils;

//详谈
public class TalkActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private TextView topTitleTv, noLocationTipTv;
    private ImageView iconIv, phoneIv;
    private MapView mapView;
    //取消工人弹窗
    private View cancelWorkerPopView;
    private PopupWindow cancelWorkerPopWindow;
    private TextView cancelWorkerNoTv, cancelWorkerYesTv;
    //确认开工弹窗
    private View qrkgPopView;
    private PopupWindow qrkgPopWindow;
    private TextView qrkgNoTv, qrkgChangeTv, qrkgYesTv;
    //解雇工人弹窗
    private View jggrPopView;
    private PopupWindow jggrPopWindow;
    private TextView jggrNoTv, jggrYesTv;
    //确认完工弹窗
    private View qrwgPopView;
    private PopupWindow qrwgPopWindow;
    private TextView qrwgNoTv, qrwgYesTv;

    private BaiduMap baiduMap;
    private LatLng point;
    private BitmapDescriptor bitmap;
    private OverlayOptions option;
    private MapStatusUpdate mapStatusUpdate;
    private double latitude, longitude;

    private int state = VarConfig.WYZG;
    private TalkWyzgFragment wyzgFragment;
    private TalkQrkgFragment qrkgFragment;
    private FragmentManager fragmentManager;

    private Handler wyzgHandler, qrkgHandler;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                state = msg.what;
                notifyFragment();
            }
        }
    };

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
    }

    private void initPopView() {
        //取消工人弹窗
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
                FragmentTransaction t = fragmentManager.beginTransaction();
                t.replace(R.id.ll_talk_sit, wyzgFragment);
                t.commit();
                wyzgHandler.sendEmptyMessage(VarConfig.WYZG);
            }
        });
        cancelWorkerPopWindow = new PopupWindow(cancelWorkerPopView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //确认开工弹窗
        qrkgPopView = LayoutInflater.from(this).inflate(R.layout.pop_start_work, null);
        qrkgNoTv = (TextView) qrkgPopView.findViewById(R.id.tv_pop_start_work_no);
        qrkgChangeTv = (TextView) qrkgPopView.findViewById(R.id.tv_pop_start_work_change);
        qrkgYesTv = (TextView) qrkgPopView.findViewById(R.id.tv_pop_start_work_yes);
        qrkgNoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrkgPopWindow.dismiss();
            }
        });
        qrkgChangeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrkgPopWindow.dismiss();
                Utils.toast(TalkActivity.this, "修改工资");
            }
        });
        qrkgYesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrkgPopWindow.dismiss();
                qrkgHandler.sendEmptyMessage(VarConfig.DDGR);
            }
        });
        qrkgPopWindow = new PopupWindow(qrkgPopView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //解雇工人弹窗
        jggrPopView = LayoutInflater.from(this).inflate(R.layout.pop_fire_worker, null);
        jggrNoTv = (TextView) jggrPopView.findViewById(R.id.tv_pop_fire_worker_no);
        jggrYesTv = (TextView) jggrPopView.findViewById(R.id.tv_pop_fire_worker_yes);
        jggrNoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jggrPopWindow.dismiss();
            }
        });
        jggrYesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jggrPopWindow.dismiss();
                Utils.toast(TalkActivity.this, "我要解雇工人");
            }
        });
        jggrPopWindow = new PopupWindow(jggrPopView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //确认完工弹窗
        qrwgPopView = LayoutInflater.from(this).inflate(R.layout.pop_work_done, null);
        qrwgNoTv = (TextView) qrwgPopView.findViewById(R.id.tv_pop_work_done_no);
        qrwgYesTv = (TextView) qrwgPopView.findViewById(R.id.tv_pop_work_done_yes);
        qrwgNoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrwgPopWindow.dismiss();
            }
        });
        qrwgYesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrwgPopWindow.dismiss();
                Utils.toast(TalkActivity.this, "确认完工");
            }
        });
        qrwgPopWindow = new PopupWindow(qrwgPopView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String t_id = intent.getStringExtra("t_id");
            Log.e("TAG", "t_id:" + t_id);
        }
        latitude = 39.963175;
        longitude = 116.400244;
        wyzgFragment = new TalkWyzgFragment();
        qrkgFragment = new TalkQrkgFragment();
        wyzgHandler = wyzgFragment.handler;
        qrkgHandler = qrkgFragment.handler;
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        switch (state) {
            case VarConfig.WYZG:
                t.replace(R.id.ll_talk_sit, wyzgFragment);
                wyzgHandler.sendEmptyMessage(VarConfig.WYZG);
                break;
            case VarConfig.YYQQ:
                t.replace(R.id.ll_talk_sit, wyzgFragment);
                wyzgHandler.sendEmptyMessage(VarConfig.YYQQ);
                break;
            case VarConfig.QRKG:
                t.replace(R.id.ll_talk_sit, qrkgFragment);
                qrkgHandler.sendEmptyMessage(VarConfig.QRKG);
                break;
            case VarConfig.DDGR:
                t.replace(R.id.ll_talk_sit, qrkgFragment);
                qrkgHandler.sendEmptyMessage(VarConfig.DDGR);
                break;
            case VarConfig.GCJS:
                t.replace(R.id.ll_talk_sit, qrkgFragment);
                qrkgHandler.sendEmptyMessage(VarConfig.GCJS);
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

    private void notifyFragment() {
        switch (state) {
            case VarConfig.YYQQ:
                Utils.toast(TalkActivity.this, "工作邀约已发送，等待工人同意后可进行电话沟通");
                wyzgHandler.sendEmptyMessage(VarConfig.YYQQ);
                break;
            case VarConfig.QRKG:
                Utils.toast(TalkActivity.this, "工人已接受你的邀约，可点击电话图标直接与工人联系");
                FragmentTransaction t = fragmentManager.beginTransaction();
                t.replace(R.id.ll_talk_sit, qrkgFragment);
                t.commit();
                qrkgHandler.sendEmptyMessage(VarConfig.QRKG);
                break;
            case VarConfig.QXGR:
                cancelWorkerPopWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                break;
            case VarConfig.qrkg:
                qrkgPopWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                break;
            case VarConfig.GCJS:
                qrkgHandler.sendEmptyMessage(VarConfig.GCJS);
                break;
            case VarConfig.jggr:
                jggrPopWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                break;
            case VarConfig.qrwg:
                qrwgPopWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                break;
            default:
                break;
        }
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
