package talkworker.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bean.TalkToSelect;
import config.IntentConfig;
import config.NetConfig;
import persondetail.view.PersonDetailActivity;
import selecttask.view.SelectTaskActivity;
import talkworker.presenter.ITalkWorkerPresenter;
import talkworker.presenter.TalkWorkerPresenter;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import bean.WorkerBean;
import view.CImageView;
import view.CProgressDialog;

public class TalkWorkerActivity extends AppCompatActivity implements ITalkWorkerActivity, View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, waitRl, doingRl;
    private CProgressDialog cpd;
    private CImageView iconIv, phoneIv;
    private ImageView sexIv, statusIv;
    private TextView nameTv, skillTv, infoTv, addressTv;
    private MapView mapView;
    private BaiduMap baiduMap;
    private BitmapDescriptor bitmap;

    private ITalkWorkerPresenter talkWorkerPresenter;
    private WorkerBean workerBean;

    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;
    private final int CHECK_HAVE = 3;
    private final int CHECK_NONE = 4;
    private String skill;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        notifyData();
                        break;
                    case LOAD_FAILURE:
                        break;
                    case CHECK_HAVE:
                        Intent intent = new Intent(TalkWorkerActivity.this, SelectTaskActivity.class);
                        TalkToSelect talkToSelect = new TalkToSelect();
                        talkToSelect.setWorkerId(workerBean.getWorkerId());
                        talkToSelect.setSkill(skill);
                        intent.putExtra(IntentConfig.talkToSelect, talkToSelect);
                        startActivity(intent);
                        break;
                    case CHECK_NONE:
                        Utils.toast(TalkWorkerActivity.this, "请先去发布工作");
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(TalkWorkerActivity.this).inflate(R.layout.activity_talk_worker, null);
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
            handler.removeMessages(CHECK_HAVE);
            handler.removeMessages(CHECK_NONE);
            handler = null;
        }
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_talk_worker_return);
        waitRl = (RelativeLayout) rootView.findViewById(R.id.rl_talk_worker_wait);
        doingRl = (RelativeLayout) rootView.findViewById(R.id.rl_talk_worker_doing);
        cpd = Utils.initProgressDialog(TalkWorkerActivity.this, cpd);
        iconIv = (CImageView) rootView.findViewById(R.id.iv_talk_worker_icon);
        phoneIv = (CImageView) rootView.findViewById(R.id.iv_talk_worker_phone);
        sexIv = (ImageView) rootView.findViewById(R.id.iv_talk_worker_sex);
        statusIv = (ImageView) rootView.findViewById(R.id.iv_talk_worker_status);
        nameTv = (TextView) rootView.findViewById(R.id.tv_talk_worker_name);
        skillTv = (TextView) rootView.findViewById(R.id.tv_talk_worker_skill);
        infoTv = (TextView) rootView.findViewById(R.id.tv_talk_worker_info);
        addressTv = (TextView) rootView.findViewById(R.id.tv_talk_worker_address);
        mapView = (MapView) rootView.findViewById(R.id.mv_talk_worker);
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
        talkWorkerPresenter = new TalkWorkerPresenter(this);
        skill = getIntent().getStringExtra(IntentConfig.workerToTalkSkill);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        iconIv.setOnClickListener(this);
        phoneIv.setOnClickListener(this);
        waitRl.setOnClickListener(this);
    }

    private void loadData() {
        cpd.show();
        talkWorkerPresenter.load(NetConfig.workerUrl + "?u_id=" + ((WorkerBean) getIntent().getSerializableExtra(IntentConfig.workerToTalk)).getWorkerId());
    }

    private void notifyData() {
        Picasso.with(TalkWorkerActivity.this).load(workerBean.getIcon()).placeholder(R.mipmap.person_face_default).error(R.mipmap.person_face_default).into(iconIv);
        String sex = workerBean.getSex();
        if (sex.equals("0")) {
            sexIv.setImageResource(R.mipmap.female);
        } else if (sex.equals("-1")) {
            sexIv.setImageResource(R.mipmap.male);
        }
        String status = workerBean.getStatus();
        if (status.equals("0")) {
            statusIv.setImageResource(R.mipmap.worker_leisure);
            waitRl.setVisibility(View.VISIBLE);
            doingRl.setVisibility(View.GONE);
        } else {
            statusIv.setImageResource(R.mipmap.worker_mid);
            waitRl.setVisibility(View.GONE);
            doingRl.setVisibility(View.VISIBLE);
        }
        nameTv.setText(workerBean.getTitle());
        skillTv.setText(workerBean.getSkillName());
        infoTv.setText(workerBean.getInfo());
        addressTv.setText(workerBean.getAddress());
        loadMap();
        cpd.dismiss();
    }

    private void loadMap() {
        if (TextUtils.isEmpty(workerBean.getPositionX()) || workerBean.getPositionX().equals("null")) {
            workerBean.setPositionX("0");
        }
        if (TextUtils.isEmpty(workerBean.getPositionY()) || workerBean.getPositionY().equals("null")) {
            workerBean.setPositionY("0");
        }
        LatLng latLng = new LatLng(Double.parseDouble(workerBean.getPositionY()), Double.parseDouble(workerBean.getPositionX()));
        OverlayOptions overlayOptions = new MarkerOptions().position(latLng).icon(bitmap);
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latLng, Float.parseFloat("19"));
        baiduMap.animateMapStatus(mapStatusUpdate);
        baiduMap.addOverlay(overlayOptions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_talk_worker_return:
                finish();
                break;
            case R.id.iv_talk_worker_icon:
                Intent intent = new Intent(TalkWorkerActivity.this, PersonDetailActivity.class);
                intent.putExtra(IntentConfig.talkToDetail, workerBean.getWorkerId());
                startActivity(intent);
                break;
            case R.id.iv_talk_worker_phone:
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + workerBean.getMobile()));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
                break;
            case R.id.rl_talk_worker_wait:
                talkWorkerPresenter.check(NetConfig.taskBaseUrl + "?t_author=" + UserUtils.readUserData(TalkWorkerActivity.this).getId() + "&t_storage=0&t_status=0&skills=" + skill);
                break;
        }
    }

    @Override
    public void loadSuccess(String json) {
        workerBean = DataUtils.getWorkerBeanList(json).get(0);
        talkWorkerPresenter.getSkillJson(NetConfig.skillUrl);
    }

    @Override
    public void loadFailure(String failure) {
        handler.sendEmptyMessage(LOAD_FAILURE);
    }

    @Override
    public void getSkillSuccess(String json) {
        String skill = workerBean.getSkill();
        String[] skillArr = skill.split(",");
        List<String> skillList = new ArrayList<>();
        for (int i = 0; i < skillArr.length; i++) {
            skillList.add(skillArr[i]);
        }
        List<String> skillNameList = DataUtils.getSkillNameList(json, skillList);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < skillNameList.size(); i++) {
            sb.append(skillNameList.get(i));
            if (i != skillNameList.size() - 1) {
                sb.append("、");
            }
        }
        workerBean.setSkillName(sb.toString());
        handler.sendEmptyMessage(LOAD_SUCCESS);
    }

    @Override
    public void getSkillFailure(String failure) {

    }

    @Override
    public void checkSuccess(String json) {
        Utils.log(TalkWorkerActivity.this, json);
        if (DataUtils.getTaskBeanList(json).size() == 0) {
            handler.sendEmptyMessage(CHECK_NONE);
        } else {
            handler.sendEmptyMessage(CHECK_HAVE);
        }
    }

    @Override
    public void checkFailure(String failure) {

    }
}
