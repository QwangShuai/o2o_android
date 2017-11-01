package editinfo.presenter;


import android.os.Handler;

import java.util.List;

import bean.SkillBean;
import editinfo.listener.AddSkillListener;
import editinfo.listener.SubmitListener;
import editinfo.module.EditInfoModule;
import editinfo.module.IEditInfoModule;
import editinfo.view.IEditInfoFragment;
import usermanage.bean.UserInfoBean;

public class EditInfoPresenter implements IEditInfoPresenter {

    private IEditInfoFragment editInfoFragment;
    private IEditInfoModule editInfoModule;
    private Handler handler;

    public EditInfoPresenter(IEditInfoFragment editInfoFragment) {
        this.editInfoFragment = editInfoFragment;
        editInfoModule = new EditInfoModule();
        handler = new Handler();
    }

    @Override
    public void load(String url) {
        editInfoFragment.showLoading();
        editInfoModule.load(url, new AddSkillListener() {
            @Override
            public void success(final List<SkillBean> skillBeanList) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        editInfoFragment.showAddSkillSuccess(skillBeanList);
                        editInfoFragment.hideLoading();
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        editInfoFragment.showAddSkillFailure(failure);
                        editInfoFragment.hideLoading();
                    }
                });
            }
        });
    }

    @Override
    public void submit(UserInfoBean userInfoBean) {
        editInfoFragment.showLoading();
        editInfoModule.submit(userInfoBean, new SubmitListener() {
            @Override
            public void success(final String success) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        editInfoFragment.showSubmitSuccess(success);
                        editInfoFragment.hideLoading();
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        editInfoFragment.showSubmitFailure(failure);
                        editInfoFragment.hideLoading();
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (editInfoModule != null) {
            editInfoModule.cancelTask();
            editInfoModule = null;
        }
        if (handler != null) {
            handler = null;
        }
        if (editInfoFragment != null) {
            editInfoFragment = null;
        }
    }
}
