package pay.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.PayWayAdapter;
import bean.PayWayBean;
import config.NetConfig;
import config.StateConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;

public class PayWayActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private EditText moneyEt;
    private TextView toPayTv;
    private ListView lv;
    private List<PayWayBean> list;
    private PayWayAdapter adapter;
    private OkHttpClient okHttpClient;
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
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_pay_way, null);
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
        handler.removeMessages(StateConfig.LOAD_NO_NET);
        handler.removeMessages(StateConfig.LOAD_DONE);
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_recharge_return);
        moneyEt = (EditText) rootView.findViewById(R.id.et_recharge_money);
        toPayTv = (TextView) rootView.findViewById(R.id.tv_recharge_pay);
        lv = (ListView) rootView.findViewById(R.id.lv_pay_way);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new PayWayAdapter(this, list);
        okHttpClient = new OkHttpClient();
    }

    private void setData() {
        lv.setAdapter(adapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        toPayTv.setOnClickListener(this);
        lv.setOnItemClickListener(this);
    }

    private void loadData() {
        Request request = new Request.Builder().url(NetConfig.payWayUrl).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    parsePayWayJson(result);
                }
            }
        });
    }

    private void parsePayWayJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                JSONArray arrData = objBean.optJSONArray("data");
                if (arrData != null) {
                    for (int i = 0; i < arrData.length(); i++) {
                        JSONObject o = arrData.optJSONObject(i);
                        if (o != null) {
                            PayWayBean p = new PayWayBean();
                            p.setP_id(o.optString("p_id"));
                            p.setP_type(o.optString("p_type"));
                            p.setP_name(o.optString("p_name"));
                            p.setP_info(o.optString("p_info"));
                            p.setP_status(o.optString("p_status"));
                            p.setP_author(o.optString("p_author"));
                            p.setP_last_editor(o.optString("p_last_editor"));
                            p.setP_last_edit_time(o.optString("p_last_edit_time"));
                            p.setP_default(o.optString("p_default"));
                            p.setCheck(false);
                            list.add(p);
                        }
                    }
                }
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyNet() {

    }

    private void notifyData() {
        adapter.notifyDataSetChanged();
    }

    private void toPay() {
        String money = moneyEt.getText().toString();
        if (TextUtils.isEmpty(money)) {
            Utils.toast(this, "请输入金额");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_recharge_return:
                finish();
                break;
            case R.id.tv_recharge_pay:
                toPay();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean b = list.get(position).isCheck();
        if (!b) {
            for (int i = 0; i < list.size(); i++) {
                if (i == position) {
                    list.get(i).setCheck(!b);
                } else {
                    list.get(i).setCheck(false);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}
