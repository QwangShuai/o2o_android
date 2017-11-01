package talkworker.presenter;

/**
 * Created by Administrator on 2017/10/29.
 */

public interface ITalkWorkerPresenter {

    void load(String url);

    void getSkillJson(String url);

    void check(String url);

    void destroy();
}
