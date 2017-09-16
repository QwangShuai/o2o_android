package activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import java.util.ArrayList;
import java.util.List;

import config.ColorConfig;
import config.VarConfig;
import fragment.DcFragment;
import fragment.FpFragment;
import fragment.MagFragment;
import fragment.MeFragment;
import utils.Utils;

//主页-首页、管理、优惠、我的
public class MainActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout fpRl, magRl, dcRl, meRl;
    private ImageView fpIv, magIv, dcIv, meIv;
    private TextView fpTv, magTv, dcTv, meTv;

    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private final int FP = 0, MAG = 1, DC = 2, ME = 3;
    private int curState = FP, tarState = -1;
    private long lastBackTime = 0;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main, null);
    }

    @Override
    protected void initView() {
        initRootView();
    }

    private void initRootView() {
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

    @Override
    protected void initData() {
        Fragment fpFrag = new FpFragment();
        Fragment magFrag = new MagFragment();
        Fragment dcFrag = new DcFragment();
        Fragment meFrag = new MeFragment();
        fragmentList.add(fpFrag);
        fragmentList.add(magFrag);
        fragmentList.add(dcFrag);
        fragmentList.add(meFrag);
        FragmentTransaction t = fragmentManager.beginTransaction();
        t.add(R.id.ll_main_sit, fragmentList.get(curState));
        t.commit();
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        fpRl.setOnClickListener(this);
        magRl.setOnClickListener(this);
        dcRl.setOnClickListener(this);
        meRl.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_main_fp:
                tarState = FP;
                break;
            case R.id.rl_main_mag:
                tarState = MAG;
                break;
            case R.id.rl_main_dc:
                tarState = DC;
                break;
            case R.id.rl_main_me:
                tarState = ME;
                break;
            default:
                break;
        }
        changeBlock();
    }

    private void changeBlock() {
        if (tarState != curState) {
            changeButton();
            changeFrag();
            curState = tarState;
        }
    }

    private void changeFrag() {
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
    }

    private void changeButton() {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (System.currentTimeMillis() - lastBackTime < VarConfig.exitTime) {
            finish();
        } else {
            lastBackTime = System.currentTimeMillis();
            Utils.toast(this, VarConfig.exitTip);
        }
    }
}