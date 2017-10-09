package personmanage.presenter;


import android.content.Context;
import android.net.Uri;

public interface IPersonInfoManagePresenter {

    void upLoadIcon(Context context, String id, Uri uri);

    void destroy();
}
