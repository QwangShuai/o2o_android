package activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.PersonAdapter;
import bean.PersonBean;
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

public class JobActivity extends CommonActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    private View rootView, emptyNetView, emptyDataView;
    private TextView emptyNetTv;
    private FrameLayout fl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;

    private RelativeLayout returnRl, screenRl;
    private CProgressDialog cpd;

    private List<PersonBean> list;
    private PersonAdapter adapter;

    private OkHttpClient okHttpClient;

    private int state = StateConfig.LOAD_DONE;

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
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_job, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_screen);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initDialogView() {
        cpd = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyDataView = LayoutInflater.from(this).inflate(R.layout.empty_data, null);
        emptyDataView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fl.addView(emptyDataView);
        emptyDataView.setVisibility(View.GONE);
        emptyNetView = LayoutInflater.from(this).inflate(R.layout.empty_net, null);
        emptyNetTv = (TextView) emptyNetView.findViewById(R.id.tv_no_net_refresh);
        emptyNetView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fl.addView(emptyNetView);
        emptyNetView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new PersonAdapter(this, list);
        okHttpClient = new OkHttpClient();
    }

    @Override
    protected void setData() {
        plv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        cpd.show();
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
                    if (state == StateConfig.LOAD_REFRESH) {
                        list.clear();
                    }
                    String json = response.body().string();
                    parseJson(json);
                }
            }
        });
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                PersonBean p0 = new PersonBean();
                p0.setName("专业水泥工");
                p0.setCollect(false);
                p0.setState(StateConfig.LEISURE);
                p0.setDistance("距离3公里");
                p0.setPlay("X月X日开工，工期2天");
                p0.setShow("工资：200/人/天");
                PersonBean p1 = new PersonBean();
                p1.setName("专业水泥工");
                p1.setCollect(false);
                p1.setState(StateConfig.WORKING);
                p1.setDistance("距离3公里");
                p1.setPlay("10月2号开工，工期5天");
                p1.setShow("工资：100/人/天");
                PersonBean p2 = new PersonBean();
                p2.setName("专业水泥工");
                p2.setPlay("11月1日开工，工期一天");
                p2.setShow("工资：500/人/天");
                p2.setState(StateConfig.TALKING);
                p2.setCollect(true);
                p2.setDistance("距离3公里");
                list.add(p0);
                list.add(p1);
                list.add(p2);
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_job_return:
                finish();
                break;
            case R.id.rl_job_screen:
                startActivity(new Intent(this, JobScnActivity.class));
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

    private void notifyNet() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                cpd.dismiss();
                ptrl.setVisibility(View.GONE);
                emptyDataView.setVisibility(View.GONE);
                emptyNetView.setVisibility(View.VISIBLE);
                break;
            case StateConfig.LOAD_REFRESH:
                ptrl.hideHeadView();
                Utils.toast(this, StateConfig.loadNonet);
                break;
            case StateConfig.LOAD_LOAD:
                ptrl.hideFootView();
                Utils.toast(this, StateConfig.loadNonet);
                break;
            default:
                break;
        }
    }

    private void notifyData() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                cpd.dismiss();
                if (list.size() == 0) {
                    ptrl.setVisibility(View.GONE);
                    emptyNetView.setVisibility(View.GONE);
                    emptyDataView.setVisibility(View.VISIBLE);
                } else {
                    ptrl.setVisibility(View.VISIBLE);
                    emptyNetView.setVisibility(View.GONE);
                    emptyDataView.setVisibility(View.GONE);
                }
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
        adapter.notifyDataSetChanged();
    }
}
