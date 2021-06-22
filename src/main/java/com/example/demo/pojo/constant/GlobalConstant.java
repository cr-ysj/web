package com.example.demo.pojo.constant;

@SuppressWarnings("all")
public class GlobalConstant {
    //盐
    public static final String SALT ="CHENRUI" ;
    //忽略的地址
    public static final String[] ignorings={"/static/images/**","/static/js/**", "/static/css/**","/static/fonts/**"};
    //登录的页面
    public static String loginUrl="/index/login.html";
    //登录的url
    public static String doLoginUrl="/doLogin";
    //忽略的urls
    public static String[] ignoringUrls={loginUrl,doLoginUrl};
    //无权限状态码
    public static int noAuthCode=501;
    //登录失效
    public static int accountExpired=502;
    //成功状态码
    public static int successCode=200;
    //失败状态码
    public static int failCode=500;

    //jwt
    public static String jwt="jwt";
    //jwt的私钥
    public static String key="cr5201314YSJysjcr5201314YSJysjcr5201314YSJysjcr5201314YSJysjcr5201314YSJysjcr5201314YSJysj";

    public static long sec=1000;

    public static long min=60*sec;

    public static  long hour=60*min;

    //有效时间 30分钟
    public static long tokenValidityInMilliseconds=min*10;

    //POST
    public static String httpMethod_POST="POST";

}
