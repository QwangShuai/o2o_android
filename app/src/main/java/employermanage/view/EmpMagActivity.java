package employermanage.view;

import android.content.Intent;
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
import draft.view.DraftActivity;
import fragment.EmpMagAllFrag;
import fragment.EmpMagContactingFrag;
import fragment.EmpMagFinishedFrag;
import fragment.EmpMagUnderWayFrag;
import fragment.EmpMagWaitContactFrag;

public class EmpMagActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout draftRl;
    private RelativeLayout allRl;
    private TextView allTv;
    private RelativeLayout waitContactRl;
    private TextView waitContactTv;
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
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_emp_mag, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_return);
        draftRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_draft);
        allRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_all);
        allTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_all);
        waitContactRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_wait_contact);
        waitContactTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_wait_contact);
        contactingRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_contacting);
        contactingTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_contacting);
        underWayRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_under_way);
        underWayTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_under_way);
        finishedRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_finished);
        finishedTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_finished);
    }

    private void initData() {
        curPosition = 0;
        tarPosition = 0;
        fragmentList = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        EmpMagAllFrag allFrag = new EmpMagAllFrag();
        EmpMagWaitContactFrag waitContactFrag = new EmpMagWaitContactFrag();
        EmpMagContactingFrag contactingFrag = new EmpMagContactingFrag();
        EmpMagUnderWayFrag underWayFrag = new EmpMagUnderWayFrag();
        EmpMagFinishedFrag finishedFrag = new EmpMagFinishedFrag();
        fragmentList.add(allFrag);
        fragmentList.add(waitContactFrag);
        fragmentList.add(contactingFrag);
        fragmentList.add(underWayFrag);
        fragmentList.add(finishedFrag);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_emp_mag_sit, fragmentList.get(curPosition));
        transaction.commit();
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        draftRl.setOnClickListener(this);
        allRl.setOnClickListener(this);
        waitContactRl.setOnClickListener(this);
        contactingRl.setOnClickListener(this);
        underWayRl.setOnClickListener(this);
        finishedRl.setOnClickListener(this);
    }

    private void changeFragment() {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    allTv.setTextColor(ColorConfig.red_ff3e50);
                    waitContactTv.setTextColor(ColorConfig.gray_a0a0a0);
                    contactingTv.setTextColor(ColorConfig.gray_a0a0a0);
                    underWayTv.setTextColor(ColorConfig.gray_a0a0a0);
                    finishedTv.setTextColor(ColorConfig.gray_a0a0a0);
                    break;
                case 1:
                    allTv.setTextColor(ColorConfig.gray_a0a0a0);
                    waitContactTv.setTextColor(ColorConfig.red_ff3e50);
                    contactingTv.setTextColor(ColorConfig.gray_a0a0a0);
                    underWayTv.setTextColor(ColorConfig.gray_a0a0a0);
                    finishedTv.setTextColor(ColorConfig.gray_a0a0a0);
                    break;
                case 2:
                    allTv.setTextColor(ColorConfig.gray_a0a0a0);
                    waitContactTv.setTextColor(ColorConfig.gray_a0a0a0);
                    contactingTv.setTextColor(ColorConfig.red_ff3e50);
                    underWayTv.setTextColor(ColorConfig.gray_a0a0a0);
                    finishedTv.setTextColor(ColorConfig.gray_a0a0a0);
                    break;
                case 3:
                    allTv.setTextColor(ColorConfig.gray_a0a0a0);
                    waitContactTv.setTextColor(ColorConfig.gray_a0a0a0);
                    contactingTv.setTextColor(ColorConfig.gray_a0a0a0);
                    underWayTv.setTextColor(ColorConfig.red_ff3e50);
                    finishedTv.setTextColor(ColorConfig.gray_a0a0a0);
                    break;
                case 4:
                    allTv.setTextColor(ColorConfig.gray_a0a0a0);
                    waitContactTv.setTextColor(ColorConfig.gray_a0a0a0);
                    contactingTv.setTextColor(ColorConfig.gray_a0a0a0);
                    underWayTv.setTextColor(ColorConfig.gray_a0a0a0);
                    finishedTv.setTextColor(ColorConfig.red_ff3e50);
                    break;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment curFrag = fragmentList.get(curPosition);
            Fragment tarFrag = fragmentList.get(tarPosition);
            transaction.hide(curFrag);
            if (tarFrag.isAdded()) {
                transaction.show(tarFrag);
            } else {
                transaction.add(R.id.ll_emp_mag_sit, tarFrag);
            }
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_employer_manage_return:
                finish();
                break;
            case R.id.rl_employer_manage_draft:
                startActivity(new Intent(this, DraftActivity.class));
                break;
            case R.id.rl_employer_manage_all:
                tarPosition = 0;
                changeFragment();
                break;
            case R.id.rl_employer_manage_wait_contact:
                tarPosition = 1;
                changeFragment();
                break;
            case R.id.rl_employer_manage_contacting:
                tarPosition = 2;
                changeFragment();
                break;
            case R.id.rl_employer_manage_under_way:
                tarPosition = 3;
                changeFragment();
                break;
            case R.id.rl_employer_manage_finished:
                tarPosition = 4;
                changeFragment();
                break;
        }
    }
}
