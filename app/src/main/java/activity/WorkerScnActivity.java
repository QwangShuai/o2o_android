package activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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
import bean.Screen;
import bean.ScreenHistory;
import cache.LruJsonCache;
import config.CodeConfig;
import listener.ListItemClickHelp;
import utils.Utils;

public class WorkerScnActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, ListItemClickHelp {

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

    private List<Screen> screenList;
    private ScreenHistoryAdapter screenHistoryAdapter;

    private AlertDialog dialog;

    private int workerState = 0;
    private int dialogState = 0;

    private boolean disClick, kindClick;

    private Screen screen;

    private LruJsonCache lruJsonCache;
    private ScreenHistory screenHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_screen_worker, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    private void initView() {
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
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_screen, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        dialog = builder.create();
        dialogLv = (ListView) dialogView.findViewById(R.id.lv_dialog_screen);
    }

    private void initData() {
        kindList = new ArrayList<>();
        disList = new ArrayList<>();
        dialogList = new ArrayList<>();
        scnDiaAdapter = new ScnDiaAdapter(this, dialogList);
        lruJsonCache = LruJsonCache.get(this);
        screenList = new ArrayList<>();
        screenHistoryAdapter = new ScreenHistoryAdapter(this, screenList, this);
    }

    private void setData() {
        dialogLv.setAdapter(scnDiaAdapter);
        historyLv.setAdapter(screenHistoryAdapter);
    }

    private void setListener() {
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

    private void loadData() {
        loadKindData();
        loadDisData();
        loadHistoryData();
    }

    private void loadHistoryData() {
        if (checkHistoryData()) {
            screenList.addAll(screenHistory.getScreenList());
            screenHistoryAdapter.notifyDataSetChanged();
        }
    }

    private boolean checkHistoryData() {
        boolean b;
        screenHistory = (ScreenHistory) lruJsonCache.getAsObject("screenHistory");
        if (screenHistory == null) {
            screenHistory = new ScreenHistory();
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
            Utils.toast(this, "青选择工种");
        } else {
            saveToLocalData();
            result(screen);
        }
    }

    private void saveToLocalData() {
        screen = new Screen();
        screen.setState(workerState);
        screen.setName(nameEt.getText().toString());
        screen.setDis(disTv.getText().toString());
        screen.setKind(kindTv.getText().toString());
        if (isDif(screen)) {
            screenList.add(screen);
        }
        screenHistory.setScreenList(screenList);
        lruJsonCache.put("screenHistory", screenHistory);
    }

    private boolean isDif(Screen screen) {
        boolean b = true;
        for (int i = 0; i < screenList.size(); i++) {
            Screen o = screenList.get(i);
            if (o.getState() == screen.getState()) {
                if (o.getDis().equals(screen.getDis())) {
                    if (o.getKind().equals(screen.getKind())) {
                        b = false;
                    }
                }
            }
        }
        return b;
    }

    private void result(Screen screen) {
        Intent intent = new Intent();
        intent.putExtra("screen", screen);
        setResult(CodeConfig.screenResultCode, intent);
        finish();
    }

    private void showDia(int tarState) {
        dialogList.clear();
        switch (tarState) {
            case 0:
                dialogList.addAll(disList);
                break;
            case 1:
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
            case R.id.lv_dialog_screen:
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
                result(screenList.get(position - 1));
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
        lruJsonCache.remove("screenHistory");
        screenList.remove(p);
        screenHistoryAdapter.notifyDataSetChanged();
        screenHistory.setScreenList(screenList);
        lruJsonCache.put("screenHistory", screenHistory);
    }
}
