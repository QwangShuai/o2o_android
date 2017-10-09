package accountdetail.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.DetailAdapter;
import bean.AccountDetailBean;
import bean.WithDrawBean;
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

public class AccountDetailActivity extends AppCompatActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private RelativeLayout returnRl;
    private LinearLayout menuLl;
    private View menuPopView;
    private PopupWindow menuPopWindow;
    private RelativeLayout menuAllRl, menuOutRl, menuInRl;
    private TextView menuContentTv;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<AccountDetailBean> list;
    private DetailAdapter adapter;
    private int state = StateConfig.LOAD_DONE;
    private OkHttpClient okHttpClient;

    private int id = 2, page = 1;
    private final String CATEGORY_ALL = "all";
    private final String CATEGORY_WITHDRAW = "withdraw";
    private final String CATEGORY_RECHARGE = "recharge";
    private String category = CATEGORY_ALL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_detail, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    private void initView() {
        initRootView();
        initPopWindowView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_detail_return);
        menuLl = (LinearLayout) rootView.findViewById(R.id.ll_detail_menu);
        menuContentTv = (TextView) rootView.findViewById(R.id.tv_detail_menu_content);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
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

    private void initData() {
        list = new ArrayList<>();
        adapter = new DetailAdapter(this, list);
        okHttpClient = new OkHttpClient();
    }

    private void setData() {
        plv.setAdapter(adapter);
    }

    private void setListener() {
        ptrl.setOnRefreshListener(this);
        returnRl.setOnClickListener(this);
        menuLl.setOnClickListener(this);
        menuAllRl.setOnClickListener(this);
        menuOutRl.setOnClickListener(this);
        menuInRl.setOnClickListener(this);
    }

    private void loadData() {
        AccountDetailBean d0 = new AccountDetailBean();
        d0.setTitle("提现");
        d0.setDes("-300.00");
        d0.setTime("2017-08-06");
        d0.setBalance("余额：100.00");
        AccountDetailBean d1 = new AccountDetailBean();
        d1.setTitle("支付");
        d1.setDes("-300.00");
        d1.setTime("2017-08-01");
        d1.setBalance("余额：100.00");
        AccountDetailBean d2 = new AccountDetailBean();
        d2.setTitle("充值");
        d2.setDes("+100.00");
        d2.setTime("2017-07-06");
        d2.setBalance("余额：200.00");
        AccountDetailBean d3 = new AccountDetailBean();
        d3.setTitle("收入");
        d3.setDes("+100.00");
        d3.setTime("2016-08-06");
        d3.setBalance("余额：300.00");
        list.add(d0);
        list.add(d1);
        list.add(d2);
        list.add(d3);
        adapter.notifyDataSetChanged();
        switch (state) {
            case StateConfig.LOAD_REFRESH:
                ptrl.hideHeadView();
                break;
            case StateConfig.LOAD_LOAD:
                ptrl.hideFootView();
                break;
            default:
                break;
        }
        loadAccountDetailData();
    }

    private void loadAccountDetailData() {
        String url = NetConfig.accountDetailUrl + "?u_id=" + id + "&page=" + page + "&category=" + category;
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Utils.log(AccountDetailActivity.this, "json=" + json);
                    if (category.equals("withdraw")) {
                        parseWithDrawJson(json);
                    }
                }
            }
        });
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    private void parseWithDrawJson(String json) {
        Utils.log(AccountDetailActivity.this, "withDraw=" + json);
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 1) {
                JSONObject objData = objBean.optJSONObject("data");
                if (objData != null) {
                    JSONArray arrWithDraw = objData.optJSONArray("withdraw_list");
                    if (arrWithDraw != null) {
                        for (int i = 0; i < arrWithDraw.length(); i++) {
                            JSONObject o = arrWithDraw.optJSONObject(i);
                            if (o != null) {
                                WithDrawBean w = new WithDrawBean();
                                w.setUwl_id(o.optString("uwl_id"));
                                w.setUwl_amount(o.optString("uwl_amount"));
                                w.setUwl_in_time(o.optString("uwl_in_time"));
                                w.setUwl_status(o.optString("uwl_status"));
                                w.setUwl_solut_time(o.optString("uwl_solut_time"));
                                w.setUwl_card(o.optString("uwl_card"));
                                w.setP_id(o.optString("p_id"));
                                Utils.log(AccountDetailActivity.this, "withDrawBean=" + w.toString());
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                    page = 1;
                    category = CATEGORY_ALL;
                    loadAccountDetailData();
                }
                break;
            case R.id.rl_popwindow_detail_menu_out:
                if (menuPopWindow.isShowing()) {
                    menuPopWindow.dismiss();
                    menuContentTv.setText("支出");
                    page = 1;
                    category = CATEGORY_WITHDRAW;
                    loadAccountDetailData();
                }
                break;
            case R.id.rl_popwindow_detail_menu_in:
                if (menuPopWindow.isShowing()) {
                    menuPopWindow.dismiss();
                    menuContentTv.setText("收入");
                    page = 1;
                    category = CATEGORY_RECHARGE;
                    loadAccountDetailData();
                }
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        list.clear();
        state = StateConfig.LOAD_REFRESH;
        loadData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        state = StateConfig.LOAD_LOAD;
        loadData();
    }
}
