package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.EvaluateAdapter;
import bean.EvaluateBean;
import config.NetConfig;
import config.StateConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import view.CProgressDialog;

/**
 * 创建日期：2017/8/8 on 10:50
 * 作者:孙明明
 * 描述:给别人的评价
 */

public class EvaluateGiveFragment extends Fragment implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private LinearLayout noNetLl;
    private LinearLayout noDataLl;
    private TextView noNetTv;
    private PullToRefreshLayout evaluateGivePtrl;
    private PullableListView evaluateGiveLv;
    private CProgressDialog progressDialog;

    private List<EvaluateBean> evaluateBeanGiveList;
    private EvaluateAdapter evaluateGiveAdapter;

    private OkHttpClient okHttpClient;

    private int state = StateConfig.LOAD_DONE;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case StateConfig.LOAD_NO_NET:
                        notifyNoNet();
                        break;
                    case StateConfig.LOAD_DONE:
                        notifyData();
                        break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(StateConfig.LOAD_NO_NET);
        handler.removeMessages(StateConfig.LOAD_DONE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.common_listview, null);
        initView();
        initData();
        setData();
        setListener();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        noNetLl = (LinearLayout) rootView.findViewById(R.id.ll_no_net);
        noDataLl = (LinearLayout) rootView.findViewById(R.id.ll_no_data);
        noNetTv = (TextView) rootView.findViewById(R.id.tv_no_net_refresh);
        evaluateGivePtrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl_common_listview);
        evaluateGiveLv = (PullableListView) rootView.findViewById(R.id.plv_common_listview);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initData() {
        evaluateBeanGiveList = new ArrayList<>();
        evaluateGiveAdapter = new EvaluateAdapter(getActivity(), evaluateBeanGiveList);
        okHttpClient = new OkHttpClient();
    }

    private void setData() {
        evaluateGiveLv.setAdapter(evaluateGiveAdapter);
    }

    private void setListener() {
        noNetTv.setOnClickListener(this);
        evaluateGivePtrl.setOnRefreshListener(this);
    }

    private void loadData() {
        progressDialog.show();
        loadNetData();
    }

    private void loadNetData() {
        Request evaluateGiveRequest = new Request.Builder().url(NetConfig.testUrl).get().build();
        okHttpClient.newCall(evaluateGiveRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (state == StateConfig.LOAD_REFRESH) {
                        evaluateBeanGiveList.clear();
                    }
                    String result = response.body().string();
                    parseJson(result);
                }
            }
        });
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                for (int i = 0; i < 5; i++) {
                    EvaluateBean e = new EvaluateBean();
                    e.setGet(false);
                    e.setNumCount(5);
                    e.setIcon("");
                    e.setContent("老板太苛刻");
                    e.setPraiseCount(0);
                    e.setTime("2017年5月4日 17:25");
                    evaluateBeanGiveList.add(e);
                }
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyNoNet() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                progressDialog.dismiss();
                if (evaluateBeanGiveList.size() == 0) {
                    noNetLl.setVisibility(View.VISIBLE);
                    noDataLl.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                evaluateGivePtrl.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case StateConfig.LOAD_LOAD:
                evaluateGivePtrl.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }
    }

    private void notifyData() {
        evaluateGiveAdapter.notifyDataSetChanged();
        switch (state) {
            case StateConfig.LOAD_DONE:
                progressDialog.dismiss();
                if (evaluateBeanGiveList.size() == 0) {
                    noNetLl.setVisibility(View.GONE);
                    noDataLl.setVisibility(View.VISIBLE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                evaluateGivePtrl.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case StateConfig.LOAD_LOAD:
                evaluateGivePtrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_no_net_refresh:
                noNetLl.setVisibility(View.GONE);
                loadNetData();
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        state = StateConfig.LOAD_REFRESH;
        loadNetData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        state = StateConfig.LOAD_LOAD;
        loadNetData();
    }
}
