package fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import activity.PersonManageActivity;
import adapter.AddKindAdapter;
import adapter.RoleAdapter;
import bean.AddKind;
import bean.PersonPreview;
import bean.Role;
import listener.ListItemClickHelp;
import utils.Utils;

/**
 * 创建日期：2017/8/10 on 14:32
 * 作者:孙明明
 * 描述:编辑信息
 */

public class PersonManageEditFragment extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener, ListItemClickHelp {

    //根视图
    private View rootView;
    //姓名视图
    private EditText nameEt;
    //性别视图
    private RadioGroup sexRg;
    //身份证号视图
    private EditText idNumberEt;
    //现居地视图
    private TextView addressTv;
    //户口所在地视图
    private TextView houseHoldTv;
    //个人简介视图
    private EditText briefEt;
    //手机号码视图
    private TextView phoneTv;
    //角色名称视图
    private RadioGroup roleRg;
    //我是工人视图
    private LinearLayout workerLl;
    private GridView workerGv;
    private TextView addWorkerTv;
    //提交信息视图
    private TextView submitTv;

    //添加工种对话框视图
    private AlertDialog addKindDialog;
    private View addKindDialogView;
    private ListView addKindDialogLv;
    private TextView addKindDialogNoTv;
    private TextView addKindDialogYesTv;

    //工人种类数据类集合
    private List<Role> roleList;
    //工人种类适配器
    private RoleAdapter roleAdapter;

    //添加工种数据类集合
    private List<AddKind> addKindList;
    //添加工种适配器
    private AddKindAdapter addKindAdapter;

