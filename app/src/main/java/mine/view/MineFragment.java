package mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import collect.view.CollectActivity;
import config.IntentConfig;
import leftright.view.LeftRightActivity;
import login.bean.UserBean;
import login.view.LoginActivity;
import mine.presenter.IMinePresenter;
import mine.presenter.MinePresenter;
import myevaluate.view.MyEvaluateActivity;
import passwordset.view.SetPwdActivity;
import redpacket.view.RedPacketActivity;
import set.view.SetActivity;
import usermanage.bean.UserInfoBean;
import utils.UserUtils;
import voucher.view.VoucherActivity;
import wallet.view.WalletActivity;

public class MineFragment extends Fragment implements IMineFragment, View.OnClickListener {

    private View rootView;
    private RelativeLayout collectRl, evaluateRl, walletRl, msgRl, redBagRl, voucherRl;
    private LinearLayout setCashPwdLl, settingLl;
    private TextView loginTv;
    private RelativeLayout loginRightNowRl, loginDoneRl;
    private TextView loginNameTv;
    private ImageView loginIconIv, loginSexIv, loginOnlineIv;
    private IMinePresenter minePresenter;
    private final int REFRESH_DONE = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case REFRESH_DONE:
                        notifyUserData();
                        break;
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mine, null);
        initView();
        initData();
        setListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserUtils.isUserLogin(getActivity())) {
            minePresenter.load(UserUtils.readUserData(getActivity()).getId());
        } else {
            loginRightNowRl.setVisibility(View.VISIBLE);
            loginDoneRl.setVisibility(View.GONE);
            loginIconIv.setImageResource(R.mipmap.person_face_default);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeMessages(REFRESH_DONE);
            handler = null;
        }
        if (minePresenter != null) {
            minePresenter.destroy();
            minePresenter = null;
        }
    }

    private void initView() {
        initRootView();
    }

    private void initData() {
        minePresenter = new MinePresenter(this);
    }

    private void initRootView() {
        collectRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_collect);
        evaluateRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_evaluate);
        walletRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_wallet);
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_msg);
        redBagRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_red_bag);
        voucherRl = (RelativeLayout) rootView.findViewById(R.id.rl_mine_voucher);
        setCashPwdLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_set_cash_pwd);
        settingLl = (LinearLayout) rootView.findViewById(R.id.ll_mine_setting);
        loginTv = (TextView) rootView.findViewById(R.id.tv_mine_login);
        loginRightNowRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_me_login_right_now);
        loginDoneRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_me_login_done);
        loginIconIv = (ImageView) rootView.findViewById(R.id.iv_frag_me_login_icon);
        loginNameTv = (TextView) rootView.findViewById(R.id.tv_frag_me_login_name);
        loginSexIv = (ImageView) rootView.findViewById(R.id.iv_frag_me_login_sex);
        loginOnlineIv = (ImageView) rootView.findViewById(R.id.iv_frag_me_login_online);
    }

    private void setListener() {
        collectRl.setOnClickListener(this);
        evaluateRl.setOnClickListener(this);
        walletRl.setOnClickListener(this);
        msgRl.setOnClickListener(this);
        redBagRl.setOnClickListener(this);
        voucherRl.setOnClickListener(this);
        setCashPwdLl.setOnClickListener(this);
        settingLl.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        loginOnlineIv.setOnClickListener(this);
    }

    private void notifyUserData() {
        UserBean userBean = UserUtils.readUserData(getActivity());
        loginRightNowRl.setVisibility(View.GONE);
        loginDoneRl.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(userBean.getIcon())) {
            Picasso.with(getActivity()).load(userBean.getIcon()).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(loginIconIv);
        }
        loginNameTv.setText(userBean.getName());
        String sex = userBean.getSex();
        if (!TextUtils.isEmpty(sex)) {
            if (sex.equals("-1")) {
                loginSexIv.setVisibility(View.GONE);
            } else if (sex.equals("0")) {
                loginSexIv.setVisibility(View.VISIBLE);
                loginSexIv.setImageResource(R.mipmap.female);
            } else if (sex.equals("1")) {
                loginSexIv.setVisibility(View.VISIBLE);
                loginSexIv.setImageResource(R.mipmap.male);
            }
        }
        String online = userBean.getOnline();
        if (!TextUtils.isEmpty(online)) {
            if (online.equals("-1")) {
                loginOnlineIv.setImageResource(R.mipmap.user_off_line);
            } else if (online.equals("0")) {
                loginOnlineIv.setImageResource(R.mipmap.user_off_line);
            } else if (online.equals("1")) {
                loginOnlineIv.setImageResource(R.mipmap.user_on_line);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (UserUtils.isUserLogin(getActivity())) {
            switch (v.getId()) {
                case R.id.rl_mine_collect:
                    startActivity(new Intent(getActivity(), CollectActivity.class));
                    break;
                case R.id.rl_mine_evaluate:
                    startActivity(new Intent(getActivity(), MyEvaluateActivity.class));
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
                case R.id.ll_mine_set_cash_pwd:
                    startActivity(new Intent(getActivity(), SetPwdActivity.class));
                    break;
                case R.id.ll_mine_setting:
                    Intent settingIntent = new Intent(getActivity(), SetActivity.class);
                    startActivity(settingIntent);
                    break;
                case R.id.tv_mine_login:
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(loginIntent);
                    break;
                case R.id.iv_frag_me_login_online:
                    String online = UserUtils.readUserData(getActivity()).getOnline();
                    if (online.equals("-1")) {
                        minePresenter.postOnline(UserUtils.readUserData(getActivity()).getId(), "1");
                    } else if (online.equals("1")) {
                        minePresenter.postOnline(UserUtils.readUserData(getActivity()).getId(), "-1");
                    }
                    break;
            }
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void showUserInfoSuccess(UserInfoBean userInfoBean) {
        UserBean userBean = UserUtils.readUserData(getActivity());
        userBean.setId(userInfoBean.getU_id());
        userBean.setName(userInfoBean.getU_true_name());
        userBean.setIcon(userInfoBean.getU_img());
        userBean.setIdcard(userInfoBean.getU_idcard());
        userBean.setOnline(userInfoBean.getU_online());
        userBean.setSex(userInfoBean.getU_sex());
        userBean.setMobile(userInfoBean.getU_mobile());
        UserUtils.saveUserData(getActivity(), userBean);
        Log.e("MineFragment", "userBean=" + userBean.toString());
        handler.sendEmptyMessage(REFRESH_DONE);
    }

    @Override
    public void showUserInfoFailure(String failure) {
        handler.sendEmptyMessage(REFRESH_DONE);
    }

    @Override
    public void showPostOnlineSuccess(String success) {
        minePresenter.load(UserUtils.readUserData(getActivity()).getId());
    }

    @Override
    public void showPostOnlineFailure(String failure) {
        Log.e("showPostOnlineFailure", failure);
        handler.sendEmptyMessage(REFRESH_DONE);
    }
}
