package city.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import city.adapter.CityAdapter;
import city.bean.CityBean;
import city.presenter.CityPresenter;
import city.presenter.ICityPresenter;
import config.IntentConfig;
import utils.Utils;
import view.SlideBar;

public class CityActivity extends AppCompatActivity implements ICityActivity, View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private SlideBar sb;
    private ListView lv;
    private List<CityBean> list;
    private CityAdapter adapter;

    private ICityPresenter iCityPresenter = new CityPresenter(this);

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) {
                Utils.log(CityActivity.this, "msg == null");
            } else {
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_city, null);
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
        iCityPresenter.destroy();
        if (iCityPresenter != null)
            iCityPresenter = null;
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_city_return);
        sb = (SlideBar) rootView.findViewById(R.id.sb_city);
        lv = (ListView) rootView.findViewById(R.id.lv_city);
    }

    private void initData() {
        list = new ArrayList<>();
        adapter = new CityAdapter(this, list);
        Intent intent = getIntent();
        if (intent != null) {
            CityBean c = (CityBean) intent.getSerializableExtra(IntentConfig.LOCAL_CITY);
            list.add(c);
        }
    }

    private void setData() {
        lv.setAdapter(adapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        sb.setOnTouchLetterChangeListenner(new SlideBar.OnTouchLetterChangeListenner() {
            @Override
            public void onTouchLetterChange(MotionEvent event, String s) {
                for (int i = 0; i < list.size(); i++) {
                    if (s.equals(list.get(i).getName())) {
                        lv.setSelection(i);
                    }
                }
            }
        });
        lv.setOnItemClickListener(this);
    }

    private void loadData() {
        iCityPresenter.load(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_city_return:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CityBean c = list.get(position);
        if (c != null) {
            Intent intent = new Intent();
            intent.putExtra(IntentConfig.CITY, c);
            setResult(IntentConfig.CITY_RESULT, intent);
            finish();
        }
    }

    @Override
    public void showSuccess(List<CityBean> cityBeanList) {
        list.addAll(cityBeanList);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void showFailure(String failure) {

    }

}
