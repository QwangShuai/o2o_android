package jobmanage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gjzg.R;

import employermanage.view.EmpMagActivity;
import wallet.view.WalletActivity;
import workermanage.view.WorkerManageActivity;
import personmanage.view.PersonInfoManageActivity;
import publishjob.view.PublishJobActivity;

public class JobManageFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout prepaidRl, sendJobRl, workerRl, employerRl, personRl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_job_manage, null);
        initView();
        setListener();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        prepaidRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_prepaid);
        sendJobRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_send_job);
        workerRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_worker);
        employerRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_emp);
        personRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_person);
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
            case R.id.rl_frag_job_mag_prepaid:
                startActivity(new Intent(getActivity(), WalletActivity.class));
                break;
            case R.id.rl_frag_job_mag_send_job:
                Intent sendJobIntent = new Intent(getActivity(), PublishJobActivity.class);
                startActivity(sendJobIntent);
                break;
            case R.id.rl_frag_job_mag_worker:
                Intent workerManageIntent = new Intent(getActivity(), WorkerManageActivity.class);
                startActivity(workerManageIntent);
                break;
            case R.id.rl_frag_job_mag_emp:
                Intent employerManageIntent = new Intent(getActivity(), EmpMagActivity.class);
                startActivity(employerManageIntent);
                break;
            case R.id.rl_frag_job_mag_person:
                Intent personManageIntent = new Intent(getActivity(), PersonInfoManageActivity.class);
                startActivity(personManageIntent);
                break;
        }
    }

}