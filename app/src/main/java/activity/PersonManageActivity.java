package activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import fragment.PersonManageEditFragment;
import fragment.PersonManagePreviewFragment;
import fragment.PersonManageRecordFragment;

public class PersonManageActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, previewRl, editRl, recordRl;
    private TextView previewTv, editTv, recordTv;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private List<Fragment> fragmentList = new ArrayList<>();

    private int curPosition;

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

    private void changeFragment(int tarPosition) {
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
                changeFragment(0);
                break;
            case R.id.rl_person_manage_edit:
                changeFragment(1);
                break;
            case R.id.rl_person_manage_record:
                changeFragment(2);
                break;
        }
    }
}
