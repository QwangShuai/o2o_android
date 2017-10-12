package leftright.view;

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
import config.IntentConfig;
import fragment.JobOfferFragment;
import fragment.SysMsgFragment;

public class LeftRightActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, lRl, rRl;
    private TextView titleTv, lTv, rTv;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private final int LEFT = 0, RIGHT = 1;
    private int curState = LEFT, tarState = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_left_right, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_left_right_return);
        titleTv = (TextView) rootView.findViewById(R.id.tv_left_right_title);
        lRl = (RelativeLayout) rootView.findViewById(R.id.rl_left_right_l);
        lTv = (TextView) rootView.findViewById(R.id.tv_left_right_l);
        rRl = (RelativeLayout) rootView.findViewById(R.id.rl_left_right_r);
        rTv = (TextView) rootView.findViewById(R.id.tv_left_right_r);
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        Intent intent = getIntent();
        int status = intent.getIntExtra(IntentConfig.intentName, 0);
        if (status != 0) {
            switch (status) {
                case IntentConfig.MESSAGE:
                    titleTv.setText("我的消息");
                    lTv.setText("工作邀约");
                    rTv.setText("系统消息");
                    Fragment jobOfferFragment = new JobOfferFragment();
                    Fragment sysMsgFragment = new SysMsgFragment();
                    fragmentList.add(jobOfferFragment);
                    fragmentList.add(sysMsgFragment);
                    break;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.ll_left_right_sit, fragmentList.get(curState));
            transaction.commit();
        }
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        lRl.setOnClickListener(this);
        rRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_left_right_return:
                finish();
                break;
            case R.id.rl_left_right_l:
                tarState = LEFT;
                changeFragment();
                break;
            case R.id.rl_left_right_r:
                tarState = RIGHT;
                changeFragment();
                break;
        }
    }

    private void changeFragment() {
        if (tarState != curState) {
            switch (tarState) {
                case LEFT:
                    lTv.setTextColor(ColorConfig.red_ff3e50);
                    rTv.setTextColor(ColorConfig.gray_a0a0a0);
                    break;
                case RIGHT:
                    lTv.setTextColor(ColorConfig.gray_a0a0a0);
                    rTv.setTextColor(ColorConfig.red_ff3e50);
                    break;
            }
            Fragment curFragment = fragmentList.get(curState);
            Fragment tarFragment = fragmentList.get(tarState);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(curFragment);
            if (tarFragment.isAdded()) {
                transaction.show(tarFragment);
            } else {
                transaction.add(R.id.ll_left_right_sit, tarFragment);
            }
            transaction.commit();
            curState = tarState;
        }
    }
}
