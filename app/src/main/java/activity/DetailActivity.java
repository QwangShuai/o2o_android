package activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.DetailAdapter;
import bean.Detail;
import view.CProgressDialog;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private ListView listView;
    private CProgressDialog progressDialog;

    private List<Detail> detailList = new ArrayList<>();
    private DetailAdapter detailAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        for (int i = 0; i < 10; i++) {
                            Detail detail = new Detail();
                            detail.setTitle("收入");
                            detail.setBalance("余额：100");
                            detail.setTime("2017-08-09");
                            detail.setDes("+100");
                            detailList.add(detail);
                        }
                        progressDialog.dismiss();
                        detailAdapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_detail, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_detail_return);
        listView = (ListView) rootView.findViewById(R.id.lv_detail);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    private void initData() {
        detailAdapter = new DetailAdapter(this, detailList);
    }

    private void setData() {
        listView.setAdapter(detailAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
    }

    private void loadData() {
        progressDialog.show();
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_detail_return:
                finish();
                break;
        }
    }
}
