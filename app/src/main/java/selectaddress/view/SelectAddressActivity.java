package selectaddress.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import selectaddress.adapter.SelectAddressAdapter;
import selectaddress.bean.SelectAddressBean;
import selectaddress.presenter.ISelectAddressPresenter;
import selectaddress.presenter.SelectAddressPresenter;
import utils.Utils;
import view.CProgressDialog;

public class SelectAddressActivity extends AppCompatActivity implements ISelectAddressActivity, View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl, provinceRl, cityRl, areaRl;
    private TextView provinceTv, cityTv;
    private CProgressDialog cpd;
    private ListView lv;
    private List<SelectAddressBean> list;
    private SelectAddressAdapter adapter;

    private String provinceId, cityId, areaId;

    private final int PROVINCE = 0, CITY = 1, AREA = 2;
    private int STATE;

    private ISelectAddressPresenter iSelectAddressPresenter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    };

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
        if (handler != null) {
            handler.removeMessages(1);
            handler = null;
        }
    }

    private void initView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_select_address_return);
        provinceRl = (RelativeLayout) rootView.findViewById(R.id.rl_select_address_province);
        cityRl = (RelativeLayout) rootView.findViewById(R.id.rl_select_address_city);
        areaRl = (RelativeLayout) rootView.findViewById(R.id.rl_select_address_area);
        cpd = Utils.initProgressDialog(SelectAddressActivity.this, cpd);
        lv = (ListView) rootView.findViewById(R.id.lv_select_address);
        provinceTv = (TextView) rootView.findViewById(R.id.tv_select_address_province);
        cityTv = (TextView) rootView.findViewById(R.id.tv_select_address_city);
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
        lv.setOnItemClickListener(this);
    }

    private void loadData() {
        iSelectAddressPresenter.load("1");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select_address_return:
                finish();
                break;
            case R.id.rl_select_address_province:
                Utils.log(SelectAddressActivity.this, "省");
                if (STATE != PROVINCE) {
                    provinceTv.setText("省");
                    provinceTv.setTextColor(ColorConfig.black_252323);
                    cityTv.setText("市");
                    cityTv.setTextColor(ColorConfig.black_252323);
                    STATE = PROVINCE;
                    iSelectAddressPresenter.load("1");
                }
                break;
            case R.id.rl_select_address_city:
                Utils.log(SelectAddressActivity.this, "市");
                if (STATE == AREA) {
                    cityTv.setText("市");
                    cityTv.setTextColor(ColorConfig.black_252323);
                    STATE = CITY;
                    iSelectAddressPresenter.load(provinceId);
                }
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
        list.clear();
        list.addAll(selectAddressBeanList);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void showFailure(String failure) {
        Utils.log(SelectAddressActivity.this, "failure=" + failure);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SelectAddressBean selectAddressBean = list.get(position);
        if (selectAddressBean != null) {
            String selectId = selectAddressBean.getId();
            String selectName = selectAddressBean.getName();
            switch (STATE) {
                case PROVINCE:
                    provinceId = selectId;
                    Utils.log(SelectAddressActivity.this, "provinceId=" + provinceId + ":provinceName=" + selectName);
                    provinceTv.setText(selectName);
                    provinceTv.setTextColor(ColorConfig.red_ff3e50);
                    STATE = CITY;
                    iSelectAddressPresenter.load(selectId);
                    break;
                case CITY:
                    cityId = selectId;
                    Utils.log(SelectAddressActivity.this, "cityId=" + cityId + ":cityName=" + selectName);
                    cityTv.setText(selectName);
                    cityTv.setTextColor(ColorConfig.red_ff3e50);
                    STATE = AREA;
                    iSelectAddressPresenter.load(selectId);
                    break;
                case AREA:
                    areaId = selectId;
                    Utils.log(SelectAddressActivity.this, "areaId=" + areaId + ":areaName=" + selectName);
                    Utils.log(SelectAddressActivity.this, provinceTv.getText().toString() + " " + cityTv.getText().toString() + " " + selectName);
                    SelectAddressBean sa = new SelectAddressBean(provinceId + "," + cityId + "," + areaId, provinceTv.getText().toString() + " " + cityTv.getText().toString() + " " + selectName);
                    Intent i = new Intent();
                    i.putExtra("sa", sa);
                    setResult(1, i);
                    finish();
                    break;
            }
        }
    }
}
