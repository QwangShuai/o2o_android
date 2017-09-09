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

import adapter.PersonAdapter;
import bean.PersonBean;
import bean.ScreenBean;
import config.CodeConfig;
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

public class WorkerActivity extends CommonActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout screenRl;
    private PullToRefreshLayout pTrl;
    private PullableListView pLv;
    private CProgressDialog cPd;
    private List<PersonBean> personBeanList;
    private PersonAdapter personAdapter;
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
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_worker, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_screen);
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
        personBeanList = new ArrayList<>();
        personAdapter = new PersonAdapter(this, personBeanList);
        okHttpClient = new OkHttpClient();
        state = StateConfig.LOAD_DONE;
    }

    @Override
    protected void setData() {
        pLv.setAdapter(personAdapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
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
                        personBeanList.clear();
                    }
                    parseJson(result);
                }
            }
        });
    }

    private void notifyNoNet() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                cPd.dismiss();
                if (personBeanList.size() == 0) {
                    pTrl.setVisibility(View.GONE);
                    emptyDataView.setVisibility(View.GONE);
                    emptyNetView.setVisibility(View.VISIBLE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                Utils.toast(this, StateConfig.loadNonet);
                break;
            case StateConfig.LOAD_LOAD:
                Utils.toast(this, StateConfig.loadNonet);
                break;
        }
    }

    private void notifyData() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                cPd.dismiss();
                if (personBeanList.size() == 0) {
                    pTrl.setVisibility(View.GONE);
                    emptyNetView.setVisibility(View.GONE);
                    emptyDataView.setVisibility(View.VISIBLE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                pTrl.hideHeadView();
                break;
            case StateConfig.LOAD_LOAD:
                pTrl.hideFootView();
                break;
        }
        personAdapter.notifyDataSetChanged();
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                PersonBean p0 = new PersonBean();
                p0.setName("专业水泥工");
                p0.setPlay("精通XX，XX，XX");
                p0.setShow("完成过xx项目，个人家装");
                p0.setState(StateConfig.LEISURE);
                p0.setCollect(false);
                p0.setDistance("距离3公里");
                PersonBean p1 = new PersonBean();
                p1.setName("专业水泥工");
                p1.setPlay("精通XX，XX，XX");
                p1.setShow("完成过xx项目，个人家装");
                p1.setState(StateConfig.WORKING);
                p1.setCollect(false);
                p1.setDistance("距离3公里");
                PersonBean p2 = new PersonBean();
                p2.setName("专业水泥工");
                p2.setPlay("精通XX，XX，XX");
                p2.setShow("完成过xx项目，个人家装");
                p2.setState(StateConfig.TALKING);
                p2.setCollect(true);
                p2.setDistance("距离3公里");
                personBeanList.add(p0);
                personBeanList.add(p1);
                personBeanList.add(p2);
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_worker_return:
                finish();
                break;
            case R.id.rl_worker_screen:
                startActivityForResult(new Intent(this, WorkerScnActivity.class), CodeConfig.screenRequestCode);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TalkActivity.class);
        intent.putExtra("worker", personBeanList.get(position));
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeConfig.screenRequestCode && resultCode == CodeConfig.screenResultCode && data != null) {
            ScreenBean screenBean = (ScreenBean) data.getSerializableExtra("screenBean");
            if (screenBean != null) {
                int a = screenBean.getState();
                Utils.toast(this, a + "");
            }
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