    //信息预览数据类
    private PersonPreview personPreview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person_manage_edit, null);
        initView();
        initData();
        setData();
        setListener();
        loadData();
        return rootView;
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        //初始化姓名视图
        nameEt = (EditText) rootView.findViewById(R.id.et_person_edit_name);
        //初始化性别视图
        sexRg = (RadioGroup) rootView.findViewById(R.id.rg_person_edit_sex);
        //初始化身份证号视图
        idNumberEt = (EditText) rootView.findViewById(R.id.et_person_edit_id_number);
        //初始化现居地视图
        addressTv = (TextView) rootView.findViewById(R.id.tv_person_edit_address);
        //初始化户口所在地视图
        houseHoldTv = (TextView) rootView.findViewById(R.id.tv_person_edit_household);
        //初始化个人简介视图
        briefEt = (EditText) rootView.findViewById(R.id.et_person_edit_brief);
        //初始化手机号码（已绑定）视图
        phoneTv = (TextView) rootView.findViewById(R.id.tv_person_edit_phone);
        //初始化角色选择视图
        roleRg = (RadioGroup) rootView.findViewById(R.id.rg_person_edit_role);
        //初始化我是工人视图
        workerLl = (LinearLayout) rootView.findViewById(R.id.ll_person_edit_worker);
        workerGv = (GridView) rootView.findViewById(R.id.gv_person_edit_worker);
        addWorkerTv = (TextView) rootView.findViewById(R.id.tv_person_edit_worker_add);
        //初始化提交信息视图
        submitTv = (TextView) rootView.findViewById(R.id.tv_person_edit_submit);
    }

    private void initDialogView() {
        //初始化添加工种对话框视图
        addKindDialogView = View.inflate(getActivity(), R.layout.dialog_person_edit_add_kind, null);
        addKindDialog = new AlertDialog.Builder(getActivity()).setView(addKindDialogView).create();
        addKindDialog.setCanceledOnTouchOutside(false);
        addKindDialogLv = (ListView) addKindDialogView.findViewById(R.id.lv_dialog_person_edit_add_kind);
        addKindDialogNoTv = (TextView) addKindDialogView.findViewById(R.id.tv_dialog_person_edit_add_kind_no);
        addKindDialogYesTv = (TextView) addKindDialogView.findViewById(R.id.tv_dialog_person_edit_add_kind_yes);
    }

    private void initData() {
        //初始化信息预览数据类
        personPreview = ((PersonManageActivity) getActivity()).personPreview;
        //初始化工人种类数据类集合
        roleList = new ArrayList<>();
        //初始化工人种类适配器
        roleAdapter = new RoleAdapter(getActivity(), 1, roleList);
        //初始化添加工种数据类集合
        addKindList = new ArrayList<>();
        //初始化添加工种适配器
        addKindAdapter = new AddKindAdapter(getActivity(), addKindList, this);
    }

    private void setData() {
        //绑定工人种类适配器
        workerGv.setAdapter(roleAdapter);
        //绑定添加工种适配器
        addKindDialogLv.setAdapter(addKindAdapter);
    }

    private void setListener() {
        //姓名视图监听
        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                personPreview.setNameContent(s.toString());
            }
        });
        //性别视图监听
        sexRg.setOnCheckedChangeListener(this);
        //身份证号视图监听
        idNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                personPreview.setIdNumberContent(s.toString());
            }
        });
        //现居地视图监听
        addressTv.setOnClickListener(this);
        //户口所在地视图监听
        houseHoldTv.setOnClickListener(this);
        //个人简介视图监听
        briefEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                personPreview.setBriefContent(s.toString());
            }
        });
        //角色选择视图监听
        roleRg.setOnCheckedChangeListener(this);
        //grid的item的监听
        workerGv.setOnItemClickListener(this);
        //添加工种视图监听
        addWorkerTv.setOnClickListener(this);
        //添加工种取消视图监听
        addKindDialogNoTv.setOnClickListener(this);
        //添加工种添加视图监听
        addKindDialogYesTv.setOnClickListener(this);
        //提交信息视图监听
        submitTv.setOnClickListener(this);
    }

    private void loadData() {
        nameEt.setText(personPreview.getNameContent());
        if (personPreview.isSex()) {
            ((RadioButton) sexRg.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) sexRg.getChildAt(1)).setChecked(true);
        }
        idNumberEt.setText(personPreview.getIdNumberContent());
        addressTv.setText(personPreview.getAddressContent());
        houseHoldTv.setText(personPreview.getHouseHoldContent());
        briefEt.setText(personPreview.getBriefContent());
        phoneTv.setText(personPreview.getPhoneNumberContent());
        if (personPreview.isRole()) {
            ((RadioButton) roleRg.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) roleRg.getChildAt(1)).setChecked(true);
        }
        roleList.addAll(personPreview.getRoleList());
        roleAdapter.notifyDataSetChanged();
        //动态设置gridview高度
        Utils.setGridViewHeight(workerGv, 3);

        //加载添加工种数据
        for (int i = 0; i < 10; i++) {
            AddKind ak = new AddKind();
            ak.setId(i);
            ak.setImg("");
            ak.setContent("瓦工" + i);
            ak.setChecked(false);
            addKindList.add(ak);
        }
        addKindAdapter.notifyDataSetChanged();
    }

    private void addKind() {
        for (int i = 0; i < addKindList.size(); i++) {
            AddKind a = addKindList.get(i);
            if (a.isChecked()) {
                Role r = new Role();
                r.setId(a.getId() + "");
                r.setContent(a.getContent());
                roleList.add(r);
            }
        }
        roleAdapter.notifyDataSetChanged();
        Utils.setGridViewHeight(workerGv, 3);
        for (int i = 0; i < addKindList.size(); i++) {
            addKindList.get(i).setChecked(false);
        }
        addKindAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //现居地视图点击事件
            case R.id.tv_person_edit_address:
                Utils.toast(getActivity(), "选择现居地");
                break;
            //户口所在地视图点击事件
            case R.id.tv_person_edit_household:
                Utils.toast(getActivity(), "选择户口所在地");
                break;
            //添加工种视图点击事件
            case R.id.tv_person_edit_worker_add:
                addKindDialog.show();
                break;
            //添加工种取消视图点击事件
            case R.id.tv_dialog_person_edit_add_kind_no:
                addKindDialog.dismiss();
                for (int i = 0; i < addKindList.size(); i++) {
                    addKindList.get(i).setChecked(false);
                }
                addKindAdapter.notifyDataSetChanged();
                break;
            //添加工种添加视图点击事件
            case R.id.tv_dialog_person_edit_add_kind_yes:
                addKindDialog.dismiss();
                Utils.toast(getActivity(), "添加工种");
                addKind();
                break;
            //提交信息视图点击事件
            case R.id.tv_person_edit_submit:
                Utils.toast(getActivity(), personPreview.toString());
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (group.getId()) {
            //性别视图点击事件
            case R.id.rg_person_edit_sex:
                switch (checkedId) {
                    case R.id.rb_person_edit_male:
                        break;
                    case R.id.rb_person_edit_female:
                        break;
                    default:
                        break;
                }
                break;
            //角色选择视图点击事件
            case R.id.rg_person_edit_role:
                switch (checkedId) {
                    case R.id.rb_person_edit_no:
                        workerLl.setVisibility(View.GONE);
                        break;
                    case R.id.rb_person_edit_yes:
                        workerLl.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            //gridview的item的点击事件
            case R.id.gv_person_edit_worker:
                roleList.remove(position);
                roleAdapter.notifyDataSetChanged();
                Utils.setGridViewHeight(workerGv, 3);
                personPreview.setRoleList(roleList);
                break;
        }
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {
        switch (which) {
            case R.id.cb_item_add_kind_checked:
                addKindList.get(position).setChecked(isChecked);
                break;
        }
    }
}
