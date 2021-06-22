package com.example.demo.config.md5;

import com.example.demo.pojo.constant.GlobalConstant;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;


/**
 * MD5加密策略
 * */
@Component("MD5PasswordEncoder")
public class MD5PasswordEncoder implements PasswordEncoder {

    //加密
    @Override
    public String encode(CharSequence charSequence) {
        if(charSequence.equals("userNotFoundPassword")){
            return null;
        }
        return encode(charSequence);
    }


    //密码匹配
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if(encodedPassword==null){
            return  false;
        }
        return encodedPassword.equals(encode((String)rawPassword));
    }

    //加密的盐
    private static final String SALT = GlobalConstant.SALT;

    //md5加密
    public static String encode(String password) {
        password = password + SALT;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
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
}
