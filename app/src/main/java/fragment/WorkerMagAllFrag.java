package fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import complain.view.ComplainActivity;
import evaluate.view.EvaluateActivity;
import refuseorder.view.RefuseOrderActivity;
import resign.view.ResignActivity;
import adapter.WorkerMagAdapter;
import bean.PersonBean;
import config.StateConfig;
import listener.ListItemClickHelp;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;

//工人管理-全部
public class WorkerMagAllFrag extends CommonFragment implements PullToRefreshLayout.OnRefreshListener, ListItemClickHelp {

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
        PersonBean p0 = new PersonBean();
        p0.setImage("");
        p0.setName("专业水泥工");
        p0.setPlay("X月X日开工，工期2天");
        p0.setShow("工资：200/人/天");
        p0.setState(StateConfig.WORKING);
        p0.setCollect(false);
        p0.setDistance("距离3公里");
        PersonBean p1 = new PersonBean();
        p1.setImage("");
        p1.setName("专业水泥工");
        p1.setPlay("X月X日开工，工期5天");
        p1.setShow("工资：100/人/天");
        p1.setState(StateConfig.TALKING);
        p1.setCollect(false);
        p1.setDistance("距离3公里");
        PersonBean p2 = new PersonBean();
        p2.setImage("");
        p2.setName("专业水泥工");
        p2.setPlay("11月1日开工，工期一天");
        p2.setShow("工资：500/人/天");
        p2.setState(StateConfig.OVER);
        p2.setCollect(true);
        p2.setDistance("距离3公里");
        list.add(p0);
        list.add(p1);
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

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        switch (which) {
            case R.id.tv_item_worker_mag_quit:
                startActivity(new Intent(getActivity(), ResignActivity.class));
                break;
            case R.id.tv_item_worker_mag_refuse:
                startActivity(new Intent(getActivity(), RefuseOrderActivity.class));
                break;
            case R.id.tv_item_worker_mag_complain:
                startActivity(new Intent(getActivity(), ComplainActivity.class));
                break;
            case R.id.tv_item_worker_mag_delete:
                list.remove(position);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_item_worker_mag_evaluate:
                startActivity(new Intent(getActivity(), EvaluateActivity.class));
                break;
            default:
                break;
        }
    }
}
