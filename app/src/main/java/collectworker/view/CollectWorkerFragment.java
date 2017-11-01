package collectworker.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import adapter.CollectWorkerAdapter;
import bean.WorkerBean;
import collectworker.presenter.CollectWorkerPresenter;
import collectworker.presenter.ICollectWorkerPresenter;
import config.NetConfig;
import config.VarConfig;
import listener.IdPosClickHelp;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import skill.view.SkillActivity;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;

public class CollectWorkerFragment extends Fragment implements ICollectWorkerFragment, PullToRefreshLayout.OnRefreshListener, IdPosClickHelp {

    private View rootView, emptyView, netView;
    private FrameLayout fl;
    private TextView netTv;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private CProgressDialog cpd;
    private List<WorkerBean> collectWorkerList;
    private CollectWorkerAdapter collectWorkerAdapter;

    private ICollectWorkerPresenter collectWorkerPresenter;

    private int clickPosition = 0;
    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;
    private final int CANCEL_COLLECT_SUCCESS = 3;
    private final int CANCEL_COLLECT_FAILURE = 4;
    private final int FIRST = 5;
    private final int REFRESH = 6;
    private int STATE = FIRST;
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
                    case CANCEL_COLLECT_SUCCESS:
                        Utils.toast(getActivity(), tip);
                        collectWorkerList.remove(clickPosition);
                        notifyData();
                        break;
                    case CANCEL_COLLECT_FAILURE:
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
        handler.removeMessages(1);
        if (collectWorkerPresenter != null) {
            collectWorkerPresenter.destroy();
            collectWorkerPresenter = null;
        }
    }

    private void initView() {
        initRootView();
        initEmptyView();
    }

    private void initRootView() {
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
        cpd = Utils.initProgressDialog(getActivity(), cpd);
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
        collectWorkerPresenter = new CollectWorkerPresenter(this);
        collectWorkerList = new ArrayList<>();
        collectWorkerAdapter = new CollectWorkerAdapter(getActivity(), collectWorkerList, this);
    }

    private void setData() {
        plv.setAdapter(collectWorkerAdapter);
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
        collectWorkerPresenter.load(NetConfig.collectWorkerUrl + "?u_id=" + UserUtils.readUserData(getActivity()).getId());
    }

    private void notifyData() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                if (collectWorkerList.size() == 0) {
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
        collectWorkerAdapter.notifyDataSetChanged();
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
    public void loadSuccess(String loadJson) {
        switch (STATE) {
            case REFRESH:
                collectWorkerList.clear();
                break;
        }
        collectWorkerList.addAll(DataUtils.getWorkerBeanList(loadJson));
        handler.sendEmptyMessage(LOAD_SUCCESS);
    }

    @Override
    public void loadFailure(String failure) {
        handler.sendEmptyMessage(LOAD_FAILURE);
    }

    @Override
    public void cancelCollectSuccess(String cancelCollectJson) {
        try {
            JSONObject beanObj = new JSONObject(cancelCollectJson);
            int code = beanObj.optInt("code");
            switch (code) {
                case 1:
                    JSONObject dataObj = beanObj.optJSONObject("data");
                    tip = dataObj.optString("msg");
                    handler.sendEmptyMessage(CANCEL_COLLECT_SUCCESS);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelCollectFailure(String failure) {
        handler.sendEmptyMessage(CANCEL_COLLECT_FAILURE);
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
        switch (id) {
            case R.id.ll_item_worker:
                break;
            case R.id.iv_item_worker_collect:
                clickPosition = pos;
                collectWorkerPresenter.cancelCollect(NetConfig.favorateDelUrl + "?f_id=" + collectWorkerList.get(clickPosition).getCollectId());
                break;
        }
    }
}
