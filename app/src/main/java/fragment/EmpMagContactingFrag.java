package fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

/**
 * 创建日期：2017/8/29 on 17:26
 * 作者:孙明明
 * 描述:雇主工作管理-洽谈中
 */

public class EmpMagContactingFrag extends CommonFragment implements PullToRefreshLayout.OnRefreshListener {

    private View rootView, emptyDataView, emptyNetView;
    private FrameLayout fl;
    private TextView emptyNetTv;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
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
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(StateConfig.LOAD_NO_NET);
        handler.removeMessages(StateConfig.LOAD_DONE);
    }

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.common_listview, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
        initEmptyView();
    }

    private void initRootView() {
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initDialogView() {
        cpd = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyDataView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_data, null);
        emptyDataView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fl.addView(emptyDataView);
        emptyDataView.setVisibility(View.GONE);
        emptyNetView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_net, null);
        emptyNetTv = (TextView) emptyNetView.findViewById(R.id.tv_no_net_refresh);
        emptyNetView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fl.addView(emptyNetView);
        emptyNetView.setVisibility(View.GONE);
        emptyNetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyNetView.setVisibility(View.GONE);
                loadNetData();
            }
        });
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new PersonAdapter(getActivity(), list);
        okHttpClient = new OkHttpClient();
    }

    @Override
    protected void setData() {
        plv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
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
                PersonBean p1 = new PersonBean();
                p1.setImage("");
                p1.setName("急招水泥工");
                p1.setPlay("10月2日开工，工期5天");
                p1.setShow("工资：100/人/天");
                p1.setState(StateConfig.TALKING);
                p1.setCollect(false);
                p1.setDistance("南马路12号");
                list.add(p1);
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                Utils.toast(getActivity(), StateConfig.loadNonet);
                break;
            case StateConfig.LOAD_LOAD:
                ptrl.hideFootView();
                Utils.toast(getActivity(), StateConfig.loadNonet);
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
                    emptyNetView.setVisibility(View.GONE);
                    emptyDataView.setVisibility(View.GONE);
                    ptrl.setVisibility(View.VISIBLE);
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
