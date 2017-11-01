package manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gjzg.R;

import employermanage.view.EmployerManageActivity;
import login.view.LoginActivity;
import usermanage.view.UserManageActivity;
import utils.UserUtils;
import utils.Utils;
import wallet.view.WalletActivity;
import workermanage.view.WorkerManageActivity;
import publishjob.view.PublishJobActivity;

public class ManageFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout rechargeRl, publishRl, workerRl, employerRl, userRl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_manage, null);
        initView();
        setListener();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rechargeRl = (RelativeLayout) rootView.findViewById(R.id.rl_manage_recharge);
        publishRl = (RelativeLayout) rootView.findViewById(R.id.rl_manage_publish);
        workerRl = (RelativeLayout) rootView.findViewById(R.id.rl_manage_worker);
        employerRl = (RelativeLayout) rootView.findViewById(R.id.rl_manage_employer);
        userRl = (RelativeLayout) rootView.findViewById(R.id.rl_manage_user);
    }

    private void setListener() {
        rechargeRl.setOnClickListener(this);
        publishRl.setOnClickListener(this);
        workerRl.setOnClickListener(this);
        employerRl.setOnClickListener(this);
        userRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (UserUtils.isUserLogin(getActivity())) {
            switch (v.getId()) {
                case R.id.rl_manage_recharge:
                    startActivity(new Intent(getActivity(), WalletActivity.class));
                    break;
                case R.id.rl_manage_publish:
                    startActivity(new Intent(getActivity(), PublishJobActivity.class));
                    break;
                case R.id.rl_manage_worker:
                    startActivity(new Intent(getActivity(), WorkerManageActivity.class));
                    break;
                case R.id.rl_manage_employer:
                    startActivity(new Intent(getActivity(), EmployerManageActivity.class));
                    break;
                case R.id.rl_manage_user:
                    startActivity(new Intent(getActivity(), UserManageActivity.class));
                    break;
            }
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

}