package talkemployer.presenter;

import android.os.Handler;

import listener.JsonListener;
import talkemployer.module.ITalkEmployerModule;
import talkemployer.module.TalkEmployerModule;
import talkemployer.view.ITalkEmployerActivity;

/**
 * Created by Administrator on 2017/10/26.
 */

public class TalkEmployerPresenter implements ITalkEmployerPresenter {

    private ITalkEmployerActivity talkActivity;
    private ITalkEmployerModule talkEmployerModule;
    private Handler handler;

    public TalkEmployerPresenter(ITalkEmployerActivity talkActivity) {
        this.talkActivity = talkActivity;
        talkEmployerModule = new TalkEmployerModule();
        handler = new Handler();
    }

    @Override
    public void load(String url) {
        talkEmployerModule.load(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        talkActivity.success(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        talkActivity.failure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void loadSkill(String url) {
        talkEmployerModule.loadSkill(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        talkActivity.skillSuccess(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        talkActivity.skillFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (talkEmployerModule != null) {
            talkEmployerModule.cancelTask();
            talkEmployerModule = null;
        }
        if (handler != null) {
            handler = null;
        }
        if (talkActivity != null) {
            talkActivity = null;
        }
    }
}
