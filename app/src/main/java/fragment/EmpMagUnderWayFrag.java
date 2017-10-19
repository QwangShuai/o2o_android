package fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PersonTestAdapter;
import bean.PersonBean;
import config.StateConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;

//雇主管理-进行中
public class EmpMagUnderWayFrag extends CommonFragment implements PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<PersonBean> list;
    private PersonTestAdapter adapter;
    private int state = StateConfig.LOAD_DONE;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(getActivity()).inflate(R.layout.common_listview, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new PersonTestAdapter(getActivity(), list);
    }

    @Override
    protected void setData() {
        plv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        ptrl.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        PersonBean p0 = new PersonBean();
        p0.setImage("");
        p0.setState(StateConfig.WORKING);
        p0.setName("急招水泥工");
        p0.setCollect(false);
        p0.setShow("工资：200/人/天");
        p0.setDistance("南马路12号");
        p0.setPlay("X月X日开工，工期2天");
        list.add(p0);
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
