package skill.view;

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

import bean.SkillBean;
import adapter.SkillAdapter;
import config.IntentConfig;
import config.NetConfig;
import config.VarConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import skill.presenter.ISkillPresenter;
import skill.presenter.SkillPresenter;
import utils.DataUtils;
import utils.Utils;
import view.CProgressDialog;
import worker.view.WorkerActivity;

public class SkillActivity extends AppCompatActivity implements ISkillActivity, View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView, emptyView, netView;
    private FrameLayout fl;
    private TextView netTv;
    private RelativeLayout returnRl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private CProgressDialog cpd;
    private List<SkillBean> skillBeanList = new ArrayList<>();
    private SkillAdapter skillAdapter;
    private ISkillPresenter skillsPresenter;
    private final int SUCCESS = 1;
    private final int FAILURE = 2;
    private final int FIRST = 3;
    private final int REFRESH = 4;
    private int STATE = FIRST;

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
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_skill, null);
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
        if (skillsPresenter != null) {
            skillsPresenter.destroy();
            skillsPresenter = null;
        }
        if (handler != null) {
            handler.removeMessages(SUCCESS);
            handler.removeMessages(FAILURE);
            handler = null;
        }
    }

    private void initView() {
        initRootView();
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_skill_return);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
        cpd = Utils.initProgressDialog(this, cpd);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyView = LayoutInflater.from(SkillActivity.this).inflate(R.layout.empty_data, null);
        fl.addView(emptyView);
        emptyView.setVisibility(View.GONE);
        netView = LayoutInflater.from(SkillActivity.this).inflate(R.layout.empty_net, null);
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
        skillAdapter = new SkillAdapter(this, skillBeanList);
        skillsPresenter = new SkillPresenter(SkillActivity.this);
    }

    private void setData() {
        plv.setAdapter(skillAdapter);
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
        skillsPresenter.load(NetConfig.skillUrl);
    }

    private void notifyData() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                if (skillBeanList.size() == 0) {
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
        skillAdapter.notifyDataSetChanged();
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
                Utils.toast(SkillActivity.this, VarConfig.noNetTip);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_skill_return:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, WorkerActivity.class);
        intent.putExtra(IntentConfig.skillToWorker, skillBeanList.get(position));
        startActivity(intent);
    }

    @Override
    public void skillSuccess(String skillJson) {
        Utils.log(SkillActivity.this, "skillJson=" + skillJson);
        switch (STATE) {
            case REFRESH:
                skillBeanList.clear();
                break;
        }
        skillBeanList.addAll(DataUtils.getSkillBeanList(skillJson));
        Utils.log(SkillActivity.this, "skillBeanList=" + skillBeanList.toString());
        handler.sendEmptyMessage(SUCCESS);
    }

    @Override
    public void skillFailure(String failure) {
        handler.sendEmptyMessage(FAILURE);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        STATE = REFRESH;
        skillsPresenter.load(NetConfig.skillUrl);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        ptrl.hideFootView();
    }
}
