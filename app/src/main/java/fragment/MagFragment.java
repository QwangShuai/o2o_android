package fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.gjzg.R;

import activity.EmpMagActivity;
import activity.PersonMagActivity;
import activity.SendJobActivity;
import activity.WalletActivity;
import activity.WorkerMagActivity;
//管理
public class MagFragment extends CommonFragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout prepaidRl, sendJobRl, workerRl, employerRl, personRl;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_job_mag, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        prepaidRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_prepaid);
        sendJobRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_send_job);
        workerRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_worker);
        employerRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_emp);
        personRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_person);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        prepaidRl.setOnClickListener(this);
        sendJobRl.setOnClickListener(this);
        workerRl.setOnClickListener(this);
        employerRl.setOnClickListener(this);
        personRl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_frag_job_mag_prepaid:
                startActivity(new Intent(getActivity(), WalletActivity.class));
                break;
            case R.id.rl_frag_job_mag_send_job:
                Intent sendJobIntent = new Intent(getActivity(), SendJobActivity.class);
                startActivity(sendJobIntent);
                break;
            case R.id.rl_frag_job_mag_worker:
                Intent workerManageIntent = new Intent(getActivity(), WorkerMagActivity.class);
                startActivity(workerManageIntent);
                break;
            case R.id.rl_frag_job_mag_emp:
                Intent employerManageIntent = new Intent(getActivity(), EmpMagActivity.class);
                startActivity(employerManageIntent);
                break;
            case R.id.rl_frag_job_mag_person:
                Intent personManageIntent = new Intent(getActivity(), PersonMagActivity.class);
                startActivity(personManageIntent);
                break;
        }
    }

}