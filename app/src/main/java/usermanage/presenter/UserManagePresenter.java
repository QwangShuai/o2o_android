package usermanage.presenter;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;

import usermanage.bean.UserInfoBean;
import usermanage.listener.UserInfoListener;
import usermanage.listener.UpLoadingIconListener;
import usermanage.listener.UserSkillListener;
import usermanage.module.IUserManageModule;
import usermanage.module.UserManageModule;
import usermanage.view.IUserManageActivity;

public class UserManagePresenter implements IUserManagePresenter {

    private IUserManageActivity iUserManageActivity;
    private IUserManageModule iUserManageModule;
    private Handler mHandler = new Handler();

    public UserManagePresenter(IUserManageActivity iUserManageActivity) {
        this.iUserManageActivity = iUserManageActivity;
        iUserManageModule = new UserManageModule();
    }

    @Override
    public void loadUserInfo(String url) {
        iUserManageModule.loadUserInfo(url, new UserInfoListener() {
            @Override
            public void success(final UserInfoBean userInfoBean) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserManageActivity.showLoadUserInfoSuccess(userInfoBean);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserManageActivity.showLoadUserInfoFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void loadUserSkill(UserInfoBean userInfoBean) {
        iUserManageModule.loadUserSkill(userInfoBean, new UserSkillListener() {
            @Override
            public void success(final UserInfoBean uib) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserManageActivity.showUserSkillSuccess(uib);
                    }
                });
            }

            @Override
            public void failure(String failure) {

            }
        });
    }

    @Override
    public void upLoadIcon(Context context, String id, Uri uri) {
        iUserManageActivity.showLoading();
        iUserManageModule.upLoadIcon(context, id, uri, new UpLoadingIconListener() {
            @Override
            public void upLoadingIconFailure(final String failure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserManageActivity.showUpLoadIconFailure(failure);
                        iUserManageActivity.hideLoading();
                    }
                });
            }

            @Override
            public void upLoadingIconSuccess(final String success, final Bitmap bitmap) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iUserManageActivity.showUpLoadIconSuccess(success, bitmap);
                        iUserManageActivity.hideLoading();
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (iUserManageModule != null) {
            iUserManageModule.cancelTask();
            iUserManageModule = null;
        }
        if (iUserManageActivity != null) {
            iUserManageActivity = null;
        }
        if (mHandler != null) {
            mHandler = null;
        }
    }
}
