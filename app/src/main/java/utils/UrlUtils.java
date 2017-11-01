package utils;

import android.content.Context;

import config.NetConfig;

/**
 * Created by Administrator on 2017/10/23.
 */

public class UrlUtils {

    public static String getEmployerManageUrl(Context context, int state) {
        String url = null;
        switch (state) {
            case 0:
                url = NetConfig.taskBaseUrl + "?t_author=" + (UserUtils.readUserData(context)).getId() + "&t_storage=0";
                break;
            case 1:
                url = NetConfig.taskBaseUrl + "?t_author=" + (UserUtils.readUserData(context)).getId() + "&t_storage=0&t_status=" + 0;
                break;
            case 2:
                url = NetConfig.taskBaseUrl + "?t_author=" + (UserUtils.readUserData(context)).getId() + "&t_storage=0&t_status=" + 1;
                break;
            case 3:
                url = NetConfig.taskBaseUrl + "?t_author=" + (UserUtils.readUserData(context)).getId() + "&t_storage=0&t_status=" + 2;
                break;
            case 4:
                url = NetConfig.taskBaseUrl + "?t_author=" + (UserUtils.readUserData(context)).getId() + "&t_storage=0&t_status=" + 3;
                break;
        }
        return url;
    }

    public static String getUsersFundsLogUrl(Context context, int logState) {
        String url = null;
        switch (logState) {
            case 0:
//                url = NetConfig.usersFundsLogUrl + "?u_id=" + UserUtils.readUserData(context).getId() + "&category=all";
                url = NetConfig.usersFundsLogUrl + "?u_id=" + 12 + "&category=all";
                break;
            case 1:
//                url = NetConfig.usersFundsLogUrl + "?u_id=" + UserUtils.readUserData(context).getId() + "&category=withdraw";
                url = NetConfig.usersFundsLogUrl + "?u_id=" + 12 + "&category=withdraw";
                break;
            case 2:
//                url = NetConfig.usersFundsLogUrl + "?u_id=" + UserUtils.readUserData(context).getId() + "&category=recharge";
                url = NetConfig.usersFundsLogUrl + "?u_id=" + 12 + "&category=recharge";
                break;
        }
        return url;
    }

}
