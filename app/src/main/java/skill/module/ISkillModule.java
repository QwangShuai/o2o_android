package skill.module;


import listener.JsonListener;

public interface ISkillModule {

    void load(String url,JsonListener jsonListener);

    void cancelTask();
}
