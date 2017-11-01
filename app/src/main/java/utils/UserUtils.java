package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import login.bean.UserBean;
import usermanage.bean.UserInfoBean;

/**
 * Created by Administrator on 2017/10/23.
 */

public class UserUtils {

    //保存用户数据
    public static void saveUserData(Context context, UserBean userBean) {
        if (context != null && userBean != null) {
            SharedPreferences sp = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
            SharedPreferences.Editor et = sp.edit();
            et.putString("id", userBean.getId());
            et.putString("name", userBean.getName());
            et.putString("sex", userBean.getSex());
            et.putString("online", userBean.getOnline());
            et.putString("icon", userBean.getIcon());
            et.putString("token", userBean.getToken());
            et.putString("pass", userBean.getPass());
            et.putString("idcard", userBean.getIdcard());
            et.putString("mobile", userBean.getMobile());
            et.commit();
        }
    }

    //读取用户数据
    public static UserBean readUserData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        UserBean userBean = new UserBean();
        userBean.setId(sp.getString("id", null));
        userBean.setName(sp.getString("name", null));
        userBean.setSex(sp.getString("sex", null));
        userBean.setOnline(sp.getString("online", null));
        userBean.setIcon(sp.getString("icon", null));
        userBean.setToken(sp.getString("token", null));
        userBean.setPass(sp.getString("pass", null));
        userBean.setIdcard(sp.getString("idcard", null));
        userBean.setMobile(sp.getString("mobile", null));
        return userBean;
    }

    //用户登录状态
    public static boolean isUserLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(sp.getString("id", null))) {
            return true;
        }
        return false;
    }

    //清除用户数据
    public static void clearUserData(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.remove("id");
        et.remove("name");
        et.remove("sex");
        et.remove("online");
        et.remove("icon");
        et.remove("token");
        et.remove("pass");
        et.remove("idcard");
        et.remove("mobile");
        et.commit();
    }

    //刷新用户数据
    public static void refreshUserData(Context context, UserInfoBean userInfoBean) {
        UserBean userBean = UserUtils.readUserData(context);
        userBean.setIcon(userInfoBean.getU_img());
        userBean.setName(userInfoBean.getU_true_name());
        userBean.setOnline(userInfoBean.getU_online());
        userBean.setSex(userInfoBean.getU_sex());
        userBean.setIdcard(userInfoBean.getU_idcard());
        saveUserData(context, userBean);
    }
}
