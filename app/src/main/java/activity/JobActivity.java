package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.JobAdapter;
import utils.Utils;
import view.CLinearItemDecoration;

public class JobActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, screenRl;

    private RecyclerView recyclerView;
    private List<String> stringList = new ArrayList<>();
    private JobAdapter jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_job, null);
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
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_job_screen);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_job);
    }

    private void initData() {
        jobAdapter = new JobAdapter(this, stringList);
        jobAdapter.setOnItemClickLitener(new JobAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Utils.toast(JobActivity.this, "click:" + position);
                jobAdapter.addData(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Utils.toast(JobActivity.this, "longClick:" + position);
                jobAdapter.removeData(position);
            }
        });
    }

    private void setData() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.addItemDecoration(new CGridItemDecoration(this));
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new CLinearItemDecoration(this, CLinearItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(jobAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
    }

    private void loadData() {
        for (int i = 0; i < 20; i++) {
            stringList.add("i:" + i);
        }
        jobAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_job_return:
                finish();
                break;
            case R.id.rl_job_screen:
                Utils.toast(this, "筛选");
                break;
        }
    }
}
