package activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.DetailAdapter;
import bean.Detail;
import config.NetConfig;
import config.StateConfig;
import listener.OnRefreshListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.Utils;
import view.CListView;
import view.CProgressDialog;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, OnRefreshListener {

    private View rootView, menuPopView;
    private RelativeLayout returnRl;
    private LinearLayout menuLl;
    private CListView listView;
    private CProgressDialog progressDialog;
    private PopupWindow menuPopWindow;
    private RelativeLayout menuAllRl, menuOutRl, menuInRl;
    private TextView menuContentTv;

    private List<Detail> detailList;
    private DetailAdapter detailAdapter;

    private OkHttpClient okHttpClient;

    private int LOAD_STATE;
    private String tip;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                stopAnim();
                switch (msg.what) {
                    case StateConfig.LOAD_NO_NET:
                        notifyNoNet();
                        break;
                    case StateConfig.LOAD_DONE:
                        notifyData();
                        break;
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_detail, null);
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
        handler.removeMessages(StateConfig.LOAD_NO_NET);
        handler.removeMessages(StateConfig.LOAD_DONE);
    }

    private void initView() {
        initRootView();
        initDialogView();
        initPopWindowView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_detail_return);
        listView = (CListView) rootView.findViewById(R.id.lv_detail);
        menuLl = (LinearLayout) rootView.findViewById(R.id.ll_detail_menu);
        menuContentTv = (TextView) rootView.findViewById(R.id.tv_detail_menu_content);
    }

    private void initDialogView() {
        progressDialog = new CProgressDialog(this, R.style.dialog_cprogress);
    }

    private void initPopWindowView() {
        menuPopView = LayoutInflater.from(this).inflate(R.layout.popwindow_detail_menu, null);
        menuAllRl = (RelativeLayout) menuPopView.findViewById(R.id.rl_popwindow_detail_menu_all);
        menuOutRl = (RelativeLayout) menuPopView.findViewById(R.id.rl_popwindow_detail_menu_out);
        menuInRl = (RelativeLayout) menuPopView.findViewById(R.id.rl_popwindow_detail_menu_in);
        menuPopWindow = new PopupWindow(menuPopView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        menuPopWindow.setAnimationStyle(R.style.popwin_anim_style);
        menuPopWindow.setFocusable(true);
        menuPopWindow.setTouchable(true);
        menuPopWindow.setOutsideTouchable(true);
        menuPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    private void initData() {
        detailList = new ArrayList<>();
        detailAdapter = new DetailAdapter(this, detailList);
        okHttpClient = new OkHttpClient();
    }

    private void setData() {
        listView.setAdapter(detailAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        menuLl.setOnClickListener(this);
        menuAllRl.setOnClickListener(this);
        menuOutRl.setOnClickListener(this);
        menuInRl.setOnClickListener(this);
        listView.setOnRefreshListener(this);
    }

    private void loadData() {
        startAnim();
        if (checkLocalData()) {
            loadLocalData();
        } else {
            loadNetData();
        }
    }

    private boolean checkLocalData() {
        return false;
    }

    private void loadLocalData() {

    }

    private void loadNetData() {
        Request request = new Request.Builder().url(NetConfig.testUrl).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(StateConfig.LOAD_NO_NET);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        switch (LOAD_STATE) {
                            case StateConfig.LOAD_REFRESH:
                                detailList.clear();
                                break;
                        }
                        saveToLocalData(json);
                        if (parseJson(json))
                            handler.sendEmptyMessage(StateConfig.LOAD_DONE);
                    }
                }
            }
        });
    }

    private void startAnim() {
        progressDialog.show();
    }

    private void stopAnim() {
        progressDialog.dismiss();
    }

    private boolean parseJson(String json) {
        boolean b = false;
        try {
            JSONObject objBean = new JSONObject(json);
            if (objBean.optInt("code") == 200) {
                Detail d0 = new Detail();
                d0.setTitle("支出");
                d0.setDes("dfdfd");
                d0.setTime("derer");
                d0.setBalance("gtfbrt");
                detailList.add(d0);
                b = true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b;
    }

    private void saveToLocalData(String json) {

    }

    private void notifyNoNet() {
        switch (LOAD_STATE) {
            case StateConfig.LOAD_REFRESH:
                listView.hideHeadView();
                tip = StateConfig.loadRefreshFailure;
                break;
            case StateConfig.LOAD_LOAD:
                listView.hideFootView();
                tip = StateConfig.loadLoadFailure;
                break;
        }
        showTip(tip);
    }

    private void notifyData() {
        switch (LOAD_STATE) {
            case StateConfig.LOAD_REFRESH:
                listView.hideHeadView();
                tip = StateConfig.loadRefreshSuccess;
                break;
            case StateConfig.LOAD_LOAD:
                listView.hideFootView();
                tip = StateConfig.loadLoadSuccess;
                break;
        }
        detailAdapter.notifyDataSetChanged();
        showTip(tip);
    }

    private void showTip(String tip) {
        if (!TextUtils.isEmpty(tip))
            Utils.toast(this, tip);

    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_detail_return:
                finish();
                break;
            case R.id.ll_detail_menu:
                if (!menuPopWindow.isShowing()) {
                    menuPopWindow.showAsDropDown(menuLl, -10, 10);
                    backgroundAlpha(0.8f);
                }
                break;
            case R.id.rl_popwindow_detail_menu_all:
                if (menuPopWindow.isShowing()) {
                    menuPopWindow.dismiss();
                    menuContentTv.setText("全部");
                }
                break;
            case R.id.rl_popwindow_detail_menu_out:
                if (menuPopWindow.isShowing()) {
                    menuPopWindow.dismiss();
                    menuContentTv.setText("支出");
                }
                break;
            case R.id.rl_popwindow_detail_menu_in:
                if (menuPopWindow.isShowing()) {
                    menuPopWindow.dismiss();
                    menuContentTv.setText("收入");
                }
                break;
        }
    }

    @Override
    public void onDownPullRefresh() {
        LOAD_STATE = StateConfig.LOAD_REFRESH;
        loadNetData();
    }

    @Override
    public void onLoadingMore() {
        LOAD_STATE = StateConfig.LOAD_LOAD;
        loadNetData();
    }
}
