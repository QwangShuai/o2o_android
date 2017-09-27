package config;

//网络配置
public interface NetConfig {
    //城市
    String baseCityUrl = "http://api.gangjianwang.com/Regions/index";
    String hotCityUrl = "?action=hot";
    String letterCityUrl = "?action=letter";
    //工种
    String kindUrl = "http://api.gangjianwang.com/Skills/index";
    //工人信息
    String workerUrl = "http://api.gangjianwang.com/Users/getUsersBySkills";
    //工作
    String jobBaseUrl = "http://api.gangjianwang.com/Tasks/index";
    String jobListUrl = "?action=list";
    String jobDetailUrl = "?action=info&t_id=";

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
}
