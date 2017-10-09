package voucher.view;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import listener.ListItemClickHelp;
import utils.Utils;
import view.CProgressDialog;
import voucher.adapter.VoucherAdapter;
import voucher.bean.VoucherBean;
import voucher.presenter.IVoucherPresenter;
import voucher.presenter.VoucherPresenter;

public class VoucherActivity extends AppCompatActivity implements IVoucherActivity, View.OnClickListener, ListItemClickHelp {

    private View rootView;
    private RelativeLayout returnRl;
    private CProgressDialog cpd;
    private ListView lv;
    private List<VoucherBean> list;
    private VoucherAdapter adapter;

    private IVoucherPresenter iVoucherPresenter = new VoucherPresenter(this);

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
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_voucher, null);
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
        if (iVoucherPresenter != null) {
            iVoucherPresenter.destroy();
            iVoucherPresenter = null;
        }
        handler.removeMessages(1);
    }

    private void initView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_voucher_return);
        cpd = Utils.initProgressDialog(this, cpd);
        lv = (ListView) rootView.findViewById(R.id.lv_voucher);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new VoucherAdapter(this, list, this);
    }

    private void setData() {
        lv.setAdapter(adapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
    }

    private void loadData() {
        iVoucherPresenter.load();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_voucher_return:
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
    public void receiveData(List<VoucherBean> voucherBeanList) {
        list.addAll(voucherBeanList);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        switch (which) {
            case R.id.tv_item_red_packet_status:
                Utils.log(VoucherActivity.this, "您点击了第" + position + "个代金券");
                break;
            default:
                break;
        }
    }
}
