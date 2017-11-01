package main.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import discount.view.DiscountFragment;
import firstpage.view.FirstPageFragment;
import manage.view.ManageFragment;
import mine.view.MineFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout fpRl, magRl, dcRl, meRl;
    private ImageView fpIv, magIv, dcIv, meIv;
    private TextView fpTv, magTv, dcTv, meTv;
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    private int curPos, tarPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        fpRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_fp);
        magRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_mag);
        dcRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_dc);
        meRl = (RelativeLayout) rootView.findViewById(R.id.rl_main_me);
        fpIv = (ImageView) rootView.findViewById(R.id.iv_main_fp);
        magIv = (ImageView) rootView.findViewById(R.id.iv_main_mag);
        dcIv = (ImageView) rootView.findViewById(R.id.iv_main_dc);
        meIv = (ImageView) rootView.findViewById(R.id.iv_main_me);
        fpTv = (TextView) rootView.findViewById(R.id.tv_main_fp);
        magTv = (TextView) rootView.findViewById(R.id.tv_main_mag);
        dcTv = (TextView) rootView.findViewById(R.id.tv_main_dc);
        meTv = (TextView) rootView.findViewById(R.id.tv_main_me);
    }

    private void initData() {
        Fragment fpFrag = new FirstPageFragment();
        Fragment magFrag = new ManageFragment();
        Fragment dcFrag = new DiscountFragment();
        Fragment meFrag = new MineFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(fpFrag);
        fragmentList.add(magFrag);
        fragmentList.add(dcFrag);
        fragmentList.add(meFrag);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_main_sit, fragmentList.get(curPos));
        transaction.commit();
    }

    private void setListener() {
        fpRl.setOnClickListener(this);
        magRl.setOnClickListener(this);
        dcRl.setOnClickListener(this);
        meRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_main_fp:
                tarPos = 0;
                break;
            case R.id.rl_main_mag:
                tarPos = 1;
                break;
            case R.id.rl_main_dc:
                tarPos = 2;
                break;
            case R.id.rl_main_me:
                tarPos = 3;
                break;
            default:
                break;
        }
        changeBlock();
    }

    private void changeBlock() {
        if (tarPos != curPos) {
            changeButton();
            changeFrag();
            curPos = tarPos;
        }
    }

    private void changeFrag() {
        Fragment curFragment = fragmentList.get(curPos);
        Fragment tarFragment = fragmentList.get(tarPos);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(curFragment);
        if (tarFragment.isAdded()) {
            transaction.show(tarFragment);
        } else {
            transaction.add(R.id.ll_main_sit, tarFragment);
        }
        transaction.commit();
    }

    private void changeButton() {
        switch (curPos) {
            case 0:
                fpIv.setImageResource(R.mipmap.fp_default);
                fpTv.setTextColor(ColorConfig.blue_2681fc);
                break;
            case 1:
                magIv.setImageResource(R.mipmap.mag_default);
                magTv.setTextColor(ColorConfig.blue_2681fc);
                break;
            case 2:
                dcIv.setImageResource(R.mipmap.dc_default);
                dcTv.setTextColor(ColorConfig.blue_2681fc);
                break;
            case 3:
                meIv.setImageResource(R.mipmap.me_default);
                meTv.setTextColor(ColorConfig.blue_2681fc);
                break;
            default:
                break;
        }
        switch (tarPos) {
            case 0:
                fpIv.setImageResource(R.mipmap.fp_choosed);
                fpTv.setTextColor(ColorConfig.red_ff3e50);
                break;
            case 1:
                magIv.setImageResource(R.mipmap.mag_choosed);
                magTv.setTextColor(ColorConfig.red_ff3e50);
                break;
            case 2:
                dcIv.setImageResource(R.mipmap.dc_choosed);
                dcTv.setTextColor(ColorConfig.red_ff3e50);
                break;
            case 3:
                meIv.setImageResource(R.mipmap.me_choosed);
                meTv.setTextColor(ColorConfig.red_ff3e50);
                break;
            default:
                break;
        }
    }

}