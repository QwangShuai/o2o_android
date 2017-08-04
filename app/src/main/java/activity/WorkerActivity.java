package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.WorkerAdapter;
import bean.Type;
import bean.Worker;
import listener.OnRefreshListener;
import utils.Utils;
import view.CListView;

public class WorkerActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl, screenRl;
    private CListView cListView;

    private List<Worker> workerList = new ArrayList<>();
    private WorkerAdapter workerAdapter;

    private Type job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_worker, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_screen);
        cListView = (CListView) rootView.findViewById(R.id.clv_worker);
    }

    private void initData() {
        Intent intent = getIntent();
        job = (Type) intent.getSerializableExtra("job");
        workerAdapter = new WorkerAdapter(this, workerList);
    }

    private void setData() {
        cListView.setAdapter(workerAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
        cListView.setOnRefreshListener(this);
        cListView.setOnItemClickListener(this);
    }

    private void loadData() {
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setImage("");
            worker.setName(job.getName() + "-" + i);
            worker.setPlay("精通刮大白");
            worker.setShow("十年刮大白经验");
            worker.setState(1);
            worker.setCollect(false);
            worker.setDistance("距离3公里");
            workerList.add(worker);
        }
        workerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_worker_return:
                finish();
                break;
            case R.id.rl_worker_screen:
                Utils.toast(this, "筛选");
                break;
        }
    }

    @Override
    public void onDownPullRefresh() {
        cListView.hideHeadView();
    }

    @Override
    public void onLoadingMore() {
        cListView.hideFootView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TalkActivity.class);
        intent.putExtra("worker", workerList.get(position - 1));
        startActivity(intent);
    }
}
