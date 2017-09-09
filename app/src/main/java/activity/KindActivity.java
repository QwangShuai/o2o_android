package activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.KindAdapter;
import bean.KindBean;
import config.NetConfig;
import config.StateConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.Utils;
import view.CProgressDialog;

public class KindActivity extends CommonActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private PullToRefreshLayout pTrl;
    private PullableListView pLv;
    private CProgressDialog cPd;
    private List<KindBean> kindBeanList, tempList;
    private KindAdapter kindAdapter;
    private OkHttpClient okHttpClient;
    private int state;

    private FrameLayout emptyFl;
    private View emptyDataView;
    private View emptyNetView;
    private TextView noNetRefreshTv;

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
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(StateConfig.LOAD_NO_NET);
        handler.removeMessages(StateConfig.LOAD_DONE);
    }

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_kind, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_kind_return);
        pTrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        pLv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initDialogView() {
        cPd = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    private void initEmptyView() {
        emptyFl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyDataView = LayoutInflater.from(this).inflate(R.layout.empty_data, null);
        emptyDataView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyFl.addView(emptyDataView);
        emptyDataView.setVisibility(View.GONE);
        emptyNetView = LayoutInflater.from(this).inflate(R.layout.empty_net, null);
        noNetRefreshTv = (TextView) emptyNetView.findViewById(R.id.tv_no_net_refresh);
        emptyNetView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyFl.addView(emptyNetView);
        emptyNetView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        kindBeanList = new ArrayList<>();
        tempList = new ArrayList<>();
        kindAdapter = new KindAdapter(this, kindBeanList);
        okHttpClient = new OkHttpClient();
        state = StateConfig.LOAD_DONE;
    }

    @Override
    protected void setData() {
        pLv.setAdapter(kindAdapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        pTrl.setOnRefreshListener(this);
        pLv.setOnItemClickListener(this);
        noNetRefreshTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyNetView.setVisibility(View.GONE);
                loadNetData();
            }
        });
    }

    @Override
    protected void loadData() {
        cPd.show();
        loadNetData();
    }

    private void loadNetData() {
        Request request = new Request.Builder().url(NetConfig.testUrl).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (state == StateConfig.LOAD_REFRESH) {
                        kindBeanList.clear();
                    }
                    parseJson(result);
                }
            }
        });
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                KindBean k0 = new KindBean();
                k0.setName("水泥工（瓦匠）");
                KindBean k1 = new KindBean();
                k1.setName("搬运工");
                KindBean k2 = new KindBean();
                k2.setName("焊接工");
                tempList.add(k0);
                tempList.add(k1);
                tempList.add(k2);
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyNoNet() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                cPd.dismiss();
                if (kindBeanList.size() == 0) {
                    emptyNetView.setVisibility(View.VISIBLE);
                    pTrl.setVisibility(View.GONE);
                    emptyDataView.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                pTrl.hideHeadView();
                Utils.toast(this, StateConfig.loadNonet);
                break;
            case StateConfig.LOAD_LOAD:
                pTrl.hideFootView();
                Utils.toast(this, StateConfig.loadNonet);
                break;
        }
    }

    private void notifyData() {
        kindBeanList.addAll(tempList);
        tempList.clear();
        switch (state) {
            case StateConfig.LOAD_DONE:
                cPd.dismiss();
                if (kindBeanList.size() == 0) {
                    pTrl.setVisibility(View.GONE);
                    emptyDataView.setVisibility(View.VISIBLE);
                    emptyNetView.setVisibility(View.GONE);
                } else {
                    pTrl.setVisibility(View.VISIBLE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                pTrl.hideHeadView();
                break;
            case StateConfig.LOAD_LOAD:
                pTrl.hideFootView();
                break;
        }
        kindAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_kind_return:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, WorkerActivity.class));
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
