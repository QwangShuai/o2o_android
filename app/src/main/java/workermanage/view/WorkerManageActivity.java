package workermanage.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import fragment.WorkerMagAllFrag;
import fragment.WorkerMagOverFrag;
import fragment.WorkerMagTalkingFrag;
import fragment.WorkerMagWorkingFrag;

public class WorkerManageActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout allRl;
    private TextView allTv;
    private RelativeLayout contactingRl;
    private TextView contactingTv;
    private RelativeLayout underWayRl;
    private TextView underWayTv;
    private RelativeLayout finishedRl;
    private TextView finishedTv;
    private int curPosition;
    private int tarPosition;
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_worker_mag, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_manage_return);
        allRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_mag_all);
        allTv = (TextView) rootView.findViewById(R.id.tv_worker_mag_all);
        contactingRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_mag_contacting);
        contactingTv = (TextView) rootView.findViewById(R.id.tv_worker_mag_contacting);
        underWayRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_mag_under_way);
        underWayTv = (TextView) rootView.findViewById(R.id.tv_worker_mag_under_way);
        finishedRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_mag_finished);
        finishedTv = (TextView) rootView.findViewById(R.id.tv_worker_mag_finished);
    }

    private void initData() {
        curPosition = 0;
        tarPosition = 0;
        fragmentList = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        WorkerMagAllFrag allFrag = new WorkerMagAllFrag();
        WorkerMagTalkingFrag contactingFrag = new WorkerMagTalkingFrag();
        WorkerMagWorkingFrag underWayFrag = new WorkerMagWorkingFrag();
        WorkerMagOverFrag finishedFrag = new WorkerMagOverFrag();
        fragmentList.add(allFrag);
        fragmentList.add(contactingFrag);
        fragmentList.add(underWayFrag);
        fragmentList.add(finishedFrag);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_worker_mag_sit, fragmentList.get(curPosition));
        transaction.commit();
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        allRl.setOnClickListener(this);
        contactingRl.setOnClickListener(this);
        underWayRl.setOnClickListener(this);
        finishedRl.setOnClickListener(this);
    }

    private void changeFrag() {
        if (curPosition != tarPosition) {
            switch (tarPosition) {
                case 0:
                    allTv.setTextColor(ColorConfig.red_ff3e50);
                    contactingTv.setTextColor(ColorConfig.gray_a0a0a0);
                    underWayTv.setTextColor(ColorConfig.gray_a0a0a0);
                    finishedTv.setTextColor(ColorConfig.gray_a0a0a0);
                    break;
                case 1:
                    allTv.setTextColor(ColorConfig.gray_a0a0a0);
                    contactingTv.setTextColor(ColorConfig.red_ff3e50);
                    underWayTv.setTextColor(ColorConfig.gray_a0a0a0);
                    finishedTv.setTextColor(ColorConfig.gray_a0a0a0);
                    break;
                case 2:
                    allTv.setTextColor(ColorConfig.gray_a0a0a0);
                    contactingTv.setTextColor(ColorConfig.gray_a0a0a0);
                    underWayTv.setTextColor(ColorConfig.red_ff3e50);
                    finishedTv.setTextColor(ColorConfig.gray_a0a0a0);
                    break;
                case 3:
                    allTv.setTextColor(ColorConfig.gray_a0a0a0);
                    contactingTv.setTextColor(ColorConfig.gray_a0a0a0);
                    underWayTv.setTextColor(ColorConfig.gray_a0a0a0);
                    finishedTv.setTextColor(ColorConfig.red_ff3e50);
                    break;
                default:
                    break;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment curFrag = fragmentList.get(curPosition);
            Fragment tarFrag = fragmentList.get(tarPosition);
            transaction.hide(curFrag);
            if (tarFrag.isAdded()) {
                transaction.show(tarFrag);
            } else {
                transaction.add(R.id.ll_worker_mag_sit, tarFrag);
            }
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_worker_manage_return:
                finish();
                break;
            case R.id.rl_worker_mag_all:
                tarPosition = 0;
                changeFrag();
                break;
            case R.id.rl_worker_mag_contacting:
                tarPosition = 1;
                changeFrag();
                break;
            case R.id.rl_worker_mag_under_way:
                tarPosition = 2;
                changeFrag();
                break;
            case R.id.rl_worker_mag_finished:
                tarPosition = 3;
                changeFrag();
                break;
        }
    }
}
