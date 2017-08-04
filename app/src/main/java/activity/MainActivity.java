package activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import fragment.DiscountFragment;
import fragment.FirstPageFragment;
import fragment.MineFragment;
import fragment.WorkManageFragment;

/**
 * 创建日期：2017/7/28 on 13:50
 * 作者:孙明明
 * 描述:主页
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout firstPageRl, workerManageRl, discountRl, mineRl;
    private ImageView firstPageIv, workerManageIv, discountIv, mineIv;
    private TextView firstPageTv, workerManageTv, discountTv, mineTv;

    private List<Fragment> cFragmentList;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private int curIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = View.inflate(this, R.layout.activity_main, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        firstPageRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_first_page);
        workerManageRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_worker_manage);
        discountRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_discount);
        mineRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_mine);
        firstPageIv = (ImageView) rootView.findViewById(R.id.iv_main_first_page);
        workerManageIv = (ImageView) rootView.findViewById(R.id.iv_main_worker_manage);
        discountIv = (ImageView) rootView.findViewById(R.id.iv_main_discount);
        mineIv = (ImageView) rootView.findViewById(R.id.iv_main_mine);
        firstPageTv = (TextView) rootView.findViewById(R.id.tv_main_first_page);
        workerManageTv = (TextView) rootView.findViewById(R.id.tv_main_worker_manage);
        discountTv = (TextView) rootView.findViewById(R.id.tv_main_discount);
        mineTv = (TextView) rootView.findViewById(R.id.tv_main_mine);
    }

    private void initData() {
        FirstPageFragment firstPageFragment = new FirstPageFragment();
        WorkManageFragment workManageFragment = new WorkManageFragment();
        DiscountFragment discountInfoFragment = new DiscountFragment();
        MineFragment mineFragment = new MineFragment();
        cFragmentList = new ArrayList<>();
        cFragmentList.add(firstPageFragment);
        cFragmentList.add(workManageFragment);
        cFragmentList.add(discountInfoFragment);
        cFragmentList.add(mineFragment);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ll_main_set, cFragmentList.get(curIndex));
        fragmentTransaction.commit();
    }

    private void setListener() {
        firstPageRl.setOnClickListener(this);
        workerManageRl.setOnClickListener(this);
        discountRl.setOnClickListener(this);
        mineRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_main_first_page:
                changeFragment(0);
                break;
            case R.id.rl_main_worker_manage:
                changeFragment(1);
                break;
            case R.id.rl_main_discount:
                changeFragment(2);
                break;
            case R.id.rl_main_mine:
                changeFragment(3);
                break;
        }
    }

    private void changeFragment(int tarIndex) {
        if (tarIndex != curIndex) {
            switch (tarIndex) {
                case 0:
                    firstPageIv.setImageResource(R.mipmap.first_page_choosed);
                    workerManageIv.setImageResource(R.mipmap.work_manage_default);
                    discountIv.setImageResource(R.mipmap.discount_default);
                    mineIv.setImageResource(R.mipmap.mine_default);
                    firstPageTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_CHOOSED));
                    workerManageTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    discountTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    mineTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    break;
                case 1:
                    firstPageIv.setImageResource(R.mipmap.first_page_default);
                    workerManageIv.setImageResource(R.mipmap.work_manage_choosed);
                    discountIv.setImageResource(R.mipmap.discount_default);
                    mineIv.setImageResource(R.mipmap.mine_default);
                    firstPageTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    workerManageTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_CHOOSED));
                    discountTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    mineTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    break;
                case 2:
                    firstPageIv.setImageResource(R.mipmap.first_page_default);
                    workerManageIv.setImageResource(R.mipmap.work_manage_default);
                    discountIv.setImageResource(R.mipmap.discount_choosed);
                    mineIv.setImageResource(R.mipmap.mine_default);
                    firstPageTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    workerManageTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    discountTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_CHOOSED));
                    mineTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    break;
                case 3:
                    firstPageIv.setImageResource(R.mipmap.first_page_default);
                    workerManageIv.setImageResource(R.mipmap.work_manage_default);
                    discountIv.setImageResource(R.mipmap.discount_default);
                    mineIv.setImageResource(R.mipmap.mine_choosed);
                    firstPageTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    workerManageTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    discountTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_DEFAULT));
                    mineTv.setTextColor(Color.parseColor(ColorConfig.TV_MAIN_CHOOSED));
                    break;
            }
            Fragment curFragment = cFragmentList.get(curIndex);
            Fragment tarFragment = cFragmentList.get(tarIndex);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(curFragment);
            if (tarFragment.isAdded()) {
                transaction.show(tarFragment);
            } else {
                transaction.add(R.id.ll_main_set, tarFragment);
            }
            curIndex = tarIndex;
            transaction.commit();
        }
    }
}