package fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import activity.CityActivity;
import activity.JobActivity;
import activity.KindActivity;
import activity.LeftRightActivity;
import activity.SendJobActivity;
import config.IntentConfig;
//首页
public class FpFragment extends CommonFragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout cityRl, msgRl;
    private ImageView findWorkerIv, findJobIv, sendJobIv;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.frag_first_page, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        cityRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_first_page_city);
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_frag_first_page_msg);
        findWorkerIv = (ImageView) rootView.findViewById(R.id.iv_frag_first_page_find_worker);
        findJobIv = (ImageView) rootView.findViewById(R.id.iv_frag_first_page_find_job);
        sendJobIv = (ImageView) rootView.findViewById(R.id.iv_frag_first_page_send_job);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setData() {
    }

    @Override
    protected void setListener() {
        cityRl.setOnClickListener(this);
        msgRl.setOnClickListener(this);
        findWorkerIv.setOnClickListener(this);
        findJobIv.setOnClickListener(this);
        sendJobIv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_frag_first_page_city:
                Intent cityIntent = new Intent(getActivity(), CityActivity.class);
                startActivity(cityIntent);
                break;
            case R.id.rl_frag_first_page_msg:
                Intent msgIntent = new Intent(getActivity(), LeftRightActivity.class);
                msgIntent.putExtra(IntentConfig.intentName, IntentConfig.MESSAGE);
                startActivity(msgIntent);
                break;
            case R.id.iv_frag_first_page_find_worker:
                startActivity(new Intent(getActivity(), KindActivity.class));
                break;
            case R.id.iv_frag_first_page_find_job:
                startActivity(new Intent(getActivity(), JobActivity.class));
                break;
            case R.id.iv_frag_first_page_send_job:
                startActivity(new Intent(getActivity(), SendJobActivity.class));
                break;
            default:
                break;
        }
    }

}
