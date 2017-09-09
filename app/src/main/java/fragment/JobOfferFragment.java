package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.SysMsgAdapter;
import bean.SysMsg;
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

/**
 * 创建日期：2017/8/8 on 11:06
 * 作者:孙明明
 * 描述:工作邀约
 */

public class JobOfferFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private LinearLayout noNetLl, noDataLl;
    private TextView noNetTv;
    private PullToRefreshLayout jobOfferPtrl;
    private PullableListView jobOfferLv;
    private CProgressDialog progressDialog;

    private List<SysMsg> jobOfferList;
    private SysMsgAdapter jobOfferAdapter;
    private OkHttpClient okHttpClient;

    private int state = StateConfig.LOAD_DONE;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                progressDialog.dismiss();
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
        jobOfferPtrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl_msg);
        jobOfferLv = (PullableListView) rootView.findViewById(R.id.plv_msg);
        noNetLl = (LinearLayout) rootView.findViewById(R.id.ll_no_net);
        noDataLl = (LinearLayout) rootView.findViewById(R.id.ll_no_data);
        noNetTv = (TextView) rootView.findViewById(R.id.tv_empty_no_net_refresh);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initData() {
        jobOfferList = new ArrayList<>();
        jobOfferAdapter = new SysMsgAdapter(getActivity(), jobOfferList);
        okHttpClient = new OkHttpClient();
    }

    private void setData() {
        jobOfferLv.setAdapter(jobOfferAdapter);
    }

    private void setListener() {
        noNetTv.setOnClickListener(this);
        jobOfferLv.setOnItemClickListener(this);
    }

    private void loadData() {
        progressDialog.show();
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
                        jobOfferList.clear();
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
                SysMsg mo0 = new SysMsg();
                mo0.setTitle("工作邀约");
                mo0.setDate("2017/03/07");
                mo0.setDes("有一份适合你的工作，雇主已经向您发起邀约，等待您同意");
                mo0.setArrowShow(true);
                SysMsg mo1 = new SysMsg();
                mo1.setTitle("工作邀约");
                mo1.setDate("2017/03/06");
                mo1.setDes("您发布的工作有工人感兴趣并向您发起邀约，等待您同意");
                mo1.setArrowShow(true);
                jobOfferList.add(mo0);
                jobOfferList.add(mo1);
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
                if (jobOfferList.size() == 0) {
                    noNetLl.setVisibility(View.VISIBLE);
                    noDataLl.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                jobOfferPtrl.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case StateConfig.LOAD_LOAD:
                jobOfferPtrl.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }
    }

    private void notifyData() {
        jobOfferAdapter.notifyDataSetChanged();
        switch (state) {
            case StateConfig.LOAD_DONE:
                progressDialog.dismiss();
                break;
            case StateConfig.LOAD_REFRESH:
                jobOfferPtrl.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case StateConfig.LOAD_LOAD:
                jobOfferPtrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_empty_no_net_refresh:
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Utils.toast(getActivity(), "你点击了第" + (position + 1) + "个" + jobOfferList.get(position).getTitle());
    }
}
