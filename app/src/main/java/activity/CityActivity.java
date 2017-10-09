package activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.CityAdapter;
import bean.CityBean;
import config.CacheConfig;
import config.IntentConfig;
import utils.Utils;
import view.SlideBar;

//城市
public class CityActivity extends CommonActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private SlideBar sb;
    private ListView lv;

    private List<CityBean> list;
    private CityAdapter adapter;
    private String[] lowerLetter;

    private String userId = "-100";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        loadComCityData();
                        break;
                    case 2:
                        adapter.notifyDataSetChanged();
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_city, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_city_return);
        sb = (SlideBar) rootView.findViewById(R.id.sb_city);
        lv = (ListView) rootView.findViewById(R.id.lv_city);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new CityAdapter(this, list);
        lowerLetter = getResources().getStringArray(R.array.lowerletter);
        Intent intent = getIntent();
        if (intent != null) {
            CityBean c = (CityBean) intent.getSerializableExtra(IntentConfig.LOCAL_CITY);
            list.add(c);
        }
    }

    @Override
    protected void setData() {
        lv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
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

    @Override
    protected void loadData() {
        loadHotCityData();
    }

    private void loadHotCityData() {
        String hotCityJson = Utils.readCache(CityActivity.this, userId, CacheConfig.hotCity);
        if (!TextUtils.isEmpty(hotCityJson)) {
            parseHotJson(hotCityJson);
        }
    }

    private void loadComCityData() {
        String comCityJson = Utils.readCache(CityActivity.this, userId, CacheConfig.comCity);
        if (!TextUtils.isEmpty(comCityJson)) {
            parseComJson(comCityJson);
        }
    }

    private void parseHotJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                CityBean b = new CityBean();
                b.setId("-1");
                b.setName("热门城市");
                list.add(b);
                JSONArray arrData = objBean.optJSONArray("data");
                for (int i = 0; i < arrData.length(); i++) {
                    JSONObject o = arrData.optJSONObject(i);
                    if (o != null) {
                        CityBean cb = new CityBean();
                        cb.setId(o.optString("r_id"));
                        cb.setName(o.optString("r_name"));
                        list.add(cb);
                    }
                }
                handler.sendEmptyMessage(1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseComJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                JSONObject objData = objBean.optJSONObject("data");
                for (int i = 0; i < lowerLetter.length; i++) {
                    JSONArray lowArr = objData.optJSONArray(lowerLetter[i]);
                    if (lowArr != null) {
                        CityBean cb = new CityBean();
                        cb.setId("-1");
                        cb.setName(lowerLetter[i].toUpperCase());
                        list.add(cb);
                        for (int j = 0; j < lowArr.length(); j++) {
                            JSONObject o = lowArr.optJSONObject(j);
                            if (o != null) {
                                CityBean c = new CityBean();
                                c.setId(o.optString("r_id"));
                                c.setName(o.optString("r_name"));
                                list.add(c);
                            }
                        }
                    }
                }
                handler.sendEmptyMessage(2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
}
