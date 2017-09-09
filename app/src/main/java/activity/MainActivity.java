package activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import fragment.DiscountFrag;
import fragment.FirstPageFrag;
import fragment.JobMagFrag;
import fragment.MineFrag;

/**
 * 创建日期：2017/7/28 on 13:50
 * 作者:孙明明
 * 描述:主页
 */

public class MainActivity extends CommonActivity implements View.OnClickListener {

    //根视图
    private View rootView;
    //首页视图
    private RelativeLayout fpRl;
    private ImageView fpIv;
    private TextView fpTv;
    //管理视图
    private RelativeLayout magRl;
    private ImageView magIv;
    private TextView magTv;
    //优惠视图
    private RelativeLayout dcRl;
    private ImageView dcIv;
    private TextView dcTv;
    //我的视图
    private RelativeLayout meRl;
    private ImageView meIv;
    private TextView meTv;
    //碎片集合
    private List<Fragment> fragmentList;
    //碎片管理者
    private FragmentManager fragmentManager;
    //状态
    private final int FP = 0;
    private final int MAG = 1;
    private final int DC = 2;
    private final int ME = 3;
    //当前状态
    private int curState;
    //目标状态
    private int tarState;

    @Override
    protected View getRootView() {
        //初始化根视图
        return rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
    }

    @Override
    protected void initView() {
        //初始化首页视图
        fpRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_fp);
        fpIv = (ImageView) rootView.findViewById(R.id.iv_main_fp);
        fpTv = (TextView) rootView.findViewById(R.id.tv_main_fp);
        //初始化管理视图
        magRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_mag);
        magIv = (ImageView) rootView.findViewById(R.id.iv_main_mag);
        magTv = (TextView) rootView.findViewById(R.id.tv_main_mag);
        //初始化优惠视图
        dcRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_dc);
        dcIv = (ImageView) rootView.findViewById(R.id.iv_main_dc);
        dcTv = (TextView) rootView.findViewById(R.id.tv_main_dc);
        //初始化我的视图
        meRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_me);
        meIv = (ImageView) rootView.findViewById(R.id.iv_main_me);
        meTv = (TextView) rootView.findViewById(R.id.tv_main_me);
    }

    @Override
    protected void initData() {
        //初始化碎片集合
        fragmentList = new ArrayList<>();
        //初始化碎片管理者
        fragmentManager = getSupportFragmentManager();
        //初始化当前状态
        curState = FP;
        //初始化目标状态
        tarState = -1;
        FirstPageFrag firstPageFrag = new FirstPageFrag();
        JobMagFrag jobMagFrag = new JobMagFrag();
        DiscountFrag discountInfoFragment = new DiscountFrag();
        MineFrag mineFrag = new MineFrag();
        fragmentList.add(firstPageFrag);
        fragmentList.add(jobMagFrag);
        fragmentList.add(discountInfoFragment);
        fragmentList.add(mineFrag);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.ll_main_sit, fragmentList.get(curState));
        fragmentTransaction.commit();
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        //首页视图监听
        fpRl.setOnClickListener(this);
        //管理视图监听
        magRl.setOnClickListener(this);
        //优惠视图监听
        dcRl.setOnClickListener(this);
        //我的视图监听
        meRl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //首页视图点击事件
            case R.id.rl_main_fp:
                tarState = FP;
                break;
            //管理视图点击事件
            case R.id.rl_main_mag:
                tarState = MAG;
                break;
            //优惠视图点击事件
            case R.id.rl_main_dc:
                tarState = DC;
                break;
            //我的视图点击事件
            case R.id.rl_main_me:
                tarState = ME;
                break;
            default:
                break;
        }
        changeFragment();
    }

    private void changeFragment() {
        if (tarState != curState) {
            changeColor();
            Fragment curFragment = fragmentList.get(curState);
            Fragment tarFragment = fragmentList.get(tarState);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(curFragment);
            if (tarFragment.isAdded()) {
                transaction.show(tarFragment);
            } else {
                transaction.add(R.id.ll_main_sit, tarFragment);
            }
            transaction.commit();
            curState = tarState;
        }
    }

    private void changeColor() {
        switch (curState) {
            case FP:
                fpIv.setImageResource(R.mipmap.fp_default);
                fpTv.setTextColor(ColorConfig.blue_2681fc);
                break;
            case MAG:
                magIv.setImageResource(R.mipmap.mag_default);
                magTv.setTextColor(ColorConfig.blue_2681fc);
                break;
            case DC:
                dcIv.setImageResource(R.mipmap.dc_default);
                dcTv.setTextColor(ColorConfig.blue_2681fc);
                break;
            case ME:
                meIv.setImageResource(R.mipmap.me_default);
                meTv.setTextColor(ColorConfig.blue_2681fc);
                break;
            default:
                break;
        }
        switch (tarState) {
            case FP:
                fpIv.setImageResource(R.mipmap.fp_choosed);
                fpTv.setTextColor(ColorConfig.red_ff3e50);
                break;
            case MAG:
                magIv.setImageResource(R.mipmap.mag_choosed);
                magTv.setTextColor(ColorConfig.red_ff3e50);
                break;
            case DC:
                dcIv.setImageResource(R.mipmap.dc_choosed);
                dcTv.setTextColor(ColorConfig.red_ff3e50);
                break;
            case ME:
                meIv.setImageResource(R.mipmap.me_choosed);
                meTv.setTextColor(ColorConfig.red_ff3e50);
                break;
            default:
                break;
        }
    }
}