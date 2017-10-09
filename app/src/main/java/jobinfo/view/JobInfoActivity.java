package jobinfo.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import jobscreen.view.JobScnActivity;
import talk.view.TalkActivity;
import jobinfo.adapter.JobInfoAdapter;
import config.NetConfig;
import jobinfo.bean.JobInfoBean;
import jobinfo.presenter.IJobInfoPresenter;
import jobinfo.presenter.JobInfoPresenter;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.Utils;

public class JobInfoActivity extends AppCompatActivity implements IJobInfoActivity, View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl, screenRl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<JobInfoBean> list;
    private JobInfoAdapter adapter;

    private IJobInfoPresenter iJobInfoPresenter = new JobInfoPresenter(this);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) {
                Utils.log(JobInfoActivity.this, "msg == null");
            } else {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_job, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
        iJobInfoPresenter.destroy();
        if (iJobInfoPresenter != null)
            iJobInfoPresenter = null;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_screen);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new JobInfoAdapter(this, list);
    }

    private void setData() {
        plv.setAdapter(adapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
        plv.setOnItemClickListener(this);
    }

    private void loadData() {
        iJobInfoPresenter.load(NetConfig.jobInfoUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_job_return:
                finish();
                break;
            case R.id.rl_job_screen:
                startActivity(new Intent(this, JobScnActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        ptrl.hideHeadView();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        ptrl.hideFootView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String t_id = list.get(position).getT_id();
        if (!TextUtils.isEmpty(t_id)) {
            Intent intent = new Intent(this, TalkActivity.class);
            intent.putExtra("t_id", t_id);
            startActivity(intent);
        }
    }

    @Override
    public void showSuccess(List<JobInfoBean> jobInfoBeanList) {
        Utils.log(JobInfoActivity.this, "jobInfoBeanList=" + jobInfoBeanList.toString());
        list.addAll(jobInfoBeanList);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void showFailure(String failure) {
        Utils.log(JobInfoActivity.this, "failure=" + failure);
    }
}
