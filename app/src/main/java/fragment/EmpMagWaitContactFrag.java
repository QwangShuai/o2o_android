package fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
import view.CProgressDialog;

/**
 * 创建日期：2017/8/29 on 17:25
 * 作者:孙明明
 * 描述:雇主工作管理-待联系
 */

public class EmpMagWaitContactFrag extends CommonFragment implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener{

    //根视图
    private View rootView;
    //无网络视图
    private LinearLayout noNetLl;
    private TextView noNetTv;
    //无数据视图
    private LinearLayout noDataLl;
    //刷新布局视图
    private PullToRefreshLayout pTrl;
    //刷新ListView视图
    private PullableListView pLv;
    //加载对话框视图
    private CProgressDialog cPd;
    //全部数据类集合
    private List<PersonBean> allList;
    //全部数据适配器
    private PersonAdapter allAdapter;
    //okHttpClient
    private OkHttpClient okHttpClient;
    //加载状态
    private int state;
    //handler
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
                        notifyNoData();
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
    }

    private void initRootView() {
        //初始化无网络视图
        noNetLl = (LinearLayout) rootView.findViewById(R.id.ll_no_net);
        noNetTv = (TextView) rootView.findViewById(R.id.tv_no_net_refresh);
        //初始化无数据视图
        noDataLl = (LinearLayout) rootView.findViewById(R.id.ll_no_data);
        //初始化刷新布局视图
        pTrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        //初始化刷新ListView视图
        pLv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initDialogView() {
        //初始化加载对话框视图
        cPd = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    @Override
    protected void initData() {
        //初始化全部数据类集合
        allList = new ArrayList<>();
        //初始化全部数据适配器
        allAdapter = new PersonAdapter(getActivity(), allList);
        //初始化okHttpClient
        okHttpClient = new OkHttpClient();
        //初始化加载状态
        state = StateConfig.LOAD_DONE;
    }

    @Override
    protected void setData() {
        //绑定全部数据适配器
        pLv.setAdapter(allAdapter);
    }

    @Override
    protected void setListener() {
        //无网络视图监听
        noNetTv.setOnClickListener(this);
        //刷新布局视图监听
        pTrl.setOnRefreshListener(this);
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
                    if (state == StateConfig.LOAD_REFRESH) {
                        allList.clear();
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
                    PersonBean p = new PersonBean();
                    p.setImage("");
                    p.setLongitude(1);
                    p.setState(0);
                    p.setName("dfdfweedsf");
                    p.setLatitude(2);
                    p.setCollect(false);
                    p.setShow("dfeiefdfjdlf");
                    p.setDistance("dfseodfjdkf");
                    p.setPlay("dfefdlfj");
                    allList.add(p);
                }
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //无网络通知
    private void notifyNoNet() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                cPd.dismiss();
                if (allList.size() == 0) {
                    noNetLl.setVisibility(View.VISIBLE);
                    noDataLl.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                break;
            case StateConfig.LOAD_LOAD:
                break;
            default:
                break;
        }
    }

    //无数据通知
    private void notifyNoData() {
        switch (state) {
            case StateConfig.LOAD_DONE:
                cPd.dismiss();
                if (allList.size() == 0) {
                    noDataLl.setVisibility(View.VISIBLE);
                    noNetLl.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                break;
            case StateConfig.LOAD_LOAD:
                break;
            default:
                break;
        }
        allAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //无网络视图点击事件
            case R.id.tv_no_net_refresh:
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
