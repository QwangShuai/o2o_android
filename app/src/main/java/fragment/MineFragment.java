package fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
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

import activity.InviteActivity;
import activity.LoginActivity;
import activity.MsgActivity;
import activity.SevClsActivity;
import activity.SetActivity;
import activity.WalletActivity;
import config.IntentConfig;
import config.StateConfig;
import utils.Utils;

/**
 * 创建日期：2017/7/28 on 13:54
 * 作者:孙明明
 * 描述:我的
 */

public class MineFragment extends Fragment implements View.OnClickListener {

    //根视图
    private View rootView;
    //我的收藏视图
    private RelativeLayout collectRl;
    //我的评价视图
    private RelativeLayout evaluateRl;
    //我的钱包视图
    private RelativeLayout walletRl;
    //我的消息视图
    private RelativeLayout msgRl;
    //红包视图
    private RelativeLayout redBagRl;
    //代金券视图
    private RelativeLayout voucherRl;
    //积分视图
    private RelativeLayout pointRl;
    //邀请好友视图
    private LinearLayout inviteFriendLl;
    //服务条款视图
    private LinearLayout serviceClauseLl;
    //设置视图
    private LinearLayout settingLl;
    //客服视图
    private LinearLayout cusSevLl;

    private TextView loginTv, userNameTv;
    private ImageView headShotIv, sexIv;

    //客服电话对话框
    private AlertDialog cusSevDialog;
    private View cusSevDialogView;
    private TextView cusSevNumberTv;
    private TextView cusSevCancelTv;
    private TextView cusSevSureTv;

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
        initDialogView();
    }

    private void initRootView() {
        //初始化我的收藏视图
        collectRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_collect);
        //初始化我的评价视图
        evaluateRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_evaluate);
        //初始化我的钱包视图
        walletRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_wallet);
        //初始化我的消息视图
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_msg);
        //初始化钱包视图
        redBagRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_red_bag);
        //初始化代金券视图
        voucherRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_voucher);
        //初始化积分视图
        pointRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_point);
        //初始化邀请好友视图
        inviteFriendLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_invite_friend);
        //初始化服务条款视图
        serviceClauseLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_service_clause);
        //初始化设置视图
        settingLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_setting);
        //初始化客服视图
        cusSevLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_custom_service);


        loginTv = (TextView) rootView.findViewById(R.id.tv_mine_login);
        userNameTv = (TextView) rootView.findViewById(R.id.tv_mine_username);
        headShotIv = (ImageView) rootView.findViewById(R.id.iv_mine_head_shot);
        sexIv = (ImageView) rootView.findViewById(R.id.iv_mine_sex);
    }

    private void initDialogView() {
        //初始化客服对话框视图
        cusSevDialogView = View.inflate(getActivity(), R.layout.dialog_cus_sev, null);
        cusSevNumberTv = (TextView) cusSevDialogView.findViewById(R.id.tv_dialog_cus_sev_number);
        cusSevNumberTv.setText(StateConfig.cusSevNumber);
        cusSevCancelTv = (TextView) cusSevDialogView.findViewById(R.id.tv_dialog_cus_sev_cancel);
        cusSevSureTv = (TextView) cusSevDialogView.findViewById(R.id.tv_dialog_cus_sev_sure);
        cusSevCancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cusSevDialog.dismiss();
            }
        });
        cusSevSureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cusSevDialog.dismiss();
                dial(cusSevNumberTv.getText().toString());
            }
        });
        AlertDialog.Builder cusSevDialogBuilder = new AlertDialog.Builder(getActivity());
        cusSevDialogBuilder.setView(cusSevDialogView);
        cusSevDialog = cusSevDialogBuilder.create();
        cusSevDialog.setCanceledOnTouchOutside(false);
    }

    private void setListener() {
        //我的收藏视图监听
        collectRl.setOnClickListener(this);
        //我的评价视图监听
        evaluateRl.setOnClickListener(this);
        //我的钱包视图监听
        walletRl.setOnClickListener(this);
        //我的消息视图监听
        msgRl.setOnClickListener(this);
        //红包视图监听
        redBagRl.setOnClickListener(this);
        //代金券视图监听
        voucherRl.setOnClickListener(this);
        //积分视图监听
        pointRl.setOnClickListener(this);
        //邀请好友视图监听
        inviteFriendLl.setOnClickListener(this);
        //服务条款视图监听
        serviceClauseLl.setOnClickListener(this);
        //设置视图监听
        settingLl.setOnClickListener(this);
        //客服视图监听
        cusSevLl.setOnClickListener(this);

        loginTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //我的收藏视图点击事件
            case R.id.rl_mine_collect:
                Intent collectIntent = new Intent(getActivity(), MsgActivity.class);
                collectIntent.putExtra(IntentConfig.intentName, IntentConfig.COLLECT);
                startActivity(collectIntent);
                break;
            //我的评价视图点击事件
            case R.id.rl_mine_evaluate:
                Intent evaluateIntent = new Intent(getActivity(), MsgActivity.class);
                evaluateIntent.putExtra(IntentConfig.intentName, IntentConfig.EVALUATE);
                startActivity(evaluateIntent);
                break;
            //我的钱包视图点击事件
            case R.id.rl_mine_wallet:
                Intent walletIntent = new Intent(getActivity(), WalletActivity.class);
                startActivity(walletIntent);
                break;
            //我的消息视图点击事件
            case R.id.rl_mine_msg:
                Intent messageIntent = new Intent(getActivity(), MsgActivity.class);
                messageIntent.putExtra(IntentConfig.intentName, IntentConfig.MESSAGE);
                startActivity(messageIntent);
                break;
            //公报视图点击事件
            case R.id.rl_mine_red_bag:
                Utils.toast(getActivity(), "红包");
                break;
            //代金券视图点击事件
            case R.id.rl_mine_voucher:
                Utils.toast(getActivity(), "代金券");
                break;
            //积分视图点击事件
            case R.id.rl_mine_point:
                Utils.toast(getActivity(), "积分");
                break;
            //邀请好友视图点击事件
            case R.id.ll_mine_invite_friend:
                startActivity(new Intent(getActivity(), InviteActivity.class));
                break;
            //服务条款视图点击事件
            case R.id.ll_mine_service_clause:
                Intent serviceClauseIntent = new Intent(getActivity(), SevClsActivity.class);
                startActivity(serviceClauseIntent);
                break;
            //设置视图点击事件
            case R.id.ll_mine_setting:
                Intent settingIntent = new Intent(getActivity(), SetActivity.class);
                startActivity(settingIntent);
                break;
            //客服视图点击事件
            case R.id.ll_mine_custom_service:
                cusSevDialog.show();
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

    private void dial(String num) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + num));
        startActivity(i);
    }
}
