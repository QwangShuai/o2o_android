package redpacket.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.NetConfig;
import config.VarConfig;
import adapter.RedPacketAdapter;
import bean.RedPacketBean;
import redpacket.presenter.IRedPacketPresenter;
import redpacket.presenter.RedPacketPresenter;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import skill.view.SkillActivity;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;

public class RedPacketActivity extends AppCompatActivity implements IRedPacketActivity, View.OnClickListener, PullToRefreshLayout.OnRefreshListener {

    private View rootView, emptyView, netView;
    private FrameLayout fl;
    private TextView netTv;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private RelativeLayout returnRl;
    private CProgressDialog cpd;
    private List<RedPacketBean> redPacketBeanList = new ArrayList<>();
    private RedPacketAdapter redPacketAdapter;
    private IRedPacketPresenter redPacketPresenter;

    private final int LOAD_SUCCESS = 1;
    private final int LOAD_FAILURE = 2;
    private final int FIRST = 3;
    private final int REFRESH = 4;
    private int STATE = FIRST;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case LOAD_SUCCESS:
                        notifyData();
                        break;
                    case LOAD_FAILURE:
                        notifyNet();
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(RedPacketActivity.this).inflate(R.layout.activity_red_packet, null);
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
        if (redPacketPresenter != null) {
            redPacketPresenter.destroy();
            redPacketPresenter = null;
        }
        if (handler != null) {
            handler.removeMessages(LOAD_SUCCESS);
            handler.removeMessages(LOAD_FAILURE);
            handler = null;
        }
    }

    private void initView() {
        initRootView();
        initEmptyView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_red_packet_return);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
        cpd = Utils.initProgressDialog(this, cpd);
    }

    private void initEmptyView() {
        fl = (FrameLayout) rootView.findViewById(R.id.fl);
        emptyView = LayoutInflater.from(RedPacketActivity.this).inflate(R.layout.empty_data, null);
        fl.addView(emptyView);
        emptyView.setVisibility(View.GONE);
        netView = LayoutInflater.from(RedPacketActivity.this).inflate(R.layout.empty_net, null);
        netTv = (TextView) netView.findViewById(R.id.tv_no_net_refresh);
        netTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ptrl.setVisibility(View.VISIBLE);
                netView.setVisibility(View.GONE);
                STATE = FIRST;
                loadData();
            }
        });
        fl.addView(netView);
        netView.setVisibility(View.GONE);
    }

    private void initData() {
        redPacketPresenter = new RedPacketPresenter(this);
        redPacketAdapter = new RedPacketAdapter(this, redPacketBeanList);
    }

    private void setData() {
        plv.setAdapter(redPacketAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
    }

    private void loadData() {
        cpd.show();
        redPacketPresenter.load(NetConfig.redBagUrl + "?action=list&uid=" + UserUtils.readUserData(RedPacketActivity.this).getId() + "&bt_id=2");
    }

    private void notifyData() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                if (redPacketBeanList.size() == 0) {
                    ptrl.setVisibility(View.GONE);
                    netView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    ptrl.setVisibility(View.VISIBLE);
                    netView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                }
                break;
            case REFRESH:
                ptrl.hideHeadView();
                break;
        }
        redPacketAdapter.notifyDataSetChanged();
    }

    private void notifyNet() {
        switch (STATE) {
            case FIRST:
                cpd.dismiss();
                ptrl.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                netView.setVisibility(View.VISIBLE);
                break;
            case REFRESH:
                ptrl.hideHeadView();
                Utils.toast(RedPacketActivity.this, VarConfig.noNetTip);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_red_packet_return:
                finish();
                break;
        }
    }

    @Override
    public void loadSuccess(String json) {
        redPacketBeanList.addAll(DataUtils.getRedPacketBeanList(json));
        handler.sendEmptyMessage(LOAD_SUCCESS);
    }

    @Override
    public void loadFailure(String failure) {
        handler.sendEmptyMessage(LOAD_FAILURE);
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        ptrl.hideHeadView();
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        ptrl.hideFootView();
    }
}
