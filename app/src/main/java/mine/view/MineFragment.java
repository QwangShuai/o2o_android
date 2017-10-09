package mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import config.IntentConfig;
import config.VarConfig;
import invite.view.InviteActivity;
import leftright.view.LeftRightActivity;
import login.view.LoginActivity;
import passwordset.view.SetPwdActivity;
import redpacket.view.RedPacketActivity;
import set.view.SetActivity;
import utils.Utils;
import voucher.view.VoucherActivity;
import wallet.view.WalletActivity;

public class MineFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout collectRl, evaluateRl, walletRl, msgRl, redBagRl, voucherRl, pointRl;
    private LinearLayout setCashPwdLl, inviteFriendLl, serviceClauseLl, settingLl, cusSevLl;
    private TextView loginTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_mine, null);
        initView();
        setListener();
        return rootView;
    }

    private void initView() {
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

    private void setListener() {
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
                startActivity(new Intent(getActivity(), RedPacketActivity.class));
                break;
            case R.id.rl_mine_voucher:
                startActivity(new Intent(getActivity(), VoucherActivity.class));
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
