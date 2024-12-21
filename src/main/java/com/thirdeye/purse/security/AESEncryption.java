package com.thirdeye.purse.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class AESEncryption {

    private static final String ALGORITHM = "AES";
    private static SecretKey key = null;

    public static SecretKey generateSecretKey() throws Exception {
    	if(key == null)
    	{
	        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
	        keyGen.init(128);
	        key = keyGen.generateKey();
    	}
        return key;
    }

    public static String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}
