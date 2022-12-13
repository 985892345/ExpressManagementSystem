package com.example.express.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * .
 *
 * @author 985892345
 * 2022/12/12 20:27
 */
public class EncryptUtil {
  
  // 加密
  public static String encrypt(String data, String key) {
    try {
      // 创建密码器
      //"算法/模式/补码方式"
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      // 初始化为加密模式的密码器
      cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(key));
      byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
      //此处使用BASE64做转码功能，同时能起到2次加密的作用。
      return Base64.getEncoder().encodeToString(encrypted);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  // 解密
  public static String decrypt(String encData, String key) {
    try {
      // 创建密码器
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      // 初始化为解密模式的密码器
      cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(key));
      //先用base64解密
      byte[] encDataByte = cipher.doFinal(Base64.getDecoder().decode(encData));
      String decData = new String(encDataByte,StandardCharsets.UTF_8);
      return decData;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  private static SecretKeySpec getSecretKeySpec(String key) throws NoSuchAlgorithmException {
    //创建AES的Key生产者
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    // 利用"很随便的密钥"作为随机数初始化生成一个128字节16位的密钥
    kgen.init(128, new SecureRandom(key.getBytes()));
    //SecureRandom是生成安全随机数序列
    SecretKey secretKey = kgen.generateKey();
    byte[] rKey = secretKey.getEncoded();
    return new SecretKeySpec(rKey, "AES");
  }
}
