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
 * 创建日期：2017/8/8 on 10:18
 * 作者:孙明明
 * 描述:收藏的工人碎片
 */

public class CollectWorkerFragment extends Fragment implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private PullToRefreshLayout collectWorkerPtrl;
    private PullableListView collectWorkerLv;
    private LinearLayout noNetLl, noDataLl;
    private TextView noNetTv;
    private CProgressDialog progressDialog;

    private List<Person> collectWorkerList;
    private PersonAdapter collectWorkerAdapter;

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
        collectWorkerPtrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl_msg);
        collectWorkerLv = (PullableListView) rootView.findViewById(R.id.lv_msg);
        noNetLl = (LinearLayout) rootView.findViewById(R.id.ll_no_net);
        noDataLl = (LinearLayout) rootView.findViewById(R.id.ll_no_data);
        noNetTv = (TextView) rootView.findViewById(R.id.tv_empty_no_net_refresh);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initData() {
        collectWorkerList = new ArrayList<>();
        collectWorkerAdapter = new PersonAdapter(getActivity(), collectWorkerList);
        okHttpClient = new OkHttpClient();
    }

    private void setData() {
        collectWorkerLv.setAdapter(collectWorkerAdapter);
    }

    private void setListener() {
        noNetTv.setOnClickListener(this);
        collectWorkerPtrl.setOnRefreshListener(this);
    }

    private void loadData() {
        progressDialog.show();
        loadNetData();
    }

    private void loadNetData() {
        Request collectWorkerRequest = new Request.Builder().url(NetConfig.testUrl).get().build();
        okHttpClient.newCall(collectWorkerRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (state == StateConfig.LOAD_REFRESH) {
                        collectWorkerList.clear();
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
                    p.setName("dfdf");
                    p.setShow("weedt");
                    p.setPlay("hadifdf");
                    p.setDistance("vcvcv");
                    p.setState(2);
                    p.setCollect(true);
                    collectWorkerList.add(p);
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
                if (collectWorkerList.size() == 0) {
                    noNetLl.setVisibility(View.VISIBLE);
                    noDataLl.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                collectWorkerPtrl.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case StateConfig.LOAD_LOAD:
                collectWorkerPtrl.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }
    }

    private void notifyData() {
        collectWorkerAdapter.notifyDataSetChanged();
        switch (state) {
            case StateConfig.LOAD_DONE:
                progressDialog.dismiss();
                if (collectWorkerList.size() == 0) {
                    noDataLl.setVisibility(View.VISIBLE);
                    noNetLl.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                collectWorkerPtrl.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case StateConfig.LOAD_LOAD:
                collectWorkerPtrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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
