package talkemployer.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.gjzg.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import complain.view.ComplainActivity;
import config.IntentConfig;
import config.NetConfig;
import adapter.TalkEmployerAdapter;
import bean.TalkEmployerBean;
import evaluate.view.EvaluateActivity;
import talkemployer.presenter.TalkEmployerPresenter;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import view.CImageView;
import view.CProgressDialog;

public class TalkEmployerActivity extends AppCompatActivity implements ITalkEmployerActivity, View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, complainRl;
    private View headMapView;
    private CImageView iconIv, phoneIv;
    private ImageView sexIv;
    private TextView nameTv, infoTv, addressTv;
    private MapView mapView;
    private ListView talkLv;
    private BaiduMap baiduMap;
    private BitmapDescriptor bitmap;
    private CProgressDialog cpd;
    private String taskId;
    private TalkEmployerPresenter talkEmployerPresenter;
    private TalkEmployerBean talkEmployerBean;
    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;
    private final int SKILL_SUCCESS = 3;
    private final int SKILL_FAILURE = 4;
    private List<String> idList = new ArrayList<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        talkEmployerPresenter.loadSkill(NetConfig.skillUrl);
                        break;
                    case LOAD_FAILURE:
                        break;
                    case SKILL_SUCCESS:
                        cpd.dismiss();
                        notifyData();
                        break;
                    case SKILL_FAILURE:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_talk_employer, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
        loadData();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (handler != null) {
            handler.removeMessages(LOAD_SUCCESS);
            handler.removeMessages(LOAD_FAILURE);
            handler.removeMessages(SKILL_SUCCESS);
            handler.removeMessages(SKILL_FAILURE);
            handler = null;
        }
    }

    private void initView() {
        initRootView();
        initHeadView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_talk_employer_return);
        complainRl = (RelativeLayout) rootView.findViewById(R.id.rl_talk_employer_complain);
        talkLv = (ListView) rootView.findViewById(R.id.lv_talk_employer);
        cpd = Utils.initProgressDialog(TalkEmployerActivity.this, cpd);
    }

    private void initHeadView() {
        headMapView = LayoutInflater.from(TalkEmployerActivity.this).inflate(R.layout.head_talk_employer, null);
        iconIv = (CImageView) headMapView.findViewById(R.id.iv_head_talk_employer_icon);
        phoneIv = (CImageView) headMapView.findViewById(R.id.iv_head_talk_employer_phone);
        sexIv = (ImageView) headMapView.findViewById(R.id.iv_head_talk_employer_sex);
        nameTv = (TextView) headMapView.findViewById(R.id.tv_head_talk_employer_name);
        infoTv = (TextView) headMapView.findViewById(R.id.tv_head_talk_employer_info);
        addressTv = (TextView) headMapView.findViewById(R.id.tv_head_talk_employer_address);
        talkLv.addHeaderView(headMapView);
        mapView = (MapView) headMapView.findViewById(R.id.mv_head_talk_employer);
        mapView.showScaleControl(false);
        mapView.showZoomControls(false);
        baiduMap = mapView.getMap();
        UiSettings settings = baiduMap.getUiSettings();
        settings.setAllGesturesEnabled(false);
        settings.setOverlookingGesturesEnabled(false);
        settings.setZoomGesturesEnabled(false);
        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.point_blue);
    }

    private void initData() {
        talkEmployerPresenter = new TalkEmployerPresenter(this);
        taskId = getIntent().getStringExtra(IntentConfig.taskToTalk);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        complainRl.setOnClickListener(this);
    }

    private void loadData() {
        cpd.show();
        talkEmployerPresenter.load(NetConfig.taskBaseUrl + "?action=info&o_worker=" + UserUtils.readUserData(TalkEmployerActivity.this).getId() + "&t_id=" + taskId);
    }

    private void notifyData() {
        LatLng point = new LatLng(Double.parseDouble(talkEmployerBean.getPosY()), Double.parseDouble(talkEmployerBean.getPosX()));
        OverlayOptions overlayOptions = new MarkerOptions().position(point).icon(bitmap);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(point, Float.parseFloat("19"));
        baiduMap.animateMapStatus(mapStatusUpdate);
        baiduMap.addOverlay(overlayOptions);
        Picasso.with(TalkEmployerActivity.this).load(talkEmployerBean.getIcon()).placeholder(R.mipmap.person_face_default).error(R.mipmap.person_face_default).into(iconIv);
        String sex = talkEmployerBean.getSex();
        if (sex.equals("0")) {
            sexIv.setImageResource(R.mipmap.female);
        } else if (sex.equals("1")) {
            sexIv.setImageResource(R.mipmap.male);
        }
        nameTv.setText(talkEmployerBean.getName());
        infoTv.setText(talkEmployerBean.getDesc());
        addressTv.setText(talkEmployerBean.getAddress());
        talkLv.setAdapter(new TalkEmployerAdapter(TalkEmployerActivity.this, talkEmployerBean.getTalkEmployerWorkerBeanList()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_talk_employer_return:
                finish();
                break;
            case R.id.rl_talk_employer_complain:
                startActivity(new Intent(TalkEmployerActivity.this, EvaluateActivity.class));
                break;
        }
    }

    @Override
    public void success(String json) {
        talkEmployerBean = DataUtils.getTalkEmployerBean(json);
        for (int i = 0; i < talkEmployerBean.getTalkEmployerWorkerBeanList().size(); i++) {
            idList.add(talkEmployerBean.getTalkEmployerWorkerBeanList().get(i).getId());
        }
        handler.sendEmptyMessage(LOAD_SUCCESS);
    }

    @Override
    public void failure(String failure) {
    }

    @Override
    public void skillSuccess(String json) {
        List<String> skillNameList = new ArrayList<>();
        skillNameList.addAll(DataUtils.getSkillNameList(json, idList));
        for (int i = 0; i < skillNameList.size(); i++) {
            talkEmployerBean.getTalkEmployerWorkerBeanList().get(i).setSkill(skillNameList.get(i));
        }
        handler.sendEmptyMessage(SKILL_SUCCESS);
    }


    @Override
    public void skillFailure(String failure) {

    }
}
