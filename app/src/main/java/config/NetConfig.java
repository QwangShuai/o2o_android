package config;

public interface NetConfig {

    //工种
    String skillUrl = "http://api.gangjianwang.com/Skills/index";
    //工人
    String workerUrl = "http://api.gangjianwang.com/Users/getUsers";
    //收藏的工人
    String collectWorkerUrl = "http://api.gangjianwang.com/Users/favorateUsers";
    //收藏的任务
    String collectTaskUrl = "http://api.gangjianwang.com/Users/favorateTasks";
    //添加收藏
    String addCollectUrl = "http://api.gangjianwang.com/Users/favorateAdd";
    //取消收藏
    String delCollectUrl = "http://api.gangjianwang.com/Users/favorateDel";
    //修改位置
    String changePositionUrl = "http://api.gangjianwang.com/Users/updatePosition";
    //用户详细信息
    String userInfoUrl = "http://api.gangjianwang.com/Users/usersInfo?u_id=";
    //他人评价
    String otherEvaluateUrl = "http://api.gangjianwang.com/Users/otherCommentUser";
    //评价他人
    String evaluateOtherUrl = "http://api.gangjianwang.com/Users/userCommentOther";
    //用户余额
    String userFundUrl = "http://api.gangjianwang.com/Users/usersFunds";
    //红包、代金券
    String redBagUrl = "http://api.gangjianwang.com/Bouns/index";
    //热门城市
    String hotCityUrl = "http://api.gangjianwang.com/Regions/index?action=hot";
    //城市列表
    String comCityUrl = "http://api.gangjianwang.com/Regions/index?action=letter";
    //订单
    String orderUrl = "http://api.gangjianwang.com/Orders/index";

    String setPwdUrl = "http://api.gangjianwang.com/Users/setPassword";
    String editPwdUrl = "http://api.gangjianwang.com/Users/passwordEdit";
    String fgtPwdCodeUrl = "http://api.gangjianwang.com/Users/passwordEdit?u_mobile=";
    String fgtPwdUrl = "http://api.gangjianwang.com/Users/passwordEdit";
    String provePhoneUrl = "http://api.gangjianwang.com/Users/passwordEdit";
    String proveIdcardUrl = "http://api.gangjianwang.com/Users/passwordEdit";
    String proveOriPwdUrl = "http://api.gangjianwang.com/Users/passwordEdit";
    String userInfoEditUrl = "http://api.gangjianwang.com/Users/usersInfoEdit";
    String taskBaseUrl = "http://api.gangjianwang.com/Tasks/index";
    String codeUrl = "http://api.gangjianwang.com/Users/sendVerifyCode?phone_number=";
    String loginUrl = "http://api.gangjianwang.com/Users/login";
    String payWayUrl = "http://api.gangjianwang.com/Payments/index";
    String iconUpdateUrl = "http://api.gangjianwang.com/Users/usersHeadEidt";
    String applyWithdrawUrl = "http://api.gangjianwang.com/Users/applyWithdraw";
    String useCplIs = "http://api.gangjianwang.com/Users/complaintsType?ct_type=";
    String selectAddressBaseUrl = "http://api.gangjianwang.com/Regions/index?action=list&r_pid=";
    String usersFundsLogUrl = "http://api_zy.gangjianwang.com/Users/getUsersFundsLog";
    String favorateAddUrl = "http://api.gangjianwang.com/Users/favorateAdd";
    String favorateDelUrl = "http://api.gangjianwang.com/Users/favorateDel";
    String subTotalUrl = "http://api.gangjianwang.com/Tools/subTotal";
    String publishUrl = "http://api.gangjianwang.com/Tasks/index?action=publish";
}
