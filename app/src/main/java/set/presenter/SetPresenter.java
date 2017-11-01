package set.presenter;

import android.os.Handler;

import set.listener.QuitListener;
import set.module.ISetModule;
import set.module.SetModule;
import set.view.ISetActivity;

/**
 * Created by Administrator on 2017/10/24.
 */

public class SetPresenter implements ISetPresenter {

    private ISetActivity setActivity;
    private ISetModule setModule;
    private Handler handler;

    public SetPresenter(ISetActivity setActivity) {
        this.setActivity = setActivity;
        setModule = new SetModule();
        handler = new Handler();
    }

    @Override
    public void quit(String id) {
        setModule.quit(id, new QuitListener() {
            @Override
            public void success(final String success) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setActivity.showQuitSuccess(success);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setActivity.showQuitFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (setModule != null) {
            setModule.cancelTask();
            setModule = null;
        }
        if (handler != null) {
            handler = null;
        }
        if (setActivity != null) {
            setActivity = null;
        }
    }
}
