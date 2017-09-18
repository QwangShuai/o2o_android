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
import bean.ScreenBean;
import config.CodeConfig;
import config.StateConfig;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;

//工人
public class WorkerActivity extends CommonActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout screenRl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<PersonBean> list;
    private PersonAdapter adapter;
    private int state = StateConfig.LOAD_DONE;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_worker, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_screen);
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
        p0.setPlay("精通XX，XX，XX");
        p0.setShow("完成过xx项目，个人家装");
        p0.setState(StateConfig.LEISURE);
        p0.setCollect(false);
        p0.setDistance("距离3公里");
        PersonBean p1 = new PersonBean();
        p1.setName("专业水泥工");
        p1.setPlay("精通XX，XX，XX");
        p1.setShow("完成过xx项目，个人家装");
        p1.setState(StateConfig.WORKING);
        p1.setCollect(false);
        p1.setDistance("距离3公里");
        PersonBean p2 = new PersonBean();
        p2.setName("专业水泥工");
        p2.setPlay("精通XX，XX，XX");
        p2.setShow("完成过xx项目，个人家装");
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
            case R.id.rl_worker_return:
                finish();
                break;
            case R.id.rl_worker_screen:
                startActivityForResult(new Intent(this, WorkerScnActivity.class), CodeConfig.screenRequestCode);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, TalkActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeConfig.screenRequestCode && resultCode == CodeConfig.screenResultCode && data != null) {
            ScreenBean screenBean = (ScreenBean) data.getSerializableExtra("screenBean");
            if (screenBean != null) {

            }
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
