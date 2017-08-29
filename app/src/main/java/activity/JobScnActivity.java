package activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ScnDiaAdapter;
import adapter.ScnJobCdtAdapter;
import bean.ScnJob;
import listener.ListItemClickHelp;
import utils.Utils;

public class JobScnActivity extends AppCompatActivity implements View.OnClickListener, ListItemClickHelp, AdapterView.OnItemClickListener {

    private View rootView, scnDialogView;
    private AlertDialog scnDialog;
    private ListView scnDialogLv;
    private RelativeLayout returnRl, searchRl;
    private ListView jobScnCdtLv;

    private ScnJob scnJobCdt;
    private ScnJobCdtAdapter scnJobCdtAdapter;

    private ScnDiaAdapter scnDiaAdapter;
    private List<String> scnDialogList, disList, durationList, moneyList, startTimeList, kindList, typeList;
    private int scnDiaPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_scn_job, null);
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
        jobScnCdtLv = (ListView) rootView.findViewById(R.id.lv_scn_job_cdt);
    }

    private void initDialogView() {
        scnDialogView = View.inflate(this, R.layout.dialog_screen, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(scnDialogView);
        scnDialog = builder.create();
        scnDialogLv = (ListView) scnDialogView.findViewById(R.id.lv_dialog_screen);
    }

    private void initData() {
        scnJobCdt = new ScnJob();
        scnJobCdt.setName("");
        scnJobCdt.setNameHint("输入职位");
        scnJobCdt.setDisTitle("搜索范围：");
        scnJobCdt.setDisContent("请选择搜索范围");
        scnJobCdt.setDurationTitle("项目工期：");
        scnJobCdt.setDurationContent("请选择项目工期");
        scnJobCdt.setMoneyTitle("工资金额：");
        scnJobCdt.setMoneyContent("请选择工资金额");
        scnJobCdt.setStartTimeTitle("开始时间：");
        scnJobCdt.setStartTimeContent("请选择项目开始时间");
        scnJobCdt.setKindTitle("招聘工种：");
        scnJobCdt.setKindContent("请选择招聘工种");
        scnJobCdt.setTypeTitle("项目类型：");
        scnJobCdt.setTypeContent("请选择项目类型");
        scnJobCdtAdapter = new ScnJobCdtAdapter(this, scnJobCdt, this);

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
        jobScnCdtLv.setAdapter(scnJobCdtAdapter);
        Utils.setListViewHeight(jobScnCdtLv);
        scnDialogLv.setAdapter(scnDiaAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        searchRl.setOnClickListener(this);
        scnDialogLv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_screen_job_return:
                finish();
                break;
            case R.id.rl_screen_job_search:
                Utils.toast(this, scnJobCdt.toString());
                break;
        }
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        switch (which) {
            case R.id.ll_item_scn_job_cdt_click:
                scnDialogList.clear();
                switch (position) {
                    case 1:
                        scnDiaPos = 1;
                        scnDialogList.addAll(disList);
                        break;
                    case 2:
                        scnDiaPos = 2;
                        scnDialogList.addAll(durationList);
                        break;
                    case 3:
                        scnDiaPos = 3;
                        scnDialogList.addAll(moneyList);
                        break;
                    case 4:
                        scnDiaPos = 4;
                        scnDialogList.addAll(startTimeList);
                        break;
                    case 5:
                        scnDiaPos = 5;
                        scnDialogList.addAll(kindList);
                        break;
                    case 6:
                        scnDiaPos = 6;
                        scnDialogList.addAll(typeList);
                        break;
                }
                scnDiaAdapter.notifyDataSetChanged();
                scnDialog.show();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String diaRes = scnDialogList.get(position);
        switch (scnDiaPos) {
            case 1:
                scnJobCdt.setDisContent(diaRes);
                break;
            case 2:
                scnJobCdt.setDurationContent(diaRes);
                break;
            case 3:
                scnJobCdt.setMoneyContent(diaRes);
                break;
            case 4:
                scnJobCdt.setStartTimeContent(diaRes);
                break;
            case 5:
                scnJobCdt.setKindContent(diaRes);
                break;
            case 6:
                scnJobCdt.setTypeContent(diaRes);
                break;
        }
        scnDialog.dismiss();
        scnJobCdtAdapter.notifyDataSetChanged();
    }
}
