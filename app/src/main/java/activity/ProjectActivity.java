package activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ProjectAdapter;
import bean.ProjectBean;
import config.StateConfig;
import listener.ListItemClickHelp;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;

//招工项目
public class ProjectActivity extends CommonActivity implements View.OnClickListener, ListItemClickHelp, PullToRefreshLayout.OnRefreshListener {

    private View rootView;
    private RelativeLayout returnRl;
    private FrameLayout fl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<ProjectBean> list;
    private ProjectAdapter adapter;
    private int state = StateConfig.LOAD_DONE;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_project, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_project_return);
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new ProjectAdapter(this, list, this);
    }

    @Override
    protected void setData() {
        plv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        ProjectBean p0 = new ProjectBean();
        p0.setIcon("");
        p0.setTitle("急招木工");
        p0.setContent("11月3号开始、工期13天");
        p0.setPrice("工资：9999");
        p0.setAddress("北京街上海路");
        p0.setState(StateConfig.WAIT);
        ProjectBean p1 = new ProjectBean();
        p1.setIcon("");
        p1.setTitle("急招电工");
        p1.setContent("11月3号开始、工期13天");
        p1.setPrice("工资：9999");
        p1.setAddress("北京街上海路");
        p1.setState(StateConfig.WAIT);
        list.add(p0);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_project_return:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
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
