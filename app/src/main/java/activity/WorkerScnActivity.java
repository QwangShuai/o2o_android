package activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ScnDiaAdapter;
import adapter.ScreenHistoryAdapter;
import bean.ScreenBean;
import bean.ScreenHistoryBean;
import cache.LruJsonCache;
import config.CodeConfig;
import listener.ListItemClickHelp;
import utils.Utils;

public class WorkerScnActivity extends CommonActivity implements View.OnClickListener, AdapterView.OnItemClickListener, ListItemClickHelp {

    private View rootView, headView, dialogView;
    private RelativeLayout closeRl, searchRl;
    private EditText nameEt;
    private ImageView leisureIv, talkIv, midIv;

    private LinearLayout disLl, kindLl;
    private ListView dialogLv, historyLv;
    private TextView disTv, kindTv;

    private List<String> disList;
    private List<String> kindList;
    private List<String> dialogList;
    private ScnDiaAdapter scnDiaAdapter;

    private List<ScreenBean> screenBeanList;
    private ScreenHistoryAdapter screenHistoryAdapter;

    private AlertDialog dialog;
    private TextView dialogTitleTv;
    private ImageView dialogCloseIv;

    private int workerState = 0;
    private int dialogState = 0;

    private boolean disClick, kindClick;

    private ScreenBean screenBean;

