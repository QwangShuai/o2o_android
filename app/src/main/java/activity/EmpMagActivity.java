package activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import fragment.EmpMagAllFrag;
import fragment.EmpMagContactingFrag;
import fragment.EmpMagFinishedFrag;
import fragment.EmpMagUnderWayFrag;
import fragment.EmpMagWaitContactFrag;
import utils.Utils;

public class EmpMagActivity extends CommonActivity implements View.OnClickListener {

    //根视图
    private View rootView;
    //返回视图
    private RelativeLayout returnRl;
    //草稿箱视图
    private RelativeLayout draftRl;
    //全部视图
    private RelativeLayout allRl;
    private TextView allTv;
    //待联系视图
    private RelativeLayout waitContactRl;
    private TextView waitContactTv;
    //洽谈中视图
    private RelativeLayout contactingRl;
    private TextView contactingTv;
    //进行中视图
    private RelativeLayout underWayRl;
    private TextView underWayTv;
    //已结束视图
    private RelativeLayout finishedRl;
    private TextView finishedTv;
    //当前碎片索引
    private int curPosition;
    //目标碎片索引
    private int tarPosition;
    //碎片集合
    private List<Fragment> fragmentList;
    //碎片管理者
    private FragmentManager fragmentManager;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_emp_mag, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        //初始化返回视图
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_return);
        //初始化草稿箱视图
        draftRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_draft);
        //初始化全部视图
        allRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_all);
        allTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_all);
        //初始化待联系视图
        waitContactRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_wait_contact);
        waitContactTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_wait_contact);
        //初始化洽谈中视图
        contactingRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_contacting);
        contactingTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_contacting);
        //初始化进行中视图
        underWayRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_under_way);
        underWayTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_under_way);
        //初始化已结束视图
        finishedRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_finished);
        finishedTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_finished);
    }

    @Override
    protected void initData() {
        //初始化当前碎片索引
        curPosition = 0;
        //初始化目标碎片索引
        tarPosition = 0;
        //初始化碎片集合
        fragmentList = new ArrayList<>();
        //初始化碎片管理者
        fragmentManager = getSupportFragmentManager();
        //初始化加载碎片
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

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        //返回视图监听
        returnRl.setOnClickListener(this);
        //草稿箱视图监听
        draftRl.setOnClickListener(this);
        //全部视图监听
        allRl.setOnClickListener(this);
        //待联系视图监听
        waitContactRl.setOnClickListener(this);
        //洽谈中视图监听
        contactingRl.setOnClickListener(this);
        //进行中视图监听
        underWayRl.setOnClickListener(this);
        //已结束视图监听
        finishedRl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

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
                Utils.toast(this, "草稿箱");
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
