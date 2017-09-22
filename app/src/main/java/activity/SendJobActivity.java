package activity;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.JobKindAdapter;
import adapter.ScnDiaAdapter;
import bean.JobKindBean;
import bean.SendJobBean;
import listener.ListItemClickHelp;
import utils.Utils;

public class SendJobActivity extends CommonActivity implements View.OnClickListener, AdapterView.OnItemClickListener, ListItemClickHelp {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout addRl;
    private EditText projectNameEt;
    private EditText projectDesEt;
    private TextView projectKindTv;
    private TextView projectAreaTv;
    private EditText projectAddressEt;
    private ListView projectLv;
    private TextView projectSubmitTv;
    private List<JobKindBean> jobKindBeanList;
    private JobKindAdapter jobKindAdapter;
    private SendJobBean sendJobBean;
    private AlertDialog kindDialog;
    private View kindDialogView;
    private ListView kindDialogLv;
    private List<String> kindList;
    private ScnDiaAdapter kindAdapter;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_send_job, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_send_job_return);
        addRl = (RelativeLayout) rootView.findViewById(R.id.rl_send_job_add);
        projectNameEt = (EditText) rootView.findViewById(R.id.et_send_job_project_name);
        projectDesEt = (EditText) rootView.findViewById(R.id.et_send_job_project_des);
        projectKindTv = (TextView) rootView.findViewById(R.id.tv_send_job_project_kind);
        projectAreaTv = (TextView) rootView.findViewById(R.id.tv_send_job_project_area);
        projectAddressEt = (EditText) rootView.findViewById(R.id.et_send_job_project_address);
        projectLv = (ListView) rootView.findViewById(R.id.lv_send_job);
        projectSubmitTv = (TextView) rootView.findViewById(R.id.tv_send_job_submit);
    }

    private void initDialogView() {
        kindDialogView = View.inflate(this, R.layout.dialog_scn, null);
        kindDialog = new AlertDialog.Builder(this).setView(kindDialogView).create();
        kindDialog.setCanceledOnTouchOutside(false);
        kindDialogLv = (ListView) kindDialogView.findViewById(R.id.lv_dialog_scn);
    }

    @Override
    protected void initData() {
        kindList = new ArrayList<>();
        kindAdapter = new ScnDiaAdapter(this, kindList);
        jobKindBeanList = new ArrayList<>();
        jobKindAdapter = new JobKindAdapter(this, jobKindBeanList, this);
        sendJobBean = new SendJobBean();
    }

    @Override
    protected void setData() {
        kindDialogLv.setAdapter(kindAdapter);
        projectLv.setAdapter(jobKindAdapter);
    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        addRl.setOnClickListener(this);
        projectNameEt.addTextChangedListener(nameTw);
        projectDesEt.addTextChangedListener(desTw);
        projectKindTv.setOnClickListener(this);
        kindDialogLv.setOnItemClickListener(this);
        projectAreaTv.setOnClickListener(this);
        projectAddressEt.addTextChangedListener(addressTw);
        projectSubmitTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        kindList.add("小型工地");
        kindList.add("个人家装");
        kindList.add("大型建筑项目");
        kindAdapter.notifyDataSetChanged();
        JobKindBean jobKindBean = new JobKindBean();
        jobKindBean.setDel(false);
        jobKindBeanList.add(jobKindBean);
        jobKindAdapter.notifyDataSetChanged();
        Utils.setListViewHeight(projectLv);
    }

    private void addKind() {
        JobKindBean jobKindBean = new JobKindBean();
        jobKindBean.setDel(true);
        jobKindBeanList.add(jobKindBean);
        jobKindAdapter.notifyDataSetChanged();
        Utils.setListViewHeight(projectLv);
    }

    private void judge() {
        if (TextUtils.isEmpty(sendJobBean.getName())) {
            Utils.toast(this, "请输入标题");
            return;
        } else if (TextUtils.isEmpty(sendJobBean.getDes())) {
            Utils.toast(this, "请输入描述");
            return;
        } else if (TextUtils.isEmpty(sendJobBean.getKind())) {
            Utils.toast(this, "请选择工程类型");
            return;
        } else {
            Utils.toast(this, sendJobBean.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_send_job_return:
                finish();
                break;
            case R.id.rl_send_job_add:
                addKind();
                break;
            case R.id.tv_send_job_project_kind:
                kindDialog.show();
                break;
            case R.id.tv_send_job_project_area:
                Utils.toast(this, "选择工作所在区域");
                break;
            case R.id.tv_send_job_submit:
                judge();
                break;
        }
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        switch (which) {
            case R.id.tv_item_send_job_del:
                Utils.toast(this, "删除该工种" + position);
                jobKindBeanList.remove(position);
                jobKindAdapter.notifyDataSetChanged();
                Utils.setListViewHeight(projectLv);
                break;
        }
    }

    private TextWatcher nameTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            sendJobBean.setName(s.toString());
        }
    };

    private TextWatcher desTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            sendJobBean.setDes(s.toString());
        }
    };

    private TextWatcher addressTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            sendJobBean.setAdddress(s.toString());
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_dialog_scn:
                projectKindTv.setText(kindList.get(position));
                sendJobBean.setKind(projectKindTv.getText().toString());
                kindDialog.dismiss();
                break;
        }
    }
}
