package usermanage.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjzg.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import config.NetConfig;
import config.PermissionConfig;
import editinfo.view.EditInfoFragment;
import login.bean.UserBean;
import userinfo.view.UserInfoFragment;
import usermanage.bean.UserInfoBean;
import usermanage.presenter.IUserManagePresenter;
import usermanage.presenter.UserManagePresenter;
import utils.UserUtils;
import utils.Utils;
import view.CImageView;
import view.CProgressDialog;

public class UserManageActivity extends AppCompatActivity implements IUserManageActivity, View.OnClickListener {

    private View rootView;
    private RelativeLayout returnRl, previewRl, editRl;
    private TextView previewTv, editTv;
    private CImageView iconIv;
    private CProgressDialog cpd;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private int curPosition = 0, tarPosition = -1;

    private View editPopView;
    private PopupWindow editPop;

    private IUserManagePresenter userManagePresenter = new UserManagePresenter(this);

    private Handler userInfoHandler;

    public UserInfoBean mainUserInfoBean;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        Utils.log(UserManageActivity.this, "修改信息来着");
                        tarPosition = 0;
                        changeFragment();
                        loadData();
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        rootView = LayoutInflater.from(this).inflate(R.layout.activity_user_manage, null);
        setContentView(rootView);
        initView();
        initData();
        setListener();
        loadData();
    }

    private void initView() {
        initRootView();
        initDialogView();
    }

    private void initRootView() {
        returnRl = (RelativeLayout) rootView.findViewById(R.id.rl_user_manage_return);
        previewRl = (RelativeLayout) rootView.findViewById(R.id.rl_user_manage_info);
        previewTv = (TextView) rootView.findViewById(R.id.tv_person_manage_info);
        editRl = (RelativeLayout) rootView.findViewById(R.id.rl_user_manage_edit);
        editTv = (TextView) rootView.findViewById(R.id.tv_user_manage_edit);
        iconIv = (CImageView) rootView.findViewById(R.id.iv_user_manage_icon);
        cpd = Utils.initProgressDialog(UserManageActivity.this, cpd);
    }

    private void initDialogView() {
        editPopView = View.inflate(this, R.layout.pop_dialog_0, null);
        ((TextView) editPopView.findViewById(R.id.tv_pop_dialog_0_content)).setText("是否要修改您的个人信息？");
        ((TextView) editPopView.findViewById(R.id.tv_pop_dialog_0_cancel)).setText("取消");
        ((TextView) editPopView.findViewById(R.id.tv_pop_dialog_0_sure)).setText("确认");
        (editPopView.findViewById(R.id.rl_pop_dialog_0_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPop.dismiss();
            }
        });
        (editPopView.findViewById(R.id.rl_pop_dialog_0_sure)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPop.dismiss();
                changeFragment();
            }
        });
        editPop = new PopupWindow(editPopView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        editPop.setTouchable(true);
        editPop.setFocusable(true);
        editPop.setOutsideTouchable(true);
        editPop.setBackgroundDrawable(new BitmapDrawable());
        editPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = f;
        getWindow().setAttributes(layoutParams);
    }

    private void initData() {
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        userInfoHandler = userInfoFragment.handler;
        EditInfoFragment editInfoFragment = new EditInfoFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(userInfoFragment);
        fragmentList.add(editInfoFragment);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.ll_user_manage_sit, fragmentList.get(curPosition));
        transaction.commit();
    }

    private void setListener() {
        returnRl.setOnClickListener(this);
        previewRl.setOnClickListener(this);
        editRl.setOnClickListener(this);
        iconIv.setOnClickListener(this);
    }

    private void loadData() {
        UserBean userBean = UserUtils.readUserData(UserManageActivity.this);
        if (userBean != null) {
            userManagePresenter.loadUserInfo(NetConfig.userInfoUrl + userBean.getId());
        }
    }

    private void changeFragment() {
        if (tarPosition != curPosition) {
            switch (tarPosition) {
                case 0:
                    previewTv.setTextColor(Color.parseColor("#ff3e50"));
                    editTv.setTextColor(Color.parseColor("#a0a0a0"));
                    break;
                case 1:
                    previewTv.setTextColor(Color.parseColor("#a0a0a0"));
                    editTv.setTextColor(Color.parseColor("#ff3e50"));
                    break;
                default:
                    break;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment curFragment = fragmentList.get(curPosition);
            Fragment tarFragment = fragmentList.get(tarPosition);
            transaction.hide(curFragment);
            if (tarFragment.isAdded()) {
                transaction.show(tarFragment);
            } else {
                transaction.add(R.id.ll_user_manage_sit, tarFragment);
            }
            transaction.commit();
            curPosition = tarPosition;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_user_manage_return:
                finish();
                break;
            case R.id.rl_user_manage_info:
                tarPosition = 0;
                changeFragment();
                break;
            case R.id.rl_user_manage_edit:
                tarPosition = 1;
                if (curPosition != tarPosition) {
                    backgroundAlpha(0.5f);
                    editPop.showAtLocation(rootView, Gravity.CENTER, 0, 0);
                }
                break;
            case R.id.iv_user_manage_icon:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PermissionConfig.GALLERY);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionConfig.GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Utils.log(UserManageActivity.this, "uri=" + uri.toString());
                UserBean userBean = UserUtils.readUserData(UserManageActivity.this);
                if (userBean != null) {
                    userManagePresenter.upLoadIcon(UserManageActivity.this, userBean.getId(), uri);
                }
            }
        }
    }

    @Override
    public void showLoading() {
        cpd.show();
    }

    @Override
    public void hideLoading() {
        cpd.dismiss();
    }

    @Override
    public void showUpLoadIconFailure(String upLoadIconFailure) {
        Utils.toast(UserManageActivity.this, upLoadIconFailure);
    }

    @Override
    public void showUpLoadIconSuccess(String upLoadIconSuccess, Bitmap bitmap) {
        Utils.toast(UserManageActivity.this, upLoadIconSuccess);
        iconIv.setImageBitmap(bitmap);
    }

    @Override
    public void showLoadUserInfoSuccess(UserInfoBean userInfoBean) {
        Uri uri = Uri.parse(userInfoBean.getU_img());
        Picasso.with(UserManageActivity.this).invalidate(uri);
        Picasso.with(UserManageActivity.this).load(userInfoBean.getU_img()).placeholder(iconIv.getDrawable()).into(iconIv);
        userManagePresenter.loadUserSkill(userInfoBean);
    }

    @Override
    public void showLoadUserInfoFailure(String failure) {

    }

    @Override
    public void showUserSkillSuccess(UserInfoBean uib) {
        UserUtils.refreshUserData(UserManageActivity.this, uib);
        mainUserInfoBean = uib;
        Bundle bundle0 = new Bundle();
        bundle0.putSerializable("userInfoBean", uib);
        Message message0 = new Message();
        message0.setData(bundle0);
        userInfoHandler.sendMessage(message0);
    }

    @Override
    public void showUserSkillFailure(String failure) {

    }
}
