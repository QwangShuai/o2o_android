package activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PersonAdapter;
import bean.PersonBean;
import config.StateConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;

//工作
public class JobActivity extends CommonActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl, screenRl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<PersonBean> list;
    private PersonAdapter adapter;
    private int state = StateConfig.LOAD_DONE;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_job, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_screen);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }


    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new PersonAdapter(this, list);
    }

    @Override
    protected void setData() {
        plv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
        plv.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        PersonBean p0 = new PersonBean();
        p0.setName("专业水泥工");
        p0.setCollect(false);
        p0.setState(StateConfig.LEISURE);
        p0.setDistance("距离3公里");
        p0.setPlay("X月X日开工，工期2天");
        p0.setShow("工资：200/人/天");
        PersonBean p1 = new PersonBean();
        p1.setName("专业水泥工");
        p1.setCollect(false);
        p1.setState(StateConfig.WORKING);
        p1.setDistance("距离3公里");
        p1.setPlay("10月2号开工，工期5天");
        p1.setShow("工资：100/人/天");
        PersonBean p2 = new PersonBean();
        p2.setName("专业水泥工");
        p2.setPlay("11月1日开工，工期一天");
        p2.setShow("工资：500/人/天");
        p2.setState(StateConfig.TALKING);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_job_return:
                finish();
                break;
            case R.id.rl_job_screen:
                startActivity(new Intent(this, JobScnActivity.class));
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, TalkActivity.class));
    }
}
