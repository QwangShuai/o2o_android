package taskscreen.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ScnDiaAdapter;
import taskscreen.bean.TaskScreenBean;

public class TaskScreenActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView, scnDialogView;
    private AlertDialog scnDialog;
    private ImageView dialogCloseIv;
    private ListView scnDialogLv;
    private RelativeLayout returnRl, searchRl;
    private EditText titleEt;
    private TextView durationTv, priceTv, timeTv, typeTv, kindTv;

    private ScnDiaAdapter scnDiaAdapter;
    private List<String> scnDialogList, disList, durationList, moneyList, startTimeList, kindList, typeList;

    private int dialogState;

    private TaskScreenBean taskScreenBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_task_screen, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_job_return);
        searchRl = (RelativeLayout) rootView.findViewById(R.id.rl_screen_job_search);
        titleEt = (EditText) rootView.findViewById(R.id.et_task_screen_title);
        durationTv = (TextView) rootView.findViewById(R.id.tv_job_scn_duration);
        priceTv = (TextView) rootView.findViewById(R.id.tv_job_scn_price);
        timeTv = (TextView) rootView.findViewById(R.id.tv_job_scn_time);
        kindTv = (TextView) rootView.findViewById(R.id.tv_job_scn_kind);
    }

    private void initDialogView() {
        scnDialogView = View.inflate(this, R.layout.dialog_scn, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(scnDialogView);
        scnDialog = builder.create();
        scnDialogLv = (ListView) scnDialogView.findViewById(R.id.lv_dialog_scn);
        dialogCloseIv = (ImageView) scnDialogView.findViewById(R.id.iv_dialog_scn_close);
        dialogCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogState = 0;
                scnDialog.dismiss();
            }
        });
        scnDialog.setCanceledOnTouchOutside(false);
    }

    private void initData() {
        taskScreenBean = new TaskScreenBean();
        scnDialogList = new ArrayList<>();
        scnDiaAdapter = new ScnDiaAdapter(this, scnDialogList);
        disList = new ArrayList<>();
        durationList = new ArrayList<>();
        moneyList = new ArrayList<>();
        startTimeList = new ArrayList<>();
        kindList = new ArrayList<>();
        typeList = new ArrayList<>();
        disList.add("2公里以内");
        disList.add("5公里以内");
        disList.add("10公里以内");
        disList.add("10公里以外");
        durationList.add("2日以内");
        durationList.add("5日以内");
        durationList.add("10日以内");
        durationList.add("1月以内");
        durationList.add("1月以外");
        moneyList.add("500元以内");
        moneyList.add("1000元以内");
        moneyList.add("2000元以内");
        moneyList.add("2000元以上");
        startTimeList.add("1天以内");
        startTimeList.add("3天以内");
        startTimeList.add("1周以内");
        startTimeList.add("2周以内");
        startTimeList.add("2周以外");
        kindList.add("水泥工");
        kindList.add("瓦工");
        kindList.add("力工");
        kindList.add("搬运工");
        kindList.add("焊接工");
        kindList.add("其他工种");
        typeList.add("小型工地");
        typeList.add("个人家装");
        typeList.add("大型建筑项目");
    }

    private void setData() {
        scnDialogLv.setAdapter(scnDiaAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        searchRl.setOnClickListener(this);
        titleEt.addTextChangedListener(titleTw);
        durationTv.setOnClickListener(this);
        priceTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        typeTv.setOnClickListener(this);
        kindTv.setOnClickListener(this);
        scnDialogLv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_screen_job_return:
                finish();
                break;
            case R.id.rl_screen_job_search:
                Intent i = new Intent();
                i.putExtra("taskScreenBean", taskScreenBean);
                setResult(1, i);
                finish();
                break;
            case R.id.tv_job_scn_duration:
                dialogState = 2;
                showDialog();
                break;
            case R.id.tv_job_scn_price:
                dialogState = 3;
                showDialog();
                break;
            case R.id.tv_job_scn_time:
                dialogState = 4;
                showDialog();
                break;
            case R.id.tv_job_scn_kind:
                dialogState = 6;
                showDialog();
                break;
        }
    }

    private void showDialog() {
        scnDialogList.clear();
        switch (dialogState) {
            case 1:
                scnDialogList.addAll(disList);
                break;
            case 2:
                scnDialogList.addAll(durationList);
                break;
            case 3:
                scnDialogList.addAll(moneyList);
                break;
            case 4:
                scnDialogList.addAll(startTimeList);
                break;
            case 5:
                scnDialogList.addAll(typeList);
                break;
            case 6:
                scnDialogList.addAll(kindList);
                break;
            default:
                break;
        }
        scnDiaAdapter.notifyDataSetChanged();
        scnDialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String result = scnDialogList.get(position);
        switch (dialogState) {
            case 2:
                durationTv.setText(result);
                break;
            case 3:
                priceTv.setText(result);
                break;
            case 4:
                timeTv.setText(result);
                break;
            case 5:
                typeTv.setText(result);
                break;
            case 6:
                kindTv.setText(result);
                break;
            default:
                break;
        }
        dialogState = 0;
        scnDialog.dismiss();
    }

    TextWatcher titleTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            taskScreenBean.setT_title(s.toString());
        }
    };
}
