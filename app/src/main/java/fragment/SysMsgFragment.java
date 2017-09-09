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
import view.CProgressDialog;

/**
 * 创建日期：2017/8/8 on 11:07
 * 作者:孙明明
 * 描述:系统消息
 */

public class SysMsgFragment extends Fragment implements View.OnClickListener , PullToRefreshLayout.OnRefreshListener{

    private View rootView;
    private LinearLayout noNetLl, noDataLl;
    private TextView noNetTv;
    private PullToRefreshLayout sysMsgPtrl;
    private PullableListView sysMsgLv;
    private CProgressDialog progressDialog;

    private List<SysMsg> sysMsgList;
    private SysMsgAdapter sysMsgAdapter;

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
        noNetLl = (LinearLayout) rootView.findViewById(R.id.ll_no_net);
        noDataLl = (LinearLayout) rootView.findViewById(R.id.ll_no_data);
        noNetTv = (TextView) rootView.findViewById(R.id.tv_empty_no_net_refresh);
        sysMsgPtrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl_msg);
        sysMsgLv = (PullableListView) rootView.findViewById(R.id.plv_msg);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initData() {
        sysMsgList = new ArrayList<>();
        sysMsgAdapter = new SysMsgAdapter(getActivity(), sysMsgList);
        okHttpClient = new OkHttpClient();
    }

    private void setData() {
        sysMsgLv.setAdapter(sysMsgAdapter);
    }

    private void setListener() {
        noNetTv.setOnClickListener(this);
    }

    private void loadData() {
        progressDialog.show();
        loadNetData();
    }

    private void loadNetData() {
        Request sysMsgRequest = new Request.Builder().url(NetConfig.testUrl).get().build();
        okHttpClient.newCall(sysMsgRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (state == StateConfig.LOAD_REFRESH) {
                        sysMsgList.clear();
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
                SysMsg sm0 = new SysMsg();
                sm0.setTitle("系统消息");
                sm0.setDate("2017/03/05");
                sm0.setDes("系统升级，钢建众工2.0版本全新上市，更多高薪工作等你来");
                sm0.setArrowShow(false);
                SysMsg sm1 = new SysMsg();
                sm1.setTitle("系统消息");
                sm1.setDate("2017/03/04");
                sm1.setDes("你有新的红包等待领取");
                sm1.setArrowShow(false);
                sysMsgList.add(sm0);
                sysMsgList.add(sm1);
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
                if (sysMsgList.size() == 0) {
                    noNetLl.setVisibility(View.VISIBLE);
                    noDataLl.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                sysMsgPtrl.refreshFinish(PullToRefreshLayout.FAIL);
                break;
            case StateConfig.LOAD_LOAD:
                sysMsgPtrl.loadmoreFinish(PullToRefreshLayout.FAIL);
                break;
        }
    }

    private void notifyData() {
        sysMsgAdapter.notifyDataSetChanged();
        switch (state) {
            case StateConfig.LOAD_DONE:
                progressDialog.dismiss();
                if(sysMsgList.size() == 0){
                    noDataLl.setVisibility(View.VISIBLE);
                    noNetLl.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                sysMsgPtrl.refreshFinish(PullToRefreshLayout.SUCCEED);
                break;
            case StateConfig.LOAD_LOAD:
                sysMsgPtrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

    }
}
