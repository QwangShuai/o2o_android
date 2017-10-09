package wallet.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import accountdetail.view.AccountDetailActivity;
import config.NetConfig;
import config.StateConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pay.view.PayWayActivity;
import withdraw.view.WithDrawActivity;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, detailRl, rechargeRl, withDrawRl;
    private TextView overageTv;
    private String u_id, overage;
    private OkHttpClient okHttpClient;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case StateConfig.LOAD_NO_NET:
                        break;
                    case StateConfig.LOAD_DONE:
                        if (!TextUtils.isEmpty(overage))
                            overageTv.setText(overage);
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
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_wallet, null);
        setContentView(rootView);
        initView();
        initData();
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
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_return);
        detailRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_detail);
        rechargeRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_recharge);
        withDrawRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_withdraw);
        overageTv = (TextView) rootView.findViewById(R.id.tv_wallet_overage);
    }

    private void initData() {
        okHttpClient = new OkHttpClient();
        Intent intent = getIntent();
        if (intent != null) {
            u_id = intent.getStringExtra("u_id");
            if (TextUtils.isEmpty(u_id))
                u_id = "1";
        }
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        detailRl.setOnClickListener(this);
        rechargeRl.setOnClickListener(this);
        withDrawRl.setOnClickListener(this);
    }

    private void loadData() {
        if (!TextUtils.isEmpty(u_id)) {
            Request request = new Request.Builder().url(NetConfig.userFundUrl + u_id).get().build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        if (!TextUtils.isEmpty(result))
                            parseJson(result);
                    }
                }
            });
        }
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 1) {
                JSONObject objData = objBean.optJSONObject("data");
                if (objData != null) {
                    JSONObject o = objData.optJSONObject("data");
                    if (o != null) {
                        overage = o.optString("uef_overage");
                        handler.sendEmptyMessage(StateConfig.LOAD_DONE);
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
            case R.id.rl_wallet_return:
                finish();
                break;
            case R.id.rl_wallet_detail:
                startActivity(new Intent(this, AccountDetailActivity.class));
                break;
            case R.id.rl_wallet_recharge:
                startActivity(new Intent(this, PayWayActivity.class));
                break;
            case R.id.rl_wallet_withdraw:
                startActivity(new Intent(this, WithDrawActivity.class));
                break;
        }
    }
}
