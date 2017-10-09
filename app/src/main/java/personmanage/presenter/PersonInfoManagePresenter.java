package personmanage.presenter;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;

import personmanage.listener.UpLoadingIconListener;
import personmanage.module.IPersonInfoManageModule;
import personmanage.module.PersonInfoManageModule;
import personmanage.view.IPersonInfoManageActivity;

public class PersonInfoManagePresenter implements IPersonInfoManagePresenter {

    private IPersonInfoManageActivity iPersonInfoManageActivity;
    private IPersonInfoManageModule iPersonInfoManageModule;
    private Handler mHandler = new Handler();

    public PersonInfoManagePresenter(IPersonInfoManageActivity iPersonInfoManageActivity) {
        this.iPersonInfoManageActivity = iPersonInfoManageActivity;
        iPersonInfoManageModule = new PersonInfoManageModule();
    }

    @Override
    public void upLoadIcon(Context context, String id, Uri uri) {
        iPersonInfoManageActivity.showLoading();
        iPersonInfoManageModule.upLoadIcon(context, id, uri, new UpLoadingIconListener() {
            @Override
            public void upLoadingIconFailure(final String failure) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iPersonInfoManageActivity.showUpLoadIconFailure(failure);
                        iPersonInfoManageActivity.hideLoading();
                    }
                });
            }

            @Override
            public void upLoadingIconSuccess(final String success, final Bitmap bitmap) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iPersonInfoManageActivity.showUpLoadIconSuccess(success, bitmap);
                        iPersonInfoManageActivity.hideLoading();
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (iPersonInfoManageModule != null) {
            iPersonInfoManageModule.cancelTask();
            iPersonInfoManageModule = null;
        }
        if (iPersonInfoManageActivity != null)
            iPersonInfoManageActivity = null;
        if (mHandler != null)
            mHandler = null;
    }
}
