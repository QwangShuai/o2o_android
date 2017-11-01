package skill.presenter;

import android.os.Handler;

import listener.JsonListener;
import skill.module.ISkillModule;
import skill.module.SkillModule;
import skill.view.ISkillActivity;

public class SkillPresenter implements ISkillPresenter {

    private ISkillActivity skillActivity;
    private ISkillModule skillModule;
    private Handler handler;

    public SkillPresenter(ISkillActivity skillActivity) {
        this.skillActivity = skillActivity;
        skillModule = new SkillModule();
        handler = new Handler();
    }

    @Override
    public void load(String url) {
        skillModule.load(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        skillActivity.skillSuccess(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        skillActivity.skillFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (skillModule != null) {
            skillModule.cancelTask();
            skillModule = null;
        }
        if (handler != null) {
            handler = null;
        }
        if (skillActivity != null) {
            skillActivity = null;
        }
    }
}
