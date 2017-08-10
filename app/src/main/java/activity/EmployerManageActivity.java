package activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ManageEmployerAdapter;
import bean.ManageEmployer;
import config.ColorConfig;
import listener.ListItemClickHelp;
import utils.Utils;

public class EmployerManageActivity extends AppCompatActivity implements View.OnClickListener, ListItemClickHelp {

    private View rootView;
    private RelativeLayout returnRl, draftRl;
    private RelativeLayout allRl, waitContactRl, contactingRl, underWayRl, finishedRl;
    private TextView allTv, waitContactTv, contactingTv, underWayTv, finishedTv;
    private ListView listView;

    private List<ManageEmployer> manageEmployerList = new ArrayList<>();
    private ManageEmployerAdapter manageEmployerAdapter;

    private int curPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_employer_manage, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        loadData();
    }

    private void initView() {
        initRootView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_return);
        draftRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_draft);
        allRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_all);
        waitContactRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_wait_contact);
        contactingRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_contacting);
        underWayRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_under_way);
        finishedRl = (RelativeLayout) rootView.findViewById(R.id.rl_employer_manage_finished);
        allTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_all);
        waitContactTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_wait_contact);
        contactingTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_contacting);
        underWayTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_under_way);
        finishedTv = (TextView) rootView.findViewById(R.id.tv_employer_manage_finished);
        listView = (ListView) rootView.findViewById(R.id.lv_employer_manage);
    }

    private void initData() {
        manageEmployerAdapter = new ManageEmployerAdapter(this, manageEmployerList, this);
    }

    private void setData() {
        listView.setAdapter(manageEmployerAdapter);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        draftRl.setOnClickListener(this);
        allRl.setOnClickListener(this);
        waitContactRl.setOnClickListener(this);
        contactingRl.setOnClickListener(this);
        underWayRl.setOnClickListener(this);
        finishedRl.setOnClickListener(this);
    }

    private void changeFragment(int tarPosition) {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    allTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    waitContactTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    contactingTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    underWayTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    finishedTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    break;
                case 1:
                    allTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    waitContactTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    contactingTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    underWayTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    finishedTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    break;
                case 2:
                    allTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    waitContactTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    contactingTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    underWayTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    finishedTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    break;
                case 3:
                    allTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    waitContactTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    contactingTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    underWayTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    finishedTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    break;
                case 4:
                    allTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    waitContactTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    contactingTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    underWayTv.setTextColor(Color.parseColor(ColorConfig.TV_GRAY));
                    finishedTv.setTextColor(Color.parseColor(ColorConfig.TV_RED));
                    break;
            }
            curPosition = tarPosition;
        }
    }

    private void loadData() {
        ManageEmployer manageEmployer1 = new ManageEmployer();
        manageEmployer1.setIcon("");
        manageEmployer1.setTitle("标题1");
        manageEmployer1.setDesc("描述1");
        manageEmployer1.setPrice("价格1");
        manageEmployer1.setState(3);
        manageEmployer1.setCollect(false);
        manageEmployer1.setDis("位置1");
        ManageEmployer manageEmployer2 = new ManageEmployer();
        manageEmployer2.setIcon("");
        manageEmployer2.setTitle("标题2");
        manageEmployer2.setDesc("描述2");
        manageEmployer2.setPrice("价格2");
        manageEmployer2.setState(2);
        manageEmployer2.setCollect(false);
        manageEmployer2.setDis("位置2");
        ManageEmployer manageEmployer3 = new ManageEmployer();
        manageEmployer3.setIcon("");
        manageEmployer3.setTitle("标题3");
        manageEmployer3.setDesc("描述3");
        manageEmployer3.setPrice("价格3");
        manageEmployer3.setState(5);
        manageEmployer3.setCollect(true);
        manageEmployer3.setDis("位置3");
        manageEmployerList.add(manageEmployer1);
        manageEmployerList.add(manageEmployer2);
        manageEmployerList.add(manageEmployer3);
        manageEmployerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_employer_manage_return:
                finish();
                break;
            case R.id.rl_employer_manage_draft:
                Utils.toast(this, "草稿箱");
                break;
            case R.id.rl_employer_manage_all:
                changeFragment(0);
                break;
            case R.id.rl_employer_manage_wait_contact:
                changeFragment(1);
                break;
            case R.id.rl_employer_manage_contacting:
                changeFragment(2);
                break;
            case R.id.rl_employer_manage_under_way:
                changeFragment(3);
                break;
            case R.id.rl_employer_manage_finished:
                changeFragment(4);
                break;
        }
    }

    @Override
    public void onClick(View item, View widget, int position, int which, boolean isChecked) {

    }
}
