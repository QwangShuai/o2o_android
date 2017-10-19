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

//雇主管理-已结束
public class EmpMagFinishedFrag extends CommonFragment implements PullToRefreshLayout.OnRefreshListener {

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
        state = StateConfig.LOAD_DONE;
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
        PersonBean p2 = new PersonBean();
        p2.setImage("");
        p2.setName("急招水泥工");
        p2.setPlay("11月1日开工，工期一天");
        p2.setShow("工资：500/人/天");
        p2.setState(StateConfig.OVER);
        p2.setCollect(true);
        p2.setDistance("南马路12号");
        list.add(p2);
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
