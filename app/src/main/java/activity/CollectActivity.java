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
import fragment.CollectJobFragment;
import fragment.CollectWorkerFragment;

public class CollectActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout jobRl, workerRl;
    private TextView jobTv, workerTv;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private List<Fragment> fragmentList = new ArrayList<>();
    private int curPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_collect, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_collect_return);
        jobRl = (RelativeLayout) rootView.findViewById(R.id.rl_collect_job);
        workerRl = (RelativeLayout) rootView.findViewById(R.id.rl_collect_worker);
        jobTv = (TextView) rootView.findViewById(R.id.tv_collect_job);
        workerTv = (TextView) rootView.findViewById(R.id.tv_collect_worker);
    }

    private void initData() {
        CollectJobFragment collectJobFragment = new CollectJobFragment();
        CollectWorkerFragment collectWorkerFragment = new CollectWorkerFragment();
        fragmentList.add(collectJobFragment);
        fragmentList.add(collectWorkerFragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_collect_sit, fragmentList.get(curPosition));
        transaction.commit();
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        jobRl.setOnClickListener(this);
        workerRl.setOnClickListener(this);
    }

    private void changeFragment(int tarPosition) {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    jobTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    workerTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    break;
                case 1:
                    jobTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    workerTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    break;
            }
            Fragment curFragment = fragmentList.get(curPosition);
            Fragment tarFragment = fragmentList.get(tarPosition);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(curFragment);
            if (tarFragment.isAdded()) {
                transaction.show(tarFragment);
            } else {
                transaction.add(R.id.ll_collect_sit, tarFragment);
            }
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_collect_return:
                finish();
                break;
            case R.id.rl_collect_job:
                changeFragment(0);
                break;
            case R.id.rl_collect_worker:
                changeFragment(1);
                break;
        }
    }
}
