package com.thirdeye.purse.security;

import java.sql.Timestamp;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.thirdeye.purse.pojos.Token;

@Component
public class TokenGeneraterAndParser {
	
	private static final SecretKey secretKey;
	
	static {
        try {
            secretKey = AESEncryption.generateSecretKey();
        } catch (Exception e) {
            throw new RuntimeException("Error generating AES key: " + e.getMessage(), e);
        }
    }
	
	public String tokenGenerater(Long userId, String username, Timestamp currentTime, Integer hasAdminRights) throws Exception {
        return AESEncryption.encrypt(new Token(userId, username, currentTime, hasAdminRights).toString(), secretKey);
	}

	public Token tokenParser(String token) throws Exception {
        String decryptedToken;
        try {
            decryptedToken = AESEncryption.decrypt(token, secretKey);
        } catch (Exception ex) {
            throw new Exception("Invalid token", ex);
        }
        String cleanedString = decryptedToken.replace("TKN|", "").replace("|END", "");
        String[] parts = cleanedString.split("\\|");
        if (parts.length < 4) {
            throw new Exception("Malformed token");
        }
        Long id = Long.valueOf(parts[0]);
        String name = parts[1];
        Timestamp timestamp = Timestamp.valueOf(parts[2]);
        Integer isAdmin = Integer.valueOf(parts[3]);
        return new Token(id, name, timestamp, isAdmin);
    }
}
