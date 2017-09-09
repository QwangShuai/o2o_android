package activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.gjzg.R;

public class WalletActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, detailRl, rechargeRl, withDrawRl;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_wallet, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_return);
        detailRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_detail);
        rechargeRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_recharge);
        withDrawRl = (RelativeLayout) rootView.findViewById(R.id.rl_wallet_withdraw);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        detailRl.setOnClickListener(this);
        rechargeRl.setOnClickListener(this);
        withDrawRl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

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
                startActivity(new Intent(this, WithDrawActivity.class));
                break;
        }
    }
}
