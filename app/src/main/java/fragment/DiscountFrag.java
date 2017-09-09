package fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.DcAdapter;
import bean.DcBean;
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
 * 创建日期：2017/7/28 on 13:53
 * 作者:孙明明
 * 描述:优惠
 */

public class DiscountFrag extends CommonFragment implements AdapterView.OnItemClickListener, PullToRefreshLayout.OnRefreshListener, View.OnClickListener {

    private View rootView;
    private LinearLayout noNetLl;
    private TextView noNetTv;
    private LinearLayout noDataLl;
    private PullToRefreshLayout pTrl;
    private PullableListView pLv;
    private CProgressDialog cPd;
    private List<DcBean> dcBeanList;
    private DcAdapter dcAdapter;
    private OkHttpClient okHttpClient;
    private int state;

    private List<DcBean> tempList;

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
                    default:
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
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_discount, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        noNetLl = (LinearLayout) rootView.findViewById(R.id.ll_no_net);
        noNetTv = (TextView) rootView.findViewById(R.id.tv_no_net_refresh);
        noDataLl = (LinearLayout) rootView.findViewById(R.id.ll_no_data);
        pTrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl_frag_discount);
        pLv = (PullableListView) rootView.findViewById(R.id.plv_frag_discount);
    }

    private void initDialogView() {
        cPd = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    @Override
    protected void initData() {
        dcBeanList = new ArrayList<>();
        tempList = new ArrayList<>();
        dcAdapter = new DcAdapter(getActivity(), dcBeanList);
        okHttpClient = new OkHttpClient();
        state = StateConfig.LOAD_DONE;
    }

    @Override
    protected void setData() {
        pLv.setAdapter(dcAdapter);
    }

    @Override
    protected void setListener() {
        pLv.setOnItemClickListener(this);
        pTrl.setOnRefreshListener(this);
        noNetTv.setOnClickListener(this);
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
                        dcBeanList.clear();
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
                DcBean d0 = new DcBean();
                d0.setTitle("优惠一");
                d0.setUrl("https://www.baidu.com/");
                DcBean d1 = new DcBean();
                d1.setTitle("优惠二");
                d1.setUrl("https://www.baidu.com/");
                DcBean d2 = new DcBean();
                d2.setTitle("优惠三");
                d2.setUrl("https://www.baidu.com/");
                tempList.add(d0);
                tempList.add(d1);
                tempList.add(d2);
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
                if (dcBeanList.size() == 0) {
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

    private void notifyData() {
        dcBeanList.addAll(tempList);
        dcAdapter.notifyDataSetChanged();
        tempList.clear();
        switch (state) {
            case StateConfig.LOAD_DONE:
                cPd.dismiss();
                if (dcBeanList.size() == 0) {
                    noDataLl.setVisibility(View.VISIBLE);
                    noNetLl.setVisibility(View.GONE);
                }
                break;
            case StateConfig.LOAD_REFRESH:
                break;
            case StateConfig.LOAD_LOAD:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.plv_frag_discount:
                Utils.toast(getActivity(), dcBeanList.get(position).getTitle());
                break;
            default:
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_no_net_refresh:
                noNetLl.setVisibility(View.GONE);
                loadNetData();
                break;
            default:
                break;
        }
    }
}
