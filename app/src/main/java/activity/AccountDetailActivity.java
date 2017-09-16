package activity;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.DetailAdapter;
import bean.DetailBean;
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

public class AccountDetailActivity extends CommonActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private RelativeLayout returnRl;
    private LinearLayout menuLl;
    private View menuPopView;
    private PopupWindow menuPopWindow;
    private RelativeLayout menuAllRl, menuOutRl, menuInRl;
    private TextView menuContentTv;
    private CProgressDialog cpd;
    private FrameLayout fl;
    private View emptyNetView, emptyDataView;
    private TextView emptyNetTv;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<DetailBean> list;
    private DetailAdapter adapter;
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
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_detail, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
        initPopWindowView();
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_detail_return);
        menuLl = (LinearLayout) rootView.findViewById(R.id.ll_detail_menu);
        menuContentTv = (TextView) rootView.findViewById(R.id.tv_detail_menu_content);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initDialogView() {
        cpd = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    private void initPopWindowView() {
        menuPopView = LayoutInflater.from(this).inflate(R.layout.popwindow_detail_menu, null);
        menuAllRl = (RelativeLayout) menuPopView.findViewById(R.id.rl_popwindow_detail_menu_all);
        menuOutRl = (RelativeLayout) menuPopView.findViewById(R.id.rl_popwindow_detail_menu_out);
        menuInRl = (RelativeLayout) menuPopView.findViewById(R.id.rl_popwindow_detail_menu_in);
        menuPopWindow = new PopupWindow(menuPopView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuPopWindow.setAnimationStyle(R.style.popwin_anim_style);
        menuPopWindow.setFocusable(true);
        menuPopWindow.setTouchable(true);
        menuPopWindow.setOutsideTouchable(true);
        menuPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
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
        adapter = new DetailAdapter(this, list);
        okHttpClient = new OkHttpClient();
    }

    @Override
    protected void setData() {
        plv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        ptrl.setOnRefreshListener(this);
        returnRl.setOnClickListener(this);
        menuLl.setOnClickListener(this);
        menuAllRl.setOnClickListener(this);
        menuOutRl.setOnClickListener(this);
        menuInRl.setOnClickListener(this);
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
                    String result = response.body().string();
                    if (state == StateConfig.LOAD_REFRESH) {
                        list.clear();
                    }
                    parseJson(result);
                }
            }
        });
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                DetailBean d0 = new DetailBean();
                d0.setTitle("提现");
                d0.setDes("-300.00");
                d0.setTime("2017-08-06");
                d0.setBalance("余额：100.00");
                DetailBean d1 = new DetailBean();
                d1.setTitle("支付");
                d1.setDes("-300.00");
                d1.setTime("2017-08-01");
                d1.setBalance("余额：100.00");
                DetailBean d2 = new DetailBean();
                d2.setTitle("充值");
                d2.setDes("+100.00");
                d2.setTime("2017-07-06");
                d2.setBalance("余额：200.00");
                DetailBean d3 = new DetailBean();
                d3.setTitle("收入");
                d3.setDes("+100.00");
                d3.setTime("2016-08-06");
                d3.setBalance("余额：300.00");
                list.add(d0);
                list.add(d1);
                list.add(d2);
                list.add(d3);
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
                Utils.toast(this, StateConfig.loadNonet);
                break;
            case StateConfig.LOAD_LOAD:
                ptrl.hideFootView();
                Utils.toast(this, StateConfig.loadNonet);
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
                    emptyDataView.setVisibility(View.GONE);
                    emptyNetView.setVisibility(View.GONE);
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


    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_detail_return:
                finish();
                break;
            case R.id.ll_detail_menu:
                if (!menuPopWindow.isShowing()) {
                    menuPopWindow.showAsDropDown(menuLl, -20, -10);
                    backgroundAlpha(0.8f);
                }
                break;
            case R.id.rl_popwindow_detail_menu_all:
                if (menuPopWindow.isShowing()) {
                    menuPopWindow.dismiss();
                    menuContentTv.setText("全部");
                }
                break;
            case R.id.rl_popwindow_detail_menu_out:
                if (menuPopWindow.isShowing()) {
                    menuPopWindow.dismiss();
                    menuContentTv.setText("支出");
                }
                break;
            case R.id.rl_popwindow_detail_menu_in:
                if (menuPopWindow.isShowing()) {
                    menuPopWindow.dismiss();
                    menuContentTv.setText("收入");
                }
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
