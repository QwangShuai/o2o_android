package fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import activity.InviteActivity;
import activity.LeftRightActivity;
import activity.LoginActivity;
import activity.SetActivity;
import activity.SetPwdActivity;
import activity.WalletActivity;
import config.IntentConfig;
import config.VarConfig;
import utils.Utils;

//我的
public class MeFragment extends CommonFragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout collectRl, evaluateRl, walletRl, msgRl, redBagRl, voucherRl, pointRl;
    private LinearLayout setCashPwdLl, inviteFriendLl, serviceClauseLl, settingLl, cusSevLl;
    private TextView loginTv;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_mine, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        collectRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_collect);
        evaluateRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_evaluate);
        walletRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_wallet);
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_msg);
        redBagRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_red_bag);
        voucherRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_voucher);
        pointRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_point);
        setCashPwdLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_set_cash_pwd);
        inviteFriendLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_invite_friend);
        serviceClauseLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_service_clause);
        settingLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_setting);
        cusSevLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_custom_service);
        loginTv = (TextView) rootView.findViewById(R.id.tv_mine_login);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        collectRl.setOnClickListener(this);
        evaluateRl.setOnClickListener(this);
        walletRl.setOnClickListener(this);
        msgRl.setOnClickListener(this);
        redBagRl.setOnClickListener(this);
        voucherRl.setOnClickListener(this);
        pointRl.setOnClickListener(this);
        setCashPwdLl.setOnClickListener(this);
        inviteFriendLl.setOnClickListener(this);
        serviceClauseLl.setOnClickListener(this);
        settingLl.setOnClickListener(this);
        cusSevLl.setOnClickListener(this);
        loginTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_mine_collect:
                Intent collectIntent = new Intent(getActivity(), LeftRightActivity.class);
                collectIntent.putExtra(IntentConfig.intentName, IntentConfig.COLLECT);
                startActivity(collectIntent);
                break;
            case R.id.rl_mine_evaluate:
                Intent evaluateIntent = new Intent(getActivity(), LeftRightActivity.class);
                evaluateIntent.putExtra(IntentConfig.intentName, IntentConfig.EVALUATE);
                startActivity(evaluateIntent);
                break;
            case R.id.rl_mine_wallet:
                Intent walletIntent = new Intent(getActivity(), WalletActivity.class);
                startActivity(walletIntent);
                break;
            case R.id.rl_mine_msg:
                Intent messageIntent = new Intent(getActivity(), LeftRightActivity.class);
                messageIntent.putExtra(IntentConfig.intentName, IntentConfig.MESSAGE);
                startActivity(messageIntent);
                break;
            case R.id.rl_mine_red_bag:
                Utils.toast(getActivity(), VarConfig.notyetTip);
                break;
            case R.id.rl_mine_voucher:
                Utils.toast(getActivity(), VarConfig.notyetTip);
                break;
            case R.id.rl_mine_point:
                Utils.toast(getActivity(), VarConfig.notyetTip);
                break;
            case R.id.ll_mine_set_cash_pwd:
                startActivity(new Intent(getActivity(), SetPwdActivity.class));
                break;
            case R.id.ll_mine_invite_friend:
                startActivity(new Intent(getActivity(), InviteActivity.class));
                break;
            case R.id.ll_mine_service_clause:
                Utils.toast(getActivity(), VarConfig.notyetTip);
//                Intent serviceClauseIntent = new Intent(getActivity(), SevClsActivity.class);
//                startActivity(serviceClauseIntent);
                break;
            case R.id.ll_mine_setting:
                Intent settingIntent = new Intent(getActivity(), SetActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.ll_mine_custom_service:
                Utils.toast(getActivity(), VarConfig.notyetTip);
                break;
            case R.id.tv_mine_login:
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }

}