    private LruJsonCache lruJsonCache;
    private ScreenHistoryBean screenHistoryBean;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_screen_worker, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initHeadView();
        initDialogView();
    }

    private void initRootView() {
        closeRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_close);
        searchRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_search);
        nameEt = (EditText) rootView.findViewById(R.id.et_screen_name);
        leisureIv = (ImageView) rootView.findViewById(R.id.iv_screen_leisure);
        talkIv = (ImageView) rootView.findViewById(R.id.iv_screen_talk);
        midIv = (ImageView) rootView.findViewById(R.id.iv_screen_mid);
        kindLl = (LinearLayout) rootView.findViewById(R.id.ll_screen_kind);
        kindTv = (TextView) rootView.findViewById(R.id.tv_screen_kind);
        disLl = (LinearLayout) rootView.findViewById(R.id.ll_screen_dis);
        disTv = (TextView) rootView.findViewById(R.id.tv_screen_dis);
        historyLv = (ListView) rootView.findViewById(R.id.lv_screen_history);
    }

    private void initHeadView() {
        headView = LayoutInflater.from(this).inflate(R.layout.head_screen_history, null);
        historyLv.addHeaderView(headView);
    }

    private void initDialogView() {
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_scn, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialogLv = (ListView) dialogView.findViewById(R.id.lv_dialog_scn);
        dialogTitleTv = (TextView) dialogView.findViewById(R.id.tv_dialog_scn_title);
        dialogCloseIv = (ImageView) dialogView.findViewById(R.id.iv_dialog_scn_close);
        dialogCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void initData() {
        kindList = new ArrayList<>();
        disList = new ArrayList<>();
        dialogList = new ArrayList<>();
        scnDiaAdapter = new ScnDiaAdapter(this, dialogList);
        lruJsonCache = LruJsonCache.get(this);
        screenBeanList = new ArrayList<>();
        screenHistoryAdapter = new ScreenHistoryAdapter(this, screenBeanList, this);
    }

    @Override
    protected void setData() {
        dialogLv.setAdapter(scnDiaAdapter);
        historyLv.setAdapter(screenHistoryAdapter);
    }

    @Override
    protected void setListener() {
        closeRl.setOnClickListener(this);
        searchRl.setOnClickListener(this);
        leisureIv.setOnClickListener(this);
        talkIv.setOnClickListener(this);
        midIv.setOnClickListener(this);
        kindLl.setOnClickListener(this);
        dialogLv.setOnItemClickListener(this);
        disLl.setOnClickListener(this);
        historyLv.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        loadKindData();
        loadDisData();
        loadHistoryData();
    }

    private void loadHistoryData() {
        if (checkHistoryData()) {
            screenBeanList.addAll(screenHistoryBean.getScreenBeanList());
            screenHistoryAdapter.notifyDataSetChanged();
            if (screenBeanList.size() == 0) {
                historyLv.setVisibility(View.GONE);
            } else {
                historyLv.setVisibility(View.VISIBLE);
            }
        }
    }

    private boolean checkHistoryData() {
        boolean b;
        screenHistoryBean = (ScreenHistoryBean) lruJsonCache.getAsObject("screenHistoryBean");
        if (screenHistoryBean == null) {
            screenHistoryBean = new ScreenHistoryBean();
            b = false;
        } else {
            b = true;
        }
        return b;
    }

    private void loadKindData() {
        kindList.add("水泥工");
        kindList.add("搬运工");
        kindList.add("焊接工");
        kindList.add("水暖工");
        kindList.add("瓦工");
    }

    private void loadDisData() {
        disList.add("距您2公里以内");
        disList.add("距您5公里以内");
        disList.add("距您10公里以内");
        disList.add("距您10公里以外");
    }

    private void changeState(int tarState) {
        if (tarState != workerState) {
            switch (tarState) {
                case 0:
                    leisureIv.setImageResource(R.mipmap.worker_leisure);
                    talkIv.setImageResource(R.mipmap.worker_talk_g);
                    midIv.setImageResource(R.mipmap.worker_mid_g);
                    break;
                case 1:
                    leisureIv.setImageResource(R.mipmap.worker_leisure_g);
                    talkIv.setImageResource(R.mipmap.worker_talk);
                    midIv.setImageResource(R.mipmap.worker_mid_g);
                    break;
                case 2:
                    leisureIv.setImageResource(R.mipmap.worker_leisure_g);
                    talkIv.setImageResource(R.mipmap.worker_talk_g);
                    midIv.setImageResource(R.mipmap.worker_mid);
                    break;
            }
            workerState = tarState;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_screen_close:
                finish();
                break;
            case R.id.rl_screen_search:
                searchJudge();
                break;
            case R.id.iv_screen_leisure:
                changeState(0);
                break;
            case R.id.iv_screen_talk:
                changeState(1);
                break;
            case R.id.iv_screen_mid:
                changeState(2);
                break;
            case R.id.ll_screen_dis:
                showDia(0);
                break;
            case R.id.ll_screen_kind:
                showDia(1);
                break;

        }
    }

    private void searchJudge() {
        if (!disClick) {
            Utils.toast(this, "请选择搜索范围");
        } else if (!kindClick) {
            Utils.toast(this, "请选择工种");
        } else {
            saveToLocalData();
            result(screenBean);
        }
    }

    private void saveToLocalData() {
        screenBean = new ScreenBean();
        screenBean.setState(workerState);
        screenBean.setName(nameEt.getText().toString());
        screenBean.setDis(disTv.getText().toString());
        screenBean.setKind(kindTv.getText().toString());
        if (isDif(screenBean)) {
            screenBeanList.add(screenBean);
        }
        screenHistoryBean.setScreenBeanList(screenBeanList);
        lruJsonCache.put("screenHistoryBean", screenHistoryBean);
    }

    private boolean isDif(ScreenBean screenBean) {
        boolean b = true;
        for (int i = 0; i < screenBeanList.size(); i++) {
            ScreenBean o = screenBeanList.get(i);
            if (o.getState() == screenBean.getState()) {
                if (o.getDis().equals(screenBean.getDis())) {
                    if (o.getKind().equals(screenBean.getKind())) {
                        b = false;
                    }
                }
            }
        }
        return b;
    }

    private void result(ScreenBean screenBean) {
        Intent intent = new Intent();
        intent.putExtra("screenBean", screenBean);
        setResult(CodeConfig.screenResultCode, intent);
        finish();
    }

    private void showDia(int tarState) {
        dialogList.clear();
        switch (tarState) {
            case 0:
                dialogTitleTv.setText("搜索范围");
                dialogList.addAll(disList);
                break;
            case 1:
                dialogTitleTv.setText("选择工种");
                dialogList.addAll(kindList);
                break;
        }
        scnDiaAdapter.notifyDataSetChanged();
        dialog.show();
        dialogState = tarState;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_dialog_scn:
                dialog.dismiss();
                switch (dialogState) {
                    case 0:
                        disTv.setText(dialogList.get(position));
                        disClick = true;
                        break;
                    case 1:
                        kindTv.setText(dialogList.get(position));
                        kindClick = true;
                        break;
                }
                break;
            case R.id.lv_screen_history:
                result(screenBeanList.get(position - 1));
                break;
        }
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        switch (which) {
            case R.id.iv_item_screen_history_del:
                delHis(position);
                break;
        }
    }

    private void delHis(int p) {
        lruJsonCache.remove("screenHistoryBean");
        screenBeanList.remove(p);
        screenHistoryAdapter.notifyDataSetChanged();
        if (screenBeanList.size() == 0) {
            historyLv.setVisibility(View.GONE);
        } else {
            historyLv.setVisibility(View.VISIBLE);
        }
        screenHistoryBean.setScreenBeanList(screenBeanList);
        lruJsonCache.put("screenHistoryBean", screenHistoryBean);
    }
}
