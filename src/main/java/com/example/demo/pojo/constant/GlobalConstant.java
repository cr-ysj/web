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
    //忽略的urls
    public static String[] ignoringUrls={doLoginUrl,loginUrl};
    //无权限状态码
    public static int noAuthCode=501;
    //登录失效
    public static int accountExpired=502;
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
    public static long tokenValidityInMilliseconds=10;
    //登录过期时间
    public static long TokenExpireTime=60*30;
    //POST
    public static String httpMethod_POST="POST";

}
