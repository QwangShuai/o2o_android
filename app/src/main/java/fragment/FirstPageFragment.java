package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import activity.CityActivity;
import activity.JobActivity;
import activity.TypeActivity;
import activity.MessageActivity;
import utils.Utils;

/**
 * 创建日期：2017/7/28 on 13:52
 * 作者:孙明明
 * 描述:首页
 */

public class FirstPageFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private RelativeLayout cityRl, msgRl;
    private ImageView findWorkerIv, findJobIv, sendJobIv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_first_page, null);
        initView();
        setListener();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        cityRl = (RelativeLayout) rootView.findViewById(R.id.rl_first_page_city);
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_first_page_msg);
        findWorkerIv = (ImageView) rootView.findViewById(R.id.iv_first_page_find_workder);
        findJobIv = (ImageView) rootView.findViewById(R.id.iv_first_page_find_job);
        sendJobIv = (ImageView) rootView.findViewById(R.id.iv_first_page_send_job);
    }

    private void setListener() {
        cityRl.setOnClickListener(this);
        msgRl.setOnClickListener(this);
        findWorkerIv.setOnClickListener(this);
        findJobIv.setOnClickListener(this);
        sendJobIv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_first_page_city:
                Intent cityIntent = new Intent(getActivity(), CityActivity.class);
                startActivity(cityIntent);
                break;
            case R.id.rl_first_page_msg:
                Intent msgIntent = new Intent(getActivity(), MessageActivity.class);
                startActivity(msgIntent);
                break;
            case R.id.iv_first_page_find_workder:
                Intent findWorkerIntent = new Intent(getActivity(), TypeActivity.class);
                startActivity(findWorkerIntent);
                break;
            case R.id.iv_first_page_find_job:
                Intent findJobIntent = new Intent(getActivity(), JobActivity.class);
                startActivity(findJobIntent);
                break;
            case R.id.iv_first_page_send_job:
                Utils.toast(getActivity(), "发布工作");
                break;
        }
    }
}
