package activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.WorkerAdapter;
import bean.PositionBean;
import bean.ScreenBean;
import bean.WorkerBean;
import config.CodeConfig;
import config.StateConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import refreshload.PullToRefreshLayout;
import refreshload.PullableListView;
import utils.Utils;

//工人
public class WorkerActivity extends CommonActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout screenRl;
    private PullToRefreshLayout ptrl;
    private PullableListView plv;
    private List<WorkerBean> list;
    private WorkerAdapter adapter;
    private int state = StateConfig.LOAD_DONE;
    private OkHttpClient okHttpClient;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) {
                Utils.log(WorkerActivity.this, "msg = null");
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
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_worker, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_return);
        screenRl = (RelativeLayout) rootView.findViewById(R.id.rl_worker_screen);
        ptrl = (PullToRefreshLayout) rootView.findViewById(R.id.ptrl);
        plv = (PullableListView) rootView.findViewById(R.id.plv);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        adapter = new WorkerAdapter(WorkerActivity.this, list);
        okHttpClient = new OkHttpClient();
    }

    @Override
    protected void setData() {
        plv.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        screenRl.setOnClickListener(this);
        ptrl.setOnRefreshListener(this);
        plv.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        PositionBean positionBean = new PositionBean();
        positionBean.setPositionX("126.65771686");
        positionBean.setPositionY("45");
        String url = Utils.getWorkerUrl("2", positionBean);
        if (url == null) {
            Utils.log(WorkerActivity.this, "url = null");
        } else {
            Request request = new Request.Builder().url(url).get().build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String result = response.body().string();
                        String json = Utils.cutJson(result);
                        Utils.log(WorkerActivity.this, "json:" + json);
                        parseJson(json);
                    }
                }
            });
        }
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 1) {
                JSONObject objData = objBean.optJSONObject("data");
                if (objData == null) {
                    Utils.log(WorkerActivity.this, "objData = null");
                } else {
                    JSONArray arrData = objData.optJSONArray("data");
                    if (arrData == null) {
                        Utils.log(WorkerActivity.this, "arrData = null");
                    } else {
                        for (int i = 0; i < arrData.length(); i++) {
                            JSONObject o = arrData.optJSONObject(i);
                            if (o == null) {
                                Utils.log(WorkerActivity.this, "o = null");
                            } else {
                                WorkerBean wb = new WorkerBean();
                                wb.setId(o.optString("u_id"));
                                wb.setIcon(o.optString("u_img"));
                                wb.setName(o.optString("u_true_name"));
                                wb.setBrief(o.optString("uei_info"));
                                wb.setStatus(o.optString("u_task_status"));
                                wb.setDistance("离我" + o.optDouble("distance") + "公里");
                                wb.setPositionX(o.optString("ucp_posit_x"));
                                wb.setPositionY(o.optString("ucp_posit_y"));
                                Utils.log(WorkerActivity.this, "wb:" + wb.toString());
                                list.add(wb);
                            }
                        }
                        handler.sendEmptyMessage(1);
                    }
                }
            } else {
                Utils.log(WorkerActivity.this, "code:" + objBean.optInt("code"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Utils.log(WorkerActivity.this, "exception:" + e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_worker_return:
                finish();
                break;
            case R.id.rl_worker_screen:
                startActivityForResult(new Intent(this, WorkerScnActivity.class), CodeConfig.screenRequestCode);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, TalkActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeConfig.screenRequestCode && resultCode == CodeConfig.screenResultCode && data != null) {
            ScreenBean screenBean = (ScreenBean) data.getSerializableExtra("screenBean");
            if (screenBean != null) {

            }
        }
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
