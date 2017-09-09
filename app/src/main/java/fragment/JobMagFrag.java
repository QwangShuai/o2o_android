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

/**
 * 创建日期：2017/7/28 on 13:52
 * 作者:孙明明
 * 描述:工作管理
 */

public class JobMagFrag extends CommonFragment implements View.OnClickListener {

    //根视图
    private View rootView;
    //充值视图
    private RelativeLayout prepaidRl;
    //发布工作视图
    private RelativeLayout sendJobRl;
    //工人工作管理视图
    private RelativeLayout workerRl;
    //雇主发布管理视图
    private RelativeLayout employerRl;
    //个人信息管理视图
    private RelativeLayout personRl;

    @Override
    protected View getRootView() {
        //初始化根视图
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_job_mag, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        //初始化充值视图
        prepaidRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_prepaid);
        //初始化发布工作视图
        sendJobRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_send_job);
        //初始化工人工作管理视图
        workerRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_worker);
        //初始化雇主发布管理视图
        employerRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_job_mag_emp);
        //初始化个人信息管理视图
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
        //充值视图监听
        prepaidRl.setOnClickListener(this);
        //发布工作视图监听
        sendJobRl.setOnClickListener(this);
        //工人工作管理监听
        workerRl.setOnClickListener(this);
        //雇主发布管理监听
        employerRl.setOnClickListener(this);
        //个人信息管理监听
        personRl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //充值视图点击事件
            case R.id.rl_frag_job_mag_prepaid:
                startActivity(new Intent(getActivity(), WalletActivity.class));
                break;
            //发布工作视图点击事件
            case R.id.rl_frag_job_mag_send_job:
                Intent sendJobIntent = new Intent(getActivity(), SendJobActivity.class);
                startActivity(sendJobIntent);
                break;
            //工人工作管理视图点击事件
            case R.id.rl_frag_job_mag_worker:
                Intent workerManageIntent = new Intent(getActivity(), WorkerMagActivity.class);
                startActivity(workerManageIntent);
                break;
            //雇主发布管理视图点击事件
            case R.id.rl_frag_job_mag_emp:
                Intent employerManageIntent = new Intent(getActivity(), EmpMagActivity.class);
                startActivity(employerManageIntent);
                break;
            //个人信息管理视图点击事件
            case R.id.rl_frag_job_mag_person:
                Intent personManageIntent = new Intent(getActivity(), PersonMagActivity.class);
                startActivity(personManageIntent);
                break;
        }
    }


}