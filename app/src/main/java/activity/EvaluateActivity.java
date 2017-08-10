package activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import fragment.EvaluateGetFragment;
import fragment.EvaluateGiveFragment;

public class EvaluateActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout getRl, giveRl;
    private TextView getTv, giveTv;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private List<Fragment> fragmentList = new ArrayList<>();

    private int curPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_evaluate, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_evaluate_return);
        getRl = (RelativeLayout) rootView.findViewById(R.id.rl_evaluate_get);
        giveRl = (RelativeLayout) rootView.findViewById(R.id.rl_evaluate_give);
        getTv = (TextView) rootView.findViewById(R.id.tv_evaluate_get);
        giveTv = (TextView) rootView.findViewById(R.id.tv_evaluate_give);
    }

    private void initData() {
        EvaluateGetFragment evaluateGetFragment = new EvaluateGetFragment();
        EvaluateGiveFragment evaluateGiveFragment = new EvaluateGiveFragment();
        fragmentList.add(evaluateGetFragment);
        fragmentList.add(evaluateGiveFragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_evaluate_sit, fragmentList.get(curPosition));
        transaction.commit();
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        getRl.setOnClickListener(this);
        giveRl.setOnClickListener(this);
    }

    private void changeFragment(int tarPosition) {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    getTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    giveTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    break;
                case 1:
                    getTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    giveTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    break;
            }
            Fragment curFragment = fragmentList.get(curPosition);
            Fragment tarFragment = fragmentList.get(tarPosition);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(curFragment);
            if (tarFragment.isAdded()) {
                transaction.show(tarFragment);
            } else {
                transaction.add(R.id.ll_evaluate_sit, tarFragment);
            }
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_evaluate_return:
                finish();
                break;
            case R.id.rl_evaluate_get:
                changeFragment(0);
                break;
            case R.id.rl_evaluate_give:
                changeFragment(1);
                break;
        }
    }
}
