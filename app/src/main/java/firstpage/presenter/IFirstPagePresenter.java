package firstpage.presenter;


import android.content.Context;

public interface IFirstPagePresenter {

    void loadHotCity(String hotUrl);

    void loadComCity(String comUrl);

    void saveHotCity(Context context, String userId, String hotJson, String cacheTime);

    void saveComCity(Context context, String userId, String comJson, String cacheTime);

    void getLocateCityId(Context context,String userId,String cityName);

    void destroy();
}
