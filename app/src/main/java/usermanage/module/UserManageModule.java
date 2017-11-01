package usermanage.module;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import config.VarConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import usermanage.bean.UserInfoBean;
import usermanage.listener.UpLoadingIconListener;
import usermanage.listener.UserInfoListener;
import usermanage.listener.UserSkillListener;
import utils.DataUtils;
import utils.Utils;

public class UserManageModule implements IUserManageModule {

    private OkHttpClient okHttpClient;
    private Call call, loadPersonInfoCall, userSkillCall;

    public UserManageModule() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void loadUserInfo(String url, final UserInfoListener userInfoListener) {
        Request request = new Request.Builder().url(url).get().build();
        loadPersonInfoCall = okHttpClient.newCall(request);
        loadPersonInfoCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                userInfoListener.failure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    if (!TextUtils.isEmpty(result)) {
                        try {
                            JSONObject objBean = new JSONObject(result);
                            if (objBean.optInt("code") == 1) {
                                JSONObject objData = objBean.optJSONObject("data");
                                if (objData != null) {
                                    JSONObject obj = objData.optJSONObject("data");
                                    if (obj != null) {
                                        UserInfoBean userInfoBean = new UserInfoBean();
                                        userInfoBean.setU_id(obj.optString("u_id"));
                                        userInfoBean.setU_name(obj.optString("u_name"));
                                        userInfoBean.setU_mobile(obj.optString("u_mobile"));
                                        userInfoBean.setU_phone(obj.optString("u_phone"));
                                        userInfoBean.setU_fax(obj.optString("u_fax"));
                                        userInfoBean.setU_sex(obj.optString("u_sex"));
                                        userInfoBean.setU_in_time(obj.optString("u_in_time"));
                                        userInfoBean.setU_online(obj.optString("u_online"));
                                        userInfoBean.setU_status(obj.optString("u_status"));
                                        userInfoBean.setU_type(obj.optString("u_type"));
                                        userInfoBean.setU_task_status(obj.optString("u_task_status"));
                                        userInfoBean.setU_skills(obj.optString("u_skills"));
                                        userInfoBean.setU_start(obj.optString("u_start"));
                                        userInfoBean.setU_credit(obj.optString("u_credit"));
                                        userInfoBean.setU_top(obj.optString("u_top"));
                                        userInfoBean.setU_recommend(obj.optString("u_recommend"));
                                        userInfoBean.setU_jobs_num(obj.optString("u_jobs_num"));
                                        userInfoBean.setU_worked_num(obj.optString("u_worked_num"));
                                        userInfoBean.setU_high_opinions(obj.optString("u_high_opinions"));
                                        userInfoBean.setU_low_opinions(obj.optString("u_low_opinions"));
                                        userInfoBean.setU_middle_opinions(obj.optString("u_middle_opinions"));
                                        userInfoBean.setU_dissensions(obj.optString("u_dissensions"));
                                        userInfoBean.setU_true_name(obj.optString("u_true_name"));
                                        userInfoBean.setU_idcard(obj.optString("u_idcard"));
                                        userInfoBean.setU_info(obj.optString("u_info"));
                                        JSONObject objArea = obj.optJSONObject("area");
                                        if (objArea != null) {
                                            userInfoBean.setArea_uei_province(objArea.optString("uei_province"));
                                            userInfoBean.setArea_uei_city(objArea.optString("uei_city"));
                                            userInfoBean.setArea_uei_area(objArea.optString("uei_area"));
                                            userInfoBean.setArea_uei_address(objArea.optString("uei_address"));
                                            userInfoBean.setArea_user_area_name(objArea.optString("user_area_name"));
                                        }
                                        userInfoBean.setU_img(obj.optString("u_img"));
                                        userInfoListener.success(userInfoBean);
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
    public void loadUserSkill(final UserInfoBean userInfoBean, final UserSkillListener userSkillListener) {
        if (userInfoBean != null && userSkillListener != null) {
            if (userInfoBean.getU_skills().equals("0")) {
                userSkillListener.success(userInfoBean);
            } else {
                String url = Utils.getUserSkillUrl(userInfoBean);
                if (!TextUtils.isEmpty(url)) {
                    Request request = new Request.Builder().url(url).get().build();
                    userSkillCall = okHttpClient.newCall(request);
                    userSkillCall.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            userSkillListener.failure(VarConfig.noNet);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String json = response.body().string();
                                Log.e("UserManageModule", "json=" + json);
                                if (!TextUtils.isEmpty(json)) {
                                    userInfoBean.setSkillBeanList(DataUtils.getSkillBeanList(json));
                                    for (int i = 0; i < userInfoBean.getSkillBeanList().size(); i++) {
                                        userInfoBean.getSkillBeanList().get(i).setCheck(true);
                                    }
                                    userSkillListener.success(userInfoBean);
                                } else {
                                    userSkillListener.failure("json == null");
                                }
                            } else {
                                userSkillListener.failure("response fail");
                            }
                        }
                    });
                }
            }
        }
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
        if (loadPersonInfoCall != null) {
            loadPersonInfoCall.cancel();
            loadPersonInfoCall = null;
        }
        if (userSkillCall != null) {
            userSkillCall.cancel();
            userSkillCall = null;
        }
        if (okHttpClient != null) {
            okHttpClient = null;
        }
    }
}
