package fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import refuseorder.view.RefuseOrderActivity;
import adapter.WorkerMagAdapter;
import bean.PersonBean;
import config.StateConfig;
import listener.ListItemClickHelp;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;

//工人管理-洽谈中
public class WorkerMagTalkingFrag extends CommonFragment implements PullToRefreshLayout.OnRefreshListener, ListItemClickHelp {

    private View rootView;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<PersonBean> list;
    private WorkerMagAdapter adapter;
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
        adapter = new WorkerMagAdapter(getActivity(), list, this);
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
        PersonBean p1 = new PersonBean();
        p1.setImage("");
        p1.setName("专业水泥工");
        p1.setPlay("X月X日开工，工期5天");
        p1.setShow("工资：100/人/天");
        p1.setState(StateConfig.TALKING);
        p1.setCollect(false);
        p1.setDistance("距离3公里");
        list.add(p1);
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

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        switch (which){
            case R.id.tv_item_worker_mag_refuse:
                startActivity(new Intent(getActivity(), RefuseOrderActivity.class));
                break;
            default:
                break;
        }
    }
}
