package workerkind.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.IntentConfig;
import config.StateConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.Utils;
import view.CProgressDialog;
import workerInfo.view.WorkerInfoActivity;
import workerkind.adapter.WorkerKindAdapter;
import workerkind.bean.WorkerKindBean;
import workerkind.presenter.IWorkerKindPresenter;
import workerkind.presenter.WorkerKindPresenter;

public class WorkerKindActivity extends AppCompatActivity implements IWorkerKindActivity, View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView, emptyView, netView;
    private FrameLayout fl;
    private TextView netTv;
    private RelativeLayout returnRl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private CProgressDialog cpd;
    private List<WorkerKindBean> list;
    private WorkerKindAdapter adapter;

    private final int FIRST = 0, REFRESH = 1, LOAD = 2;
    private int STATE;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        notifyData();
                        break;
                }
            }
        }
    };

    private IWorkerKindPresenter iWorkerKindPresenter = new WorkerKindPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_worker_kind, null);
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
        iWorkerKindPresenter.destroy();
        iWorkerKindPresenter = null;
    }

    private void initView() {
        initRootView();
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_kind_return);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
        cpd = Utils.initProgressDialog(this, cpd);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyView = LayoutInflater.from(WorkerKindActivity.this).inflate(R.layout.empty_data, null);
        fl.addView(emptyView);
        emptyView.setVisibility(View.GONE);
        netView = LayoutInflater.from(WorkerKindActivity.this).inflate(R.layout.empty_net, null);
        netTv = (TextView) netView.findViewById(R.id.tv_no_net_refresh);
        netTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                netView.setVisibility(View.GONE);
                STATE = FIRST;
                loadData();
            }
        });
        fl.addView(netView);
        netView.setVisibility(View.GONE);
    }

    private void initData() {
        STATE = FIRST;
        list = new ArrayList<>();
        adapter = new WorkerKindAdapter(this, list);
    }

    private void setData() {
        plv.setAdapter(adapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
        plv.setOnItemClickListener(this);
    }

    private void loadData() {
        switch (STATE) {
            case FIRST:
                cpd.show();
                break;
        }
        iWorkerKindPresenter.load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_worker_kind_return:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, WorkerInfoActivity.class);
        intent.putExtra(IntentConfig.WORKER_KIND, list.get(position));
        startActivity(intent);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        STATE = REFRESH;
        loadData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        ptrl.hideFootView();
    }

    @Override
    public void showSuccess(List<WorkerKindBean> workerKindBeanList) {
        Utils.log(WorkerKindActivity.this, "list=" + workerKindBeanList.toString());
        switch (STATE) {
            case FIRST:
                list.addAll(workerKindBeanList);
                break;
            case REFRESH:
                list.clear();
                list.addAll(workerKindBeanList);
                break;
        }
        handler.sendEmptyMessage(1);
    }

    @Override
    public void showFailure(String failure) {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                ptrl.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                netView.setVisibility(View.VISIBLE);
                break;
            case REFRESH:
                ptrl.hideHeadView();
                Utils.toast(WorkerKindActivity.this, StateConfig.loadNonet);
                break;
            case LOAD:
                ptrl.hideFootView();
                Utils.toast(WorkerKindActivity.this, StateConfig.loadNonet);
                break;
        }
        Utils.log(WorkerKindActivity.this, "showFailure:" + failure);
    }

    private void notifyData() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    ptrl.setVisibility(View.GONE);
                    netView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                break;
            case REFRESH:
                ptrl.hideHeadView();
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
