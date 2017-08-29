package activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import bean.PersonPreview;
import config.ColorConfig;
import fragment.PersonManageEditFragment;
import fragment.PersonManagePreviewFragment;
import fragment.PersonManageRecordFragment;

public class PersonManageActivity extends AppCompatActivity implements View.OnClickListener {

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

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private List<Fragment> fragmentList = new ArrayList<>();

    private int curPosition = 0;
    private int tarPosition = -1;

    public PersonPreview personPreview;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                personPreview = (PersonPreview) (msg.getData()).getSerializable("PersonPreview");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(personPreview);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_person_manage, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_return);
        previewRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_preview);
        editRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_edit);
        recordRl = (RelativeLayout) rootView.findViewById(R.id.rl_person_manage_record);
        previewTv = (TextView) rootView.findViewById(R.id.tv_person_manage_preview);
        editTv = (TextView) rootView.findViewById(R.id.tv_person_manage_edit);
        recordTv = (TextView) rootView.findViewById(R.id.tv_person_manage_record);
    }

    private void initDialogView() {
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

    private void initData() {
        PersonManagePreviewFragment personManagePreviewFragment = new PersonManagePreviewFragment();
        PersonManageEditFragment personManageEditFragment = new PersonManageEditFragment();
        PersonManageRecordFragment personManageRecordFragment = new PersonManageRecordFragment();
        fragmentList.add(personManagePreviewFragment);
        fragmentList.add(personManageEditFragment);
        fragmentList.add(personManageRecordFragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_person_manage_sit, fragmentList.get(curPosition));
        transaction.commit();
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        previewRl.setOnClickListener(this);
        editRl.setOnClickListener(this);
        recordRl.setOnClickListener(this);
    }

    private void changeFragment() {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    previewTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    editTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    recordTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    break;
                case 1:
                    previewTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    editTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    recordTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    break;
                case 2:
                    previewTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    editTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    recordTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
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
