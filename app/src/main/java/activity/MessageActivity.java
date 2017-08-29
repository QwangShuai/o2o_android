package activity;

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
import fragment.SysMsgFragment;
import fragment.JobOfferFragment;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout offerRl, msgRl;
    private TextView offerTv, msgTv;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private List<Fragment> fragmentList = new ArrayList<>();

    private int curPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_message, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_message_return);
        offerRl = (RelativeLayout) rootView.findViewById(R.id.rl_message_offer);
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_message_msg);
        offerTv = (TextView) rootView.findViewById(R.id.tv_message_offer);
        msgTv = (TextView) rootView.findViewById(R.id.tv_message_msg);
    }

    private void initData() {
        JobOfferFragment jobOfferFragment = new JobOfferFragment();
        SysMsgFragment sysMsgFragment = new SysMsgFragment();
        fragmentList.add(jobOfferFragment);
        fragmentList.add(sysMsgFragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_message_sit, fragmentList.get(curPosition));
        transaction.commit();
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
                transaction.add(R.id.ll_message_sit, tarFragment);
            }
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_message_return:
                finish();
                break;
            case R.id.rl_message_offer:
                changeFragment(0);
                break;
            case R.id.rl_message_msg:
                changeFragment(1);
                break;
        }
    }
}
