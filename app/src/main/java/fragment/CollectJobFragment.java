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

import adapter.PersonAdapter;
import bean.Person;
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
 * 创建日期：2017/8/8 on 10:16
 * 作者:孙明明
 * 描述:收藏的工作碎片
 */

public class CollectJobFragment extends Fragment implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private LinearLayout noDataLl, noNetLl;
    private TextView emptyNoNetTv;
    private PullToRefreshLayout collectJobPtrl;
    private PullableListView collectJobLv;
    private CProgressDialog progressDialog;

    private List<Person> collectJobList;
    private PersonAdapter collectJobAdapter;

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
        rootView = inflater.inflate(R.layout.fragment_msg, null);
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
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        collectJobPtrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl_msg);
        collectJobLv = (PullableListView) rootView.findViewById(R.id.lv_msg);
        noDataLl = (LinearLayout) rootView.findViewById(R.id.ll_no_data);
        noNetLl = (LinearLayout) rootView.findViewById(R.id.ll_no_net);
        emptyNoNetTv = (TextView) rootView.findViewById(R.id.tv_empty_no_net_refresh);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initData() {
        collectJobList = new ArrayList<>();
        collectJobAdapter = new PersonAdapter(getActivity(), collectJobList);
        okHttpClient = new OkHttpClient();
    }

    private void setData() {
        collectJobLv.setAdapter(collectJobAdapter);
    }

    private void setListener() {
        emptyNoNetTv.setOnClickListener(this);
        collectJobPtrl.setOnRefreshListener(this);
    }

    private void loadData() {
        progressDialog.show();
        loadNetData();
    }

    private void loadNetData() {
        Request collectJobRequest = new Request.Builder().url(NetConfig.testUrl).get().build();
        okHttpClient.newCall(collectJobRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (state == StateConfig.LOAD_REFRESH) {
                        collectJobList.clear();
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
                for (int i = 0; i < 10; i++) {
                    Person p = new Person();
                    p.setName("急招X工");
                    p.setState(1);
                    p.setShow("x月x日开工、工期XX天");
                    p.setPlay("工资：xxx");
                    p.setDistance("距我x公里");
                    p.setCollect(false);
                    collectJobList.add(p);
                }
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyNoNet() {
        collectJobAdapter.notifyDataSetChanged();
        switch (state) {
            case StateConfig.LOAD_DONE:
                progressDialog.dismiss();
                if (collectJobList.size() == 0) {
                    noDataLl.setVisibility(View.GONE);
                    noNetLl.setVisibility(View.VISIBLE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                collectJobPtrl.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case StateConfig.LOAD_LOAD:
                collectJobPtrl.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }
    }

    private void notifyData() {
        collectJobAdapter.notifyDataSetChanged();
        switch (state) {
            case StateConfig.LOAD_DONE:
                progressDialog.dismiss();
                if (collectJobList.size() == 0) {
                    noNetLl.setVisibility(View.GONE);
                    noDataLl.setVisibility(View.VISIBLE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                collectJobPtrl.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case StateConfig.LOAD_LOAD:
                collectJobPtrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_empty_no_net_refresh:
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
