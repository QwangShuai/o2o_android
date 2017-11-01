package task.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import config.IntentConfig;
import listener.IdPosClickHelp;
import adapter.TaskAdapter;
import bean.TaskBean;
import config.NetConfig;
import config.StateConfig;
import config.VarConfig;
import login.view.LoginActivity;
import talkemployer.view.TalkEmployerActivity;
import taskscreen.bean.TaskScreenBean;
import taskscreen.view.TaskScreenActivity;
import task.presenter.ITaskPresenter;
import task.presenter.TaskPresenter;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;

public class TaskActivity extends AppCompatActivity implements ITaskActivity, View.OnClickListener, PullToRefreshLayout.OnRefreshListener, IdPosClickHelp {

    private View rootView, emptyView, netView;
    private FrameLayout fl;
    private TextView netTv;
    private CProgressDialog cpd;
    private RelativeLayout returnRl, screenRl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<TaskBean> taskBeanList;
    private TaskAdapter adapter;
    private final int FIRST = 0, REFRESH = 1;
    private int STATE;
    private ITaskPresenter taskPresenter;

    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;
    private final int COLLECT_SUCCESS = 3;
    private final int COLLECT_FAILURE = 4;
    private int clickPostion;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        notifyData();
                        break;
                    case COLLECT_SUCCESS:
                        taskBeanList.get(clickPostion).setFavorite(1);
                        notifyData();
                        Utils.toast(TaskActivity.this, VarConfig.collectSuccess);
                        break;
                    case COLLECT_FAILURE:
                        Utils.toast(TaskActivity.this, VarConfig.collectFailure);
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_task, null);
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
        if (handler != null) {
            handler.removeMessages(1);
            handler = null;
        }
        if (taskPresenter != null) {
            taskPresenter.destroy();
            taskPresenter = null;
        }
    }

    private void initView() {
        initRootView();
        initEmptyView();
    }

    private void initRootView() {
        cpd = Utils.initProgressDialog(TaskActivity.this, cpd);
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_task_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_task_screen);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyView = LayoutInflater.from(TaskActivity.this).inflate(R.layout.empty_data, null);
        fl.addView(emptyView);
        emptyView.setVisibility(View.GONE);
        netView = LayoutInflater.from(TaskActivity.this).inflate(R.layout.empty_net, null);
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
        taskBeanList = new ArrayList<>();
        adapter = new TaskAdapter(this, taskBeanList, this);
        taskPresenter = new TaskPresenter(TaskActivity.this);
        STATE = FIRST;
    }

    private void setData() {
        plv.setAdapter(adapter);
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
        if (UserUtils.isUserLogin(TaskActivity.this)) {
            taskPresenter.load(NetConfig.taskBaseUrl + "?u_id=" + UserUtils.readUserData(TaskActivity.this).getId());
        } else {
            taskPresenter.load(NetConfig.taskBaseUrl);
        }
    }

    private void notifyData() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                if (taskBeanList.size() == 0) {
                    ptrl.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    netView.setVisibility(View.GONE);
                } else {
                    ptrl.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    netView.setVisibility(View.GONE);
                }
                break;
            case REFRESH:
                ptrl.hideHeadView();
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_task_return:
                finish();
                break;
            case R.id.rl_task_screen:
                startActivityForResult(new Intent(this, TaskScreenActivity.class), 1);
                break;
        }
    }

    @Override
    public void onClick(int id, int pos) {
        if (UserUtils.isUserLogin(TaskActivity.this)) {
            clickPostion = pos;
            switch (id) {
                case R.id.ll_item_task:
                    Intent intent = new Intent(TaskActivity.this, TalkEmployerActivity.class);
                    intent.putExtra(IntentConfig.taskToTalk, taskBeanList.get(clickPostion).getTaskId());
                    startActivity(intent);
                    break;
                case R.id.iv_item_task_collect:
                    if (UserUtils.readUserData(TaskActivity.this).getId().equals(taskBeanList.get(clickPostion).getAuthorId())) {
                        Utils.toast(TaskActivity.this, VarConfig.cannotCollectSelf);
                    } else {
                        switch (taskBeanList.get(clickPostion).getFavorite()) {
                            case 0:
                                taskPresenter.collect(NetConfig.favorateAddUrl + "?u_id=" + UserUtils.readUserData(TaskActivity.this).getId() + "&f_type_id=" + taskBeanList.get(clickPostion).getTaskId() + "&f_type=0");
                                break;
                            case 1:
                                Utils.toast(TaskActivity.this, VarConfig.collectDone);
                                break;
                        }
                    }
                    break;
            }
        } else {
            startActivity(new Intent(TaskActivity.this, LoginActivity.class));
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
    public void loadSuccess(String taskJson) {
        taskBeanList.clear();
        taskBeanList.addAll(DataUtils.getTaskBeanList(taskJson));
        handler.sendEmptyMessage(LOAD_SUCCESS);
    }

    @Override
    public void loadFailure(String loadFailure) {
        if (loadFailure.equals(VarConfig.noNet)) {
            switch (STATE) {
                case FIRST:
                    cpd.dismiss();
                    ptrl.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    netView.setVisibility(View.VISIBLE);
                    break;
                case REFRESH:
                    ptrl.hideHeadView();
                    Utils.toast(TaskActivity.this, StateConfig.loadNonet);
                    break;
            }
        }
    }

    @Override
    public void collectSuccess(String collectJson) {
        try {
            JSONObject beanObj = new JSONObject(collectJson);
            int code = beanObj.optInt("code");
            switch (code) {
                case 1:
                    handler.sendEmptyMessage(COLLECT_SUCCESS);
                    break;
                default:
                    handler.sendEmptyMessage(COLLECT_FAILURE);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void collectFailure(String collectFailure) {
        handler.sendEmptyMessage(COLLECT_FAILURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1 && data != null) {
            TaskScreenBean taskScreenBean = (TaskScreenBean) data.getSerializableExtra("taskScreenBean");
            if (taskScreenBean != null) {
                taskScreen(taskScreenBean);
            }
        }
    }

    private void taskScreen(TaskScreenBean taskScreenBean) {
        if (taskScreenBean != null) {
            STATE = FIRST;
            cpd.show();
            Utils.log(TaskActivity.this, Utils.getTaskUrl(taskScreenBean));
            taskPresenter.load(Utils.getTaskUrl(taskScreenBean));
        }
    }
}
