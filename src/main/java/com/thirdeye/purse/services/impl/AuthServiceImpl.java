package com.thirdeye.purse.services.impl;

import java.sql.Timestamp;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thirdeye.purse.dtos.UserInfoDto;
import com.thirdeye.purse.pojos.LoginResponsePayload;
import com.thirdeye.purse.pojos.Token;
import com.thirdeye.purse.security.AESEncryption;
import com.thirdeye.purse.security.TokenGeneraterAndParser;
import com.thirdeye.purse.services.AuthService;
import com.thirdeye.purse.services.UserInfoService;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	TokenGeneraterAndParser tokenGeneraterAndParser;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Override
	public LoginResponsePayload loginUser(UserInfoDto userInfoDto) throws Exception {
		String email = userInfoDto.getEmail();
		String password = userInfoDto.getPassword();
		if (!userInfoService.isUserExists(email, password)) {
			throw new Exception("User does not exist");
		}
		userInfoDto = userInfoService.getUser(email, password);
		if (!userInfoDto.getIsActive().equals(1)) {
			throw new Exception("Inactive User");
		}
		String token = tokenGeneraterAndParser.tokenGenerater(userInfoDto.getId(), userInfoDto.getName(), new Timestamp(System.currentTimeMillis()), userInfoDto.getIsAdmin());
        return new LoginResponsePayload(userInfoDto.getName(),token,userInfoDto.getIsAdmin(),userInfoDto.getId());
	}
}
