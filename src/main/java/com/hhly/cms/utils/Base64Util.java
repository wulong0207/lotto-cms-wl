/**
 * @author scott
 * @version v1.0
 * @date 2018-03-06
 * @company 益彩网络科技
 * @see Base64Util Base64加密解密工具类
 */
package com.hhly.cms.utils;


import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class Base64Util {

    /**
     * @desc 使用Base64加密
     * @date 2018-03-06
     * @author scott
     * @param  sourceStr
     * @return String
     */
    public static String encodeStr(byte[] sourceStr) {
        Base64 base64 = new Base64();
        return base64.encodeAsString(sourceStr);
    }

    /**
     * @desc 使用Base64解密
     * @date 2018-03-06
     * @author scott
     * @param   encodeStr
     * @return byte[]
     */
    public static byte[] decodeStr(String encodeStr) {
        Base64 base64 = new Base64();
        return base64.decode(encodeStr);
    }

}
