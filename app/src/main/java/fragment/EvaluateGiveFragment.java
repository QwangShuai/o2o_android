package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.EvaluateAdapter;
import bean.EvaluateBean;
import config.StateConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;

/**
 * 创建日期：2017/8/8 on 10:50
 * 作者:孙明明
 * 描述:给别人的评价
 */
//给别人的评价
public class EvaluateGiveFragment extends Fragment implements PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<EvaluateBean> list;
    private EvaluateAdapter adapter;
    private int state = StateConfig.LOAD_DONE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.common_listview, null);
        initView();
        initData();
        setData();
        setListener();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new EvaluateAdapter(getActivity(), list);
    }

    private void setData() {
        plv.setAdapter(adapter);
    }

    private void setListener() {
        ptrl.setOnRefreshListener(this);
    }

    private void loadData() {
        EvaluateBean e = new EvaluateBean();
        e.setGet(false);
        e.setNumCount(5);
        e.setIcon("");
        e.setContent("老板太苛刻");
        e.setPraiseCount(0);
        e.setTime("2017年5月4日 17:25");
        list.add(e);
        adapter.notifyDataSetChanged();
        switch (state) {
            case StateConfig.LOAD_REFRESH:
                ptrl.hideHeadView();
                break;
            case StateConfig.LOAD_LOAD:
                ptrl.hideFootView();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        list.clear();
        state = StateConfig.LOAD_REFRESH;
        loadData();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        state = StateConfig.LOAD_LOAD;
        loadData();
    }
}
