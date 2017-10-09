package personmanage.module;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import personmanage.listener.UpLoadingIconListener;
import utils.Utils;

public class PersonInfoManageModule implements IPersonInfoManageModule {

    private OkHttpClient okHttpClient;
    private Call call;

    public PersonInfoManageModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void upLoadIcon(Context context, String id, Uri uri, final UpLoadingIconListener upLoadingIconListener) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        String picPath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
        cursor.close();
        final Bitmap bitmap = BitmapFactory.decodeFile(picPath);
        String base64 = Utils.Bitmap2StrByBase64(bitmap);
        RequestBody baseBody = new FormBody.Builder().add("base64", base64).build();
        File file = new File(picPath);
        String url = Utils.getIconUpdateUrl(id, file.getName());
        Request request = new Request.Builder().url(url).post(baseBody).build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    if (!TextUtils.isEmpty(json)) {
                        try {
                            JSONObject objBean = new JSONObject(json);
                            int code = objBean.optInt("code");
                            JSONObject objData = objBean.optJSONObject("data");
                            if (objData != null) {
                                String msg = objData.optString("msg");
                                if (!TextUtils.isEmpty(msg)) {
                                    switch (code) {
                                        case 0:
                                            upLoadingIconListener.upLoadingIconFailure(msg);
                                            break;
                                        case 1:
                                            upLoadingIconListener.upLoadingIconSuccess(msg, bitmap);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void cancelTask() {
        if (call != null) {
            call.cancel();
            call = null;
        }
        if (okHttpClient != null)
            okHttpClient = null;
    }
}
