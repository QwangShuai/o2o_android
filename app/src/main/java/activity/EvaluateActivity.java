package activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import config.NetConfig;
import config.StateConfig;
import config.VarConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;
import view.CProgressDialog;

public class EvaluateActivity extends CommonActivity implements View.OnClickListener {

    private View rootView, emptyNetView;
    private TextView emptyNetTv;
    private CProgressDialog cpd;
    private RelativeLayout returnRl;
    private FrameLayout fl;
    private LinearLayout conLl;
    private TextView nameTv, identityTv, countTv, complainTv, priceTv, submitTv;
    private ImageView iconIv, sexIv, praise1Iv, praise2Iv, praise3Iv;
    private EditText desEt;
    private LinearLayout detailLl;

    private OkHttpClient okHttpClient;

    private int praise = 1;

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
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_evaluate, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initEmptyView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_evaluate_return);
        fl = (FrameLayout) rootView.findViewById(R.id.fl_evaluate);
        conLl = (LinearLayout) rootView.findViewById(R.id.ll_evaluate_con);
        nameTv = (TextView) rootView.findViewById(R.id.tv_evaluate_name);
        identityTv = (TextView) rootView.findViewById(R.id.tv_evaluate_identity);
        countTv = (TextView) rootView.findViewById(R.id.tv_evaluate_count);
        complainTv = (TextView) rootView.findViewById(R.id.tv_evaluate_complain);
        priceTv = (TextView) rootView.findViewById(R.id.tv_evaluate_price);
        iconIv = (ImageView) rootView.findViewById(R.id.iv_evaluate_icon);
        sexIv = (ImageView) rootView.findViewById(R.id.iv_evaluate_sex);
        praise1Iv = (ImageView) rootView.findViewById(R.id.iv_evaluate_praise_1);
        praise2Iv = (ImageView) rootView.findViewById(R.id.iv_evaluate_praise_2);
        praise3Iv = (ImageView) rootView.findViewById(R.id.iv_evaluate_praise_3);
        desEt = (EditText) rootView.findViewById(R.id.et_evaluate_des);
        detailLl = (LinearLayout) rootView.findViewById(R.id.ll_evaluate_detail);
        desEt = (EditText) rootView.findViewById(R.id.et_evaluate_des);
        submitTv = (TextView) rootView.findViewById(R.id.tv_evaluate_submit);
    }

    private void initEmptyView() {
        emptyNetView = LayoutInflater.from(this).inflate(R.layout.empty_net, null);
        emptyNetView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        fl.addView(emptyNetView);
        emptyNetView.setVisibility(View.GONE);
        emptyNetTv = (TextView) emptyNetView.findViewById(R.id.tv_no_net_refresh);
        emptyNetTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyNetView.setVisibility(View.GONE);

            }
        });
    }

    private void initDialogView() {
        cpd = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    @Override
    protected void initData() {
        okHttpClient = new OkHttpClient();
        iconIv.setImageResource(R.mipmap.person_face_default);
        nameTv.setText("王二妮");
        sexIv.setImageResource(R.mipmap.female);
        identityTv.setText("保洁");
        countTv.setText("好评10次");
        complainTv.setText("投诉" + "\n" + "工人");
        priceTv.setText("100");
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        complainTv.setOnClickListener(this);
        detailLl.setOnClickListener(this);
        praise1Iv.setOnClickListener(this);
        praise2Iv.setOnClickListener(this);
        praise3Iv.setOnClickListener(this);
        desEt.setOnClickListener(this);
        submitTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        loadNetData();
    }

    private void loadNetData() {
        cpd.show();
        Request request = new Request.Builder().url(NetConfig.testUrl).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    parseJson(result);
                }
            }
        });
    }

    private void parseJson(String json) {
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                handler.sendEmptyMessage(StateConfig.LOAD_DONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void notifyNet() {
        conLl.setVisibility(View.GONE);
        emptyNetView.setVisibility(View.VISIBLE);
    }

    private void notifyData() {
        conLl.setVisibility(View.VISIBLE);
        emptyNetView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_evaluate_return:
                finish();
                break;
            case R.id.tv_evaluate_complain:
                startActivity(new Intent(this, ComplainActivity.class));
                break;
            case R.id.ll_evaluate_detail:
                Utils.toast(this, VarConfig.notyetTip);
                break;
            case R.id.iv_evaluate_praise_1:
                praise = 1;
                changePraise();
                break;
            case R.id.iv_evaluate_praise_2:
                praise = 2;
                changePraise();
                break;
            case R.id.iv_evaluate_praise_3:
                praise = 3;
                changePraise();
                break;
            case R.id.et_evaluate_des:
                desEt.requestFocus();
//                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.tv_evaluate_submit:
                Utils.toast(this, "赞：" + praise + "\n描述：" + desEt.getText().toString());
                break;
            default:
                break;
        }
    }

    private void changePraise() {
        switch (praise) {
            case 1:
                praise1Iv.setImageResource(R.mipmap.praise_yes);
                praise2Iv.setImageResource(R.mipmap.praise_no);
                praise3Iv.setImageResource(R.mipmap.praise_no);
                break;
            case 2:
                praise1Iv.setImageResource(R.mipmap.praise_yes);
                praise2Iv.setImageResource(R.mipmap.praise_yes);
                praise3Iv.setImageResource(R.mipmap.praise_no);
                break;
            case 3:
                praise1Iv.setImageResource(R.mipmap.praise_yes);
                praise2Iv.setImageResource(R.mipmap.praise_yes);
                praise3Iv.setImageResource(R.mipmap.praise_yes);
                break;
            default:
                break;
        }
    }
}
