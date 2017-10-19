package publishjob.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import listener.ListItemClickHelp;
import publishjob.adapter.PublishKindAdapter;
import publishjob.bean.PublishJobBean;
import publishjob.bean.PublishKindBean;
import selectaddress.view.SelectAddressActivity;
import taskconfirm.view.TaskConfirmActivity;
import utils.Utils;

public class PublishJobActivity extends AppCompatActivity implements View.OnClickListener, ListItemClickHelp {

    private View rootView, headView;
    private RelativeLayout returnRl, addRl, submitRl;
    private EditText titleEt, descriptionEt, addressEt;
    private TextView typeTv, areaTv;
    private ListView lv;
    private PublishKindAdapter adapter;
    private List<PublishKindBean> publishKindBeanList;

    private PublishJobBean publishJobBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_send_job, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
    }

    private void initView() {
        initRootView();
        initHeadView();
    }

    private void initData() {
        publishKindBeanList = new ArrayList<>();
        PublishKindBean publishKindBean = new PublishKindBean();
        publishKindBean.setKind("0");
        publishKindBean.setAmount("0");
        publishKindBean.setSalary("0");
        publishKindBean.setStartTime("0");
        publishKindBean.setEndTime("0");
        publishKindBeanList.add(publishKindBean);
        adapter = new PublishKindAdapter(PublishJobActivity.this, publishKindBeanList, this);
        publishJobBean = new PublishJobBean();
    }

    private void setData() {
        lv.setAdapter(adapter);
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_publish_job_return);
        addRl = (RelativeLayout) rootView.findViewById(R.id.rl_publish_job_add);
        submitRl = (RelativeLayout) rootView.findViewById(R.id.rl_publish_job_submit);
        lv = (ListView) rootView.findViewById(R.id.lv_publish_job);
    }

    private void initHeadView() {
        headView = LayoutInflater.from(PublishJobActivity.this).inflate(R.layout.head_publish_job, null);
        titleEt = (EditText) headView.findViewById(R.id.et_head_publish_job_title);
        descriptionEt = (EditText) headView.findViewById(R.id.et_head_publish_job_description);
        addressEt = (EditText) headView.findViewById(R.id.et_head_publish_job_address);
        typeTv = (TextView) headView.findViewById(R.id.tv_head_publish_job_type);
        areaTv = (TextView) headView.findViewById(R.id.tv_head_publish_job_area);
        lv.addHeaderView(headView);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        addRl.setOnClickListener(this);
        submitRl.setOnClickListener(this);
        titleEt.addTextChangedListener(titleTw);
        descriptionEt.addTextChangedListener(descriptionTw);
        addressEt.addTextChangedListener(addressTw);
        typeTv.setOnClickListener(this);
        areaTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_publish_job_return:
                finish();
                break;
            case R.id.rl_publish_job_add:
                Utils.log(PublishJobActivity.this, "增加工种");
                break;
            case R.id.rl_publish_job_submit:
                Utils.log(PublishJobActivity.this, "确认提交");
                Utils.log(PublishJobActivity.this, "PublishJobBean=" + publishJobBean.toString());
                submit();
                break;
            case R.id.tv_head_publish_job_type:
                Utils.log(PublishJobActivity.this, "选择项目类型");
                break;
            case R.id.tv_head_publish_job_area:
                Utils.log(PublishJobActivity.this, "选择工作所在区域");
                startActivity(new Intent(PublishJobActivity.this, SelectAddressActivity.class));
                break;
        }
    }

    private void submit() {
        startActivity(new Intent(PublishJobActivity.this, TaskConfirmActivity.class));
        if (TextUtils.isEmpty(publishJobBean.getTitle())) {
            Utils.toast(PublishJobActivity.this, "标题不能为空！");
        } else if (TextUtils.isEmpty(publishJobBean.getDescription())) {
            Utils.toast(PublishJobActivity.this, "描述不能为空！");
        } else if (TextUtils.isEmpty(publishJobBean.getType())) {
            Utils.toast(PublishJobActivity.this, "工程类型不能为空！");
        } else if (TextUtils.isEmpty(publishJobBean.getArea())) {
            Utils.toast(PublishJobActivity.this, "工作区域不能为空！");
        } else if (TextUtils.isEmpty(publishJobBean.getAddress())) {
            Utils.toast(PublishJobActivity.this, "详细地址不能为空！");
        }
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {

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
            publishJobBean.setTitle(s.toString());
        }
    };

    TextWatcher descriptionTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            publishJobBean.setDescription(s.toString());
        }
    };

    TextWatcher addressTw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            publishJobBean.setAddress(s.toString());
        }
    };
}
