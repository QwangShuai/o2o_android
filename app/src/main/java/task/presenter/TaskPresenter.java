package task.presenter;


import android.os.Handler;

import listener.JsonListener;
import task.module.ITaskModule;
import task.module.TaskModule;
import task.view.ITaskActivity;

public class TaskPresenter implements ITaskPresenter {

    private ITaskActivity taskActivity;
    private ITaskModule taskModule;
    private Handler handler;

    public TaskPresenter(ITaskActivity taskActivity) {
        this.taskActivity = taskActivity;
        taskModule = new TaskModule();
        handler = new Handler();
    }

    @Override
    public void load(String url) {
        taskModule.load(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskActivity.loadSuccess(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskActivity.loadFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void collect(String url) {
        taskModule.collect(url, new JsonListener() {
            @Override
            public void success(final String json) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskActivity.collectSuccess(json);
                    }
                });
            }

            @Override
            public void failure(final String failure) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        taskActivity.collectFailure(failure);
                    }
                });
            }
        });
    }

    @Override
    public void destroy() {
        if (taskModule != null) {
            taskModule.cancelTask();
            taskModule = null;
        }
        if (taskActivity != null)
            taskActivity = null;
        if (handler != null)
            handler = null;
    }
}
