package activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import config.IntentConfig;
import fragment.CollectJobFragment;
import fragment.CollectWorkerFragment;
import fragment.EvaluateGetFragment;
import fragment.EvaluateGiveFragment;
import fragment.JobOfferFragment;
import fragment.SysMsgFragment;

public class MsgActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private TextView titleTv;
    private RelativeLayout offerRl, msgRl;
    private TextView offerTv, msgTv;

    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;

    private int curPosition;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentManager = null;
        fragmentList = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_msg, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_msg_return);
        titleTv = (TextView) rootView.findViewById(R.id.tv_msg_title);
        offerRl = (RelativeLayout) rootView.findViewById(R.id.rl_msg_l);
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_msg_r);
        offerTv = (TextView) rootView.findViewById(R.id.tv_msg_l);
        msgTv = (TextView) rootView.findViewById(R.id.tv_msg_r);
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        Intent intent = getIntent();
        int status = intent.getIntExtra(IntentConfig.intentName, 0);
        if (status != 0) {
            switch (status) {
                case IntentConfig.COLLECT:
                    titleTv.setText("我的收藏");
                    offerTv.setText("收藏的工作");
                    msgTv.setText("收藏的工人");
                    CollectJobFragment collectJobFragment = new CollectJobFragment();
                    CollectWorkerFragment collectWorkerFragment = new CollectWorkerFragment();
                    fragmentList.add(collectJobFragment);
                    fragmentList.add(collectWorkerFragment);
                    break;
                case IntentConfig.EVALUATE:
                    titleTv.setText("我的评价");
                    offerTv.setText("收到的评价");
                    msgTv.setText("给别人的评价");
                    EvaluateGetFragment evaluateGetFragment = new EvaluateGetFragment();
                    EvaluateGiveFragment evaluateGiveFragment = new EvaluateGiveFragment();
                    fragmentList.add(evaluateGetFragment);
                    fragmentList.add(evaluateGiveFragment);
                    break;
                case IntentConfig.MESSAGE:
                    titleTv.setText("我的消息");
                    offerTv.setText("工作邀约");
                    msgTv.setText("系统消息");
                    JobOfferFragment jobOfferFragment = new JobOfferFragment();
                    SysMsgFragment sysMsgFragment = new SysMsgFragment();
                    fragmentList.add(jobOfferFragment);
                    fragmentList.add(sysMsgFragment);
                    break;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.ll_msg_sit, fragmentList.get(curPosition));
            transaction.commit();
        }
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        offerRl.setOnClickListener(this);
        msgRl.setOnClickListener(this);
    }

    private void changeFragment(int tarPosition) {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    offerTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    msgTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    break;
                case 1:
                    offerTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    msgTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    break;
            }
            Fragment curFragment = fragmentList.get(curPosition);
            Fragment tarFragment = fragmentList.get(tarPosition);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(curFragment);
            if (tarFragment.isAdded()) {
                transaction.show(tarFragment);
            } else {
                transaction.add(R.id.ll_msg_sit, tarFragment);
            }
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_msg_return:
                finish();
                break;
            case R.id.rl_msg_l:
                changeFragment(0);
                break;
            case R.id.rl_msg_r:
                changeFragment(1);
                break;
        }
    }
}
