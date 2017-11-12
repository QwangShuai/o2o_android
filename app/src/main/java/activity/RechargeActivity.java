package activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjzg.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.RechargeAdapter;
import bean.PayWayBean;
import bean.WxDataBean;
import config.AppConfig;
import config.NetConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl, toPayRl;
    private EditText moneyEt;
    private ListView listView;
    private RechargeAdapter rechargeAdapter;
    private List<PayWayBean> payWayBeanList = new ArrayList<>();
    private CProgressDialog cProgressDialog;
    private WxDataBean wxDataBean;

    private IWXAPI wxapi;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        cProgressDialog.dismiss();
                        rechargeAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        cProgressDialog.dismiss();
                        wx();
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(RechargeActivity.this).inflate(R.layout.activity_recharge, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeMessages(1);
            handler.removeMessages(2);
            handler = null;
        }
    }


    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_recharge_return);
        moneyEt = (EditText) rootView.findViewById(R.id.et_recharge_money);
        moneyEt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        listView = (ListView) rootView.findViewById(R.id.lv_recharge);
        toPayRl = (RelativeLayout) rootView.findViewById(R.id.rl_recharge_to_pay);
        cProgressDialog = Utils.initProgressDialog(RechargeActivity.this, cProgressDialog);
    }

    private void initData() {
        registerWx();
        rechargeAdapter = new RechargeAdapter(RechargeActivity.this, payWayBeanList);
    }

    private void setData() {
        listView.setAdapter(rechargeAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        moneyEt.addTextChangedListener(moneyTw);
        toPayRl.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private void registerWx() {
        Utils.log(RechargeActivity.this, "注册微信支付");
        wxapi = WXAPIFactory.createWXAPI(RechargeActivity.this, null);
        wxapi.registerApp(AppConfig.APP_ID);
    }

    private void loadData() {
        cProgressDialog.show();
        Utils.log(RechargeActivity.this, "加载数据");
        String url = NetConfig.payWayUrl + "?p_type=0";
        Request request = new Request.Builder().url(url).get().build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Utils.log(RechargeActivity.this, "json\n" + json);
                    payWayBeanList.clear();
                    if (DataUtils.getPayWayBeanList(json) != null) {
                        payWayBeanList.addAll(DataUtils.getPayWayBeanList(json));
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });
    }

    private void toPay() {
        if (TextUtils.isEmpty(moneyEt.getText().toString()) || moneyEt.getText().toString().equals("")) {
            Utils.log(RechargeActivity.this, "请输入金额");
        } else {
            Utils.log(RechargeActivity.this, "去支付");
            int p = 0;
            for (int i = 0; i < payWayBeanList.size(); i++) {
                if (payWayBeanList.get(i).isCheck()) {
                    p = i;
                }
            }
            PayWayBean payWayBean = payWayBeanList.get(p);
            if (payWayBean != null) {
                String p_id = payWayBean.getP_id();
                if (p_id == null || p_id.equals("null") || TextUtils.isEmpty(p_id)) {
                } else {
                    if (p_id.equals("7")) {
                        //?u_id=12&p_id=1&url_amount=1
                        Utils.log(RechargeActivity.this, "微信支付");
                        String url = NetConfig.toPayUrl +
                                "?u_id=" + UserUtils.readUserData(RechargeActivity.this).getId() +
                                "&p_id=" + p_id +
                                "&url_amount=" + moneyEt.getText().toString();
                        pay(url);
                    }
                }
            }
        }
    }

    private void pay(String url) {
        cProgressDialog.show();
        Utils.log(RechargeActivity.this, "url\n" + url);
        Request request = new Request.Builder().url(url).get().build();
        OkHttpClient okhttpClient = new OkHttpClient();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Utils.log(RechargeActivity.this, "json\n" + json);
                    String testJson = test(json);
                    if (testJson == null || testJson.equals("null") || TextUtils.isEmpty(testJson)) {
                    } else {
                        Utils.log(RechargeActivity.this, "testJson\n" + testJson);
                        wxDataBean = DataUtils.getWxDataBean(testJson);
                        if (wxDataBean != null) {
                            Utils.log(RechargeActivity.this, "wxDataBean\n" + wxDataBean.toString());
                            handler.sendEmptyMessage(2);
                        }
                    }
                }
            }
        });
    }

    private String test(String json) {
        if (json == null || json.equals("null") || TextUtils.isEmpty(json)) {
        } else {
            int a = json.indexOf("{");
            int b = json.lastIndexOf("}");
            json = json.substring(a, b + 1);
            return json;
        }
        return null;
    }

    private void wx() {
        Utils.log(RechargeActivity.this, "wx");
        Utils.log(RechargeActivity.this, "wxDataBean\n" + wxDataBean);
        PayReq payReq = new PayReq();
        payReq.appId = wxDataBean.getAppid();
        payReq.partnerId = wxDataBean.getPartnerid();
        payReq.prepayId = wxDataBean.getPrepayid();
        payReq.packageValue = wxDataBean.getPackageValue();
        payReq.nonceStr = wxDataBean.getNoncestr();
        payReq.timeStamp = wxDataBean.getTimestamp();
        payReq.signType = wxDataBean.getSignType();
        String sign = DataUtils.signNum(wxDataBean);
        if (sign == null || sign.equals("null") || TextUtils.isEmpty(sign)) {
        } else {
            payReq.sign = sign;
            wxapi.sendReq(payReq);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_recharge_return:
                finish();
                break;
            case R.id.rl_recharge_to_pay:
                toPay();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (int i = 0; i < payWayBeanList.size(); i++) {
            if (position == i) {
                payWayBeanList.get(i).setCheck(true);
            } else {
                payWayBeanList.get(i).setCheck(false);
            }
        }
        rechargeAdapter.notifyDataSetChanged();
    }

    TextWatcher moneyTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //优惠券必须要达到的金额Integer.parseInt(fullPrice);
            if (s.toString().contains(".")) {
                if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                    s = s.toString().subSequence(0,
                            s.toString().indexOf(".") + 3);
                    moneyEt.setText(s);
                    moneyEt.setSelection(s.length());
                }
            }
            if (s.toString().trim().substring(0).equals(".")) {
                s = "0" + s;
                moneyEt.setText(s);
                moneyEt.setSelection(2);
            }
            if (s.toString().startsWith("0")
                    && s.toString().trim().length() > 1) {
                if (!s.toString().substring(1, 2).equals(".")) {
                    moneyEt.setText(s.subSequence(0, 1));
                    moneyEt.setSelection(1);
                    return;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
