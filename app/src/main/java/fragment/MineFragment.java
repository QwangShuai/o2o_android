package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import activity.LoginActivity;
import activity.ServiceClauseActivity;
import activity.SettingActivity;
import utils.Utils;

/**
 * 创建日期：2017/7/28 on 13:54
 * 作者:孙明明
 * 描述:我的
 */

public class MineFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout collectRl, evaluateRl, walletRl, msgRl;
    private RelativeLayout redBagRl, voucherRl, pointRl;
    private LinearLayout inviteFriendLl, serviceClauseLl, settingLl, customServiceLl;
    private TextView loginTv, userNameTv;
    private ImageView headShotIv, sexIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, null);
        initView();
        setListener();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        collectRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_collect);
        evaluateRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_evaluate);
        walletRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_wallet);
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_msg);
        redBagRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_red_bag);
        voucherRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_voucher);
        pointRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_point);
        inviteFriendLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_invite_friend);
        serviceClauseLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_service_clause);
        settingLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_setting);
        customServiceLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_custom_service);
        loginTv = (TextView) rootView.findViewById(R.id.tv_mine_login);
        userNameTv = (TextView) rootView.findViewById(R.id.tv_mine_username);
        headShotIv = (ImageView) rootView.findViewById(R.id.iv_mine_head_shot);
        sexIv = (ImageView) rootView.findViewById(R.id.iv_mine_sex);
    }

    private void setListener() {
        collectRl.setOnClickListener(this);
        evaluateRl.setOnClickListener(this);
        walletRl.setOnClickListener(this);
        msgRl.setOnClickListener(this);
        redBagRl.setOnClickListener(this);
        voucherRl.setOnClickListener(this);
        pointRl.setOnClickListener(this);
        inviteFriendLl.setOnClickListener(this);
        serviceClauseLl.setOnClickListener(this);
        settingLl.setOnClickListener(this);
        customServiceLl.setOnClickListener(this);
        loginTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_mine_collect:
                Utils.toast(getActivity(), "收藏");
                break;
            case R.id.rl_mine_evaluate:
                Utils.toast(getActivity(), "评价");
                break;
            case R.id.rl_mine_wallet:
                Utils.toast(getActivity(), "钱包");
                break;
            case R.id.rl_mine_msg:
                Utils.toast(getActivity(), "消息");
                break;
            case R.id.rl_mine_red_bag:
                Utils.toast(getActivity(), "红包");
                break;
            case R.id.rl_mine_voucher:
                Utils.toast(getActivity(), "点券");
                break;
            case R.id.rl_mine_point:
                Utils.toast(getActivity(), "积分");
                break;
            case R.id.ll_mine_invite_friend:
                Utils.toast(getActivity(), "邀请好友");
                break;
            case R.id.ll_mine_service_clause:
                Intent serviceClauseIntent = new Intent(getActivity(), ServiceClauseActivity.class);
                startActivity(serviceClauseIntent);
                break;
            case R.id.ll_mine_setting:
                Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.ll_mine_custom_service:
                Utils.toast(getActivity(), "客服");
                break;
            case R.id.tv_mine_login:
//                loginTv.setVisibility(View.INVISIBLE);
//                headShotIv.setImageResource(R.mipmap.redbag);
//                userNameTv.setVisibility(View.VISIBLE);
//                sexIv.setVisibility(View.VISIBLE);
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }
}
