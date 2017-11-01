package employermanage.view;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import config.NetConfig;
import config.StateConfig;
import config.VarConfig;
import adapter.EmployerManageAdapter;
import bean.EmployerManageBean;
import employermanage.presenter.EmployerManagePresenter;
import employermanage.presenter.IEmployerManagePresenter;
import listener.IdPosClickHelp;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import skill.view.SkillActivity;
import utils.DataUtils;
import utils.UrlUtils;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;

public class EmployerManageActivity extends AppCompatActivity implements IEmployerManageActivity, View.OnClickListener, PullToRefreshLayout.OnRefreshListener, IdPosClickHelp {

    private View rootView, emptyView, netView;
    private FrameLayout fl;
    private TextView netTv;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private CProgressDialog cpd;
    private RelativeLayout returnRl;
    private RelativeLayout allRl, waitRl, talkRl, doingRl, doneRl;
    private TextView allTv, waitTv, talkTv, doingTv, doneTv;

    private View popView;
    private PopupWindow pop;

    private EmployerManageAdapter employerManageAdapter;
    private List<EmployerManageBean> employerManageBeanList = new ArrayList<>();

    private final int ALL = 0, WAIT = 1, TALK = 2, DOING = 3, DONE = 4;
    private int curState = ALL, tarState = -1;
    private final int FIRST = 0, REFRESH = 1, LOAD = 2;
    private int STATE = FIRST;
    private IEmployerManagePresenter employerManagePresenter;

    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;
    private final int CANCEL_SUCCESS = 3;
    private final int CANCEL_FAILURE = 4;
    private int clickPosition = 0;

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
                        break;
                    case CANCEL_SUCCESS:
                        cpd.dismiss();
                        employerManageBeanList.remove(clickPosition);
                        notifyData();
                        break;
                    case CANCEL_FAILURE:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_employer_manage, null);
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
        if (employerManagePresenter != null) {
            employerManagePresenter.destroy();
            employerManagePresenter = null;
        }
        if (handler != null) {
            handler.removeMessages(LOAD_SUCCESS);
            handler.removeMessages(LOAD_FAILURE);
            handler.removeMessages(CANCEL_SUCCESS);
            handler.removeMessages(CANCEL_FAILURE);
            handler = null;
        }
    }

    private void initView() {
        initRootView();
        initEmptyView();
        initPopView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_return);
        allRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_all);
        allTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_all);
        waitRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_wait);
        waitTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_wait);
        talkRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_talk);
        talkTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_talk);
        doingRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_doing);
        doingTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_doing);
        doneRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_done);
        doneTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_done);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
        cpd = Utils.initProgressDialog(EmployerManageActivity.this, cpd);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyView = LayoutInflater.from(EmployerManageActivity.this).inflate(R.layout.empty_data, null);
        fl.addView(emptyView);
        emptyView.setVisibility(View.GONE);
        netView = LayoutInflater.from(EmployerManageActivity.this).inflate(R.layout.empty_net, null);
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

    private void initPopView() {
        popView = LayoutInflater.from(EmployerManageActivity.this).inflate(R.layout.pop_dialog_0, null);
        ((TextView) popView.findViewById(R.id.tv_pop_dialog_0_content)).setText("此工作还没有工人联系\n是否主动邀约工人？");
        ((TextView) popView.findViewById(R.id.tv_pop_dialog_0_cancel)).setText("取消");
        ((TextView) popView.findViewById(R.id.tv_pop_dialog_0_sure)).setText("确认");
        popView.findViewById(R.id.rl_pop_dialog_0_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop.isShowing()) {
                    pop.dismiss();
                }
            }
        });
        popView.findViewById(R.id.rl_pop_dialog_0_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop.isShowing()) {
                    pop.dismiss();
                    startActivity(new Intent(EmployerManageActivity.this, SkillActivity.class));
                }
            }
        });
        pop = new PopupWindow(popView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setTouchable(true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    private void initData() {
        employerManagePresenter = new EmployerManagePresenter(this);
        employerManageAdapter = new EmployerManageAdapter(EmployerManageActivity.this, employerManageBeanList, this);
    }

    private void setData() {
        plv.setAdapter(employerManageAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        allRl.setOnClickListener(this);
        waitRl.setOnClickListener(this);
        talkRl.setOnClickListener(this);
        doingRl.setOnClickListener(this);
        doneRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
    }

    private void loadData() {
        switch (STATE) {
            case FIRST:
                cpd.show();
                break;
        }
        employerManagePresenter.load(UrlUtils.getEmployerManageUrl(EmployerManageActivity.this, curState));
    }

    private void notifyData() {
        switch (STATE) {
            case FIRST:
                if (employerManageBeanList.size() == 0) {
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
        employerManageAdapter.notifyDataSetChanged();
    }

    private void refreshView() {
        if (tarState != curState) {
            switch (tarState) {
                case 0:
                    allTv.setTextColor(ColorConfig.red_ff3e50);
                    waitTv.setTextColor(ColorConfig.black_252323);
                    talkTv.setTextColor(ColorConfig.black_252323);
                    doingTv.setTextColor(ColorConfig.black_252323);
                    doneTv.setTextColor(ColorConfig.black_252323);
                    break;
                case 1:
                    allTv.setTextColor(ColorConfig.black_252323);
                    waitTv.setTextColor(ColorConfig.red_ff3e50);
                    talkTv.setTextColor(ColorConfig.black_252323);
                    doingTv.setTextColor(ColorConfig.black_252323);
                    doneTv.setTextColor(ColorConfig.black_252323);
                    break;
                case 2:
                    allTv.setTextColor(ColorConfig.black_252323);
                    waitTv.setTextColor(ColorConfig.black_252323);
                    talkTv.setTextColor(ColorConfig.red_ff3e50);
                    doingTv.setTextColor(ColorConfig.black_252323);
                    doneTv.setTextColor(ColorConfig.black_252323);
                    break;
                case 3:
                    allTv.setTextColor(ColorConfig.black_252323);
                    waitTv.setTextColor(ColorConfig.black_252323);
                    talkTv.setTextColor(ColorConfig.black_252323);
                    doingTv.setTextColor(ColorConfig.red_ff3e50);
                    doneTv.setTextColor(ColorConfig.black_252323);
                    break;
                case 4:
                    allTv.setTextColor(ColorConfig.black_252323);
                    waitTv.setTextColor(ColorConfig.black_252323);
                    talkTv.setTextColor(ColorConfig.black_252323);
                    doingTv.setTextColor(ColorConfig.black_252323);
                    doneTv.setTextColor(ColorConfig.red_ff3e50);
                    break;
            }
            curState = tarState;
            STATE = FIRST;
            loadData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_employer_manage_return:
                finish();
                break;
            case R.id.rl_employer_manage_all:
                tarState = ALL;
                refreshView();
                break;
            case R.id.rl_employer_manage_wait:
                tarState = WAIT;
                refreshView();
                break;
            case R.id.rl_employer_manage_talk:
                tarState = TALK;
                refreshView();
                break;
            case R.id.rl_employer_manage_doing:
                tarState = DOING;
                refreshView();
                break;
            case R.id.rl_employer_manage_done:
                tarState = DONE;
                refreshView();
                break;
        }
    }

    @Override
    public void loadSuccess(String json) {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                employerManageBeanList.clear();
                break;
            case REFRESH:
                employerManageBeanList.clear();
                break;
            case LOAD:
                break;
        }
        employerManageBeanList.addAll(DataUtils.getEmployerManageBeanList(json));
        handler.sendEmptyMessage(LOAD_SUCCESS);
    }

    @Override
    public void loadFailure(String failure) {
        if (failure.equals(VarConfig.noNet)) {
            switch (STATE) {
                case FIRST:
                    cpd.dismiss();
                    ptrl.setVisibility(View.GONE);
                    netView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    break;
                case REFRESH:
                    ptrl.hideHeadView();
                    Utils.toast(EmployerManageActivity.this, StateConfig.loadNonet);
                    break;
            }
        }
    }

    @Override
    public void cancelSuccess(String json) {
        Utils.log(EmployerManageActivity.this, "cancel=" + json);
        try {
            JSONObject beanObj = new JSONObject(json);
            int code = beanObj.optInt("code");
            switch (code) {
                case 200:
                    handler.sendEmptyMessage(CANCEL_SUCCESS);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelFailure(String failure) {

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
    public void onClick(int id, int pos) {
        clickPosition = pos;
        switch (id) {
            case R.id.ll_item_employer_manage:
                String status = employerManageBeanList.get(clickPosition).getStatus();
                if (status.equals("0")) {
                    if (!pop.isShowing()) {
                        backgroundAlpha(0.5f);
                        pop.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                    }
                } else if (status.equals("1")) {

                } else if (status.equals("2")) {

                } else if (status.equals("3")) {

                }
                break;
            case R.id.tv_item_employer_manage_wait_cancel:
                cpd.show();
                employerManagePresenter.cancel(NetConfig.taskBaseUrl + "?action=del&t_id=" + employerManageBeanList.get(clickPosition).getTaskId() + "&t_author=" + UserUtils.readUserData(EmployerManageActivity.this).getId());
                break;
            case R.id.tv_item_employer_manage_talk_cancel:
                cpd.show();
                employerManagePresenter.cancel(NetConfig.taskBaseUrl + "?action=del&t_id=" + employerManageBeanList.get(clickPosition).getTaskId() + "&t_author=" + UserUtils.readUserData(EmployerManageActivity.this).getId());
                break;
        }
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = f;
        getWindow().setAttributes(lp);
    }
}
