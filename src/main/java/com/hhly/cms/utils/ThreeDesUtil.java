/**
 * @author scott
 * @version v1.0
 * @date 2018-03-06
 * @company 益彩网络科技
 * @see ThreeDesUtil 3DES加密解密工具类
 */
package com.hhly.cms.utils;


import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

@Component
public class ThreeDesUtil {

    //算法DESede
    private static final String Algorithm = "DESede";

    //工作模式CBC，填充模式PKCS5Padding
    private static final String Transformation = "DESede/CBC/PKCS5Padding";

    //向量iv,ECB不需要向量iv，CBC需要向量iv
    //CBC工作模式下，同样的密钥，同样的明文，使用不同的向量iv加密 会生成不同的密文
    private static final String Iv = "12345678";


    /**
     * 对字符串数据进行加密
     * @param key 字符串密钥
     * @param src 字符串数据
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encryptMode(String key, String src) throws UnsupportedEncodingException {
        return encryptMode(getKeyByte(key), src.getBytes());
    }

    /**
     * 对字节数据进行加密
     * @param keybyte 字节密钥
     * @param data 字节明文
     * @return Base64编码的密文
     */
    public static String encryptMode(byte[] keybyte, byte[] data) {
        try {
            // 根据给定的字节数组和算法构造一个密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 加密
            IvParameterSpec iv = new IvParameterSpec(Iv.getBytes());
            Cipher cipher = Cipher.getInstance(Transformation);
            cipher.init(Cipher.ENCRYPT_MODE, deskey, iv);
            byte[] resultByte = cipher.doFinal(data);
            return Base64Util.encodeStr(resultByte);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 转化为字节
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] getKeyByte(String key) throws UnsupportedEncodingException {
        // 加密数据必须是24位，不足补0；超出24位则只取前面的24数据
        byte[] data = key.getBytes();
        int len = data.length;
        byte[] newdata = new byte[24];
        System.arraycopy(data, 0, newdata, 0, len > 24 ? 24 : len);
        return newdata;
    }

    /**
     * 解密
     * @param keybyte 密钥
     * @param data
     * @return
     */
    public static String decryptMode(byte[] keybyte, String data) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 解密
            IvParameterSpec iv = new IvParameterSpec(Iv.getBytes());
            Cipher cipher = Cipher.getInstance(Transformation);
            cipher.init(Cipher.DECRYPT_MODE, deskey, iv);
            byte[] resultByte = cipher.doFinal(Base64Util.decodeStr(data));
            return new String(resultByte, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
