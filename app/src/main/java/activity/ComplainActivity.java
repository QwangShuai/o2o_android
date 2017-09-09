package activity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;

import utils.Utils;

public class ComplainActivity extends CommonActivity implements View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl;
    private RelativeLayout addImageRl;
    private TextView submitTv;
    private View popView;
    private PopupWindow pop;
    private TextView cameraTv, mapTv, cancelTv;

    @Override
    protected View getRootView() {
        return rootView = LayoutInflater.from(this).inflate(R.layout.activity_complain, null);
    }

    @Override
    protected void initView() {
        initRootView();
        initPopView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_complain_return);
        addImageRl = (RelativeLayout) rootView.findViewById(R.id.rl_complain_add_image);
        submitTv = (TextView) rootView.findViewById(R.id.tv_complain_sumit);
    }

    private void initPopView() {
        popView = LayoutInflater.from(this).inflate(R.layout.pop_add_image, null);
        pop = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        cameraTv = (TextView) popView.findViewById(R.id.tv_pop_add_image_camera);
        mapTv = (TextView) popView.findViewById(R.id.tv_pop_add_image_map);
        cancelTv = (TextView) popView.findViewById(R.id.tv_pop_add_image_cancel);
        cameraTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast(ComplainActivity.this, "拍照");
                pop.dismiss();
            }
        });
        mapTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toast(ComplainActivity.this, "图库");
                pop.dismiss();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void setListener() {
        returnRl.setOnClickListener(this);
        addImageRl.setOnClickListener(this);
        submitTv.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_complain_return:
                finish();
                break;
            case R.id.rl_complain_add_image:
                pop.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_complain_sumit:
                Utils.toast(this, "提交投诉");
                break;
            default:
                break;
        }
    }
}
