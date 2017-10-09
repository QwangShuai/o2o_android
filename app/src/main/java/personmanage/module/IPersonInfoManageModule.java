package personmanage.module;


import android.content.Context;
import android.net.Uri;

import personmanage.listener.UpLoadingIconListener;

public interface IPersonInfoManageModule {

    void upLoadIcon(Context context, String id, Uri uri, UpLoadingIconListener upLoadingIconListener);

    void cancelTask();
}
