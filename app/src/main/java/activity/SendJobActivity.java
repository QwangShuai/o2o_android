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

    //根视图
    private View rootView;
    //返回视图
    private RelativeLayout returnRl;
    //增加工种视图
    private RelativeLayout addRl;
    //标题视图
    private EditText projectNameEt;
    //描述视图
    private EditText projectDesEt;
    //工程类型视图
    private TextView projectKindTv;
    //所在区域
    private TextView projectAreaTv;
    //详细地址
    private EditText projectAddressEt;
    //ListView视图
    private ListView projectLv;
    //确认提交视图
    private TextView projectSubmitTv;
    //招聘工种数据类集合
    private List<JobKindBean> jobKindBeanList;
    //招聘工种数据适配器
    private JobKindAdapter jobKindAdapter;
    //发布工作数据类
    private SendJobBean sendJobBean;

    //项目类型对话框视图
    private AlertDialog kindDialog;
    private View kindDialogView;
    private ListView kindDialogLv;

    //项目类型数据集合
    private List<String> kindList;
    //项目类型数据适配器
    private ScnDiaAdapter kindAdapter;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_send_job,null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        //初始化返回视图
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_send_job_return);
        //初始化增加工种视图
        addRl = (RelativeLayout) rootView.findViewById(R.id.rl_send_job_add);
        //初始化标题视图
        projectNameEt = (EditText) rootView.findViewById(R.id.et_send_job_project_name);
        //初始化描述视图
        projectDesEt = (EditText) rootView.findViewById(R.id.et_send_job_project_des);
        //初始化工程类型视图
        projectKindTv = (TextView) rootView.findViewById(R.id.tv_send_job_project_kind);
        //初始化所在区域视图
        projectAreaTv = (TextView) rootView.findViewById(R.id.tv_send_job_project_area);
        //初始化详细地址视图
        projectAddressEt = (EditText) rootView.findViewById(R.id.et_send_job_project_address);
        //初始化ListView视图
        projectLv = (ListView) rootView.findViewById(R.id.lv_send_job);
        //初始化确认提交视图
        projectSubmitTv = (TextView) rootView.findViewById(R.id.tv_send_job_submit);
    }

    private void initDialogView() {
        //初始化项目类型对话框视图
        kindDialogView = View.inflate(this, R.layout.dialog_scn, null);
        kindDialog = new AlertDialog.Builder(this).setView(kindDialogView).create();
        kindDialog.setCanceledOnTouchOutside(false);
        kindDialogLv = (ListView) kindDialogView.findViewById(R.id.lv_dialog_scn);
    }

    @Override
    protected void initData() {
        //初始化项目类型数据集合
        kindList = new ArrayList<>();
        //初始化项目类型数据适配器
        kindAdapter = new ScnDiaAdapter(this, kindList);
        //初始化招聘工种数据类集合
        jobKindBeanList = new ArrayList<>();
        //初始化招聘工种数据适配器
        jobKindAdapter = new JobKindAdapter(this, jobKindBeanList, this);
        //初始化发布工作数据类
        sendJobBean = new SendJobBean();
    }

    @Override
    protected void setData() {
        //绑定项目类型数据适配器
        kindDialogLv.setAdapter(kindAdapter);
        //绑定招聘工种数据适配器
        projectLv.setAdapter(jobKindAdapter);
    }

    @Override
    protected void setListener() {
        //返回视图监听
        returnRl.setOnClickListener(this);
        //增加工种视图监听
        addRl.setOnClickListener(this);
        //标题视图监听
        projectNameEt.addTextChangedListener(nameTw);
        //描述视图监听
        projectDesEt.addTextChangedListener(desTw);
        //工程类型视图监听
        projectKindTv.setOnClickListener(this);
        //工程类型ListView视图监听
        kindDialogLv.setOnItemClickListener(this);
        //所在区域视图监听
        projectAreaTv.setOnClickListener(this);
        //详细地址视图监听
        projectAddressEt.addTextChangedListener(addressTw);
        //确认提交视图监听
        projectSubmitTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        //加载工程类型数据
        kindList.add("小型工地");
        kindList.add("个人家装");
        kindList.add("大型建筑项目");
        kindAdapter.notifyDataSetChanged();
        //加载招聘工种数据
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
            //返回视图点击事件
            case R.id.rl_send_job_return:
                finish();
                break;
            //增加工种视图点击事件
            case R.id.rl_send_job_add:
                addKind();
                break;
            //工程类型视图点击事件
            case R.id.tv_send_job_project_kind:
                kindDialog.show();
                break;
            //所在区域视图点击事件
            case R.id.tv_send_job_project_area:
                Utils.toast(this, "选择工作所在区域");
                break;
            //确认提交视图点击事件
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
            //项目类型ListView
            case R.id.lv_dialog_scn:
                projectKindTv.setText(kindList.get(position));
                sendJobBean.setKind(projectKindTv.getText().toString());
                kindDialog.dismiss();
                break;
        }
    }
}
