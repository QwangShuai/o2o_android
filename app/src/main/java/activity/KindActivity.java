package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.KindAdapter;
import bean.Kind;
import cache.LruJsonCache;
import config.NetConfig;
import config.StateConfig;
import listener.OnRefreshListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;
import view.CListView;
import view.CProgressDialog;

public class KindActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView, noDataEmptyView, noNetEmptyView;
    private RelativeLayout returnRl;
    private CListView listView;
    private CProgressDialog progressDialog;
    private TextView noNetRefreshTv;

    private List<Kind> kindList;
    private KindAdapter kindAdapter;

    private OkHttpClient okHttpClient;
    private LruJsonCache lruJsonCache;
    private String cacheData;
    private int LOAD_STATE;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                stopAnim();
                switch (msg.what) {
                    case StateConfig.LOAD_NO_NET:
                        noNet();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_type, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    private void initView() {
        initRootView();
        initEmptyView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_type_return);
        listView = (CListView) rootView.findViewById(R.id.clv_type);
    }

    private void initEmptyView() {
        noDataEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_no_data, null);
        noDataEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewGroup) listView.getParent()).addView(noDataEmptyView);
        noDataEmptyView.setVisibility(View.GONE);
        noNetEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_no_net, null);
        noNetEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewGroup) listView.getParent()).addView(noNetEmptyView);
        noNetEmptyView.setVisibility(View.GONE);
        noNetRefreshTv = (TextView) noNetEmptyView.findViewById(R.id.tv_empty_no_net_refresh);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    private void initData() {
        kindList = new ArrayList<>();
        kindAdapter = new KindAdapter(this, kindList);
        okHttpClient = new OkHttpClient();
        lruJsonCache = LruJsonCache.get(this);
    }

    private void setData() {
        listView.setAdapter(kindAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        noNetRefreshTv.setOnClickListener(this);
    }

    private void loadData() {
        startAnim();
        if (checkLocalData()) {
            loadLocalData();
        } else {
            loadNetData();
        }
    }

    private boolean checkLocalData() {
        cacheData = lruJsonCache.getAsString("kind");
        if (!TextUtils.isEmpty(cacheData)) {
            return false;
        }
        return false;
    }

    private void loadLocalData() {
        if (parseJson(cacheData)) {
            handler.sendEmptyMessage(StateConfig.LOAD_DONE);
        }
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
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        lruJsonCache.put("kind", json, 10);
                        switch (LOAD_STATE) {
                            case StateConfig.LOAD_REFRESH:
                                kindList.clear();
                                break;
                        }
                        if (parseJson(json)) {
                            handler.sendEmptyMessage(StateConfig.LOAD_DONE);
                        }
                    }
                }
            }
        });
    }

    private boolean parseJson(String json) {
        boolean result = false;
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                Kind k0 = new Kind();
                k0.setName("水泥工");
                Kind k1 = new Kind();
                k1.setName("搬运工");
                Kind k2 = new Kind();
                k2.setName("焊接工");
                Kind k3 = new Kind();
                k3.setName("水暖工");
                Kind k4 = new Kind();
                k4.setName("瓦工");
                kindList.add(k0);
                kindList.add(k1);
                kindList.add(k2);
                kindList.add(k3);
                kindList.add(k4);
                result = true;
            } else {
                result = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void startAnim() {
        progressDialog.show();
    }

    private void stopAnim() {
        progressDialog.dismiss();
    }

    private void noNet() {
        switch (LOAD_STATE) {
            case StateConfig.LOAD_NO_NET:
                kindList.clear();
                listView.setEmptyView(noNetEmptyView);
                break;
            case StateConfig.LOAD_REFRESH:
                listView.hideHeadView();
                Utils.toast(this, StateConfig.loadRefreshFailure);
                break;
            case StateConfig.LOAD_LOAD:
                listView.hideFootView();
                Utils.toast(this, StateConfig.loadLoadFailure);
                break;
        }
    }

    private void notifyData() {
        kindAdapter.notifyDataSetChanged();
        listView.setEmptyView(noDataEmptyView);
        switch (LOAD_STATE) {
            case StateConfig.LOAD_REFRESH:
                listView.hideHeadView();
                Utils.toast(this, StateConfig.loadRefreshSuccess);
                break;
            case StateConfig.LOAD_LOAD:
                listView.hideFootView();
                Utils.toast(this, StateConfig.loadLoadSuccess);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_type_return:
                finish();
                break;
            case R.id.tv_empty_no_net_refresh:
                startAnim();
                LOAD_STATE = StateConfig.LOAD_REFRESH;
                loadNetData();
                break;
        }
    }

    @Override
    public void onDownPullRefresh() {
        LOAD_STATE = StateConfig.LOAD_REFRESH;
        loadNetData();
    }

    @Override
    public void onLoadingMore() {
        LOAD_STATE = StateConfig.LOAD_LOAD;
        loadNetData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, WorkerActivity.class);
        intent.putExtra("kind", kindList.get(position - 1));
        startActivity(intent);
    }
}
