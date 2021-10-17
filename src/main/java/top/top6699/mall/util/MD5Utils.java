package top.top6699.mall.util;

import org.apache.tomcat.util.codec.binary.Base64;
import top.top6699.mall.common.Constant;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author LongHaiJiang
 * @date 2021/10/17 23:17
 * @description MD5工具
 **/
public class MD5Utils {
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((strValue + Constant.SALT).getBytes(StandardCharsets.UTF_8)));
    }
}
