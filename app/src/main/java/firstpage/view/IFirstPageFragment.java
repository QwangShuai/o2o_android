package firstpage.view;


public interface IFirstPageFragment {

    void showLoading();

    void showHotJson(String hotJson);

    void showComJson(String comJson);

    void showSaveHotJsonSuccess();

    void showSaveComJsonSuccess();

    void getLocateCityId(String cityId);
}
