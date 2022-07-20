package com.plateer.ec1.payment.common;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESUtil {
    private SecretKeySpec secretKey;
    private IvParameterSpec IV;

    public AESUtil(String reqSecretKey, String iv){
        this.secretKey = new SecretKeySpec(reqSecretKey.getBytes(StandardCharsets.UTF_8), "AES");
        this.IV = new IvParameterSpec(iv.getBytes());
    }

    public String AesEncode(String plainText) throws Exception {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, secretKey, IV);
        byte[] encByte = c.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encByte);
    }


}
