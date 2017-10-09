package redpacket.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import listener.ListItemClickHelp;
import redpacket.adapter.RedPacketAdapter;
import redpacket.bean.RedPacketBean;
import redpacket.presenter.IRedPacketPresenter;
import redpacket.presenter.RedPacketPresenter;
import utils.Utils;
import view.CProgressDialog;

public class RedPacketActivity extends AppCompatActivity implements IRedPacketActivity, View.OnClickListener, ListItemClickHelp {

    private View rootView;
    private RelativeLayout returnRl;
    private CProgressDialog cpd;
    private ListView lv;
    private List<RedPacketBean> list;
    private RedPacketAdapter adapter;
    private IRedPacketPresenter iRedPacketPresenter = new RedPacketPresenter(this);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                    default:
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
        handler.removeMessages(1);
    }

    private void initView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_red_packet_return);
        cpd = Utils.initProgressDialog(this, cpd);
        lv = (ListView) rootView.findViewById(R.id.lv_red_packet);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new RedPacketAdapter(this, list, this);
    }

    private void setData() {
        lv.setAdapter(adapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
    }

    private void loadData() {
        iRedPacketPresenter.load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_red_packet_return:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showLoading() {
        cpd.show();
    }

    @Override
    public void hideLoading() {
        cpd.dismiss();
    }

    @Override
    public void receiveData(List<RedPacketBean> redPacketBeanList) {
        list.addAll(redPacketBeanList);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        switch (which) {
            case R.id.tv_item_red_packet_status:
                Utils.log(RedPacketActivity.this, "立即领取第" + position + "个红包");
                break;
            default:
                break;
        }
    }
}
