package workerInfo.view;

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

import talk.view.TalkActivity;
import workerscreen.view.WorkerScnActivity;
import bean.PositionBean;
import bean.ScreenBean;
import config.CodeConfig;
import config.IntentConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.Utils;
import workerInfo.adapter.WorkerInfoAdapter;
import workerInfo.bean.WorkerInfoBean;
import workerInfo.presenter.IWorkerInfoPresenter;
import workerInfo.presenter.WorkerInfoPresenter;
import workerkind.bean.WorkerKindBean;

public class WorkerInfoActivity extends AppCompatActivity implements IWorkerInfoActivity, View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout screenRl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<WorkerInfoBean> list;
    private WorkerInfoAdapter adapter;
    private String workerKindId;

    private IWorkerInfoPresenter iWorkerInfoPresenter = new WorkerInfoPresenter(this);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) {
                Utils.log(WorkerInfoActivity.this, "msg == null");
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
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_worker, null);
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
        iWorkerInfoPresenter.destroy();
        iWorkerInfoPresenter = null;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_screen);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new WorkerInfoAdapter(WorkerInfoActivity.this, list);
        Intent intent = getIntent();
        if (intent == null) {
            Utils.log(WorkerInfoActivity.this, "intent == null");
        } else {
            WorkerKindBean wkb = (WorkerKindBean) intent.getSerializableExtra(IntentConfig.WORKER_KIND);
            if (wkb == null) {
                Utils.log(WorkerInfoActivity.this, "wkb == null");
            } else {
                workerKindId = wkb.getId();
            }
        }
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
        if (TextUtils.isEmpty(workerKindId)) {
            Utils.log(WorkerInfoActivity.this, "workerKindId=" + workerKindId);
        } else {
            PositionBean positionBean = new PositionBean();
            positionBean.setPositionX("126.65771686");
            positionBean.setPositionY("45");
            iWorkerInfoPresenter.load(workerKindId, positionBean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_worker_return:
                finish();
                break;
            case R.id.rl_worker_screen:
                startActivityForResult(new Intent(this, WorkerScnActivity.class), CodeConfig.screenRequestCode);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, TalkActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeConfig.screenRequestCode && resultCode == CodeConfig.screenResultCode && data != null) {
            ScreenBean screenBean = (ScreenBean) data.getSerializableExtra("screenBean");
            if (screenBean != null) {

            }
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
    public void showSuccess(List<WorkerInfoBean> workerInfoBeanList) {
        Utils.log(WorkerInfoActivity.this, "workerInfoBeanList=" + workerInfoBeanList.toString());
        list.addAll(workerInfoBeanList);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void showFailure(String failure) {
        Utils.log(WorkerInfoActivity.this, "failure=" + failure);
    }
}
