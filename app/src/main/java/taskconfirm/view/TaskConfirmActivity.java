package taskconfirm.view;

import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import publishjob.bean.PublishJobBean;
import publishjob.bean.PublishKindBean;
import taskconfirm.adapter.InputPasswordAdapter;
import taskconfirm.adapter.SelectVoucherAdapter;
import taskconfirm.adapter.TaskConfirmAdapter;
import taskconfirm.bean.InputPasswordBean;
import taskconfirm.bean.SelectVoucherBean;
import utils.Utils;

public class TaskConfirmActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View rootView, selectVoucherView;

    private View inputPasswordView;
    private GridView inputPasswordGv;
    private List<InputPasswordBean> inputPasswordBeanList;
    private InputPasswordAdapter inputPasswordAdapter;
    private PopupWindow inputPasswordPop;
    private TextView inputPasswordCloseTv, inputPasswordForgetTv;
    private ImageView inputPasswordPoint0Iv, inputPasswordPoint1Iv, inputPasswordPoint2Iv, inputPasswordPoint3Iv, inputPasswordPoint4Iv, inputPasswordPoint5Iv;

    private RelativeLayout returnRl, sureRl;
    private LinearLayout voucherLl;
    private TextView beforeSumTv, confirmVoucherTv;
    private PopupWindow selectVoucherPop;
    private ListView lv, selectVoucherLv;
    private SelectVoucherAdapter selectVoucherAdapter;
    private TaskConfirmAdapter adapter;
    private List<SelectVoucherBean> selectVoucherBeanList;

    private StringBuilder inputPasswordSb;

    private PublishJobBean publishJobBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(TaskConfirmActivity.this).inflate(R.layout.activity_task_confirm, null);
        setContentView(rootView);
        initView();
        initData();
        setData();
        setListener();
        calculatePrice();
    }

    private void initView() {
        initRootView();
        initPopView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_task_confirm_return);
        voucherLl = (LinearLayout) rootView.findViewById(R.id.ll_task_confirm_voucher);
        confirmVoucherTv = (TextView) rootView.findViewById(R.id.tv_task_confirm_voucher);
        sureRl = (RelativeLayout) rootView.findViewById(R.id.rl_task_confirm_sure);
        beforeSumTv = (TextView) rootView.findViewById(R.id.tv_task_confirm_sum_before);
        lv = (ListView) rootView.findViewById(R.id.lv_task_confirm);
    }

    private void initPopView() {
        selectVoucherView = LayoutInflater.from(TaskConfirmActivity.this).inflate(R.layout.pop_select_voucher, null);
        selectVoucherLv = (ListView) selectVoucherView.findViewById(R.id.lv_pop_select_voucher);
        selectVoucherPop = new PopupWindow(selectVoucherView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        selectVoucherPop.setFocusable(true);
        selectVoucherPop.setTouchable(true);
        selectVoucherPop.setOutsideTouchable(true);
        selectVoucherPop.setBackgroundDrawable(new PaintDrawable());
        selectVoucherPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
                selectVoucherBeanList.clear();
            }
        });

        inputPasswordView = LayoutInflater.from(TaskConfirmActivity.this).inflate(R.layout.pop_input_password, null);
        inputPasswordGv = (GridView) inputPasswordView.findViewById(R.id.gv_pop_input_password);
        inputPasswordCloseTv = (TextView) inputPasswordView.findViewById(R.id.tv_pop_input_password_close);
        inputPasswordForgetTv = (TextView) inputPasswordView.findViewById(R.id.tv_pop_input_password_forget);
        inputPasswordPoint0Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_0);
        inputPasswordPoint1Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_1);
        inputPasswordPoint2Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_2);
        inputPasswordPoint3Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_3);
        inputPasswordPoint4Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_4);
        inputPasswordPoint5Iv = (ImageView) inputPasswordView.findViewById(R.id.iv_pwd_point_5);
        inputPasswordPop = new PopupWindow(inputPasswordView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        inputPasswordPop.setFocusable(true);
        inputPasswordPop.setTouchable(true);
        inputPasswordPop.setOutsideTouchable(true);
        inputPasswordPop.setBackgroundDrawable(new PaintDrawable());
        inputPasswordPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
                inputPasswordBeanList.clear();
            }
        });
    }

    private void initData() {
        publishJobBean = new PublishJobBean();
        publishJobBean.setTitle("个人家装招工人");
        publishJobBean.setDescription("需要搬运水泥，和水泥，贴砖。房屋面积40平，几面墙，厅，和卫生间需要贴砖，详情面谈。");
        publishJobBean.setType("个人家装");
        publishJobBean.setArea("哈尔滨-道外区");
        publishJobBean.setAddress("开源街3号");

        List<PublishKindBean> publishKindBeanList = new ArrayList<>();

        PublishKindBean publishKindBean0 = new PublishKindBean();
        publishKindBean0.setKind("水泥工");
        publishKindBean0.setAmount("2");
        publishKindBean0.setSalary("100");
        publishKindBean0.setStartTime("2017.10.01");
        publishKindBean0.setEndTime("2017.10.02");

        PublishKindBean publishKindBean1 = new PublishKindBean();
        publishKindBean1.setKind("电工");
        publishKindBean1.setAmount("1");
        publishKindBean1.setSalary("50");
        publishKindBean1.setStartTime("2017.10.02");
        publishKindBean1.setEndTime("2017.10.03");

        publishKindBeanList.add(publishKindBean0);
        publishKindBeanList.add(publishKindBean1);

        publishJobBean.setPublishKindBeanList(publishKindBeanList);

        adapter = new TaskConfirmAdapter(TaskConfirmActivity.this, publishJobBean);

        selectVoucherBeanList = new ArrayList<>();
        selectVoucherAdapter = new SelectVoucherAdapter(TaskConfirmActivity.this, selectVoucherBeanList);

        inputPasswordBeanList = new ArrayList<>();
        inputPasswordAdapter = new InputPasswordAdapter(TaskConfirmActivity.this, inputPasswordBeanList);
    }

    private void setData() {
        lv.setAdapter(adapter);
        selectVoucherLv.setAdapter(selectVoucherAdapter);
        inputPasswordGv.setAdapter(inputPasswordAdapter);
        inputPasswordGv.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        voucherLl.setOnClickListener(this);
        sureRl.setOnClickListener(this);
        selectVoucherLv.setOnItemClickListener(this);
        inputPasswordGv.setOnItemClickListener(this);
        inputPasswordCloseTv.setOnClickListener(this);
        inputPasswordForgetTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_task_confirm_return:
                finish();
                break;
            case R.id.ll_task_confirm_voucher:
                backgroundAlpha(0.5f);
                selectVoucherPop.showAtLocation(rootView, Gravity.CENTER, 0, 0);

                SelectVoucherBean selectVoucherBean0 = new SelectVoucherBean();
                selectVoucherBean0.setTitle("5元优惠券");
                selectVoucherBean0.setDescription("任务金额满100元可以使用");
                selectVoucherBean0.setStartTime("2017.09.26");
                selectVoucherBean0.setEndTime("2017.09.30");

                SelectVoucherBean selectVoucherBean1 = new SelectVoucherBean();
                selectVoucherBean1.setTitle("10元优惠券");
                selectVoucherBean1.setDescription("任务金额满100元可以使用");
                selectVoucherBean1.setStartTime("2017.09.26");
                selectVoucherBean1.setEndTime("2017.09.30");

                selectVoucherBeanList.add(selectVoucherBean0);
                selectVoucherBeanList.add(selectVoucherBean1);

                selectVoucherAdapter.notifyDataSetChanged();

                break;
            case R.id.rl_task_confirm_sure:
                inputPasswordSb = new StringBuilder();

                InputPasswordBean inputPasswordBean0 = new InputPasswordBean(0, 1);
                InputPasswordBean inputPasswordBean1 = new InputPasswordBean(0, 2);
                InputPasswordBean inputPasswordBean2 = new InputPasswordBean(0, 3);
                InputPasswordBean inputPasswordBean3 = new InputPasswordBean(0, 4);
                InputPasswordBean inputPasswordBean4 = new InputPasswordBean(0, 5);
                InputPasswordBean inputPasswordBean5 = new InputPasswordBean(0, 6);
                InputPasswordBean inputPasswordBean6 = new InputPasswordBean(0, 7);
                InputPasswordBean inputPasswordBean7 = new InputPasswordBean(0, 8);
                InputPasswordBean inputPasswordBean8 = new InputPasswordBean(0, 9);
                InputPasswordBean inputPasswordBean9 = new InputPasswordBean(1, 0);
                InputPasswordBean inputPasswordBean10 = new InputPasswordBean(0, 0);
                InputPasswordBean inputPasswordBean11 = new InputPasswordBean(2, 0);

                inputPasswordBeanList.add(inputPasswordBean0);
                inputPasswordBeanList.add(inputPasswordBean1);
                inputPasswordBeanList.add(inputPasswordBean2);
                inputPasswordBeanList.add(inputPasswordBean3);
                inputPasswordBeanList.add(inputPasswordBean4);
                inputPasswordBeanList.add(inputPasswordBean5);
                inputPasswordBeanList.add(inputPasswordBean6);
                inputPasswordBeanList.add(inputPasswordBean7);
                inputPasswordBeanList.add(inputPasswordBean8);
                inputPasswordBeanList.add(inputPasswordBean9);
                inputPasswordBeanList.add(inputPasswordBean10);
                inputPasswordBeanList.add(inputPasswordBean11);

                inputPasswordAdapter.notifyDataSetChanged();
                Utils.setGridViewHeight(inputPasswordGv, 3);

                backgroundAlpha(0.5f);
                inputPasswordPop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_pop_input_password_close:
                inputPasswordPop.dismiss();
                break;
            case R.id.tv_pop_input_password_forget:
                Utils.log(TaskConfirmActivity.this, "forget");
                break;
        }
    }

    private void calculatePrice() {
        int sum = 0;
        List<PublishKindBean> publishKindBeanList = publishJobBean.getPublishKindBeanList();
        if (publishKindBeanList != null) {
            for (int i = 0; i < publishKindBeanList.size(); i++) {
                PublishKindBean publishKindBean = publishKindBeanList.get(i);
                if (publishKindBean != null) {
                    int s = Integer.parseInt(publishKindBean.getAmount()) * Integer.parseInt(publishKindBean.getSalary());
                    sum = sum + s;
                }
            }
        }
        beforeSumTv.setText(sum + "元");
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = f;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_pop_select_voucher:
                confirmVoucherTv.setText(selectVoucherBeanList.get(position).getTitle());
                selectVoucherPop.dismiss();
                break;
            case R.id.gv_pop_input_password:
                InputPasswordBean inputPasswordBean = inputPasswordBeanList.get(position);
                if (inputPasswordBean != null) {
                    switch (inputPasswordBean.getType()) {
                        case 0:
                            if (inputPasswordSb.length() < 6) {
                                inputPasswordSb.append(inputPasswordBean.getNumber());
                                notifyPoints(inputPasswordSb.length());
                                Utils.log(TaskConfirmActivity.this, "password=" + inputPasswordSb.toString());
                            }
                            if (inputPasswordSb.length() == 6) {
                                Utils.log(TaskConfirmActivity.this, "up to Server");
                            }
                            break;
                        case 1:
                            Utils.log(TaskConfirmActivity.this, "null");
                            break;
                        case 2:
                            if (inputPasswordSb.length() != 0) {
                                inputPasswordSb.deleteCharAt(inputPasswordSb.length() - 1);
                                notifyPoints(inputPasswordSb.length());
                                Utils.log(TaskConfirmActivity.this, "password=" + inputPasswordSb.toString());
                            }
                            break;
                    }
                }
                break;
        }
    }

    private void notifyPoints(int num) {
        switch (num) {
            case 0:
                inputPasswordPoint0Iv.setVisibility(View.GONE);
                inputPasswordPoint1Iv.setVisibility(View.GONE);
                inputPasswordPoint2Iv.setVisibility(View.GONE);
                inputPasswordPoint3Iv.setVisibility(View.GONE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 1:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.GONE);
                inputPasswordPoint2Iv.setVisibility(View.GONE);
                inputPasswordPoint3Iv.setVisibility(View.GONE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 2:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.GONE);
                inputPasswordPoint3Iv.setVisibility(View.GONE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 3:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint3Iv.setVisibility(View.GONE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 4:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint3Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint4Iv.setVisibility(View.GONE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 5:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint3Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint4Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint5Iv.setVisibility(View.GONE);
                break;
            case 6:
                inputPasswordPoint0Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint1Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint2Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint3Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint4Iv.setVisibility(View.VISIBLE);
                inputPasswordPoint5Iv.setVisibility(View.VISIBLE);
                break;
        }
    }
}
