package com.lankerens.gallery.randomgallery.helper;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/*
 *@title RSADecryption
 *@description
 *@author zhus
 *@version 1.0
 *@create 2025-3-6 23:28
 */
public class RSADecryption {
    public static String decrypt(String encryptedData, String base64PrivateKey) throws Exception {
        // 1. 解析 Base64 私钥
        byte[] privateKeyBytes = Base64.getDecoder().decode(base64PrivateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);

        // 2. 设置解密模式
//        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // 3. 解析密文（Base64 解码）
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes);
    }

    public static void main(String[] args) throws Exception {
        // 替换为 Java 生成的私钥
        String privateKey = "";
        // 替换为 C# 端加密后的数据
        String encryptedData = "";
        String decryptedData = decrypt(encryptedData, privateKey);
        System.out.println("Decrypted Data: " + decryptedData);
    }
}

