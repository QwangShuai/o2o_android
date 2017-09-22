package activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.CityAdapter;
import bean.CityBean;
import config.IntentConfig;
import config.NetConfig;
import config.StateConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import view.CProgressDialog;
import view.SlideBar;

//城市
public class CityActivity extends CommonActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private CProgressDialog cpd;
    private SlideBar sb;
    private ListView lv;

    private List<CityBean> list;
    private List<CityBean> tempList;
    private CityAdapter adapter;
    private String[] lowerLetter;
    private OkHttpClient okHttpClient;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                cpd.dismiss();
                switch (msg.what) {
                    case StateConfig.LOAD_NO_NET:
                        notifyNet();
                        break;
                    case 100:
                        loadComData();
                        break;
                    case StateConfig.LOAD_DONE:
                        notifyData();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(StateConfig.LOAD_NO_NET);
        handler.removeMessages(100);
        handler.removeMessages(StateConfig.LOAD_DONE);
    }

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_city, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_city_return);
        sb = (SlideBar) rootView.findViewById(R.id.sb_city);
        lv = (ListView) rootView.findViewById(R.id.lv_city);
    }

    private void initDialogView() {
        cpd = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        tempList = new ArrayList<>();
        adapter = new CityAdapter(this, list);
        lowerLetter = getResources().getStringArray(R.array.lowerletter);
        okHttpClient = new OkHttpClient();
        Intent intent = getIntent();
        if (intent != null) {
            CityBean c = (CityBean) intent.getSerializableExtra(IntentConfig.LOCAL_CITY);
            tempList.add(c);
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
        cpd.show();
        loadHotData();
    }

    private void loadHotData() {
        Request request = new Request.Builder().url(NetConfig.baseCityUrl + NetConfig.hotCityUrl).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.e("TAG", result);
                    parseHotJson(result);
                }
            }
        });
    }

    private void parseHotJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                CityBean b = new CityBean();
                b.setId("-1");
                b.setName("热门城市");
                tempList.add(b);
                JSONArray arrData = objBean.optJSONArray("data");
                for (int i = 0; i < arrData.length(); i++) {
                    JSONObject o = arrData.optJSONObject(i);
                    if (o != null) {
                        CityBean cb = new CityBean();
                        cb.setId(o.optString("r_id"));
                        cb.setName(o.optString("r_name"));
                        tempList.add(cb);
                    }
                }
                handler.sendEmptyMessage(100);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadComData() {
        Request request = new Request.Builder().url(NetConfig.baseCityUrl + NetConfig.letterCityUrl).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    parseComJson(result);
                }
            }
        });
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
                        tempList.add(cb);
                        for (int j = 0; j < lowArr.length(); j++) {
                            JSONObject o = lowArr.optJSONObject(j);
                            if (o != null) {
                                CityBean c = new CityBean();
                                c.setId(o.optString("r_id"));
                                c.setName(o.optString("r_name"));
                                tempList.add(c);
                            }
                        }
                    }
                }
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyNet() {
        Log.e("TAG", "notifyNet");
    }

    private void notifyData() {
        list.addAll(tempList);
        adapter.notifyDataSetChanged();
        sb.setVisibility(View.VISIBLE);
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
            if (position != 0) {
                Intent intent = new Intent();
                intent.putExtra(IntentConfig.CITY, c);
                setResult(IntentConfig.CITY_RESULT, intent);
            }
            finish();
        }
    }
}
