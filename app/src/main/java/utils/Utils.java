package utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import activity.LoginActivity;

//工具类
public class Utils {

    //吐司
    public static void toast(Context c, String s) {
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }

    //日志
    public static void log(Context c, String s) {
        Log.e(c.getClass().getSimpleName(), s);
    }

    //设置ListView高度
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    //设置GridView高度
    public static void setGridViewHeight(GridView gridview, int numColumes) {
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i = i + numColumes) {
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        gridview.setLayoutParams(params);
    }

    //手机号
    public static boolean isPhonenumber(String str) {
        String regularStr = "^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$";
        Pattern p = Pattern.compile(regularStr);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //判断SD卡
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    //MD5加密
    public static String md5Encode(String inStr)
            throws UnsupportedEncodingException {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    //跳到浏览器
    public static void skipBrowser(Context context, String url) {
        if (context != null && !TextUtils.isEmpty(url)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }

    //是否登录
    public static boolean isLogin(Context context) {
        return false;
    }

    //跳到登录
    public static void skipLogin(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

}
