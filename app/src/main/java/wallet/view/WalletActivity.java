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
import bean.WalletBean;
import config.NetConfig;
import config.StateConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pay.view.PayWayActivity;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;
import wallet.presenter.IWalletPresenter;
import wallet.presenter.WalletPresenter;
import withdraw.view.WithDrawActivity;

public class WalletActivity extends AppCompatActivity implements IWalletActivity, View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, detailRl, rechargeRl, withDrawRl;
    private TextView overageTv;
    private CProgressDialog cpd;
    private WalletBean walletBean;
    private IWalletPresenter walletPresenter;
    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        cpd.dismiss();
                        notifyData();
                        break;
                    case LOAD_FAILURE:
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
        if (handler != null) {
            handler.removeMessages(LOAD_SUCCESS);
            handler.removeMessages(LOAD_FAILURE);
            handler = null;
        }
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
        cpd = Utils.initProgressDialog(WalletActivity.this, cpd);
    }

    private void initData() {
        walletPresenter = new WalletPresenter(this);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        detailRl.setOnClickListener(this);
        rechargeRl.setOnClickListener(this);
        withDrawRl.setOnClickListener(this);
    }

    private void loadData() {
        cpd.show();
        walletPresenter.load(NetConfig.userFundUrl + "?u_id=" + UserUtils.readUserData(WalletActivity.this).getId());
    }

    private void notifyData() {
        overageTv.setText(walletBean.getOverage());
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

    @Override
    public void loadSuccess(String json) {
        walletBean = DataUtils.getWalletBean(json);
        handler.sendEmptyMessage(LOAD_SUCCESS);
    }

    @Override
    public void loadFailure(String failure) {
        handler.sendEmptyMessage(LOAD_FAILURE);
    }
}
