package config;

//网络配置
public interface NetConfig {
    //城市
    String baseCityUrl = "http://api.gangjianwang.com/Regions/index";
    String hotCityUrl = "?action=hot";
    String letterCityUrl = "?action=letter";

    //工人种类
    String workerKindUrl = "http://api.gangjianwang.com/Skills/index";

    //工人信息
    String workerUrl = "http://api.gangjianwang.com/Users/getUsersBySkills";

    //工作信息
    String jobInfoUrl = "http://api.gangjianwang.com/Tasks/index";

    //获取验证码
    String codeUrl = "http://api.gangjianwang.com/Users/sendVerifyCode?phone_number=";
    //登录
    String loginUrl = "http://api.gangjianwang.com/Users/login";
    //支付方式
    String payWayUrl = "http://api.gangjianwang.com/Payments/index";
    //收藏的工人
    String collectWorkerUrl = "http://api.gangjianwang.com/Users/favorateUsers?u_id=";
    //收藏的工作
    String collectJobUrl = "http://api.gangjianwang.com/Users/favorateTasks?u_id=";
    //用户余额
    String userFundUrl = "http://api.gangjianwang.com/Users/usersFunds?u_id=";

    //明细
    String accountDetailUrl = "http://api.gangjianwang.com/Users/getUsersFundsLog";
    //用户详细信息
    String personDetailUrl = "http://api.gangjianwang.com/Users/usersInfo?u_id=";

    //测试用（城市列表）
    public static final String testUrl = "http://www.gangjianwang.com/shop/index.php?act=index&op=getWapAreaCities";
    //服务条款
    public static final String sevClsUrl = "http://zy.persistence.net.cn/";

    //时间戳
    String timeUrl = "http://api.gangjianwang.com/Tools/index";

    //头像上传
    String iconUpdateUrl = "http://api_zy.gangjianwang.com/Users/usersHeadEidt";

    //红包
    String redPacketUrl = "http://api.gangjianwang.com/Bouns/index?action=list&bt_id=2";
    //代金券
    String voucherUrl = "http://api.gangjianwang.com/Bouns/index?action=list&bt_id=1";

    /**
     * 投诉
     */
    //用户投诉问题 ct_type = 0,1,2; 0:平台;1:雇主;2:工人;
    String useCplIs = "http://api.gangjianwang.com/Users/complaintsType?ct_type=";

    /**
     * 选择区域（三级联动）
     */
    String selectAddressBaseUrl = "http://api.gangjianwang.com/Regions/index?action=list&r_pid=";
}
