package personmanage.view;


import android.graphics.Bitmap;

public interface IPersonInfoManageActivity {

    void showLoading();

    void hideLoading();

    void showUpLoadIconFailure(String upLoadIconFailure);

    void showUpLoadIconSuccess(String upLoadIconSuccess, Bitmap bitmap);
}
