package selectaddress.view;

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

import selectaddress.adapter.SelectAddressAdapter;
import selectaddress.bean.SelectAddressBean;
import selectaddress.presenter.ISelectAddressPresenter;
import selectaddress.presenter.SelectAddressPresenter;
import utils.Utils;
import view.CProgressDialog;

public class SelectAddressActivity extends AppCompatActivity implements ISelectAddressActivity, View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, provinceRl, cityRl, areaRl;
    private CProgressDialog cpd;
    private ListView lv;
    private List<SelectAddressBean> list;
    private SelectAddressAdapter adapter;

    private final int PROVINCE = 0, CITY = 1, AREA = 2;
    private int STATE;

    private ISelectAddressPresenter iSelectAddressPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(SelectAddressActivity.this).inflate(R.layout.activity_select_address, null);
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
        if (iSelectAddressPresenter != null) {
            iSelectAddressPresenter.destroy();
            iSelectAddressPresenter = null;
        }
    }

    private void initView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_select_address_return);
        provinceRl = (RelativeLayout) rootView.findViewById(R.id.rl_select_address_province);
        cityRl = (RelativeLayout) rootView.findViewById(R.id.rl_select_address_city);
        areaRl = (RelativeLayout) rootView.findViewById(R.id.rl_select_address_area);
        cpd = Utils.initProgressDialog(SelectAddressActivity.this, cpd);
        lv = (ListView) rootView.findViewById(R.id.lv_select_address);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new SelectAddressAdapter(SelectAddressActivity.this, list);
        STATE = PROVINCE;
        iSelectAddressPresenter = new SelectAddressPresenter(this);
    }

    private void setData() {
        lv.setAdapter(adapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        provinceRl.setOnClickListener(this);
        cityRl.setOnClickListener(this);
        areaRl.setOnClickListener(this);
    }

    private void loadData() {
        iSelectAddressPresenter.load("167");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select_address_return:
                finish();
                break;
            case R.id.rl_select_address_province:
                Utils.log(SelectAddressActivity.this, "省");
                break;
            case R.id.rl_select_address_city:
                Utils.log(SelectAddressActivity.this, "市");
                break;
            case R.id.rl_select_address_area:
                Utils.log(SelectAddressActivity.this, "区");
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
    public void showSuccess(List<SelectAddressBean> selectAddressBeanList) {
        Utils.log(SelectAddressActivity.this, "selectAddressBeanList=" + selectAddressBeanList.toString());
    }

    @Override
    public void showFailure(String failure) {
        Utils.log(SelectAddressActivity.this, "failure=" + failure);
    }
}
