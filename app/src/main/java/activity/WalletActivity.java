package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.gjzg.R;

public class WalletActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, detailRl, rechargeRl, withDrawRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_wallet, null);
        setContentView(rootView);
        initView();
        setListener();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_return);
        detailRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_detail);
        rechargeRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_recharge);
        withDrawRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_withdraw);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        detailRl.setOnClickListener(this);
        rechargeRl.setOnClickListener(this);
        withDrawRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_wallet_return:
                finish();
                break;
            case R.id.rl_wallet_detail:
                startActivity(new Intent(this, DetailActivity.class));
                break;
            case R.id.rl_wallet_recharge:
                startActivity(new Intent(this, RechargeActivity.class));
                break;
            case R.id.rl_wallet_withdraw:
                startActivity(new Intent(this,WithDrawActivity.class));
                break;
        }
    }
}
