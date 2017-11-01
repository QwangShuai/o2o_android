package selecttask.view;

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

import java.util.ArrayList;
import java.util.List;

import adapter.SelectTaskAdapter;
import bean.TalkToSelect;
import bean.TaskBean;
import config.IntentConfig;
import config.NetConfig;
import config.VarConfig;
import listener.IdPosClickHelp;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import selecttask.presenter.ISelectTaskPresenter;
import selecttask.presenter.SelectTaskPresenter;
import skill.view.SkillActivity;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;

public class SelectTaskActivity extends AppCompatActivity implements ISelectTaskActivity, View.OnClickListener, PullToRefreshLayout.OnRefreshListener, IdPosClickHelp {

    private View rootView, emptyView, netView;
    private RelativeLayout returnRl;
    private FrameLayout fl;
    private TextView netTv;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private CProgressDialog cpd;
    private List<TaskBean> taskBeanList = new ArrayList<>();
    private SelectTaskAdapter selectTaskAdapter;

    private ISelectTaskPresenter selectTaskPresenter;

    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;
    private final int INVITE_SUCCESS = 3;
    private final int INVITE_FAILURE = 4;
    private final int FIRST = 5;
    private final int REFRESH = 6;
    private int STATE = FIRST;

    private TalkToSelect talkToSelect;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        notifyData();
                        break;
                    case LOAD_FAILURE:
                        notifyNet();
                        break;
                    case INVITE_SUCCESS:
                        break;
                    case INVITE_FAILURE:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_select_task, null);
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
            handler.removeMessages(LOAD_SUCCESS);
            handler.removeMessages(LOAD_FAILURE);
            handler.removeMessages(INVITE_SUCCESS);
            handler.removeMessages(INVITE_FAILURE);
            handler = null;
        }
    }

    private void initView() {
        initRootView();
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_project_return);
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
        cpd = Utils.initProgressDialog(SelectTaskActivity.this, cpd);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyView = LayoutInflater.from(SelectTaskActivity.this).inflate(R.layout.empty_data, null);
        fl.addView(emptyView);
        emptyView.setVisibility(View.GONE);
        netView = LayoutInflater.from(SelectTaskActivity.this).inflate(R.layout.empty_net, null);
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
        selectTaskPresenter = new SelectTaskPresenter(this);
        selectTaskAdapter = new SelectTaskAdapter(SelectTaskActivity.this, taskBeanList, this);
        talkToSelect = (TalkToSelect) getIntent().getSerializableExtra(IntentConfig.talkToSelect);
    }

    private void setData() {
        plv.setAdapter(selectTaskAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
    }

    private void loadData() {
        switch (STATE) {
            case FIRST:
                cpd.show();
                break;
        }
        selectTaskPresenter.load(NetConfig.taskBaseUrl + "?t_author=" + UserUtils.readUserData(SelectTaskActivity.this).getId() + "&t_storage=0&t_status=0&skills=" + talkToSelect.getSkill());
    }

    private void notifyData() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                if (taskBeanList.size() == 0) {
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
        }
        selectTaskAdapter.notifyDataSetChanged();
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
                Utils.toast(SelectTaskActivity.this, VarConfig.noNetTip);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_project_return:
                finish();
                break;
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
    public void loadSuccess(String json) {
        switch (STATE) {
            case REFRESH:
                taskBeanList.clear();
                break;
        }
        taskBeanList.addAll(DataUtils.getTaskBeanList(json));
        handler.sendEmptyMessage(LOAD_SUCCESS);
    }

    @Override
    public void loadFailure(String failure) {
        handler.sendEmptyMessage(LOAD_FAILURE);
    }

    @Override
    public void inviteSuccess(String json) {
        Utils.log(SelectTaskActivity.this, json);
    }

    @Override
    public void inviteFailure(String failure) {

    }

    @Override
    public void onClick(int id, int pos) {
        switch (id) {
            case R.id.tv_item_select_task_choose:
                cpd.show();
                String url = "http://api.gangjianwang.com/Orders/index" + "?action=create" + "&tew_id=" + taskBeanList.get(pos).getTewId() + "&t_id=" + taskBeanList.get(pos).getTaskId() + "&o_worker=" + talkToSelect.getWorkerId() + "&o_sponsor=" + UserUtils.readUserData(SelectTaskActivity.this).getId();
                Utils.log(SelectTaskActivity.this, url);
                selectTaskPresenter.invite(url);
                break;
        }
    }
}
