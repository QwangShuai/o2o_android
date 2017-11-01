package worker.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bean.ScreenBean;
import bean.SkillBean;
import config.IntentConfig;
import config.NetConfig;
import config.VarConfig;
import listener.IdPosClickHelp;
import login.view.LoginActivity;
import talkworker.view.TalkWorkerActivity;
import utils.DataUtils;
import utils.UserUtils;
import bean.WorkerBean;
import config.CodeConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.Utils;
import adapter.WorkerAdapter;
import view.CProgressDialog;
import worker.presenter.IWorkerPresenter;
import worker.presenter.WorkerPresenter;
import workerscreen.view.WorkerScreenActivity;

public class WorkerActivity extends AppCompatActivity implements IWorkerActivity, View.OnClickListener, PullToRefreshLayout.OnRefreshListener, IdPosClickHelp {

    private View rootView, emptyView, netView;
    private RelativeLayout returnRl, screenRl;
    private CProgressDialog cpd;
    private FrameLayout fl;
    private TextView netTv;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<WorkerBean> workerBeanList = new ArrayList<>();
    private WorkerAdapter workerAdapter;
    private IWorkerPresenter workerPresenter;
    private int clickPosition;
    private String tip;
    private final int SUCCESS = 1;
    private final int FAILURE = 2;
    private final int COLLECT_SUCCESS = 3;
    private final int COLLECT_FAILURE = 4;
    private final int FIRST = 5;
    private final int REFRESH = 6;
    private final int SCREEN = 7;
    private int STATE = FIRST;
    private SkillBean skillBean;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case SUCCESS:
                        notifyData();
                        break;
                    case FAILURE:
                        notifyNet();
                        break;
                    case COLLECT_SUCCESS:
                        Utils.toast(WorkerActivity.this, tip);
                        workerBeanList.get(clickPosition).setFavorite(1);
                        workerAdapter.notifyDataSetChanged();
                        break;
                    case COLLECT_FAILURE:
                        Utils.toast(WorkerActivity.this, tip);
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
        if (workerPresenter != null) {
            workerPresenter.destroy();
            workerPresenter = null;
        }
        if (handler != null) {
            handler.removeMessages(SUCCESS);
            handler.removeMessages(COLLECT_SUCCESS);
            handler.removeMessages(COLLECT_FAILURE);
            handler = null;
        }
    }

    private void initView() {
        initRootView();
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_screen);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
        cpd = Utils.initProgressDialog(WorkerActivity.this, cpd);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyView = LayoutInflater.from(WorkerActivity.this).inflate(R.layout.empty_data, null);
        fl.addView(emptyView);
        emptyView.setVisibility(View.GONE);
        netView = LayoutInflater.from(WorkerActivity.this).inflate(R.layout.empty_net, null);
        netTv = (TextView) netView.findViewById(R.id.tv_no_net_refresh);
        netTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptrl.setVisibility(View.VISIBLE);
                netView.setVisibility(View.GONE);
                STATE = FIRST;
                loadData();
            }
        });
        fl.addView(netView);
        netView.setVisibility(View.GONE);
    }

    private void initData() {
        workerPresenter = new WorkerPresenter(this);
        workerAdapter = new WorkerAdapter(WorkerActivity.this, workerBeanList, this);
        skillBean = (SkillBean) getIntent().getSerializableExtra(IntentConfig.skillToWorker);
    }

    private void setData() {
        plv.setAdapter(workerAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
    }

    private void loadData() {
        switch (STATE) {
            case FIRST:
                cpd.show();
                break;
        }
        if (UserUtils.isUserLogin(WorkerActivity.this)) {
            workerPresenter.load(NetConfig.workerUrl + "?u_skills=" + skillBean.getId() + "&fu_id=" + UserUtils.readUserData(WorkerActivity.this).getId());
        } else {
            workerPresenter.load(NetConfig.workerUrl + "?u_skills=" + skillBean.getId());
        }
    }

    private void notifyData() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                if (workerBeanList.size() == 0) {
                    ptrl.setVisibility(View.GONE);
                    netView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    ptrl.setVisibility(View.VISIBLE);
                    netView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                }
                break;
            case REFRESH:
                ptrl.hideHeadView();
                break;
            case SCREEN:
                cpd.dismiss();
                if (workerBeanList.size() == 0) {
                    ptrl.setVisibility(View.GONE);
                    netView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    ptrl.setVisibility(View.VISIBLE);
                    netView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                }
                break;
        }
        workerAdapter.notifyDataSetChanged();
    }

    private void notifyNet() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                ptrl.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                netView.setVisibility(View.VISIBLE);
                break;
            case REFRESH:
                ptrl.hideHeadView();
                Utils.toast(WorkerActivity.this, VarConfig.noNetTip);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_worker_return:
                finish();
                break;
            case R.id.rl_worker_screen:
                startActivityForResult(new Intent(this, WorkerScreenActivity.class), CodeConfig.screenRequestCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeConfig.screenRequestCode && resultCode == CodeConfig.screenResultCode && data != null) {
            ScreenBean screenBean = (ScreenBean) data.getSerializableExtra(IntentConfig.screenToWorker);
            if (screenBean != null) {
                cpd.show();
                STATE = SCREEN;
                if (UserUtils.isUserLogin(WorkerActivity.this)) {
                    workerPresenter.load(NetConfig.workerUrl + "?u_skills=" + skillBean.getId() + "&u_task_status=" + screenBean.getState() + "&u_true_name=" + screenBean.getName() + "&fu_id=" + UserUtils.readUserData(WorkerActivity.this).getId());
                } else {
                    workerPresenter.load(NetConfig.workerUrl + "?u_skills=" + skillBean.getId() + "&u_task_status=" + screenBean.getState() + "&u_true_name=" + screenBean.getName());
                }
            }
        }
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
    public void loadSuccess(String workerJson) {
        switch (STATE) {
            case REFRESH:
                workerBeanList.clear();
                break;
            case SCREEN:
                workerBeanList.clear();
                break;
        }
        workerBeanList.addAll(DataUtils.getWorkerBeanList(workerJson));
        handler.sendEmptyMessage(SUCCESS);
    }

    @Override
    public void loadFailure(String failure) {
        handler.sendEmptyMessage(FAILURE);
    }

    @Override
    public void collectSuccess(String collectJson) {
        try {
            JSONObject beanObj = new JSONObject(collectJson);
            int code = beanObj.optInt("code");
            switch (code) {
                case 1:
                    tip = VarConfig.collectSuccess;
                    handler.sendEmptyMessage(COLLECT_SUCCESS);
                    break;
                default:
                    tip = VarConfig.collectFailure;
                    handler.sendEmptyMessage(COLLECT_FAILURE);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void collectFailure(String failure) {

    }

    @Override
    public void onClick(int id, int pos) {
        clickPosition = pos;
        WorkerBean workerBean = workerBeanList.get(clickPosition);
        if (UserUtils.isUserLogin(WorkerActivity.this)) {
            switch (id) {
                case R.id.ll_item_worker:
                    if (workerBean.getWorkerId().equals(UserUtils.readUserData(WorkerActivity.this).getId())) {
                        Utils.toast(WorkerActivity.this, "不能招自己");
                    } else {
                        Intent intent = new Intent(WorkerActivity.this, TalkWorkerActivity.class);
                        intent.putExtra(IntentConfig.workerToTalk, workerBeanList.get(pos));
                        intent.putExtra(IntentConfig.workerToTalkSkill, skillBean.getId());
                        startActivity(intent);
                    }
                    break;
                case R.id.iv_item_worker_collect:
                    if (UserUtils.readUserData(WorkerActivity.this).getId().equals(workerBean.getWorkerId())) {
                        Utils.toast(WorkerActivity.this, VarConfig.cannotCollectSelf);
                    } else {
                        int favorite = workerBean.getFavorite();
                        switch (favorite) {
                            case 0:
                                workerPresenter.addCollect(NetConfig.addCollectUrl + "?u_id=" + UserUtils.readUserData(WorkerActivity.this).getId() + "&f_type_id=" + workerBean.getWorkerId() + "&f_type=1");
                                break;
                            case 1:
                                Utils.toast(WorkerActivity.this, VarConfig.collectDone);
                                break;
                        }
                    }
                    break;
            }
        } else {
            startActivity(new Intent(WorkerActivity.this, LoginActivity.class));
        }
    }
}
