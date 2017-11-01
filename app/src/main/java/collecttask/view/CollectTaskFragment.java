package collecttask.view;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.CollectTaskAdapter;
import bean.TaskBean;
import collecttask.presenter.CollectTaskPresenter;
import collecttask.presenter.ICollectTaskPresenter;
import config.NetConfig;
import config.VarConfig;
import listener.IdPosClickHelp;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;

public class CollectTaskFragment extends Fragment implements ICollectTaskFragment, IdPosClickHelp, PullToRefreshLayout.OnRefreshListener {

    private View rootView, emptyView, netView;
    private FrameLayout fl;
    private TextView netTv;
    private CProgressDialog cpd;
    private ICollectTaskPresenter collectTaskPresenter;

    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<TaskBean> collectTaskList;
    private CollectTaskAdapter collectTaskAdapter;

    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;
    private final int CANCEL_SUCCESS = 3;
    private final int CANCEL_FAILURE = 4;
    private final int FIRST = 5;
    private final int REFRESH = 6;
    private int STATE = FIRST;

    private int clickPosition = 0;
    private String tip = "";

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
                    case CANCEL_SUCCESS:
                        Utils.toast(getActivity(), tip);
                        collectTaskList.remove(clickPosition);
                        notifyData();
                        break;
                    case CANCEL_FAILURE:
                        break;
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.common_listview, null);
        initView();
        initData();
        setData();
        setListener();
        loadData();
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (collectTaskPresenter != null) {
            collectTaskPresenter.destroy();
            collectTaskPresenter = null;
        }
        if (handler != null) {
            handler.removeMessages(LOAD_SUCCESS);
            handler = null;
        }
    }

    private void initView() {
        initRootView();
        initEmptyView();
    }

    private void initRootView() {
        cpd = Utils.initProgressDialog(getActivity(), cpd);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_data, null);
        fl.addView(emptyView);
        emptyView.setVisibility(View.GONE);
        netView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_net, null);
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
        collectTaskPresenter = new CollectTaskPresenter(this);
        collectTaskList = new ArrayList<>();
        collectTaskAdapter = new CollectTaskAdapter(getActivity(), collectTaskList, this);
    }

    private void setData() {
        plv.setAdapter(collectTaskAdapter);
    }

    private void setListener() {
        ptrl.setOnRefreshListener(this);
    }

    private void loadData() {
        switch (STATE) {
            case FIRST:
                cpd.show();
                break;
        }
        collectTaskPresenter.load(NetConfig.collectTaskUrl + "?u_id=" + UserUtils.readUserData(getActivity()).getId());
    }

    private void notifyData() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                if (collectTaskList.size() == 0) {
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
        collectTaskAdapter.notifyDataSetChanged();
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
                Utils.toast(getActivity(), VarConfig.noNetTip);
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
                collectTaskList.clear();
                break;
        }
        collectTaskList.addAll(DataUtils.getCollectTaskList(json));
        handler.sendEmptyMessage(LOAD_SUCCESS);
    }

    @Override
    public void loadFailure(String failure) {
    }

    @Override
    public void cancelCollectSuccess(String json) {
        try {
            JSONObject beanObj = new JSONObject(json);
            int code = beanObj.optInt("code");
            switch (code) {
                case 1:
                    JSONObject dataObj = beanObj.optJSONObject("data");
                    if (dataObj != null) {
                        tip = dataObj.optString("msg");
                        handler.sendEmptyMessage(CANCEL_SUCCESS);
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelCollectFailure(String failure) {
        handler.sendEmptyMessage(CANCEL_FAILURE);
    }

    @Override
    public void onClick(int id, int pos) {
        clickPosition = pos;
        switch (id) {
            case R.id.ll_item_task:
                break;
            case R.id.iv_item_task_collect:
                collectTaskPresenter.cancelCollect(NetConfig.delCollectUrl + "?f_id=" + collectTaskList.get(clickPosition).getCollectId());
                break;
        }
    }
}
