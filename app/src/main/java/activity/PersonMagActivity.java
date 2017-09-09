package activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import bean.PersonPreviewBean;
import fragment.PersonMagPreviewFrag;
import fragment.PersonMagEditFrag;
import fragment.PersonMagRecordFrag;

public class PersonMagActivity extends CommonActivity implements View.OnClickListener {

    //根视图
    private View rootView;
    //返回视图
    private RelativeLayout returnRl;
    //信息预览视图
    private RelativeLayout previewRl;
    private TextView previewTv;
    //编辑信息视图
    private RelativeLayout editRl;
    private TextView editTv;
    //投递记录视图
    private RelativeLayout recordRl;
    private TextView recordTv;
    //编辑信息对话框视图
    private AlertDialog editDialog;
    private View editDialogView;
    private TextView editDialogYesTv;
    private TextView editDialogNotv;
    //碎片管理者
    private FragmentManager fragmentManager;
    //碎片集合
    private List<Fragment> fragmentList;
    //当前碎片索引
    private int curPosition;
    //目标碎片索引
    private int tarPosition;
    //信息预览数据类对象
    public PersonPreviewBean personPreviewBean;
    //handler
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                personPreviewBean = (PersonPreviewBean) (msg.getData()).getSerializable("PersonPreviewBean");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(personPreviewBean);
    }

    @Override
    protected View getRootView() {
        //初始化根视图
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_person_mag, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        //初始化返回视图
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_return);
        //初始化信息预览视图
        previewRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_preview);
        previewTv = (TextView) rootView.findViewById(R.id.tv_person_manage_preview);
        //初始化编辑信息视图
        editRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_edit);
        editTv = (TextView) rootView.findViewById(R.id.tv_person_manage_edit);
        //初始化投递记录视图
        recordRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_record);
        recordTv = (TextView) rootView.findViewById(R.id.tv_person_manage_record);
    }

    private void initDialogView() {
        //初始化编辑信息对话框视图
        editDialogView = View.inflate(this, R.layout.dialog_person_edit, null);
        editDialogNotv = (TextView) editDialogView.findViewById(R.id.tv_dialog_person_edit_no);
        editDialogNotv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });
        editDialogYesTv = (TextView) editDialogView.findViewById(R.id.tv_dialog_person_edit_yes);
        editDialogYesTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
                changeFragment();
            }
        });
        editDialog = new AlertDialog.Builder(this).setView(editDialogView).create();
        editDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void initData() {
        //初始化碎片集合
        fragmentList = new ArrayList<>();
        PersonMagPreviewFrag previewFrag = new PersonMagPreviewFrag();
        PersonMagEditFrag editFrag = new PersonMagEditFrag();
        PersonMagRecordFrag recordFrag = new PersonMagRecordFrag();
        fragmentList.add(previewFrag);
        fragmentList.add(editFrag);
        fragmentList.add(recordFrag);
        //初始化碎片管理者
        fragmentManager = getSupportFragmentManager();
        //初始化当前碎片索引
        curPosition = 0;
        //初始化目标碎片索引
        tarPosition = -1;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_person_manage_sit, fragmentList.get(curPosition));
        transaction.commit();
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        //返回视图监听
        returnRl.setOnClickListener(this);
        //信息预览监听
        previewRl.setOnClickListener(this);
        //编辑信息监听
        editRl.setOnClickListener(this);
        //投递记录监听
        recordRl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void changeFragment() {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    previewTv.setTextColor(Color.parseColor("#ff3e50"));
                    editTv.setTextColor(Color.parseColor("#a0a0a0"));
                    recordTv.setTextColor(Color.parseColor("#a0a0a0"));
                    break;
                case 1:
                    previewTv.setTextColor(Color.parseColor("#a0a0a0"));
                    editTv.setTextColor(Color.parseColor("#ff3e50"));
                    recordTv.setTextColor(Color.parseColor("#a0a0a0"));
                    break;
                case 2:
                    previewTv.setTextColor(Color.parseColor("#a0a0a0"));
                    editTv.setTextColor(Color.parseColor("#a0a0a0"));
                    recordTv.setTextColor(Color.parseColor("#ff3e50"));
                    break;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.ll_person_manage_sit, fragmentList.get(tarPosition));
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_person_manage_return:
                finish();
                break;
            case R.id.rl_person_manage_preview:
                tarPosition = 0;
                changeFragment();
                break;
            case R.id.rl_person_manage_edit:
                tarPosition = 1;
                if (curPosition != tarPosition) {
                    editDialog.show();
                }
                break;
            case R.id.rl_person_manage_record:
                tarPosition = 2;
                changeFragment();
                break;
        }
    }
}
