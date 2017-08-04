package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gjzg.R;

import activity.EmployerManageActivity;
import activity.PersonManageActivity;
import activity.WorkerManageActivity;
import utils.Utils;

/**
 * 创建日期：2017/7/28 on 13:52
 * 作者:孙明明
 * 描述:工作管理
 */

public class WorkManageFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout prepaidRl, sendJobRl, workerRl, employerRl, personRl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_work_manage, null);
        initView();
        setListener();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        prepaidRl = (RelativeLayout) rootView.findViewById(R.id.rl_work_manage_prepaid);
        sendJobRl = (RelativeLayout) rootView.findViewById(R.id.rl_work_manage_send_job);
        workerRl = (RelativeLayout) rootView.findViewById(R.id.rl_work_manage_worker);
        employerRl = (RelativeLayout) rootView.findViewById(R.id.rl_work_manage_employer);
        personRl = (RelativeLayout) rootView.findViewById(R.id.rl_work_manage_person);
    }

    private void setListener() {
        prepaidRl.setOnClickListener(this);
        sendJobRl.setOnClickListener(this);
        workerRl.setOnClickListener(this);
        employerRl.setOnClickListener(this);
        personRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_work_manage_prepaid:
                Utils.toast(getActivity(), "充值");
                break;
            case R.id.rl_work_manage_send_job:
                Utils.toast(getActivity(), "发布工作");
                break;
            case R.id.rl_work_manage_worker:
                Intent workerManageIntent = new Intent(getActivity(), WorkerManageActivity.class);
                startActivity(workerManageIntent);
                break;
            case R.id.rl_work_manage_employer:
                Intent employerManageIntent = new Intent(getActivity(), EmployerManageActivity.class);
                startActivity(employerManageIntent);
                break;
            case R.id.rl_work_manage_person:
                Intent personManageIntent = new Intent(getActivity(), PersonManageActivity.class);
                startActivity(personManageIntent);
                break;
        }
    }
}