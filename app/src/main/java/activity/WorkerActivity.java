package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PersonAdapter;
import bean.Kind;
import bean.Person;
import listener.OnRefreshListener;
import utils.Utils;
import view.CListView;
import view.CProgressDialog;

public class WorkerActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView, noDataEmptyView, noNetEmptyView;
    private RelativeLayout returnRl, screenRl;
    private CListView listView;
    private CProgressDialog progressDialog;
    private TextView noNetRefreshTv;

    private List<Person> personList;
    private PersonAdapter personAdapter;

    private Kind kind;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                stopAnim();
                switch (msg.what) {
                    case 0:
                        noNet();
                        break;
                    case 1:
                        notifyData();
                        break;
                }
            }
        }
    };

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
        initEmptyView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_screen);
        listView = (CListView) rootView.findViewById(R.id.clv_worker);
    }

    private void initEmptyView() {
        noDataEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_no_data, null);
        noDataEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewGroup) listView.getParent()).addView(noDataEmptyView);
        noDataEmptyView.setVisibility(View.GONE);
        noNetEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_no_net, null);
        noNetEmptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewGroup) listView.getParent()).addView(noNetEmptyView);
        noNetEmptyView.setVisibility(View.GONE);
        noNetRefreshTv = (TextView) noNetEmptyView.findViewById(R.id.tv_empty_no_net_refresh);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    private void startAnim() {
        progressDialog.show();
    }

    private void stopAnim() {
        progressDialog.dismiss();
    }

    private void initData() {
        Intent intent = getIntent();
        kind = (Kind) intent.getSerializableExtra("kind");
        personList = new ArrayList<>();
        personAdapter = new PersonAdapter(this, personList);
    }

    private void setData() {
        listView.setAdapter(personAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        noNetRefreshTv.setOnClickListener(this);
    }

    private void loadData() {
        listView.setVisibility(View.INVISIBLE);
        startAnim();
        for (int i = 0; i < 10; i++) {
            Person person = new Person();
            person.setImage("");
            person.setName(kind.getName() + "-" + i);
            person.setPlay("精通刮大白");
            person.setShow("十年刮大白经验");
            person.setState(1);
            person.setCollect(false);
            person.setDistance("距离3公里");
            personList.add(person);
        }
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    private void notifyData() {
        personAdapter.notifyDataSetChanged();
        listView.setVisibility(View.VISIBLE);
        listView.setEmptyView(noDataEmptyView);
    }

    private void noNet() {
        listView.setEmptyView(noNetEmptyView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_worker_return:
                finish();
                break;
            case R.id.rl_worker_screen:
                Utils.toast(this, "筛选");
                personList.clear();
                handler.sendEmptyMessage(0);
                break;
            case R.id.tv_empty_no_net_refresh:
                loadData();
                break;
        }
    }

    @Override
    public void onDownPullRefresh() {
        listView.hideHeadView();
    }

    @Override
    public void onLoadingMore() {
        listView.hideFootView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TalkActivity.class);
        intent.putExtra("worker", personList.get(position - 1));
        startActivity(intent);
    }
}
