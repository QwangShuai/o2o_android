package activity;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.PersonDetailAdapter;
import bean.EvaluateBean;
import bean.PersonDetailBean;
import bean.RoleBean;
import config.NetConfig;
import config.StateConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;
import view.CProgressDialog;

//用户详细信息
public class PersonDetailActivity extends CommonActivity implements View.OnClickListener {

    private View rootView, emptyNetView;
    private TextView emptyNetTv;
    private TextView titleTv;
    private RelativeLayout returnRl;
    private CProgressDialog cpd;
    private ListView lv;

    private OkHttpClient okHttpClient;
    private PersonDetailBean personDetailBean;
    private PersonDetailAdapter adapter;

    private String userId = "1";

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
        handler.removeMessages(StateConfig.LOAD_DONE);
    }

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_person_detail, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initEmptyView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_detail_return);
        titleTv = (TextView) rootView.findViewById(R.id.tv_person_detail_title);
        lv = (ListView) rootView.findViewById(R.id.lv_person_detail);
    }

    private void initEmptyView() {
        emptyNetView = LayoutInflater.from(this).inflate(R.layout.empty_net, null);
        emptyNetView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ((ViewGroup) lv.getParent()).addView(emptyNetView);
        emptyNetView.setVisibility(View.GONE);
        emptyNetTv = (TextView) emptyNetView.findViewById(R.id.tv_no_net_refresh);
        emptyNetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyNetView.setVisibility(View.GONE);
                loadNetData();
            }
        });
    }

    private void initDialogView() {
        cpd = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    @Override
    protected void initData() {
        okHttpClient = new OkHttpClient();

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        loadNetData();
    }

    private void loadNetData() {
        cpd.show();
        Request request = new Request.Builder().url(NetConfig.personDetailUrl + userId).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Utils.log(PersonDetailActivity.this, "result:" + result);
                    String json = cutJson(result);
                    Utils.log(PersonDetailActivity.this, "cutJson:" + json);
                    parseJson(json);
                }
            }
        });
    }

    private String cutJson(String result) {
        int first = result.indexOf("{");
        return result.substring(first);
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 1) {
                JSONObject objData = objBean.optJSONObject("data");
                if (objData == null) {
                    Utils.log(PersonDetailActivity.this, "objData = null");
                } else {
                    JSONObject o = objData.optJSONObject("data");
                    if (o == null) {
                        Utils.log(PersonDetailActivity.this, "o = null");
                    } else {
                        personDetailBean = new PersonDetailBean();
                        personDetailBean.setIcon("");
                        personDetailBean.setName(o.optString("u_true_name"));
                        personDetailBean.setMale(o.optString("u_sex"));
                        personDetailBean.setBrief(o.optString("u_info"));
                        JSONObject objArea = o.optJSONObject("area");
                        if (objArea == null) {
                            Utils.log(PersonDetailActivity.this, "objArea = null");
                        } else {
                            personDetailBean.setAddress(objArea.optString("uei_address"));
                            personDetailBean.setHousehold(objArea.optString("user_area_name"));
                        }
                        List<RoleBean> roleBeanList = new ArrayList<>();
                        RoleBean rb0 = new RoleBean();
                        rb0.setContent("水泥工");
                        RoleBean rb1 = new RoleBean();
                        rb1.setContent("水暖工");
                        RoleBean rb2 = new RoleBean();
                        rb2.setContent("瓦工");
                        roleBeanList.add(rb0);
                        roleBeanList.add(rb1);
                        roleBeanList.add(rb2);
                        personDetailBean.setRoleBeanList(roleBeanList);
                        personDetailBean.setCount(10);
                        List<EvaluateBean> evaluateBeanList = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            EvaluateBean eb0 = new EvaluateBean();
                            eb0.setContent("小伙子干活特麻利，必须好评！");
                            eb0.setTime("2015年5月1日 10:25");
                            evaluateBeanList.add(eb0);
                        }
                        personDetailBean.setEvaluateBeanList(evaluateBeanList);
                        handler.sendEmptyMessage(StateConfig.LOAD_DONE);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyNet() {
        lv.setVisibility(View.GONE);
        emptyNetView.setVisibility(View.VISIBLE);
    }

    private void notifyData() {
        emptyNetView.setVisibility(View.GONE);
        lv.setVisibility(View.VISIBLE);
        adapter = new PersonDetailAdapter(this, personDetailBean);
        lv.setAdapter(adapter);
        titleTv.setText(personDetailBean.getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_person_detail_return:
                finish();
                break;
            default:
                break;
        }
    }
}
