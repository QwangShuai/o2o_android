package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

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
import listener.OnRefreshListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;
import view.CListView;
import view.CProgressDialog;

public class JobActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshListener {

    private View rootView, noNetEmptyView, noDataEmptyView;
    private RelativeLayout returnRl, screenRl;
    private CProgressDialog progressDialog;
    private CListView listView;

    private List<Person> personList;
    private PersonAdapter personAdapter;

    private OkHttpClient okHttpClient;
    private int LOAD_STATE;
    private String tip;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                stopAnim();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_job, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_screen);
        listView = (CListView) rootView.findViewById(R.id.lv_job);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    private void initData() {
        personList = new ArrayList<>();
        personAdapter = new PersonAdapter(this, personList);
        okHttpClient = new OkHttpClient();
    }

    private void setData() {
        listView.setAdapter(personAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
        listView.setOnRefreshListener(this);
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
        return false;
    }

    private void loadLocalData() {

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
                    switch (LOAD_STATE) {
                        case StateConfig.LOAD_REFRESH:
                            personList.clear();
                            break;
                    }
                    String json = response.body().string();
                    saveToLocalData(json);
                    if (parseJson(json))
                        handler.sendEmptyMessage(StateConfig.LOAD_DONE);
                }
            }
        });
    }

    private void startAnim() {
        progressDialog.show();
    }

    private void stopAnim() {
        progressDialog.dismiss();
    }

    private void saveToLocalData(String json) {

    }

    private boolean parseJson(String json) {
        boolean b = false;
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                Person p0 = new Person();
                p0.setName("专业水泥工");
                p0.setCollect(false);
                p0.setState(0);
                p0.setDistance("距离3公里");
                p0.setPlay("X月X日开工，工期2天");
                p0.setShow("工资：200/人/天");
                Person p1 = new Person();
                p1.setName("专业瓦工");
                p1.setCollect(true);
                p1.setState(1);
                p1.setDistance("距离一公里");
                p1.setPlay("10月2号开工，工期5天");
                p1.setShow("工资：500/人/天");
                personList.add(p0);
                personList.add(p1);
                b = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b;
    }

    private void notifyNoNet() {
        switch (LOAD_STATE) {
            case StateConfig.LOAD_REFRESH:
                listView.hideHeadView();
                tip = StateConfig.loadRefreshFailure;
                break;
            case StateConfig.LOAD_LOAD:
                listView.hideFootView();
                tip = StateConfig.loadLoadFailure;
                break;
        }
        showTip(tip);
    }

    private void notifyData() {
        personAdapter.notifyDataSetChanged();
        switch (LOAD_STATE) {
            case StateConfig.LOAD_REFRESH:
                listView.hideHeadView();
                tip = StateConfig.loadRefreshSuccess;
                break;
            case StateConfig.LOAD_LOAD:
                listView.hideFootView();
                tip = StateConfig.loadLoadSuccess;
                break;
        }
        showTip(tip);
    }

    private void showTip(String tip) {
        if (!TextUtils.isEmpty(tip)) {
            Utils.toast(this, tip);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_job_return:
                finish();
                break;
            case R.id.rl_job_screen:
                startActivity(new Intent(this, ScreenJobActivity.class));
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
}
