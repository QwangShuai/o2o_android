package editinfo.view;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.NetConfig;
import editinfo.adapter.AddSkillAdapter;
import editinfo.adapter.EditInfoAdapter;
import editinfo.listener.AddSkillClickHelp;
import editinfo.listener.EditInfoClickHelp;
import editinfo.presenter.EditInfoPresenter;
import editinfo.presenter.IEditInfoPresenter;
import selectaddress.bean.SelectAddressBean;
import selectaddress.view.SelectAddressActivity;
import bean.SkillBean;
import usermanage.bean.UserInfoBean;
import usermanage.view.UserManageActivity;
import utils.Utils;
import view.CProgressDialog;

public class EditInfoFragment extends Fragment implements IEditInfoFragment, View.OnClickListener, EditInfoClickHelp, AddSkillClickHelp {

    private View rootView;
    private RelativeLayout submitRl;
    private ListView lv;
    private CProgressDialog cpd;
    private UserInfoBean userInfoBean;
    private EditInfoAdapter adapter;

    private View addSkillPopView;
    private PopupWindow addSkillPop;
    private ListView addSkillLv;
    private List<SkillBean> sbList;
    private AddSkillAdapter addSkillAdapter;
    private List<SkillBean> selectList = new ArrayList<>();

    private IEditInfoPresenter editInfoPresenter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        addSkillAdapter.notifyDataSetChanged();
                        break;
                }
            }
        }
    };

    private Handler userManageHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_editinfo, null);
        initView();
        initData();
        setListener();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (editInfoPresenter != null) {
            editInfoPresenter.destroy();
            editInfoPresenter = null;
        }
        if (handler != null) {
            handler.removeMessages(1);
            handler = null;
        }
    }

    private void initView() {
        initRootView();
        initPopView();
    }

    private void initRootView() {
        lv = (ListView) rootView.findViewById(R.id.lv_editinfo);
        cpd = Utils.initProgressDialog(getActivity(), cpd);
        submitRl = (RelativeLayout) rootView.findViewById(R.id.rl_editinfo_submit);
    }

    private void initPopView() {
        addSkillPopView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_add_skill, null);
        addSkillLv = (ListView) addSkillPopView.findViewById(R.id.lv_add_skill);
        (addSkillPopView.findViewById(R.id.rl_pop_add_skill_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkillPop.dismiss();
            }
        });
        (addSkillPopView.findViewById(R.id.rl_pop_add_skill_sure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkillPop.dismiss();
                selectList.clear();
                for (int i = 0; i < sbList.size(); i++) {
                    if (sbList.get(i).isCheck()) {
                        selectList.add(sbList.get(i));
                    }
                }
                if (selectList.size() != 0) {
                    Log.e("selectList", selectList.toString());
                    if (userInfoBean.getSkillBeanList() == null) {
                        userInfoBean.setSkillBeanList(selectList);
                    } else {
                        userInfoBean.getSkillBeanList().addAll(defList());
                    }
                    changeSkill(userInfoBean.getSkillBeanList());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        addSkillPop = new PopupWindow(addSkillPopView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        addSkillPop.setFocusable(true);
        addSkillPop.setTouchable(true);
        addSkillPop.setOutsideTouchable(true);
        addSkillPop.setBackgroundDrawable(new BitmapDrawable());
        addSkillPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        sbList = new ArrayList<>();
        addSkillAdapter = new AddSkillAdapter(getActivity(), sbList, this);
        addSkillLv.setAdapter(addSkillAdapter);
    }

    private void initData() {
        editInfoPresenter = new EditInfoPresenter(this);
        userManageHandler = ((UserManageActivity) getActivity()).handler;
    }

    private void setListener() {
        submitRl.setOnClickListener(this);
    }

    private void loadData() {
        userInfoBean = ((UserManageActivity) getActivity()).mainUserInfoBean;
        adapter = new EditInfoAdapter(getActivity(), userInfoBean, this);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_editinfo_submit:
                Log.e("submit", "userInfoBean=" + userInfoBean.toString());
                judgeSubmit();
                break;
        }
    }

    private void judgeSubmit() {
        if (userInfoBean.getU_skills().equals("-1")) {
            Utils.toast(getActivity(), "请添加工种");
            return;
        }
        editInfoPresenter.submit(userInfoBean);
    }

    @Override
    public void onClick(int id, int position, int checkedId) {
        switch (id) {
            case R.id.rg_item_editinfo_sex:
                switch (checkedId) {
                    case R.id.rb_item_editinfo_male:
                        userInfoBean.setU_sex("1");
                        break;
                    case R.id.rb_item_editinfo_female:
                        userInfoBean.setU_sex("0");
                        break;
                }
                break;
            case R.id.tv_item_editinfo_area:
                startActivityForResult(new Intent(getActivity(), SelectAddressActivity.class), 1);
                break;
            case R.id.rg_item_editinfo_role:
                switch (checkedId) {
                    case R.id.rb_item_editinfo_employer:
                        userInfoBean.setU_skills("0");
                        break;
                    case R.id.rb_item_editinfo_worker:
                        userInfoBean.setU_skills("-1");
                        break;
                }
                break;
            case R.id.gv_item_editinfo:
                userInfoBean.getSkillBeanList().remove(position);
                changeSkill(userInfoBean.getSkillBeanList());
                break;
            case R.id.iv_item_editinfo_addskill:
                backgroundAlpha(0.5f);
                addSkillPop.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                editInfoPresenter.load(NetConfig.skillUrl);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private void changeSkill(List<SkillBean> skillBeanList) {
        if (skillBeanList != null) {
            if (skillBeanList.size() != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(",");
                for (int i = 0; i < skillBeanList.size(); i++) {
                    sb.append(skillBeanList.get(i).getId() + ",");
                }
                userInfoBean.setU_skills(sb.toString());
            } else {
                userInfoBean.setU_skills("-1");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1 && data != null) {
            SelectAddressBean selectAddressBean = (SelectAddressBean) data.getSerializableExtra("sa");
            if (selectAddressBean != null) {
                String[] idArr = selectAddressBean.getId().split(",");
                userInfoBean.setArea_user_area_name(selectAddressBean.getName());
                userInfoBean.setArea_uei_province(idArr[0]);
                userInfoBean.setArea_uei_city(idArr[1]);
                userInfoBean.setArea_uei_area(idArr[2]);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(int id, int position, boolean checked) {
        switch (id) {
            case R.id.cb_item_add_kind_checked:
                sbList.get(position).setCheck(checked);
                break;
        }
        addSkillAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        cpd.show();
    }

    @Override
    public void hideLoading() {
        cpd.dismiss();
    }

    @Override
    public void showAddSkillSuccess(List<SkillBean> skillBeanList) {
        sbList.clear();
        sbList.addAll(skillBeanList);
        Log.e("sbList", sbList.toString());
        handler.sendEmptyMessage(1);
    }

    @Override
    public void showAddSkillFailure(String failure) {

    }

    @Override
    public void showSubmitSuccess(String success) {
        Utils.toast(getActivity(), success);
        userManageHandler.sendEmptyMessage(1);
    }

    @Override
    public void showSubmitFailure(String failure) {
        Utils.toast(getActivity(), failure);
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.alpha = f;
        getActivity().getWindow().setAttributes(layoutParams);
    }

    private List<SkillBean> defList() {
        if (userInfoBean.getSkillBeanList() != null) {
            if (userInfoBean.getSkillBeanList().size() != 0) {
                List<SkillBean> resultList = new ArrayList<>();
                for (int i = 0; i < selectList.size(); i++) {
                    if (isDiff(i)) {
                        resultList.add(selectList.get(i));
                    }
                }
                return resultList;
            }
        }
        return selectList;
    }

    private boolean isDiff(int index) {
        int count = 0;
        for (int i = 0; i < userInfoBean.getSkillBeanList().size(); i++) {
            if (selectList.get(index).getId().equals(userInfoBean.getSkillBeanList().get(i).getId())) {
                count++;
            }
        }
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }
}
