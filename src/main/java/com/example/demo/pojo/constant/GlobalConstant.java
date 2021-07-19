package com.example.demo.pojo.constant;

@SuppressWarnings("all")
public class GlobalConstant {
    //盐
    public static final String SALT ="CHENRUI" ;
    //忽略的地址
    public static final String[] ignorings={"/static/images/**","/static/js/**", "/static/css/**","/static/fonts/**"};
    //登录的页面
    public static String loginUrl="/login";
    //登录的url
    public static String doLoginUrl="/doLogin";

    //websocket
    public static String websocketService="/ws/WebSocketServer";
    //注册
    public static String doRegister="/doRegister";
    //忽略的urls
    public static String[] ignoringUrls={doLoginUrl,loginUrl,doRegister,websocketService};
    //无权限状态码
    public static int noAuthCode=501;
    //登录失效
    public static int accountExpired=502;
    //未登录
    public static int noLogin=503;
    //成功状态码
    public static int successCode=200;
    //重复登录状态码
    public static int repeatLoginCode=201;
    //失败状态码
    public static int failCode=500;

    //jwt
    public static String jwt="jwt";

    //auths
    public static String auths="auths";

    //jwt的私钥
    public static String key="cr5201314YSJysjcr5201314YSJysjcr5201314YSJysjcr5201314YSJysjcr5201314YSJysjcr5201314YSJysj";

    public static long sec=1000;

    public static long min=60*sec;

    public static  long hour=60*min;
    //令牌刷新时间
    public static long tokenValidityInMilliseconds=30*min;
    //登录过期时间
    public static long TokenExpireTime=60*min;
    //POST
    public static String httpMethod_POST="POST";

    //存入redis的资源列表
    public static String authAttributes="authAttributes";

    public static final String optionType_View="查看";
    public static final String optionType_Save="新增";
    public static final String optionType_Del="删除";
    public static final String optionType_Edit="编辑";


    public static final String optionModel_Resource="资源";
    public static final String optionModel_Auth="权限";
    public static final String optionModel_User="用户";
    public static final String optionModel_Role="角色";


    public static final Boolean isOptionError=true;

}
