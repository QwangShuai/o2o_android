package activity;

import android.content.Intent;
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
import config.IntentConfig;
import fragment.CollectJobFragment;
import fragment.CollectWorkerFragment;
import fragment.ReceiveEvaluateFragment;
import fragment.EvaluateGiveFragment;
import fragment.JobOfferFrag;
import fragment.SysMsgFrag;

public class LeftRightActivity extends CommonActivity implements View.OnClickListener {

    //根视图
    private View rootView;
    //返回视图
    private RelativeLayout returnRl;
    //标题视图
    private TextView titleTv;
    //左视图
    private RelativeLayout lRl;
    private TextView lTv;
    //右视图
    private RelativeLayout rRl;
    private TextView rTv;
    //碎片管理者
    private FragmentManager fragmentManager;
    //碎片集合
    private List<Fragment> fragmentList;
    //碎片索引
    private int curPosition;

    @Override
    protected View getRootView() {
        //初始化根视图
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_left_right, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        //初始化返回视图
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_left_right_return);
        //初始化标题视图
        titleTv = (TextView) rootView.findViewById(R.id.tv_left_right_title);
        //初始化左视图
        lRl = (RelativeLayout) rootView.findViewById(R.id.rl_left_right_l);
        lTv = (TextView) rootView.findViewById(R.id.tv_left_right_l);
        //初始化右视图
        rRl = (RelativeLayout) rootView.findViewById(R.id.rl_left_right_r);
        rTv = (TextView) rootView.findViewById(R.id.tv_left_right_r);
    }

    @Override
    protected void initData() {
        //初始化碎片管理者
        fragmentManager = getSupportFragmentManager();
        //初始化碎片集合
        fragmentList = new ArrayList<>();
        Intent intent = getIntent();
        int status = intent.getIntExtra(IntentConfig.intentName, 0);
        if (status != 0) {
            switch (status) {
                case IntentConfig.COLLECT:
                    titleTv.setText("我的收藏");
                    lTv.setText("收藏的工作");
                    rTv.setText("收藏的工人");
                    CollectJobFragment collectJobFragment = new CollectJobFragment();
                    CollectWorkerFragment collectWorkerFragment = new CollectWorkerFragment();
                    fragmentList.add(collectJobFragment);
                    fragmentList.add(collectWorkerFragment);
                    break;
                case IntentConfig.EVALUATE:
                    titleTv.setText("我的评价");
                    lTv.setText("收到的评价");
                    rTv.setText("给别人的评价");
                    ReceiveEvaluateFragment receiveEvaluateFragment = new ReceiveEvaluateFragment();
                    EvaluateGiveFragment evaluateGiveFragment = new EvaluateGiveFragment();
                    fragmentList.add(receiveEvaluateFragment);
                    fragmentList.add(evaluateGiveFragment);
                    break;
                case IntentConfig.MESSAGE:
                    titleTv.setText("我的消息");
                    lTv.setText("工作邀约");
                    rTv.setText("系统消息");
                    JobOfferFrag jobOfferFrag = new JobOfferFrag();
                    SysMsgFrag sysMsgFrag = new SysMsgFrag();
                    fragmentList.add(jobOfferFrag);
                    fragmentList.add(sysMsgFrag);
                    break;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.ll_left_right_sit, fragmentList.get(curPosition));
            transaction.commit();
        }
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        lRl.setOnClickListener(this);
        rRl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void changeFragment(int tarPosition) {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    lTv.setTextColor(ColorConfig.red_ff3e50);
                    rTv.setTextColor(ColorConfig.gray_a0a0a0);
                    break;
                case 1:
                    lTv.setTextColor(ColorConfig.gray_a0a0a0);
                    rTv.setTextColor(ColorConfig.red_ff3e50);
                    break;
            }
            Fragment curFragment = fragmentList.get(curPosition);
            Fragment tarFragment = fragmentList.get(tarPosition);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(curFragment);
            if (tarFragment.isAdded()) {
                transaction.show(tarFragment);
            } else {
                transaction.add(R.id.ll_left_right_sit, tarFragment);
            }
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_left_right_return:
                finish();
                break;
            case R.id.rl_left_right_l:
                changeFragment(0);
                break;
            case R.id.rl_left_right_r:
                changeFragment(1);
                break;
        }
    }

}
