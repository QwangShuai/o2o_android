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

import org.json.JSONArray;
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

//工种
public class KindActivity extends CommonActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView, emptyNetView, emptyDataView;
    private RelativeLayout returnRl;
    private FrameLayout fl;
    private TextView emptyNetTv;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<KindBean> list;
    private KindAdapter adapter;
    private int state = StateConfig.LOAD_DONE;
    private OkHttpClient okHttpClient;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case StateConfig.LOAD_NO_NET:
                        notifyNet();
                        break;
                    case StateConfig.LOAD_DONE:
                        notifyData();
                        break;
                    default:
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
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_kind_return);
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initEmptyView() {
        emptyNetView = LayoutInflater.from(this).inflate(R.layout.empty_net, null);
        emptyNetView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fl.addView(emptyNetView);
        emptyNetView.setVisibility(View.GONE);
        emptyNetTv = (TextView) emptyNetView.findViewById(R.id.tv_no_net_refresh);
        emptyNetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyNetView.setVisibility(View.GONE);
                loadData();
            }
        });
        emptyDataView = LayoutInflater.from(this).inflate(R.layout.empty_data, null);
        emptyDataView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fl.addView(emptyDataView);
        emptyDataView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new KindAdapter(this, list);
        okHttpClient = new OkHttpClient();
    }

    @Override
    protected void setData() {
        plv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
        plv.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        loadNetData();
    }

    private void loadNetData() {
        Request request = new Request.Builder().url(NetConfig.kindUrl).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (state == StateConfig.LOAD_REFRESH)
                        list.clear();
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
                JSONArray arrData = objBean.optJSONArray("data");
                if (arrData != null) {
                    for (int i = 0; i < arrData.length(); i++) {
                        JSONObject o = arrData.optJSONObject(i);
                        if (o != null) {
                            KindBean kb = new KindBean();
                            kb.setId(o.optString("s_id"));
                            kb.setName(o.optString("s_name"));
                            list.add(kb);
                        }
                    }
                }
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyNet() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                ptrl.setVisibility(View.GONE);
                emptyDataView.setVisibility(View.GONE);
                emptyNetView.setVisibility(View.VISIBLE);
                break;
            case StateConfig.LOAD_REFRESH:
                ptrl.hideHeadView();
                break;
            case StateConfig.LOAD_LOAD:
                ptrl.hideFootView();
                break;
            default:
                break;
        }
    }

    private void notifyData() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                if (list.size() == 0) {
                    ptrl.setVisibility(View.GONE);
                    emptyNetView.setVisibility(View.GONE);
                    emptyDataView.setVisibility(View.VISIBLE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                ptrl.hideHeadView();
                break;
            case StateConfig.LOAD_LOAD:
                ptrl.hideFootView();
                break;
        }
        adapter.notifyDataSetChanged();
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
        ptrl.hideFootView();
    }
}
