package activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import adapter.TInfoTaskAdapter;
import bean.TInfoOrderBean;
import bean.TInfoTaskBean;
import bean.TInfoWorkerBean;
import bean.ToEvaluateBean;
import bean.ToJumpWorkerBean;
import config.IntentConfig;
import config.NetConfig;
import employermanage.view.EmployerManageActivity;
import listener.TInfoClickHelp;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.DataUtils;
import utils.UserUtils;
import utils.Utils;
import view.CProgressDialog;

public class EmployerToDoingActivity extends AppCompatActivity implements View.OnClickListener, TInfoClickHelp {

    private View rootView;
    private RelativeLayout returnRl;
    private ListView listView;
    private CProgressDialog cProgressDialog;
    private TInfoTaskBean tInfoTaskBean;
    private int clickWorkerPos;

    private View sureDonePopView;
    private PopupWindow sureDonePop;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        skill();
                        break;
                    case 2:
                        notifyData();
                        break;
                    case 3:
                        cProgressDialog.dismiss();
                        startActivity(new Intent(EmployerToDoingActivity.this, EmployerManageActivity.class));
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(EmployerToDoingActivity.this).inflate(R.layout.activity_employer_to_doing, null);
        setContentView(rootView);
        initView();
        setListener();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeMessages(1);
            handler.removeMessages(2);
            handler = null;
        }
    }

    private void initView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_to_doing_return);
        listView = (ListView) rootView.findViewById(R.id.lv_employer_to_doing);
        cProgressDialog = new CProgressDialog(EmployerToDoingActivity.this, R.style.dialog_cprogress);
        initPopView();
    }

    private void initPopView() {
        sureDonePopView = LayoutInflater.from(EmployerToDoingActivity.this).inflate(R.layout.pop_dialog_0, null);
        ((TextView) sureDonePopView.findViewById(R.id.tv_pop_dialog_0_content)).setText("请确认所有工人都在工作状态后再确认完工\n确认完工后您将付给工作中并未产生纠纷的工人相应的工资\n是否确认已经完工？");
        ((TextView) sureDonePopView.findViewById(R.id.tv_pop_dialog_0_cancel)).setText("还未完工");
        ((TextView) sureDonePopView.findViewById(R.id.tv_pop_dialog_0_sure)).setText("确认完工");
        sureDonePopView.findViewById(R.id.rl_pop_dialog_0_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sureDonePop.isShowing()) {
                    sureDonePop.dismiss();
                }
            }
        });
        sureDonePopView.findViewById(R.id.rl_pop_dialog_0_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sureDonePop.isShowing()) {
                    sureDonePop.dismiss();
                    TInfoWorkerBean t = tInfoTaskBean.gettInfoWorkerBeanList().get(clickWorkerPos);
                    boolean b = true;
                    for (int i = 0; i < t.gettInfoOrderBeanList().size(); i++) {
                        if (t.gettInfoOrderBeanList().get(i).getU_task_status().equals("0")) {
                            if(t.gettInfoOrderBeanList().get(i).getO_status().equals("-1") || t.gettInfoOrderBeanList().get(i).getO_status().equals("-2")){

                            }else{
                                b = false;
                            }
                            break;
                        }
                    }
                    if (b) {
                        String payOutUrl = NetConfig.orderUrl +
                                "?action=payout" +
                                "&tew_id=" + t.getTew_id() +
                                "&t_id=" + t.getT_id() +
                                "&t_author=" + UserUtils.readUserData(EmployerToDoingActivity.this).getId();
                        Utils.log(EmployerToDoingActivity.this, "payOutUrl\n" + payOutUrl);
                        sureDone(payOutUrl);
                    } else {
                        Utils.toast(EmployerToDoingActivity.this, "您有工人为洽谈状态，请先取消后再进行结算！");
                    }
                }
            }
        });
        sureDonePop = new PopupWindow(sureDonePopView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        sureDonePop.setFocusable(true);
        sureDonePop.setTouchable(true);
        sureDonePop.setOutsideTouchable(true);
        sureDonePop.setBackgroundDrawable(new BitmapDrawable());
        sureDonePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
    }

    private void loadData() {
        cProgressDialog.show();
        String t_id = getIntent().getStringExtra(IntentConfig.toEmployerToDoing);
        Utils.log(EmployerToDoingActivity.this, "t_id\n" + t_id);
        String url = NetConfig.taskBaseUrl + "?action=info&t_id=" + t_id;
        Utils.log(EmployerToDoingActivity.this, "url\n" + url);
        Request request = new Request
                .Builder()
                .url(url)
                .get()
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Utils.log(EmployerToDoingActivity.this, "json\n" + json);
                    tInfoTaskBean = DataUtils.getTInfoTaskBean(json);
                    if (tInfoTaskBean != null) {
                        for (int i = 0; i < tInfoTaskBean.gettInfoWorkerBeanList().size(); i++) {
                            if (tInfoTaskBean.gettInfoWorkerBeanList().get(i).gettInfoOrderBeanList() == null || tInfoTaskBean.gettInfoWorkerBeanList().get(i).gettInfoOrderBeanList().size() == 0) {
                                tInfoTaskBean.gettInfoWorkerBeanList().remove(i);
                            }
                        }
                        Utils.log(EmployerToDoingActivity.this, "tInfoTaskBean\n" + tInfoTaskBean.toString());
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        });

    }

    private void skill() {
        OkHttpClient okhttpClient = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(NetConfig.skillsUrl)
                .get()
                .build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Utils.log(EmployerToDoingActivity.this, "skillJson\n" + json);
                    for (int i = 0; i < tInfoTaskBean.gettInfoWorkerBeanList().size(); i++) {
                        String skillId = tInfoTaskBean.gettInfoWorkerBeanList().get(i).getTew_skills();
                        String skillName = DataUtils.getSkillName(json, skillId);
                        tInfoTaskBean.gettInfoWorkerBeanList().get(i).setSkill(skillName);
                        for (int j = 0; j < tInfoTaskBean.gettInfoWorkerBeanList().get(i).gettInfoOrderBeanList().size(); j++) {
                            tInfoTaskBean.gettInfoWorkerBeanList().get(i).gettInfoOrderBeanList().get(j).setSkill(skillName);
                        }
                    }
                    handler.sendEmptyMessage(2);
                }
            }
        });
    }

    private void notifyData() {
        cProgressDialog.dismiss();
        TInfoTaskAdapter tInfoTaskAdapter = new TInfoTaskAdapter(EmployerToDoingActivity.this, tInfoTaskBean, this);
        listView.setAdapter(tInfoTaskAdapter);
    }

    private void sureDone(String payoutUrl) {
        cProgressDialog.show();
        OkHttpClient okhttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(payoutUrl).get().build();
        okhttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Utils.log(EmployerToDoingActivity.this, "sureDone\n" + json);
                    try {
                        JSONObject beanObj = new JSONObject(json);
                        if (beanObj.optInt("code") == 200) {
                            if (beanObj.optString("data").equals("successs")) {
                                handler.sendEmptyMessage(3);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_employer_to_doing_return:
                finish();
                break;
        }
    }

    @Override
    public void onClick(int id, int outerPos, int innerPos, String orderId) {
        switch (id) {
            case R.id.ll_item_tinfotask_inner:
                for (int i = 0; i < tInfoTaskBean.gettInfoWorkerBeanList().size(); i++) {
                    if (tInfoTaskBean.gettInfoWorkerBeanList().get(i).gettInfoOrderBeanList().get(innerPos).getO_id().equals(orderId)) {
                        Utils.log(EmployerToDoingActivity.this, "outerPos\n" + i + "\ninnerPos\n" + innerPos);
                        int out = i;
                        int in = innerPos;
                        TInfoOrderBean tInfoOrderBean = tInfoTaskBean.gettInfoWorkerBeanList().get(out).gettInfoOrderBeanList().get(in);
                        Utils.log(EmployerToDoingActivity.this, "tInfoOrderBean\n" + tInfoOrderBean.toString());
                        String o_status = tInfoOrderBean.getO_status();
                        if (o_status.equals("-1")) {

                        } else if (o_status.equals("-2")) {

                        } else {
                            ToJumpWorkerBean toJumpWorkerBean = new ToJumpWorkerBean();
                            toJumpWorkerBean.setS_name(tInfoOrderBean.getSkill());
                            toJumpWorkerBean.setSkillId(tInfoOrderBean.getS_id());
                            toJumpWorkerBean.setTaskId(tInfoOrderBean.getT_id());
                            toJumpWorkerBean.setTewId(tInfoOrderBean.getTew_id());
                            toJumpWorkerBean.setWorkerId(tInfoOrderBean.getO_worker());
                            toJumpWorkerBean.setTew_start_time(tInfoOrderBean.getO_start_time());
                            toJumpWorkerBean.setTew_end_time(tInfoOrderBean.getO_end_time());
                            toJumpWorkerBean.setOrderId(tInfoOrderBean.getO_id());
                            toJumpWorkerBean.setTewPrice(tInfoOrderBean.getO_amount());
                            toJumpWorkerBean.setO_confirm(tInfoOrderBean.getO_confirm());
                            toJumpWorkerBean.setO_status(tInfoOrderBean.getO_status());
                            Intent jumpWorkerIntent = new Intent(EmployerToDoingActivity.this, JumpWorkerActivity.class);
                            jumpWorkerIntent.putExtra(IntentConfig.toJumpWorker, toJumpWorkerBean);
                            startActivity(jumpWorkerIntent);
                        }
                        break;
                    }
                }
                break;
            case R.id.iv_item_tinfotask_inner_mobile:
                for (int i = 0; i < tInfoTaskBean.gettInfoWorkerBeanList().size(); i++) {
                    if (tInfoTaskBean.gettInfoWorkerBeanList().get(i).gettInfoOrderBeanList().get(innerPos).getO_id().equals(orderId)) {
                        Utils.log(EmployerToDoingActivity.this, "outerPos\n" + i + "\ninnerPos\n" + innerPos);
                        int out = i;
                        int in = innerPos;
                        TInfoOrderBean tInfoOrderBean = tInfoTaskBean.gettInfoWorkerBeanList().get(out).gettInfoOrderBeanList().get(in);
                        Utils.log(EmployerToDoingActivity.this, "tInfoOrderBean\n" + tInfoOrderBean.toString());
                        break;
                    }
                }
                break;
            case R.id.tv_item_tinfotask_button:
                clickWorkerPos = outerPos;
                if (!sureDonePop.isShowing()) {
                    backgroundAlpha(0.5f);
                    sureDonePop.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                }
                Utils.log(EmployerToDoingActivity.this, "button outerPos\n" + outerPos);
                break;
            case R.id.tv_item_tinfotask_inner_evaluate:
                for (int i = 0; i < tInfoTaskBean.gettInfoWorkerBeanList().size(); i++) {
                    if (tInfoTaskBean.gettInfoWorkerBeanList().get(i).gettInfoOrderBeanList().get(innerPos).getO_id().equals(orderId)) {
                        Utils.log(EmployerToDoingActivity.this, "outerPos\n" + i + "\ninnerPos\n" + innerPos);
                        int out = i;
                        int in = innerPos;
                        TInfoOrderBean tInfoOrderBean = tInfoTaskBean.gettInfoWorkerBeanList().get(out).gettInfoOrderBeanList().get(in);
                        Utils.log(EmployerToDoingActivity.this, "评价\n" + tInfoOrderBean.toString());
                        ToEvaluateBean toEvaluateBean = new ToEvaluateBean();
                        toEvaluateBean.setT_id(tInfoOrderBean.getT_id());
                        toEvaluateBean.setTc_u_id(tInfoOrderBean.getO_worker());
                        toEvaluateBean.setTce_desc("");
                        toEvaluateBean.setTc_type("0");
                        toEvaluateBean.setTc_start("1");
                        toEvaluateBean.setSkill(tInfoOrderBean.getSkill());
                        Intent evaluateIntent = new Intent(EmployerToDoingActivity.this, EvaluateActivity.class);
                        evaluateIntent.putExtra(IntentConfig.toEvaluate, toEvaluateBean);
                        startActivity(evaluateIntent);
                        break;
                    }
                }
                break;
        }
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = f;
        getWindow().setAttributes(layoutParams);
    }
}
