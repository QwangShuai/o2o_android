package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import activity.CityActivity;
import activity.JobActivity;
import activity.MsgActivity;
import activity.SendJobActivity;
import activity.KindActivity;
import adapter.FirstPageAdapter;
import bean.FirstPage;
import config.IntentConfig;
import utils.Utils;
import view.CProgressDialog;

/**
 * 创建日期：2017/7/28 on 13:52
 * 作者:孙明明
 * 描述:首页
 */

public class FirstPageFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout cityRl, msgRl;
    private ListView listView;
    private CProgressDialog progressDialog;

    private List<FirstPage> firstPageList = new ArrayList<>();
    private FirstPageAdapter firstPageAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                stopAnim();
                switch (msg.what) {
                    case 0:
                        Utils.toast(getActivity(), "网络异常");
                        break;
                    case 1:
                        notifyData();
                        break;
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler.removeMessages(1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_first_page, null);
        initView();
        initData();
        setData();
        setListener();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        cityRl = (RelativeLayout) rootView.findViewById(R.id.rl_first_page_city);
        msgRl = (RelativeLayout) rootView.findViewById(R.id.rl_first_page_msg);
        listView = (ListView) rootView.findViewById(R.id.lv_first_page);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(getActivity(), R.style.dialog_cprogress);
    }

    private void initData() {
        firstPageAdapter = new FirstPageAdapter(getActivity(), firstPageList);
    }

    private void setData() {
        listView.setAdapter(firstPageAdapter);
    }

    private void setListener() {
        cityRl.setOnClickListener(this);
        msgRl.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    private void loadData() {
        startAnim();
        FirstPage fp0 = new FirstPage();
        fp0.setId(0);
        fp0.setImg("");
        FirstPage fp1 = new FirstPage();
        fp1.setId(1);
        fp1.setImg("");
        FirstPage fp2 = new FirstPage();
        fp2.setId(2);
        fp2.setImg("");
        firstPageList.add(fp0);
        firstPageList.add(fp1);
        firstPageList.add(fp2);
        handler.sendEmptyMessage(1);
    }

    private void notifyData() {
        firstPageAdapter.notifyDataSetChanged();
    }

    private void startAnim() {
        progressDialog.show();
    }

    private void stopAnim() {
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_first_page_city:
                Intent cityIntent = new Intent(getActivity(), CityActivity.class);
                startActivity(cityIntent);
                break;
            case R.id.rl_first_page_msg:
                Intent msgIntent = new Intent(getActivity(), MsgActivity.class);
                msgIntent.putExtra(IntentConfig.intentName, IntentConfig.MESSAGE);
                startActivity(msgIntent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_first_page:
                switch (firstPageList.get(position).getId()) {
                    case 0:
                        startActivity(new Intent(getActivity(), KindActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), JobActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), SendJobActivity.class));
                        break;
                }
                break;
        }
    }
}
