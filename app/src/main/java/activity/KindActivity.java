package activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.KindAdapter;
import bean.Kind;
import listener.OnRefreshListener;
import view.CListView;

public class KindActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private CListView cListView;

    private List<Kind> kindList = new ArrayList<>();
    private KindAdapter kindAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        cListView.hideHeadView();
                        break;
                    case 2:
                        cListView.hideFootView();
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_type, null);
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
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_type_return);
        cListView = (CListView) rootView.findViewById(R.id.clv_type);
    }

    private void initData() {
        kindAdapter = new KindAdapter(this, kindList);
    }

    private void setData() {
        cListView.setAdapter(kindAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        cListView.setOnRefreshListener(this);
        cListView.setOnItemClickListener(this);
    }

    private void loadData() {
        for (int i = 0; i < 10; i++) {
            Kind j = new Kind();
            j.setName("水泥工" + i);
            kindList.add(j);
        }
        kindAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_type_return:
                finish();
                break;
        }
    }

    @Override
    public void onDownPullRefresh() {
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void onLoadingMore() {
        handler.sendEmptyMessageDelayed(2, 1000);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, WorkerActivity.class);
        intent.putExtra("kind", kindList.get(position - 1));
        startActivity(intent);
    }
}
